package br.com.core.jdbc.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class ConnectionPool implements Runnable {

	private Logger log = Logger.getLogger(getClass());

	private String driver, url, username, password;
	private int maxConnections;
	private boolean waitIfBusy;
	private List<Connection> availableConnections, busyConnections;
	private boolean connectionPending = false;

	public ConnectionPool(String driver, String url, String username, String password, int initialConnections, int maxConnections, boolean waitIfBusy) {

		if (driver == null) {
			throw new IllegalArgumentException("The given driver is null.");
		}
		if (url == null) {
			throw new IllegalArgumentException("The given url is null.");
		}
		if (username == null || password == null) {
			throw new IllegalArgumentException("The username or password is null.");
		}
		if (maxConnections <= 0) {
			throw new IllegalArgumentException("The maximum number of connections must be greater than 0.");
		}

		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxConnections = maxConnections;
		this.waitIfBusy = waitIfBusy;
		if (initialConnections > maxConnections) {
			initialConnections = maxConnections;
		}
		availableConnections = Collections.synchronizedList(new ArrayList<Connection>(initialConnections));
		busyConnections = Collections.synchronizedList(new ArrayList<Connection>());
		for (int i = 0; i < initialConnections; i++) {
			availableConnections.add(makeNewConnection());
		}

		log.debug("available connections: " + availableConnections.size());
	}

	public synchronized Connection getConnection()  {
		if (!availableConnections.isEmpty()) {
			int lastIndex = availableConnections.size() - 1;
			Connection existingConnection = (Connection) availableConnections.get(lastIndex);
			availableConnections.remove(lastIndex);

			log.debug("available connections: " + availableConnections.size());

			// If connection on available list is closed (e.g. it timed out),
			// then remove it from available list and repeat the process of
			// obtaining a connection. Also wake up threads that were waiting
			// for a connection because maxConnection limit was reached.
			try{
				if (existingConnection.isClosed()) {
					notifyAll(); // Freed up a spot for anybody waiting
					return (getConnection());
				} else {
					busyConnections.add(existingConnection);
					return (existingConnection);
				}
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
		} else {
			// Three possible cases:
			// 1) we haven't reached maxConnections limit. So establish one in
			// the background if there isn't already one pending, then wait for
			// the next available connection (whether or not it was the newly
			// established one).
			// 2) we have reached maxConnections limit and waitIfBusy flag is
			// false.
			// Throw Runtime SQLException in such a case.
			// 3) we have reached maxConnections limit and waitIfBusy flag is
			// true.
			// Then do the same thing as in second part of step 1: wait for next
			// available connection.
			if (((getNumberOfAvailableConnections() + getNumberOfBusyConnections()) < maxConnections) && !connectionPending) {
				makeBackgroundConnection();
			} else if (!waitIfBusy) {
				throw new RuntimeException(new SQLException("Connection limit reached"));
			}

			// Wait for either a new connection to be established (if you called
			// makeBackgroundConnection) or for an existing connection to be
			// freed up.
			try {
				wait();
			} catch (InterruptedException ie) {
			}

			log.debug("available connections: " + availableConnections.size());

			// Someone freed up a connection, so try again.
			return (getConnection());
		}
	}

	// we can't just make a new connection in the foreground when none are
	// available, since this can take several seconds with a slow network
	// connection. Instead, start a thread that establishes a new connection,
	// then wait. You get woken up either when the new connection is established
	// or if someone finishes with an existing connection.
	private void makeBackgroundConnection() {
		connectionPending = true;
		try {
			Thread connectThread = new Thread(this);
			connectThread.start();
		} catch (OutOfMemoryError oome) {
			// Give up on new connection
		}
	}

	public void run() {
		try {
			Connection connection = makeNewConnection();
			synchronized (this) {
				availableConnections.add(connection);
				connectionPending = false;
				notifyAll();
			}
		} catch (Exception e) { // SQLException or OutOfMemory
			// Give up on new connection and wait for existing one to free up.
		}
	}

	// This explicitly makes a new connection. Called in the foreground when
	// initializing the ConnectionPoolImpl, and called in the background when
	// running.
	private Connection makeNewConnection()  {
		try {
			// Load database driver if not already loaded
			Class.forName(driver);
			// Establish network connection to database
			Connection connection = DriverManager.getConnection(url, username, password);
			return (connection);
		} catch (ClassNotFoundException cnfe) {
			// Simplify try/catch blocks of people using this by throwing only
			// one exception type.
			throw new RuntimeException( new SQLException("Can't find class for driver: " + driver));
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public synchronized void releaseConnection(Connection connection)  {
		busyConnections.remove(connection);
		availableConnections.add(connection);
		// Wake up threads that are waiting for a connection
		notifyAll();
	}

	// Closes all the connections. we need to make sure that no connections are
	// in use before calling.  
	public synchronized void closeAllConnections() {
		closeConnections(availableConnections);
		availableConnections = Collections.synchronizedList(new ArrayList<Connection>());
		closeConnections(busyConnections);
		busyConnections = Collections.synchronizedList(new ArrayList<Connection>());
	}

	private void closeConnections(List<Connection> connections) {
		try {
			for (Connection connection : connections) {
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch (SQLException sqle) {
			// Ignore errors; garbage collect anyhow
		}
	}

	public synchronized int getNumberOfAvailableConnections() {
		return availableConnections.size();
	}

	public synchronized int getNumberOfBusyConnections() {
		return busyConnections.size();
	}

	@Override
	public synchronized String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Class: ").append(this.getClass().getName()).append("\n");
		result.append(" available: ").append(availableConnections.size())
		.append("\n");
		result.append(" busy: ").append(busyConnections.size()).append("\n");
		result.append(" max: ").append(maxConnections).append("\n");
		return result.toString();
	}

}
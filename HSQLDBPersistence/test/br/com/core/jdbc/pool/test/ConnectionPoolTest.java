package br.com.core.jdbc.pool.test;
 
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.utils.PropertiesReader;
 
/**
 * This class contains unit tests for connection pool.
 * 
 * @author svivaramneni
 * @since company 1.0.0
 */
public class ConnectionPoolTest {
 
    private Logger log = Logger.getLogger(getClass());
    
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "database.driverClassName";
	private static final String PROPERTY_NAME_DATABASE_URL = "database.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "database.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "database.password";
	
	private static Properties properties = PropertiesReader.lerProperties("database.properties");
	
    private static final String DRIVER = properties.getProperty(PROPERTY_NAME_DATABASE_DRIVER);
    private static final String URL = properties.getProperty(PROPERTY_NAME_DATABASE_URL);
    private static final String USER_NAME = properties.getProperty(PROPERTY_NAME_DATABASE_USERNAME);
    private static final String PASSWORD = properties.getProperty(PROPERTY_NAME_DATABASE_PASSWORD);
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
 
    private ConnectionPool pool = null;
 
    @Before
    public void setUp() throws SQLException {
        pool = new ConnectionPool(DRIVER, URL, USER_NAME, PASSWORD,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
        log.debug("Connection pool created" + pool);
    }
 
    @After
    public void tearDown() {
        pool = null;
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullDriver() throws SQLException {
        pool = new ConnectionPool(null, URL, USER_NAME, PASSWORD,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = SQLException.class)
    public void testDriverNotFound() throws SQLException {
        pool = new ConnectionPool("some.funky.driver", URL,USER_NAME, PASSWORD, INITIAL_CONNECTIONS, MAX_CONNECTIONS,WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullUrl() throws SQLException {
        pool = new ConnectionPool(DRIVER, null, USER_NAME, PASSWORD,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullUserName() throws SQLException {
        pool = new ConnectionPool(DRIVER, URL, null, PASSWORD,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testNullPassword() throws SQLException {
        pool = new ConnectionPool(DRIVER, URL, USER_NAME, null,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }
 
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaxConnections() throws SQLException {
        pool = new ConnectionPool(DRIVER, URL, USER_NAME, PASSWORD,INITIAL_CONNECTIONS, -2, WAIT_IF_BUSY);
    }
 
    @Test
    public void testPoolInitialization() throws SQLException {
        assertTrue("unexpected size of pool",INITIAL_CONNECTIONS == pool.getNumberOfAvailableConnections());
    }
 
    @Test
    public void testGetConnection() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        assertTrue(pool.getNumberOfBusyConnections() == 1);
    }
 
    @Test
    public void testReleaseConnection() throws SQLException {
        Connection conn = pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        pool.releaseConnection(conn);
        assertTrue(pool.getNumberOfBusyConnections() == 0);
    }
 
    @Test
    public void testCloseAllConnections() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        pool.closeAllConnections();
        assertTrue(pool.getNumberOfBusyConnections() == 0);
        assertTrue(pool.getNumberOfAvailableConnections() == 0);
    }
}
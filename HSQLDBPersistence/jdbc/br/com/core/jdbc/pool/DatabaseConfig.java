package br.com.core.jdbc.pool;

import java.util.Properties;

import org.apache.log4j.Logger;

import br.com.core.jdbc.utils.PropertiesReader;

public class DatabaseConfig {

	private static ConnectionPool pool;
	private static Logger log = Logger.getLogger(DatabaseConfig.class);
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
	
	private DatabaseConfig(){}

	public static ConnectionPool getPool() {

		if (pool == null) {
			 pool = new ConnectionPool(DRIVER, URL, USER_NAME, PASSWORD,INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
	        log.debug("Singleton Connection pool created" + pool);
		}
		return pool;

	}
	
}


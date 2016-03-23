package br.com.core.jdbc.pool.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;

public class DatabaseConfigTest {

	@Test
	public void test() {
		ConnectionPool pool = DatabaseConfig.getPool();
		assertTrue("unexpected size of pool",2 == pool.getNumberOfAvailableConnections());
		pool.getConnection();
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        assertTrue(pool.getNumberOfBusyConnections() == 1);
	}

}

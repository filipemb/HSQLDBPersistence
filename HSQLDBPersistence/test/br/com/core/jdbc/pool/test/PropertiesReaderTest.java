package br.com.core.jdbc.pool.test;

import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Test;

import br.com.core.jdbc.utils.PropertiesReader;

public class PropertiesReaderTest {

	@Test
	public void test() {
		Properties properties = PropertiesReader.lerProperties("database.properties");
		for(Entry<Object, Object> entry : properties.entrySet()){
			System.out.println(entry.getKey().toString() +" = "+entry.getValue());
		}
	}

}

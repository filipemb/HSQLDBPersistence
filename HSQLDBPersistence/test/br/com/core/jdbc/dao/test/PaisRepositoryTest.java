package br.com.core.jdbc.dao.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.jdbc.dao.publico.PaisRepository;
import br.com.core.jdbc.dao.publico.PaisTable;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaisRepositoryTest {

	private static Connection conn;
	private static PaisRepository paisRepository;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		paisRepository = new PaisRepository(conn);
	}
	
	@AfterClass
	public static void liberaConexao(){
		ConnectionPool pool = DatabaseConfig.getPool();
		pool.releaseConnection(conn);
		pool.closeAllConnections();
	}
	
	@Before
	public void logPoolStatus(){
		System.out.println(DatabaseConfig.getPool().toString());
	}
	
	@Test
	public void test1Save() {
		PaisTable paisTable = new PaisTable();
		paisTable.setNome("NOVO");
		paisTable.setPrimeiro(true);
		paisTable = paisRepository.save(paisTable);
		assertTrue(paisTable.getId()!=null);
	}

	@Test
	public void test2Update() {
		PaisTable paisTable = paisRepository.findByNome("NOVO");
		paisTable.setPrimeiro(false);
		paisRepository.save(paisTable);
		PaisTable paisTableModificado = paisRepository.findOne(paisTable.getId());
		assertEquals(false, paisTableModificado.getPrimeiro());
	}

	@Test
	public void test3findOne() {
		PaisTable paisTable = paisRepository.findByNome("NOVO");
		PaisTable paisTableBusca = paisRepository.findOne(paisTable.getId());
		assertEquals(paisTableBusca.getId(), paisTable.getId());
		assertEquals(paisTableBusca.getPrimeiro(), paisTable.getPrimeiro());
		assertEquals(paisTableBusca.getNome(), paisTable.getNome());
	}

	@Test
	public void test4findByNome() {
		PaisTable paisTable = paisRepository.findByNome("NOVO");
		PaisTable paisTableBusca = paisRepository.findOne(paisTable.getId());
		assertEquals(paisTableBusca.getId(), paisTable.getId());
		assertEquals(paisTableBusca.getPrimeiro(), paisTable.getPrimeiro());
		assertEquals(paisTableBusca.getNome(), paisTable.getNome());
	}
	
	@Test
	public void test6findAll() {
		List<PaisTable> lista = paisRepository.findAll();
		System.out.println("Lista tem o tamanho:"+lista.size());
		assertTrue(lista.size()>0);
	}

	@Test
	public void test7Deletar() {
		PaisTable paisTable = paisRepository.findByNome("NOVO");
		paisRepository.delete(paisTable.getId());
		assertTrue(true);
	}
	
}


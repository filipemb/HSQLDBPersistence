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

import br.com.core.jdbc.dao.publico.EstadoRepository;
import br.com.core.jdbc.dao.publico.EstadoTable;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoRepositoryTest {

	private static Connection conn;
	private static EstadoRepository estadoRepository;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		estadoRepository = new EstadoRepository(conn);
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
		EstadoTable estadoTable = new EstadoTable();
		estadoTable.setNome("NOVO");
		estadoTable.setUf("NV");
		estadoTable.setPais(null);
		estadoTable = estadoRepository.save(estadoTable);
		assertTrue(estadoTable.getId()!=null);
	}

	@Test
	public void test2Update() {
		EstadoTable estadoTable = estadoRepository.findByNome("NOVO");
		estadoTable.setUf("NO");
		estadoRepository.save(estadoTable);
		EstadoTable estadoTableModificado = estadoRepository.findOne(estadoTable.getId());
		assertEquals("NO",estadoTableModificado.getUf());
	}

	@Test
	public void test3findOne() {
		EstadoTable estadoTable = estadoRepository.findByNome("NOVO");
		EstadoTable estadoTableBusca = estadoRepository.findOne(estadoTable.getId());
		assertEquals(estadoTableBusca.getId(), estadoTable.getId());
		assertEquals(estadoTableBusca.getUf(), estadoTable.getUf());
		assertEquals(estadoTableBusca.getNome(), estadoTable.getNome());
	}

	@Test
	public void test4findByNome() {
		EstadoTable estadoTable = estadoRepository.findByNome("NOVO");
		EstadoTable estadoTableBusca = estadoRepository.findOne(estadoTable.getId());
		assertEquals(estadoTableBusca.getId(), estadoTable.getId());
		assertEquals(estadoTableBusca.getUf(), estadoTable.getUf());
		assertEquals(estadoTableBusca.getNome(), estadoTable.getNome());
	}

	@Test
	public void test5findByEstado() {
		List<EstadoTable> lista = estadoRepository.findByPais(37l);//Brasil vide inserts.sql
		System.out.println("Lista tem o tamanho:"+lista.size());
		assertTrue(lista.size()>0);
	}
	
	@Test
	public void test6findAll() {
		List<EstadoTable> lista = estadoRepository.findAll();
		System.out.println("Lista tem o tamanho:"+lista.size());
		assertTrue(lista.size()>0);
	}

	@Test
	public void test7Deletar() {
		EstadoTable estadoTable = estadoRepository.findByNome("NOVO");
		estadoRepository.delete(estadoTable.getId());
		assertTrue(true);
	}
	
}


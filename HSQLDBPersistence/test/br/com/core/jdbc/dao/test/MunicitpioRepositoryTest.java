package br.com.core.jdbc.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.jdbc.dao.publico.MunicipioRepository;
import br.com.core.jdbc.dao.publico.MunicipioTable;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MunicitpioRepositoryTest {

	private static Connection conn;
	private static MunicipioRepository municipioRepository;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		municipioRepository = new MunicipioRepository(conn);
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
		MunicipioTable municipioTable = new MunicipioTable();
		municipioTable.setNome("NOVO");
		municipioTable.setCapital(true);
		municipioTable.setEstado(null);
		municipioTable = municipioRepository.save(municipioTable);
		assertTrue(municipioTable.getId()!=null);
	}

	@Test
	public void test2Update() {
		MunicipioTable municipioTable = municipioRepository.findByNome("NOVO");
		municipioTable.setCapital(false);
		municipioRepository.save(municipioTable);
		MunicipioTable municipioTableModificado = municipioRepository.findOne(municipioTable.getId());
		assertEquals(false, municipioTableModificado.getCapital());
	}

	@Test
	public void test3findOne() {
		MunicipioTable municipioTable = municipioRepository.findByNome("NOVO");
		MunicipioTable municipioTableBusca = municipioRepository.findOne(municipioTable.getId());
		assertEquals(municipioTableBusca.getId(), municipioTable.getId());
		assertEquals(municipioTableBusca.getCapital(), municipioTable.getCapital());
		assertEquals(municipioTableBusca.getNome(), municipioTable.getNome());
	}

	@Test
	public void test4findByNome() {
		MunicipioTable municipioTable = municipioRepository.findByNome("NOVO");
		MunicipioTable municipioTableBusca = municipioRepository.findOne(municipioTable.getId());
		assertEquals(municipioTableBusca.getId(), municipioTable.getId());
		assertEquals(municipioTableBusca.getCapital(), municipioTable.getCapital());
		assertEquals(municipioTableBusca.getNome(), municipioTable.getNome());
	}

	@Test
	public void test5findByEstado() {
		List<MunicipioTable> lista = municipioRepository.findByEstado(10l);
		System.out.println("Lista tem o tamanho:"+lista.size());
		assertTrue(lista.size()>0);
	}
	
	@Test
	public void test6findAll() {
		List<MunicipioTable> lista = municipioRepository.findAll();
		System.out.println("Lista tem o tamanho:"+lista.size());
		assertTrue(lista.size()>0);
	}

	@Test
	public void test7Deletar() {
		MunicipioTable municipioTable = municipioRepository.findByNome("NOVO");
		municipioRepository.delete(municipioTable.getId());
		assertTrue(true);
	}
	
}


package br.com.core.business.persistencia.test;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.business.persistencia.MunicipioPersistenciaBusiness;
import br.com.core.conversor.MunicipioConversor;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;
import br.com.core.modelo.publico.Municipio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MunicitpioPersistenciaBusinessTest {

	private static Connection conn;
	private static MunicipioPersistenciaBusiness municipioPersistenciaBusiness;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		municipioPersistenciaBusiness = MunicipioPersistenciaBusiness.getInstance(conn);
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
	public void test1findAll() {
		for(Municipio municipio : municipioPersistenciaBusiness.findAll()){
			System.out.println(MunicipioConversor.converterModeloParaJson(municipio));
		}
		assertTrue(true);
	}

//	@Test
//	public void test2Update() {
//		MunicipioTable municipioTable = municipioPersistenciaBusiness.findByNome("NOVO");
//		municipioTable.setCapital(false);
//		municipioPersistenciaBusiness.save(municipioTable);
//		MunicipioTable municipioTableModificado = municipioPersistenciaBusiness.findOne(municipioTable.getId());
//		assertEquals(false, municipioTableModificado.getCapital());
//	}
//
//	@Test
//	public void test3findOne() {
//		MunicipioTable municipioTable = municipioPersistenciaBusiness.findByNome("NOVO");
//		MunicipioTable municipioTableBusca = municipioPersistenciaBusiness.findOne(municipioTable.getId());
//		assertEquals(municipioTableBusca.getId(), municipioTable.getId());
//		assertEquals(municipioTableBusca.getCapital(), municipioTable.getCapital());
//		assertEquals(municipioTableBusca.getNome(), municipioTable.getNome());
//	}
//
//	@Test
//	public void test4findByNome() {
//		MunicipioTable municipioTable = municipioPersistenciaBusiness.findByNome("NOVO");
//		MunicipioTable municipioTableBusca = municipioPersistenciaBusiness.findOne(municipioTable.getId());
//		assertEquals(municipioTableBusca.getId(), municipioTable.getId());
//		assertEquals(municipioTableBusca.getCapital(), municipioTable.getCapital());
//		assertEquals(municipioTableBusca.getNome(), municipioTable.getNome());
//	}
//
//	@Test
//	public void test5findByEstado() {
//		List<MunicipioTable> lista = municipioPersistenciaBusiness.findByEstado(10l);
//		System.out.println("Lista tem o tamanho:"+lista.size());
//		assertTrue(lista.size()>0);
//	}
//	
//	@Test
//	public void test6findAll() {
//		List<MunicipioTable> lista = municipioPersistenciaBusiness.findAll();
//		System.out.println("Lista tem o tamanho:"+lista.size());
//		assertTrue(lista.size()>0);
//	}
//
//	@Test
//	public void test7Deletar() {
//		MunicipioTable municipioTable = municipioPersistenciaBusiness.findByNome("NOVO");
//		municipioPersistenciaBusiness.delete(municipioTable.getId());
//		assertTrue(true);
//	}
	
}


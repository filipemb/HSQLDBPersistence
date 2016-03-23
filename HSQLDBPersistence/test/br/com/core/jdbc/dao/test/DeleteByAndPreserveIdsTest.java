package br.com.core.jdbc.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.jdbc.dao.publico.EstadoRepository;
import br.com.core.jdbc.dao.publico.EstadoTable;
import br.com.core.jdbc.dao.publico.MunicipioRepository;
import br.com.core.jdbc.dao.publico.MunicipioTable;
import br.com.core.jdbc.dao.publico.PaisRepository;
import br.com.core.jdbc.dao.publico.PaisTable;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteByAndPreserveIdsTest {

	private static Connection conn;
	private static PaisRepository paisRepository;
	private static EstadoRepository estadoRepository;
	private static MunicipioRepository municipioRepository;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		paisRepository = new PaisRepository(conn);
		estadoRepository = new EstadoRepository(conn);
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
		
		PaisTable paisTable = new PaisTable();
		paisTable.setNome("Pais1");
		paisTable.setPrimeiro(true);
		paisTable = paisRepository.save(paisTable);
		
		EstadoTable estadoTable1 = new EstadoTable();
		estadoTable1.setNome("Estado1");
		estadoTable1.setPais(paisTable.getId());
		estadoTable1.setUf("E1");
		estadoTable1 = estadoRepository.save(estadoTable1);
		
		EstadoTable estadoTable2 = new EstadoTable();
		estadoTable2.setNome("Estado2");
		estadoTable2.setPais(paisTable.getId());
		estadoTable2.setUf("E2");
		estadoTable2 = estadoRepository.save(estadoTable2);
		
		MunicipioTable municipioTable1 = new MunicipioTable();
		municipioTable1.setNome("Municipio1");
		municipioTable1.setCapital(true);
		municipioTable1.setEstado(estadoTable1.getId());
		municipioTable1 = municipioRepository.save(municipioTable1);

		MunicipioTable municipioTable2 = new MunicipioTable();
		municipioTable2.setNome("Municipio2");
		municipioTable2.setCapital(true);
		municipioTable2.setEstado(estadoTable1.getId());
		municipioTable2 = municipioRepository.save(municipioTable2);

		MunicipioTable municipioTable3 = new MunicipioTable();
		municipioTable3.setNome("Municipio3");
		municipioTable3.setCapital(true);
		municipioTable3.setEstado(estadoTable2.getId());
		municipioTable3 = municipioRepository.save(municipioTable3);

		MunicipioTable municipioTable4 = new MunicipioTable();
		municipioTable4.setNome("Municipio4");
		municipioTable4.setCapital(true);
		municipioTable4.setEstado(estadoTable2.getId());
		municipioTable4 = municipioRepository.save(municipioTable4);
		
		
	}

	@Test
	public void test2MunicipioRepositoryDeleteByEstadoPreservandoTodosOsIds() {
		
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		MunicipioTable municipioTable1=municipioRepository.findByNome("Municipio1");
		MunicipioTable municipioTable2=municipioRepository.findByNome("Municipio2");

		EstadoTable estadoTable2 = estadoRepository.findByNome("Estado2");
		MunicipioTable municipioTable3=municipioRepository.findByNome("Municipio3");
		MunicipioTable municipioTable4=municipioRepository.findByNome("Municipio4");
		
		municipioRepository.deleteByEstadoPreserveIds(estadoTable1.getId(), municipioTable1.getId()+","+municipioTable2.getId());
		municipioRepository.deleteByEstadoPreserveIds(estadoTable2.getId(), municipioTable3.getId()+","+municipioTable4.getId());
		
		municipioTable1=municipioRepository.findByNome("Municipio1");
		municipioTable2=municipioRepository.findByNome("Municipio2");
		municipioTable3=municipioRepository.findByNome("Municipio3");
		municipioTable4=municipioRepository.findByNome("Municipio4");

		assertNotNull(municipioTable1);
		assertNotNull(municipioTable2);
		assertNotNull(municipioTable3);
		assertNotNull(municipioTable4);
	}

	@Test
	public void test3MunicipioRepositoryDeleteByEstadoPreservandoApenasUmId() {
		
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		MunicipioTable municipioTable1=municipioRepository.findByNome("Municipio1");
		MunicipioTable municipioTable2=municipioRepository.findByNome("Municipio2");
		
		EstadoTable estadoTable2 = estadoRepository.findByNome("Estado2");
		MunicipioTable municipioTable3=municipioRepository.findByNome("Municipio3");
		MunicipioTable municipioTable4=municipioRepository.findByNome("Municipio4");
		
		municipioRepository.deleteByEstadoPreserveIds(estadoTable1.getId(), municipioTable1.getId()+"");
		municipioRepository.deleteByEstadoPreserveIds(estadoTable2.getId(), municipioTable3.getId()+"");
		
		municipioTable1=municipioRepository.findByNome("Municipio1");
		municipioTable2=municipioRepository.findByNome("Municipio2");
		municipioTable3=municipioRepository.findByNome("Municipio3");
		municipioTable4=municipioRepository.findByNome("Municipio4");
		
		assertNotNull(municipioTable1);
		assertNull(municipioTable2);
		assertNotNull(municipioTable3);
		assertNull(municipioTable4);
	}

	@Test
	public void test4MunicipioRepositoryDeleteByEstadoSemPreservarIds() {
		
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		
		EstadoTable estadoTable2 = estadoRepository.findByNome("Estado2");
		
		municipioRepository.deleteByEstadoPreserveIds(estadoTable1.getId(), "-1");
		municipioRepository.deleteByEstadoPreserveIds(estadoTable2.getId(), "-1");
		
		MunicipioTable municipioTable1=municipioRepository.findByNome("Municipio1");
		MunicipioTable municipioTable3=municipioRepository.findByNome("Municipio3");
		
		assertNull(municipioTable1);
		assertNull(municipioTable3);
	}
	
	@Test
	public void test5EstadoRepositoryDeleteByPaisPreservandoTodosOsIds() {
		
		PaisTable paisTable1 = paisRepository.findByNome("Pais1");
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		EstadoTable estadoTable2 = estadoRepository.findByNome("Estado2");
		
		estadoRepository.deleteByPaisPreserveIds(paisTable1.getId(), estadoTable1.getId()+","+estadoTable2.getId());
		
		estadoTable1 = estadoRepository.findByNome("Estado1");
		estadoTable2 = estadoRepository.findByNome("Estado2");
		
		assertNotNull(estadoTable1);
		assertNotNull(estadoTable2);
	}
	
	@Test
	public void test6EstadoRepositoryDeleteByPaisPreservandoApenasUmId() {
		
		PaisTable paisTable1 = paisRepository.findByNome("Pais1");
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		EstadoTable estadoTable2 = estadoRepository.findByNome("Estado2");
		
		estadoRepository.deleteByPaisPreserveIds(paisTable1.getId(), estadoTable1.getId()+"");
		
		estadoTable1 = estadoRepository.findByNome("Estado1");
		estadoTable2 = estadoRepository.findByNome("Estado2");
		
		assertNotNull(estadoTable1);
		assertNull(estadoTable2);
	}

	@Test
	public void test7EstadoRepositoryDeleteByPaisSemPreservarIds() {
		
		PaisTable paisTable1 = paisRepository.findByNome("Pais1");
		EstadoTable estadoTable1 = estadoRepository.findByNome("Estado1");
		
		estadoRepository.deleteByPaisPreserveIds(paisTable1.getId(), "-1");
		
		estadoTable1 = estadoRepository.findByNome("Estado1");
		
		assertNull(estadoTable1);
	}

	@Test
	public void test8ExcluirPais() {
		PaisTable paisTable1 = paisRepository.findByNome("Pais1");
		paisRepository.delete(paisTable1.getId());
		paisTable1 = paisRepository.findByNome("Pais1");
		assertNull(paisTable1);
	}
	
	
	
	
}


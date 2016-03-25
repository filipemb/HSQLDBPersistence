package br.com.core.business.persistencia.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.business.persistencia.MunicipioPersistenciaBusiness;
import br.com.core.conversor.MunicipioConversor;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;
import br.com.core.json.utils.ConversorUtils;
import br.com.core.modelo.publico.Estado;
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
	
	@Test
	public void test1find() {
		Municipio municipio = municipioPersistenciaBusiness.find(5143l);
		System.out.println(ConversorUtils.getJsonSerializer().prettyPrint(true).serialize(municipio));
		assertTrue(true);
	}
	
	@Test
	public void test2findAll() {
		for(Municipio municipio : municipioPersistenciaBusiness.findAll()){
			System.out.println(MunicipioConversor.converterModeloParaJson(municipio));
		}
		assertTrue(true);
	}

	@Test
	public void test3findByEstadoCascade() {
		for(Municipio municipio : municipioPersistenciaBusiness.findByEstadoCascade(new Estado(20l))){
			System.out.println(MunicipioConversor.converterModeloParaJson(municipio));
		}
		assertTrue(true);
	}
	
	@Test
	public void test4save() {
		String json="{\n"+
							" \"id\": \"\",\n"+
							" \"nome\": \"Novo município em São Paulo\",\n"+
							" \"capital\": false,\n"+
							" \"estado\": {\n"+
								" \"id\": \"33\"\n"+
							" }\n"+
						"}";
		Municipio novoMunicipio = MunicipioConversor.converterJsonParaModelo(json);
		municipioPersistenciaBusiness.save(novoMunicipio);
		Municipio novo = municipioPersistenciaBusiness.find(novoMunicipio.getId());
		assertEquals(novoMunicipio.getId(),novo.getId());
		assertEquals(novoMunicipio.getNome(),novo.getNome());
		assertEquals(novoMunicipio.getEstado().getId(),novo.getEstado().getId());
	}

	@Test
	public void test5update() {
		String json="{\n"+
				" \"id\": \""+municipioPersistenciaBusiness.findByNome("Novo município em São Paulo").getId()+"\",\n"+
				" \"nome\": \"Novíssimo município em São Paulo\",\n"+
				" \"capital\": true,\n"+
				" \"estado\": {\n"+
					" \"id\": \"33\"\n"+
				" }\n"+
			"}";
		municipioPersistenciaBusiness.update(MunicipioConversor.converterJsonParaModelo(json));
		Municipio atualizado = municipioPersistenciaBusiness.findByNome("Novíssimo município em São Paulo");
		assertEquals(atualizado.getNome(),"Novíssimo município em São Paulo");
		assertEquals(atualizado.getCapital(),true);
	}
	
	@Test
	public void test6delete() {
		String json="{\n"+
				" \"id\": \""+municipioPersistenciaBusiness.findByNome("Novíssimo município em São Paulo").getId()+"\",\n"+
				" \"nome\": \"Novíssimo município em São Paulo\",\n"+
				" \"capital\": true,\n"+
				" \"estado\": {\n"+
					" \"id\": \"33\"\n"+
				" }\n"+
			"}";
		Municipio aDeletar = MunicipioConversor.converterJsonParaModelo(json);
		municipioPersistenciaBusiness.delete(aDeletar);
		assertNull(municipioPersistenciaBusiness.findByNome("Novíssimo município em São Paulo"));
	}
	


	
}


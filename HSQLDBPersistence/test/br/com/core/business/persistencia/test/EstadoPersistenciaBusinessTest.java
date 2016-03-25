package br.com.core.business.persistencia.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.core.business.persistencia.EstadoPersistenciaBusiness;
import br.com.core.business.persistencia.MunicipioPersistenciaBusiness;
import br.com.core.conversor.EstadoConversor;
import br.com.core.jdbc.pool.ConnectionPool;
import br.com.core.jdbc.pool.DatabaseConfig;
import br.com.core.json.utils.ConversorUtils;
import br.com.core.modelo.publico.Estado;
import br.com.core.modelo.publico.Pais;

/**
 * @author filipemb
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoPersistenciaBusinessTest {

	private static Connection conn;
	private static EstadoPersistenciaBusiness estadoPersistenciaBusiness;
	private static MunicipioPersistenciaBusiness municipioPersistenciaBusiness;
	
	@BeforeClass
	public static void iniciaInscancia(){
		ConnectionPool pool = DatabaseConfig.getPool();
		conn = pool.getConnection();
		estadoPersistenciaBusiness = EstadoPersistenciaBusiness.getInstance(conn);
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
		Estado estado = estadoPersistenciaBusiness.find(15l);
		System.out.println(ConversorUtils.getJsonSerializer().prettyPrint(true).serialize(estado));
		assertTrue(true);
	}

	@Test
	public void test2findCascaded() {
		Estado estado = estadoPersistenciaBusiness.findCascaded(new Estado(15l));
		System.out.println(EstadoConversor.converterModeloParaJson(estado));
		assertTrue(true);
	}
	
	@Test
	public void test3findAll() {
		for(Estado estado : estadoPersistenciaBusiness.findAll()){
			System.out.println(EstadoConversor.converterModeloParaJson(estado));
		}
		assertTrue(true);
	}

	@Test
	public void test4findByPaisCascade() {
		for(Estado estado : estadoPersistenciaBusiness.findByPaisCascade(new Pais(37l))){
			System.out.println(EstadoConversor.converterModeloParaJson(estado));
		}
		assertTrue(true);
	}
	
	@Test
	public void test5save() {
		String json=
				"{ \n"+
						" \"id\":\"\",\n"+
						" \"municipios\":[ \n"+
							" { \n"+
								" \"capital\":true,\n"+
								" \"id\":\"\",\n"+
								" \"nome\":\"Bras\u00edlia 1\"\n"+
							" },\n"+
							" { \n"+
								" \"capital\":false,\n"+
								" \"id\":\"\",\n"+
								" \"nome\":\"\u00c1guas Claras 1\"\n"+
							" },\n"+
							" { \n"+
								" \"capital\":false,\n"+
								" \"id\":\"\",\n"+
								" \"nome\":\"Brazl\u00e2ndia 1\"\n"+
							" },\n"+
							" { \n"+
								" \"capital\":false,\n"+
								" \"id\":\"\",\n"+
								" \"nome\":\"Candangol\u00e2ndia 1\"\n"+
							" }\n"+
						" ],\n"+
					" \"nome\":\"Distrito Federal 1\",\n"+
					" \"pais\":{ \n"+
						" \"id\":\"37\",\n"+
						" \"nome\":\"Brasil\",\n"+
						" \"primeiro\":true\n"+
					" },\n"+
					" \"uf\":\"DF 1\"\n"+
				"}";
		Estado novoEstado = EstadoConversor.converterJsonParaModelo(json);
		estadoPersistenciaBusiness.save(novoEstado);
		Estado novo = estadoPersistenciaBusiness.findCascaded(novoEstado);
		assertEquals(novoEstado.getId(),novo.getId());
		assertEquals(novoEstado.getNome(),novo.getNome());
		assertEquals(novoEstado.getPais().getId(),novo.getPais().getId());
		assertEquals(4,novoEstado.getMunicipios().size());
	}

	@Test
	public void test6update() {
		String json=
				"{ \n"+
						" \"id\":\""+estadoPersistenciaBusiness.findByNome("Distrito Federal 1").getId()+"\",\n"+
						" \"municipios\":[ \n"+
							" { \n"+
								" \"capital\":true,\n"+
								" \"id\":\""+municipioPersistenciaBusiness.findByNome("Bras\u00edlia 1").getId()+"\",\n"+
								" \"nome\":\"Bras\u00edlia 1\"\n"+
							" },\n"+
							" { \n"+
								" \"capital\":false,\n"+
								" \"id\":\"\",\n"+
								" \"nome\":\"Nova \u00c1guas Claras 2\"\n"+
							" }\n"+
						" ],\n"+
					" \"nome\":\"Distrito Federal 2\",\n"+
					" \"pais\":{ \n"+
						" \"id\":\"38\",\n"+
						" \"nome\":\"Brunei\",\n"+
						" \"primeiro\":false\n"+
					" },\n"+
					" \"uf\":\"DF 2\"\n"+
				"}";
		System.out.println(json);
		estadoPersistenciaBusiness.update(EstadoConversor.converterJsonParaModelo(json));
		Estado atualizado = estadoPersistenciaBusiness.findByNome("Distrito Federal 2");
		assertEquals("DF 2",atualizado.getUf());
		assertEquals(2,atualizado.getMunicipios().size());
		assertEquals("Brunei",atualizado.getPais().getNome());
	}
	
	@Test
	public void test7delete() {
		String json=
				"{ \n"+
						" \"id\":\""+estadoPersistenciaBusiness.findByNome("Distrito Federal 2").getId()+"\",\n"+
						" \"municipios\":[ \n"+
							" { \n"+
								" \"capital\":true,\n"+
								" \"id\":\""+municipioPersistenciaBusiness.findByNome("Bras\u00edlia 1").getId()+"\",\n"+
								" \"nome\":\"Bras\u00edlia 1\"\n"+
							" },\n"+
							" { \n"+
								" \"capital\":false,\n"+
								" \"id\":\""+municipioPersistenciaBusiness.findByNome("Nova \u00c1guas Claras 2").getId()+"\",\n"+
								" \"nome\":\"Nova \u00c1guas Claras 2\"\n"+
							" }\n"+
						" ],\n"+
					" \"nome\":\"Distrito Federal 2\",\n"+
					" \"pais\":{ \n"+
						" \"id\":\"38\",\n"+
						" \"nome\":\"Brunei\",\n"+
						" \"primeiro\":false\n"+
					" },\n"+
					" \"uf\":\"DF 2\"\n"+
				"}";
		Estado aDeletar = EstadoConversor.converterJsonParaModelo(json);
		estadoPersistenciaBusiness.delete(aDeletar);
		assertNull(estadoPersistenciaBusiness.findByNome("Distrito Federal 2"));
	}
	


	
}


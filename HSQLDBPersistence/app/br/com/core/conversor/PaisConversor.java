package br.com.core.conversor; 


import br.com.core.jdbc.dao.publico.PaisTable;
import br.com.core.json.utils.ConversorUtils;
import br.com.core.modelo.publico.Pais;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class PaisConversor {

	public static PaisTable converterModeloParaTabela(Pais modelo){

		PaisTable paisTable = new PaisTable();

		paisTable.setId(modelo.getId());
		paisTable.setNome(modelo.getNome());
		paisTable.setPrimeiro(modelo.getPrimeiro());
		return paisTable;
	}

	public static Pais converterTabelaParaModelo(PaisTable tabela){

		Pais pais = new Pais();

		pais.setId(tabela.getId());
		pais.setNome(tabela.getNome());
		pais.setPrimeiro(tabela.getPrimeiro());
		return pais;
	}

	public static String converterModeloParaJson(Pais pais){

		JSONSerializer serializer = ConversorUtils.getJsonSerializer();
		 serializer.include("estados");
		String json = serializer.serialize(pais);
		return json;

	}

	@SuppressWarnings("rawtypes")
	public static Pais converterJsonParaModelo(String json){

		JSONDeserializer deserializer = ConversorUtils.getJsonDeserializer();
		Object objeto = deserializer.use(null, Pais.class).deserialize(json);
		Pais pais = (Pais) objeto;
		return pais;

	}

}

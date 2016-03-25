package br.com.core.conversor; 


import java.util.ArrayList;
import java.util.List;

import br.com.core.jdbc.dao.publico.MunicipioTable;
import br.com.core.json.utils.ConversorUtils;
import br.com.core.modelo.publico.Estado;
import br.com.core.modelo.publico.Municipio;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
public class MunicipioConversor {

	public static MunicipioTable converterModeloParaTabela(Municipio modelo){

		if(modelo==null) return null;
		MunicipioTable municipioTable = new MunicipioTable();

		municipioTable.setId(modelo.getId());
		Estado estado = modelo.getEstado();
		if(estado!=null && estado.getId()!=null){
			municipioTable.setEstado(estado.getId());
		}

		municipioTable.setNome(modelo.getNome());
		municipioTable.setCapital(modelo.getCapital());
		return municipioTable;
	}

	public static List<MunicipioTable> converterMunicipiosParaTabela(Estado estado){

		List<MunicipioTable> retorno = new ArrayList<MunicipioTable>();
		for(Municipio modelo : estado.getMunicipios()){
			MunicipioTable municipioTable = converterModeloParaTabela(modelo);
			municipioTable.setEstado(estado.getId());
			retorno.add(municipioTable);
		}
		return retorno;
	}

	public static Municipio converterTabelaParaModelo(MunicipioTable tabela){

		if(tabela == null) return null;
		Municipio municipio = new Municipio();

		Estado estado = new Estado();
		estado.setId(tabela.getEstado());
		municipio.setEstado(estado);

		municipio.setId(tabela.getId());
		municipio.setNome(tabela.getNome());
		municipio.setCapital(tabela.getCapital());
		return municipio;
	}

	public static String converterModeloParaJson(Municipio municipio){

		JSONSerializer serializer = ConversorUtils.getJsonSerializer();
		String json = serializer.serialize(municipio);
		return json;

	}

	@SuppressWarnings("rawtypes")
	public static Municipio converterJsonParaModelo(String json){

		JSONDeserializer deserializer = ConversorUtils.getJsonDeserializer();
		Object objeto = deserializer.use(null, Municipio.class).deserialize(json);
		Municipio municipio = (Municipio) objeto;
		return municipio;

	}

}

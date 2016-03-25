package br.com.core.conversor; 


import java.util.ArrayList;
import java.util.List;

import br.com.core.jdbc.dao.publico.EstadoTable;
import br.com.core.json.utils.ConversorUtils;
import br.com.core.modelo.publico.Estado;
import br.com.core.modelo.publico.Pais;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class EstadoConversor {

	public static EstadoTable converterModeloParaTabela(Estado modelo){

		if(modelo == null) return null;
		EstadoTable estadoTable = new EstadoTable();

		estadoTable.setId(modelo.getId());
		Pais pais = modelo.getPais();
		if(pais!=null && pais.getId()!=null){
			estadoTable.setPais(pais.getId());
		}

		estadoTable.setNome(modelo.getNome());
		estadoTable.setUf(modelo.getUf());
		return estadoTable;
	}

	public static List<EstadoTable> converterEstadosParaTabela(Pais pais){

		List<EstadoTable> retorno = new ArrayList<EstadoTable>();
		for(Estado modelo : pais.getEstados()){
			EstadoTable estadoTable = converterModeloParaTabela(modelo);
			estadoTable.setPais(pais.getId());
			retorno.add(estadoTable);
		}
		return retorno;
	}

	public static Estado converterTabelaParaModelo(EstadoTable tabela){
		if(tabela == null) return null;
		Estado estado = new Estado();

		Pais pais = new Pais();
		pais.setId(tabela.getPais());
		estado.setPais(pais);

		estado.setId(tabela.getId());
		estado.setNome(tabela.getNome());
		estado.setUf(tabela.getUf());
		return estado;
	}

	public static String converterModeloParaJson(Estado estado){

		JSONSerializer serializer = ConversorUtils.getJsonSerializer();
		 serializer.include("municipios");
		String json = serializer.serialize(estado);
		return json;

	}

	@SuppressWarnings("rawtypes")
	public static Estado converterJsonParaModelo(String json){

		JSONDeserializer deserializer = ConversorUtils.getJsonDeserializer();
		Object objeto = deserializer.use(null, Estado.class).deserialize(json);
		Estado estado = (Estado) objeto;
		return estado;

	}

}

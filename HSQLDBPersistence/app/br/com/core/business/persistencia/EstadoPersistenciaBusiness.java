package br.com.core.business.persistencia; 

import java.util.ArrayList;
import java.util.List;

import br.com.core.conversor.EstadoConversor;
import br.com.core.jdbc.dao.publico.EstadoRepository;
import br.com.core.jdbc.dao.publico.EstadoTable;
import br.com.core.modelo.publico.Estado;
import br.com.core.modelo.publico.Pais;


public class EstadoPersistenciaBusiness {

	EstadoRepository estadoRepository;
	MunicipioPersistenciaBusiness municipioPersistenciaBusiness;
	PaisPersistenciaBusiness paisPersistenciaBusiness;

	public Estado find(Long id){
		return find(new Estado(id));
	}
	
	public Estado find(Estado estado){
		if(estado!=null && estado.getId()!=null){
			EstadoTable estadoTable  = estadoRepository.findOne(estado.getId()); 
			estado = EstadoConversor.converterTabelaParaModelo(estadoTable);
			estado.setPais(paisPersistenciaBusiness.find(estado.getPais()));
		}
		return estado;
	}

	public Estado findCascaded(Estado estado){
		if(estado!=null && estado.getId()!=null){
			EstadoTable estadoTable  = estadoRepository.findOne(estado.getId()); 
			estado = EstadoConversor.converterTabelaParaModelo(estadoTable);
			estado.setPais(paisPersistenciaBusiness.find(estado.getPais()));
			estado.setMunicipios(municipioPersistenciaBusiness.findByEstadoCascade(estado));
		}
		return estado;
	}

	public List<Estado> findByPaisCascade(Pais pais){
		List<Estado> retorno = new ArrayList<Estado>();
		for(EstadoTable estadoTable : estadoRepository.findByPais(pais.getId())){
			Estado estado = EstadoConversor.converterTabelaParaModelo(estadoTable);
			estado.setPais(pais);
			estado.setMunicipios(municipioPersistenciaBusiness.findByEstadoCascade(estado));
			retorno.add(estado);
		}
		 return retorno;
	}

	public List<Estado> findAll(){
		List<Estado> retorno = new ArrayList<Estado>();
		List<EstadoTable> all = estadoRepository.findAll();
		for(EstadoTable itemTable : all){
			Estado item = EstadoConversor.converterTabelaParaModelo(itemTable);
			item.setPais(paisPersistenciaBusiness.find(item.getPais()));
			retorno.add(item);
		}
		 return retorno;
	}

	public Estado save(Estado estado){
		if(estado!=null ){
			//o campo Pais foi salvo apenas como referencia
			EstadoTable estadoTable  = EstadoConversor.converterModeloParaTabela(estado); 
			estadoRepository.save(estadoTable);
			estado.setId(estadoTable.getId());
			municipioPersistenciaBusiness.saveMunicipios(estado);
		}
		return estado;
	}

	public void saveEstados(Pais pais){
		List<Estado> estadosColecao = pais.getEstados();
		List<EstadoTable> estadosTables = EstadoConversor.converterEstadosParaTabela(pais);
		if(estadosTables != null ){ 
			for(int i=0; i<estadosTables.size();i++){
				Estado estadoItem = estadosColecao.get(i);
				EstadoTable estadoTable = estadosTables.get(i);
				if(estadoTable.getId() == null ){ 
					estadoRepository.save(estadoTable);
					estadoItem.setId(estadoTable.getId());
				}
			}
		}
	}
	
	public List<Estado> save( List<Estado> colecao){
		List<Estado> retorno = new ArrayList<Estado>();
		for(Estado item : colecao){
			 retorno.add(save(item));
		}
		 return retorno;
	}

	
	public void update(Estado estado){
		municipioPersistenciaBusiness.deleteMunicipios(estado);
		EstadoTable estadoTable  = EstadoConversor.converterModeloParaTabela(estado); 
		estadoRepository.save(estadoTable);
		municipioPersistenciaBusiness.saveMunicipios(estado);
	}

	
	
	public void delete(Estado estado){
		if(estado!=null && estado.getId()!=null){
			municipioPersistenciaBusiness.delete(estado.getMunicipios());
			EstadoTable estadoTable  = EstadoConversor.converterModeloParaTabela(estado); 
			estadoRepository.delete(estadoTable.getId());
			//o objeto Pais nao serah removido junto
		}
	}

	
	public void deleteEstados(Pais pais){
		String ids = "";
		List<Estado> estados = pais.getEstados();
		if(estados!=null && estados.size() >0){ 
			for(int i=0; i<estados.size();i++){
				if(estados.get(i).getId()!=null){
					ids=ids+estados.get(i).getId()+", ";
				}
			}
			if(!ids.isEmpty()){
				ids=ids.substring(0,ids.length()-2);
				estadoRepository.deleteByPaisPreserveIds(pais.getId(), ids);
			}
		}else{
			estadoRepository.deleteByPaisPreserveIds(pais.getId(), "-1");
		}
	}
	
	
	public void delete( List<Estado> colecao){
		if(colecao!=null){
			for(Estado item : colecao){
				 delete(item);
			}
		}
	}
	

}
package br.com.core.business.persistencia; 

import java.util.ArrayList;
import java.util.List;

import br.com.core.conversor.PaisConversor;
import br.com.core.jdbc.dao.publico.PaisRepository;
import br.com.core.jdbc.dao.publico.PaisTable;
import br.com.core.modelo.publico.Pais;

public class PaisPersistenciaBusiness {

	PaisRepository paisRepository;
	EstadoPersistenciaBusiness estadoPersistenciaBusiness;

	
	public Pais find(Long id){
		return find(new Pais(id));
	}

	
	public Pais find(Pais pais){
		if(pais!=null && pais.getId()!=null){
			PaisTable paisTable  = paisRepository.findOne(pais.getId()); 
			pais = PaisConversor.converterTabelaParaModelo(paisTable);
		}
		return pais;
	}

	public Pais findCascaded(Pais pais){
		if(pais!=null && pais.getId()!=null){
			PaisTable paisTable  = paisRepository.findOne(pais.getId()); 
			pais = PaisConversor.converterTabelaParaModelo(paisTable);
			pais.setEstados(estadoPersistenciaBusiness.findByPaisCascade(pais));
		}
		return pais;
	}

	
	public List<Pais> findAll(){
		List<Pais> retorno = new ArrayList<Pais>();
		List<PaisTable> all = paisRepository.findAll();
		for(PaisTable itemTable : all){
			Pais item = PaisConversor.converterTabelaParaModelo(itemTable);
			 retorno.add(item);
		}
		 return retorno;
	}

	public List<Pais> findAllCascaded(){
		List<Pais> retorno = new ArrayList<Pais>();
		List<PaisTable> all = paisRepository.findAll();
		for(PaisTable itemTable : all){
			Pais item =  PaisConversor.converterTabelaParaModelo(itemTable);
			item.setEstados(estadoPersistenciaBusiness.findByPaisCascade(item));
			retorno.add(item);
		}
		return retorno;
	}
	
	
	public Pais save(Pais pais){

		if(pais!=null ){
			PaisTable paisTable  = PaisConversor.converterModeloParaTabela(pais); 
			paisRepository.save(paisTable);
			pais.setId(paisTable.getId());
			estadoPersistenciaBusiness.saveEstados(pais);
		}
		return pais;

	}

	public List<Pais> save( List<Pais> colecao){
		List<Pais> retorno = new ArrayList<Pais>();
		for(Pais item : colecao){
			 retorno.add(save(item));
		}
		 return retorno;
	}

	
	public void update(Pais pais){
		estadoPersistenciaBusiness.deleteEstados(pais);
		PaisTable paisTable  = PaisConversor.converterModeloParaTabela(pais); 
		paisRepository.save(paisTable);
		estadoPersistenciaBusiness.saveEstados(pais);
	}

	
	public void delete(Pais pais){
		if(pais!=null && pais.getId()!=null){
			estadoPersistenciaBusiness.delete(pais.getEstados());
			PaisTable paisTable  = PaisConversor.converterModeloParaTabela(pais); 
			paisRepository.delete(paisTable.getId());
		}
	}
	
	public void delete( List<Pais> colecao){
		if(colecao!=null){
			for(Pais item : colecao){
				 delete(item);
			}
		}
	}

}

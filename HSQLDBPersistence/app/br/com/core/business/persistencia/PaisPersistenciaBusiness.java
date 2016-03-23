package br.com.core.business.persistencia; 

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.core.conversor.PaisConversor;
import br.com.core.jdbc.dao.publico.PaisRepository;
import br.com.core.jdbc.dao.publico.PaisTable;
import br.com.core.modelo.publico.Pais;

public class PaisPersistenciaBusiness {

	private static PaisPersistenciaBusiness instance;
	
	private Connection conn;
	private PaisRepository paisRepository;

	private PaisPersistenciaBusiness(Connection conn){
		this.conn=conn;
		this.paisRepository=new PaisRepository(conn);
	}
	
	public static PaisPersistenciaBusiness getInstance(Connection conn){
		if(instance==null){
			instance = new PaisPersistenciaBusiness(conn);
		}
		return instance;
	}
	
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
			pais.setEstados(EstadoPersistenciaBusiness.getInstance(conn).findByPaisCascade(pais));
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
			item.setEstados(EstadoPersistenciaBusiness.getInstance(conn).findByPaisCascade(item));
			retorno.add(item);
		}
		return retorno;
	}
	
	
	public Pais save(Pais pais){

		if(pais!=null ){
			PaisTable paisTable  = PaisConversor.converterModeloParaTabela(pais); 
			paisRepository.save(paisTable);
			pais.setId(paisTable.getId());
			EstadoPersistenciaBusiness.getInstance(conn).saveEstados(pais);
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
		EstadoPersistenciaBusiness.getInstance(conn).deleteEstados(pais);
		PaisTable paisTable  = PaisConversor.converterModeloParaTabela(pais); 
		paisRepository.save(paisTable);
		EstadoPersistenciaBusiness.getInstance(conn).saveEstados(pais);
	}

	
	public void delete(Pais pais){
		if(pais!=null && pais.getId()!=null){
			EstadoPersistenciaBusiness.getInstance(conn).delete(pais.getEstados());
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

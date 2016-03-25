package br.com.core.business.persistencia; 

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.core.conversor.MunicipioConversor;
import br.com.core.jdbc.dao.publico.MunicipioRepository;
import br.com.core.jdbc.dao.publico.MunicipioTable;
import br.com.core.modelo.publico.Estado;
import br.com.core.modelo.publico.Municipio;

public class MunicipioPersistenciaBusiness {

	private static MunicipioPersistenciaBusiness instance;

	private Connection conn;
	private MunicipioRepository municipioRepository;

	private MunicipioPersistenciaBusiness(Connection conn){
		this.municipioRepository = new MunicipioRepository(conn);
		this.conn = conn;
	}

	public static MunicipioPersistenciaBusiness getInstance(Connection conn){
		if(instance==null){
			instance = new MunicipioPersistenciaBusiness(conn);
		}
		return instance;
	}

	public Municipio find(Long id){
		return find(new Municipio(id));
	}

	public Municipio find(Municipio municipio){
		if(municipio==null || municipio.getId()==null) return null;
		MunicipioTable municipioTable  = municipioRepository.findOne(municipio.getId()); 
		if(municipioTable==null) return null;
		municipio = MunicipioConversor.converterTabelaParaModelo(municipioTable);
		municipio.setEstado(EstadoPersistenciaBusiness.getInstance(conn).find(municipio.getEstado()));
		return municipio;
	}

	public Municipio findByNome(String nome){
		if(nome==null || nome.isEmpty()) return null;
		MunicipioTable municipioTable  = municipioRepository.findByNome(nome); 
		if(municipioTable==null) return null;
		Municipio municipio = MunicipioConversor.converterTabelaParaModelo(municipioTable);
		municipio.setEstado(EstadoPersistenciaBusiness.getInstance(conn).find(municipio.getEstado()));
		return municipio;
	}

	public List<Municipio> findByEstadoCascade(Estado estado){
		if(estado==null || estado.getId()==null) return null;
		List<Municipio> retorno = new ArrayList<Municipio>();
		List<MunicipioTable> allByEstado = municipioRepository.findByEstado(estado.getId());
		if(allByEstado==null) return null;
		for(MunicipioTable municipioTable : allByEstado ){
			Municipio municipio = MunicipioConversor.converterTabelaParaModelo(municipioTable);
			municipio.setEstado(estado);
			retorno.add(municipio);
		}
		return retorno;
	}

	public List<Municipio> findAll(){
		List<Municipio> retorno = new ArrayList<Municipio>();
		List<MunicipioTable> all = municipioRepository.findAll();
		if(all==null) return null;
		for(MunicipioTable itemTable : all){
			Municipio item = find(itemTable.getId());
			item.setEstado(EstadoPersistenciaBusiness.getInstance(conn).find(item.getEstado()));
			retorno.add(item);
		}
		return retorno;
	}

	public Municipio save(Municipio municipio){
		if(municipio!=null ){
			//o campo Estado foi salvo apenas como referencia
			MunicipioTable municipioTable  = MunicipioConversor.converterModeloParaTabela(municipio); 
			municipioRepository.save(municipioTable);
			municipio.setId(municipioTable.getId());
		}
		return municipio;
	}

	public void saveMunicipios(Estado estado){
		List<Municipio> municipiosColecao = estado.getMunicipios();
		List<MunicipioTable> municipiosTables = MunicipioConversor.converterMunicipiosParaTabela(estado);
		if(municipiosTables != null ){ 
			for(int i=0; i<municipiosTables.size();i++){
				Municipio municipioItem = municipiosColecao.get(i);
				MunicipioTable municipioTable = municipiosTables.get(i);
				if(municipioTable.getId() == null ){ 
					municipioRepository.save(municipioTable);
					municipioItem.setId(municipioTable.getId());
				}
			}
		}
	}

	public List<Municipio> save( List<Municipio> colecao){
		List<Municipio> retorno = new ArrayList<Municipio>();
		for(Municipio item : colecao){
			retorno.add(save(item));
		}
		return retorno;
	}

	public void update(Municipio municipio){
		MunicipioTable municipioTable  = MunicipioConversor.converterModeloParaTabela(municipio); 
		municipioRepository.save(municipioTable);
	}

	public void delete(Municipio municipio){
		if(municipio!=null && municipio.getId()!=null){
			MunicipioTable municipioTable  = MunicipioConversor.converterModeloParaTabela(municipio); 
			municipioRepository.delete(municipioTable.getId());
			//o objeto Estado nao serah removido
		}
	}

	public void deleteMunicipios(Estado estado){
		String ids = "";
		List<Municipio> municipios = estado.getMunicipios();
		if(municipios!=null && municipios.size() >0){ 
			for(int i=0; i<municipios.size();i++){
				if(municipios.get(i).getId()!=null){
					ids=ids+municipios.get(i).getId()+", ";
				}
			}
			if(!ids.isEmpty()){
				ids=ids.substring(0,ids.length()-2);
				municipioRepository.deleteByEstadoPreserveIds(estado.getId(), ids);
			}
		}else{
			municipioRepository.deleteByEstadoPreserveIds(estado.getId(), "-1");
		}
	}

	public void delete( List<Municipio> colecao){
		if(colecao!=null){
			for(Municipio item : colecao){
				delete(item);
			}
		}
	}

}

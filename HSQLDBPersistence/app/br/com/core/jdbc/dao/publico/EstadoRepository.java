package br.com.core.jdbc.dao.publico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class EstadoRepository {

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public EstadoRepository(Connection con)  {
		this.con = con;
	}

	public EstadoTable findOne(Long id) {
		EstadoTable obj = null;
		sql = "SELECT id, nome, uf, pais FROM public.estado WHERE id=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj = new EstadoTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setUf(rs.getString("uf"));
				if(rs.wasNull()){obj.setUf(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPais(rs.getLong("pais"));
				if(rs.wasNull()){obj.setPais(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public List<EstadoTable> findAll() {

		List<EstadoTable> resultado = new ArrayList<EstadoTable>();
		sql = "SELECT id, nome, uf, pais FROM public.estado";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				EstadoTable obj = new EstadoTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setUf(rs.getString("uf"));
				if(rs.wasNull()){obj.setUf(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPais(rs.getLong("pais"));
				if(rs.wasNull()){obj.setPais(null);}
				resultado.add(obj);
			}
			pstmt.close();
			rs.close();
			return resultado;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public List<EstadoTable> findByPais(Long pais) {
		
		List<EstadoTable> resultado = new ArrayList<EstadoTable>();
		sql = "SELECT id, nome, uf, pais FROM public.estado  WHERE pais=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, pais);
			rs = pstmt.executeQuery();
			while(rs.next()){
				EstadoTable obj = new EstadoTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setUf(rs.getString("uf"));
				if(rs.wasNull()){obj.setUf(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPais(rs.getLong("pais"));
				if(rs.wasNull()){obj.setPais(null);}
				resultado.add(obj);
			}
			pstmt.close();
			rs.close();
			return resultado;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public EstadoTable findByNome(String nome) {
		
		EstadoTable obj = null;
		sql = "SELECT id, nome, uf, pais FROM public.estado  WHERE nome=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, nome);
			rs = pstmt.executeQuery();
			while(rs.next()){
				obj = new EstadoTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setUf(rs.getString("uf"));
				if(rs.wasNull()){obj.setUf(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPais(rs.getLong("pais"));
				if(rs.wasNull()){obj.setPais(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public EstadoTable save(EstadoTable estadoTable) {
		
		if(estadoTable.getId()!=null){
			return update(estadoTable);
		}

		try{
			String sqlId  = "call NEXT VALUE FOR public.estado_id_seq";
			Statement stmt= con.createStatement();
			rs = stmt.executeQuery(sqlId);
			rs.next();
			Long id =  new Long(rs.getInt(1));
			estadoTable.setId(id);
			rs.close();
			stmt.close();

			sql = "INSERT INTO public.estado( id, nome, uf, pais) VALUES (?, ?, ?, ?)";
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, estadoTable.getId());
			pstmt.setString(2, estadoTable.getNome());
			pstmt.setString(3, estadoTable.getUf());
			if(estadoTable.getPais()!=null){
				pstmt.setLong(4, estadoTable.getPais());
			}else{
				pstmt.setNull(4,java.sql.Types.BIGINT);
			}
			pstmt.executeUpdate();
			pstmt.close();
			return estadoTable;

		} catch( Exception e ){
			throw new RuntimeException(e);
		}

	}

	private EstadoTable update(EstadoTable estadoTable) {
		sql = "UPDATE public.estado  SET nome=?, uf=?, pais=? WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, estadoTable.getNome());
			pstmt.setString(2, estadoTable.getUf());
			if(estadoTable.getPais()!=null){
				pstmt.setLong(3, estadoTable.getPais());
			}else{
				pstmt.setNull(3,java.sql.Types.BIGINT);
			}
			pstmt.setLong(4, estadoTable.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
		return estadoTable;
	}
	
	public void delete( Long id ) {
		sql = "DELETE FROM public.estado WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public void deleteByPaisPreserveIds(Long pais, String ids) {
		sql = "DELETE FROM public.estado WHERE pais=? and id not in ("+ids+")";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, pais);
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
		
	}
	
}

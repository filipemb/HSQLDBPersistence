package br.com.core.jdbc.dao.publico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PaisRepository {

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public PaisRepository(Connection con)  {
		this.con = con;
	}

	public PaisTable findOne(Long id) {
		PaisTable obj = null;
		sql = "SELECT id, nome, primeiro FROM public.pais where id=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj = new PaisTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPrimeiro(rs.getBoolean("primeiro"));
				if(rs.wasNull()){obj.setPrimeiro(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public List<PaisTable> findAll() {

		List<PaisTable> resultado = new ArrayList<PaisTable>();
		sql = "SELECT id, nome, primeiro FROM public.pais";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				PaisTable obj = new PaisTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPrimeiro(rs.getBoolean("primeiro"));
				if(rs.wasNull()){obj.setPrimeiro(null);}
				resultado.add(obj);
			}
			pstmt.close();
			rs.close();
			return resultado;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public PaisTable findByNome(String nome) {
		
		PaisTable obj = null;
		sql = "SELECT id, nome, primeiro FROM public.pais where nome=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, nome);
			rs = pstmt.executeQuery();
			while(rs.next()){
				obj = new PaisTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setPrimeiro(rs.getBoolean("primeiro"));
				if(rs.wasNull()){obj.setPrimeiro(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public PaisTable save(PaisTable paisTable) {
		
		if(paisTable.getId()!=null ){
			return update(paisTable);
		}

		try{
			String sqlId  = "call NEXT VALUE FOR public.pais_id_seq";
			Statement stmt= con.createStatement();
			rs = stmt.executeQuery(sqlId);
			rs.next();
			Long id =  new Long(rs.getInt(1));
			paisTable.setId(id);
			rs.close();
			stmt.close();

			sql = "INSERT INTO public.pais(id, nome, primeiro) VALUES (?, ?, ?)";
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, paisTable.getId());
			pstmt.setString(2, paisTable.getNome());
			pstmt.setBoolean(3, paisTable.getPrimeiro());
			pstmt.executeUpdate();
			pstmt.close();
			return paisTable;

		} catch( Exception e ){
			throw new RuntimeException(e);
		}

	}

	private PaisTable update(PaisTable paisTable) {
		sql = "UPDATE public.pais SET  nome=?, primeiro=? WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, paisTable.getNome());
			pstmt.setBoolean(2, paisTable.getPrimeiro());
			pstmt.setLong(3, paisTable.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
		return paisTable;
	}
	
	public void delete( Long id ) {
		sql = "DELETE FROM public.pais WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}
}

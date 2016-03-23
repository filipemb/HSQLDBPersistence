package br.com.core.jdbc.dao.publico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MunicipioRepository {

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	public MunicipioRepository(Connection con)  {
		this.con = con;
	}

	public MunicipioTable findOne(Long id) {
		MunicipioTable obj = null;
		sql = "SELECT id, capital, nome, estado FROM public.municipio where id=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj = new MunicipioTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setCapital(rs.getBoolean("capital"));
				if(rs.wasNull()){obj.setCapital(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setEstado(rs.getLong("estado"));
				if(rs.wasNull()){obj.setEstado(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public List<MunicipioTable> findAll() {

		List<MunicipioTable> resultado = new ArrayList<MunicipioTable>();
		sql = "SELECT id, capital, nome, estado FROM public.municipio";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				MunicipioTable obj = new MunicipioTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setCapital(rs.getBoolean("capital"));
				if(rs.wasNull()){obj.setCapital(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setEstado(rs.getLong("estado"));
				if(rs.wasNull()){obj.setEstado(null);}
				resultado.add(obj);
			}
			pstmt.close();
			rs.close();
			return resultado;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public List<MunicipioTable> findByEstado(Long estado) {
		
		List<MunicipioTable> resultado = new ArrayList<MunicipioTable>();
		sql = "SELECT id, capital, nome, estado FROM public.municipio WHERE estado=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, estado);
			rs = pstmt.executeQuery();
			while(rs.next()){
				MunicipioTable obj = new MunicipioTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setCapital(rs.getBoolean("capital"));
				if(rs.wasNull()){obj.setCapital(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setEstado(rs.getLong("estado"));
				if(rs.wasNull()){obj.setEstado(null);}
				resultado.add(obj);
			}
			pstmt.close();
			rs.close();
			return resultado;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public MunicipioTable findByNome(String nome) {
		MunicipioTable obj =null;
		sql = "SELECT id, capital, nome, estado FROM public.municipio WHERE nome=?";
		try {
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setString(1, nome);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj = new MunicipioTable();
				obj.setId(rs.getLong("id"));
				if(rs.wasNull()){obj.setId(null);}
				obj.setCapital(rs.getBoolean("capital"));
				if(rs.wasNull()){obj.setCapital(null);}
				obj.setNome(rs.getString("nome"));
				if(rs.wasNull()){obj.setNome(null);}
				obj.setEstado(rs.getLong("estado"));
				if(rs.wasNull()){obj.setEstado(null);}
			}
			pstmt.close();
			rs.close();
			return obj;
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public MunicipioTable save(MunicipioTable municipioTable) {
		if(municipioTable.getId()!=null){
			return update(municipioTable);
		}

		try{
			String sqlId  = "call NEXT VALUE FOR public.municipio_id_seq";
			Statement stmt= con.createStatement();
			rs = stmt.executeQuery(sqlId);
			rs.next();
			Long id =  new Long(rs.getInt(1));
			municipioTable.setId(id);
			rs.close();
			stmt.close();

			sql = "INSERT INTO public.municipio(id, capital, nome, estado) VALUES (?, ?, ?, ?)";
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, municipioTable.getId());
			pstmt.setBoolean(2, municipioTable.getCapital());
			pstmt.setString(3, municipioTable.getNome());
			if(municipioTable.getEstado()!=null){
				pstmt.setLong(4, municipioTable.getEstado());
			}else{
				pstmt.setNull(4,java.sql.Types.BIGINT);
			}
			pstmt.executeUpdate();
			pstmt.close();
			return municipioTable;

		} catch( Exception e ){
			throw new RuntimeException(e);
		}

	}

	private MunicipioTable update(MunicipioTable municipioTable) {
		sql = "UPDATE public.municipio SET capital=?, nome=?, estado=? WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setBoolean(1, municipioTable.getCapital());
			pstmt.setString(2, municipioTable.getNome());
			if(municipioTable.getEstado()!=null){
				pstmt.setLong(3, municipioTable.getEstado());
			}else{
				pstmt.setNull(3,java.sql.Types.BIGINT);
			}
			pstmt.setLong(4, municipioTable.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
		return municipioTable;
	}
	
	public void delete( Long id ) {
		sql = "DELETE FROM public.municipio WHERE id=?";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}

	public void deleteByEstadoPreserveIds(Long estado, String ids) {
		sql = "DELETE FROM public.municipio WHERE estado=?  and id not in ("+ids+")";
		try{
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			pstmt.setLong(1, estado);
			pstmt.executeUpdate();
			pstmt.close();
		} catch( Exception e ){
			throw new RuntimeException(e);
		}
	}
}

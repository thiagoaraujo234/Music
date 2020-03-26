package br.unitins.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.music.model.Perfil;
import br.unitins.music.model.Telefone;
import br.unitins.music.model.Usuario;

public class TelefoneDAO extends DAO<Telefone> {

	public TelefoneDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Telefone entity) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.telefone " + " (id, codigoarea, numero) " + "VALUES " + " (?, ?, ?) ");
		stat.setInt(1, entity.getId());
		stat.setString(2, entity.getCodigoArea());
		stat.setString(3, entity.getNumero());

		stat.execute();
		stat.close();

	}

	@Override
	public void update(Telefone entity) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"DELETE FROM public.telefone WHERE id =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	@Override
	public List<Telefone> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Telefone findById(Integer id) {
		Connection conn = getConnection();
		
		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " +
					"  id, " +
					"  codigoarea, " +
					"  numero " +					
					"FROM " +
					"  public.telefone " +
					"WHERE id = ? ");
			
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			Telefone telefone = null;
			
			if(rs.next()) {
				telefone = new Telefone();
				telefone.setId(rs.getInt("id"));
				telefone.setCodigoArea(rs.getString("codigoarea"));
				telefone.setNumero(rs.getString("numero"));
			}
			
			return telefone;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}

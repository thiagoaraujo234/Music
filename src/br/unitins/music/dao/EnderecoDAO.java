package br.unitins.music.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.music.model.Endereco;

public class EnderecoDAO extends DAO<Endereco> {

	public EnderecoDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create(Endereco entity) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.endereco "
				+ " (id, numero, cidade, estado, logradouro, cep) " + "VALUES " + " (?, ?, ?, ?, ?, ?) ");
		stat.setInt(1, entity.getId());
		stat.setInt(2, entity.getNumero());
		stat.setString(3, entity.getCidade());
		stat.setString(4, entity.getEstado());
		stat.setString(5, entity.getLogradouro());
		stat.setString(6, entity.getCep());

		stat.execute();
		stat.close();

	}

	@Override
	public void update(Endereco entity) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.endereco SET " + " numero = ?, " + " cidade = ?, "
				+ " estado = ?, " + " logradouro = ?, " + " cep = ? " +

				"WHERE " + " id = ? ");
		
		stat.setInt(1, entity.getNumero());
		stat.setString(2, entity.getCidade());
		stat.setString(3, entity.getEstado());
		stat.setString(4, entity.getLogradouro());
		stat.setString(5, entity.getCep());
		stat.setInt(6, entity.getId());
		stat.execute();
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.endereco WHERE id =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();

	}

	@Override
	public List<Endereco> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Endereco findById(Integer id) {
		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  numero, " + "  cidade, "
					+ "  estado, " + "  logradouro, " + "  cep " + "FROM " + "  public.endereco " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Endereco endereco = null;

			if (rs.next()) {
				endereco = new Endereco();
				endereco.setId(rs.getInt("id"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setCep(rs.getString("cep"));

			}

			return endereco;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

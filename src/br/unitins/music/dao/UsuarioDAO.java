package br.unitins.music.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.music.model.Endereco;
import br.unitins.music.model.Perfil;
import br.unitins.music.model.Telefone;
import br.unitins.music.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public UsuarioDAO(Connection conn) {
		super(conn);
	}

	public UsuarioDAO() {
		// cria uma nova conexao
		super(null);
	}

	public Usuario login(String login, String senha) {

		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, " + "  dataaniversario, " + "  ativo, "
							+ "  perfil " + "FROM " + "  public.usuario " + "WHERE login = ? AND senha = ? ");

			stat.setString(1, login);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setTelefone(new Telefone());
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				Date data = rs.getDate("dataaniversario");
				usuario.setDataAniversario(data == null ? null : data.toLocalDate());
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void create(Usuario usuario) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.usuario "
				+ " (nome, login, senha, dataaniversario, ativo, perfil) " + "VALUES " + " (?, ?, ?, ?, ?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setDate(4, Date.valueOf(usuario.getDataAniversario()));
		stat.setBoolean(5, usuario.getAtivo());
		stat.setInt(6, usuario.getPerfil().getValue());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		usuario.getTelefone().setId(rs.getInt("id"));
		usuario.getEndereco().setId(rs.getInt("id"));

		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.create(usuario.getTelefone());

		EnderecoDAO end = new EnderecoDAO(conn);
		end.create(usuario.getEndereco());
	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.usuario SET " + " nome = ?, " + " login = ?, "
				+ " senha = ?, " + " dataaniversario = ?, " + " ativo = ?, " + " perfil = ? " + "WHERE " + " id = ? ");
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setDate(4, Date.valueOf(usuario.getDataAniversario()));
		stat.setBoolean(5, usuario.getAtivo());
		stat.setInt(6, usuario.getPerfil().getValue());
		stat.setInt(7, usuario.getId());

		stat.execute();
		TelefoneDAO dao = new TelefoneDAO(conn);
		dao.update(usuario.getTelefone());

		EnderecoDAO end = new EnderecoDAO(conn);
		end.update(usuario.getEndereco());
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection conn = getConnection();
		// deletando o telefone (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		TelefoneDAO dao = new TelefoneDAO(conn);
		// telefone tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo
		// do telefone.
		dao.delete(id);

		EnderecoDAO end = new EnderecoDAO(conn);
		end.delete(id);

		// deletando o usuario
		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.usuario WHERE id = ?");
		stat.setInt(1, id);

		stat.execute();

	}

	@Override
	public List<Usuario> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  dataaniversario, " + "  ativo, " + "  perfil " + "FROM " + "  public.usuario ");
			ResultSet rs = stat.executeQuery();

			List<Usuario> listaUsuario = new ArrayList<Usuario>();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				Date data = rs.getDate("dataaniversario");
				usuario.setDataAniversario(data == null ? null : data.toLocalDate());
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(usuario.getId()));
				if (usuario.getTelefone() == null)
					usuario.setTelefone(new Telefone());

				EnderecoDAO end = new EnderecoDAO(conn);
				usuario.setEndereco(end.findById(usuario.getId()));
				if (usuario.getEndereco() == null)
					usuario.setEndereco(new Endereco());

				listaUsuario.add(usuario);
			}

			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Usuario findId(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, " + "  dataaniversario, " + "  ativo, "
							+ "  perfil " + "FROM " + "  public.usuario " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				Date data = rs.getDate("dataaniversario");
				usuario.setDataAniversario(data == null ? null : data.toLocalDate());
				usuario.setSenha(rs.getString("senha"));
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(usuario.getId()));
				if (usuario.getTelefone() == null)
					usuario.setTelefone(new Telefone());

				EnderecoDAO end = new EnderecoDAO(conn);
				usuario.setEndereco(end.findById(usuario.getId()));
				if (usuario.getEndereco() == null)
					usuario.setEndereco(new Endereco());

			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Usuario> findByNome(String nome) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, " + "  dataaniversario, " + "  ativo, "
							+ "  perfil " + "FROM " + "  public.usuario " + "WHERE " + " nome ilike ?");

			stat.setString(1, nome == null ? "%" : "%" + nome + "%");
			ResultSet rs = stat.executeQuery();

			List<Usuario> listaUsuario = new ArrayList<Usuario>();
			Usuario usuario = null;
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				Date data = rs.getDate("dataaniversario");
				usuario.setDataAniversario(data == null ? null : data.toLocalDate());
				usuario.setAtivo(rs.getBoolean("ativo"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				TelefoneDAO dao = new TelefoneDAO(conn);
				usuario.setTelefone(dao.findById(usuario.getId()));
				if (usuario.getTelefone() == null)
					usuario.setTelefone(new Telefone());

				EnderecoDAO end = new EnderecoDAO(conn);
				usuario.setEndereco(end.findById(usuario.getId()));
				if (usuario.getEndereco() == null)
					usuario.setEndereco(new Endereco());
				
				listaUsuario.add(usuario);
			}

			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.Usuario;

public class UsuarioDAO implements DAO<Usuario>{

	@Override
	public Usuario get(Long id) {
		Usuario usuario = new Usuario();
		String sql = "select * from usuario"
				   + " where cod_usuario = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				usuario = new Usuario();

				usuario.setCodUsuario(rset.getLong("cod_usuario"));
				usuario.setUsuario(rset.getString("usuario"));
				usuario.setNomeUsuario(rset.getString("nome"));
				usuario.setIsAdmin((rset.getLong("is_admin")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return usuario;
	}

	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "select * from usuario";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Usuario usuario = new Usuario();

				usuario.setCodUsuario(rset.getLong("cod_usuario"));
				usuario.setUsuario(rset.getString("usuario"));
				usuario.setNomeUsuario(rset.getString("nome"));
				usuario.setIsAdmin((rset.getLong("is_admin")));
				usuarios.add(usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return usuarios;
	}

	@Override
	public int save(Usuario usuario) {
		String sql = "insert into usuario (usuario, senha, nome, is_admin)" + " values (?,SHA2(?,256),?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setString(1, usuario.getUsuario());
			stm.setString(2, usuario.getSenhaUsuario());
			stm.setString(3, usuario.getNomeUsuario());
			stm.setLong(4, usuario.getIsAdmin());
			
			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(Usuario usuario, String[] params) {
		String sql = "update usuario set usuario=?, senha=SHA2(?,256), nome=?, is_admin=?"
				   + " where cod_usuario = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setString(1, usuario.getUsuario());
			stm.setString(2, usuario.getSenhaUsuario());
			stm.setString(3, usuario.getNomeUsuario());
			stm.setLong(4, usuario.getIsAdmin());
			stm.setLong(5, usuario.getCodUsuario());

			stm.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Usuario usuario) {
		String sql = "delete from usuario where cod_usuario = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, usuario.getCodUsuario());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm != null) {
					stm.close();
				}

				if (conexao != null) {
					conexao.close();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}

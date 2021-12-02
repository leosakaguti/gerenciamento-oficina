package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.Servico;

public class ServicoDAO implements DAO<Servico>{

	@Override
	public Servico get(Long id) {
		Servico servico = new Servico();
		String sql = "select * from servico"
				   + " where cod_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				servico = new Servico();

				servico.setCodServico(rset.getLong("cod_servico"));
				servico.setVlrServico(rset.getDouble("valor_servico"));
				servico.setDescServico(rset.getString("descricao_servico"));
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

		return servico;
	}

	@Override
	public List<Servico> getAll() {
		List<Servico> servicos = new ArrayList<Servico>();

		String sql = "select * from servico";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Servico servico = new Servico();

				servico.setCodServico(rset.getLong("cod_servico"));
				servico.setVlrServico(rset.getDouble("valor_servico"));
				servico.setDescServico(rset.getString("descricao_servico"));
				servicos.add(servico);
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
		return servicos;
	}

	@Override
	public int save(Servico servico) {
		String sql = "insert into servico (valor_servico, descricao_servico)" + " values (?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setDouble(1, servico.getVlrServico());
			stm.setString(2, servico.getDescServico());
			
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
	public boolean update(Servico servico, String[] params) {
		String sql = "update servico set valor_servico=?, descricao_servico=?"
				   + " where cod_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setDouble(1, servico.getVlrServico());
			stm.setString(2, servico.getDescServico());
			stm.setLong(3, servico.getCodServico());

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
	public boolean delete(Servico servico) {
		String sql = "delete from servico where cod_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, servico.getCodServico());
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

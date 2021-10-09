package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.OrdemServico;

public class OrdemServicoDAO implements DAO<OrdemServico>{
	
	private VeiculoDAO veiculoDAO;
	
	private UsuarioDAO usuarioDAO;
	
	public OrdemServicoDAO() {
		this.veiculoDAO = new VeiculoDAO();
		this.usuarioDAO = new UsuarioDAO();
	}
	
	@Override
	public OrdemServico get(Long id) {
		OrdemServico ordemServico = new OrdemServico();
		String sql = "select * from ordem_servico"
				   + " where cod_ordem = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				ordemServico = new OrdemServico();

				ordemServico.setCodOrdem(rset.getLong("cod_ordem"));
				ordemServico.setValorTotal(rset.getDouble("valor_total"));
				ordemServico.setUsuario(this.usuarioDAO.get(rset.getLong("cod_usuario")));
				ordemServico.setVeiculo(this.veiculoDAO.get(rset.getLong("cod_veiculo")));
				ordemServico.setStatusOrdem(rset.getLong("status_ordem"));
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

		return ordemServico;
	}

	@Override
	public List<OrdemServico> getAll() {
		List<OrdemServico> ordensServico = new ArrayList<OrdemServico>();

		String sql = "select * from ordem_servico";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				OrdemServico ordemServico = new OrdemServico();

				ordemServico.setCodOrdem(rset.getLong("cod_ordem"));
				ordemServico.setVeiculo(this.veiculoDAO.get(rset.getLong("cod_veiculo")));
				ordemServico.setUsuario(this.usuarioDAO.get(rset.getLong("cod_usuario")));
				ordemServico.setValorTotal(rset.getDouble("valor_total"));
				ordemServico.setStatusOrdem(rset.getLong("status_ordem"));
				ordensServico.add(ordemServico);
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
		return ordensServico;
	}

	@Override
	public int save(OrdemServico ordemServico) {
		String sql = "insert into ordem_servico ( cod_usuario, cod_veiculo, status_ordem)" + " values (?,?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, ordemServico.getUsuario().getCodUsuario());
			stm.setLong(2, ordemServico.getVeiculo().getCodVeiculo());
			stm.setLong(3, 0);
			
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
	public boolean update(OrdemServico ordemServico, String[] params) {
		String sql = "update ordem_servico set cod_usuario = ?, cod_veiculo = ? "
				   + "where cod_ordem = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, ordemServico.getUsuario().getCodUsuario());
			stm.setLong(2, ordemServico.getVeiculo().getCodVeiculo());
			stm.setLong(3, ordemServico.getCodOrdem());

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
	public boolean delete(OrdemServico ordemServico) {
		String sql = "delete from ordem_servico where cod_ordem = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, ordemServico.getCodOrdem());
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

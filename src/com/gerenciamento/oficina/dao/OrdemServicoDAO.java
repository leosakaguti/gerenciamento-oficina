package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.OrdemServico;

public class OrdemServicoDAO implements DAO<OrdemServico>{
	
	private VeiculoDAO veiculoDAO;
	
	public OrdemServicoDAO() {
		this.veiculoDAO = new VeiculoDAO();
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
				ordemServico.setCodUsuario(rset.getLong("cod_usuario"));
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
				ordemServico.setCodUsuario(rset.getLong("cod_usuario"));
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
		String sql = "insert into ordem_servico ( cod_usuario, cod_veiculo, status_ordem, data_emissao)" + " values (?,?,?,?)";
		Date currentDate = new java.sql.Date(new java.util.Date().getTime());
		
		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, ordemServico.getCodUsuario());
			stm.setLong(2, ordemServico.getVeiculo().getCodVeiculo());
			stm.setLong(3, 0);
			stm.setDate(4, currentDate);
			
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
			stm.setLong(1, ordemServico.getCodUsuario());
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

	public Double getValorTotalOrdem(Long cod_ordem) {
		Double valorTotal = Double.parseDouble("0");
		
		String sql = "select valor_servico, qtde from itens_servico"
				   + " where cod_ordem = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, cod_ordem.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {

				valorTotal += rset.getLong("valor_servico") * rset.getLong("qtde");
				System.out.println("valorTotal: "+valorTotal);
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
		
		String sql2 = "select vlr_unit, qtde from itens_produto"
				   + " where cod_ordem = ?";
		
		Connection conn2 = null;

		PreparedStatement stm2 = null;

		ResultSet rset2 = null;

		try {

			conn2 = new Conexao().getConnection();

			stm2 = conn2.prepareStatement(sql2);
			stm2.setInt(1, cod_ordem.intValue());
			rset2 = stm2.executeQuery();

			while (rset2.next()) {

				valorTotal += rset2.getLong("vlr_unit") * rset2.getLong("qtde");
				System.out.println("valorTotal: "+valorTotal);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stm2 != null) {
					stm2.close();
				}

				if (conn2 != null) {
					conn2.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return valorTotal;
	}

}

package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.ItensServico;

public class ItensServicoDAO implements DAO<ItensServico>{
	
	private ServicoDAO servicoDAO;
	
	private OrdemServicoDAO ordemDAO;
	
	public ItensServicoDAO() {
		this.servicoDAO = new ServicoDAO();
		this.ordemDAO = new OrdemServicoDAO();
	}
	
	@Override
	public ItensServico get(Long id) {
		ItensServico itensServico = new ItensServico();
		String sql = "select * from itens_servico"
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
				itensServico = new ItensServico();

				itensServico.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itensServico.setServico(this.servicoDAO.get(rset.getLong("cod_servico")));
				itensServico.setQtde(rset.getLong("qtde"));
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

		return itensServico;
	}

	@Override
	public List<ItensServico> getAll() {
		List<ItensServico> itensServico = new ArrayList<ItensServico>();

		String sql = "select * from itens_servico";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				ItensServico itemServico = new ItensServico();

				itemServico.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itemServico.setServico(this.servicoDAO.get(rset.getLong("cod_servico")));
				itemServico.setQtde(rset.getLong("qtde"));
				itensServico.add(itemServico);
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
		return itensServico;
	}
	
	public List<ItensServico> getAllByOrdem(Long cod_ordem) {
		List<ItensServico> itensServico = new ArrayList<ItensServico>();

		String sql = "select * from itens_servico"
				   + " where cod_ordem = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, cod_ordem);
			rset = stm.executeQuery();

			while (rset.next()) {
				ItensServico itemServico = new ItensServico();

				itemServico.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itemServico.setServico(this.servicoDAO.get(rset.getLong("cod_servico")));
				itemServico.setQtde(rset.getLong("qtde"));
				itensServico.add(itemServico);
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
		return itensServico;
	}
	
	@Override
	public int save(ItensServico itensServico) {
		String sql = "insert into itens_servico ( cod_ordem, cod_servico, qtde, vlr_servico)" + " values (?,?,?,?)";
		
		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, itensServico.getOrdemServico().getCodOrdem());
			stm.setLong(2, itensServico.getServico().getCodServico());
			stm.setLong(3, itensServico.getQtde());
			stm.setDouble(4,  itensServico.getServico().getVlrServico());
			
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
	public boolean update(ItensServico itensServico, String[] params) {
		String sql = "update itens_servico set qtde = ? "
				   + " where cod_ordem = ?"
				   + "   and cod_servico = ?"
				   + "   and vlr_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, itensServico.getQtde());
			stm.setLong(2, itensServico.getOrdemServico().getCodOrdem());
			stm.setLong(3, itensServico.getServico().getCodServico());
			stm.setDouble(4, itensServico.getServico().getVlrServico());

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
	public boolean delete(ItensServico itensServico) {
		String sql = "delete from itens_servico "
				   + " where cod_ordem = ?"
				   + "   and cod_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, itensServico.getOrdemServico().getCodOrdem());
			stm.setLong(2, itensServico.getServico().getCodServico());
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
	
	public boolean VerificaServicoNaTabela(ItensServico itensServico) {
		String sql = "select 1 from itens_servico"
				   + " where cod_ordem = ?"
				   + "   and cod_servico = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, itensServico.getOrdemServico().getCodOrdem());
			stm.setLong(2, itensServico.getServico().getCodServico());
			rset = stm.executeQuery();

			if (rset.next()) {
				return true;
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
		return false;
	}
}

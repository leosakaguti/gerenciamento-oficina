package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.ItensProduto;

public class ItensProdutoDAO implements DAO<ItensProduto>{
	
	private ProdutoDAO produtoDAO;
	
	private OrdemServicoDAO ordemDAO;
	
	public ItensProdutoDAO() {
		this.produtoDAO = new ProdutoDAO();
		this.ordemDAO = new OrdemServicoDAO();
	}
	
	@Override
	public ItensProduto get(Long id) {
		ItensProduto itensProduto = new ItensProduto();
		String sql = "select * from itens_produto"
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
				itensProduto = new ItensProduto();

				itensProduto.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itensProduto.setProduto(this.produtoDAO.get(rset.getLong("cod_prod")));
				itensProduto.setQtdeProd(rset.getLong("qtde"));
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

		return itensProduto;
	}

	@Override
	public List<ItensProduto> getAll() {
		List<ItensProduto> itensProduto = new ArrayList<ItensProduto>();

		String sql = "select * from itens_produto";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				ItensProduto itemProduto = new ItensProduto();

				itemProduto.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itemProduto.setProduto(this.produtoDAO.get(rset.getLong("cod_prod")));
				itemProduto.setQtdeProd(rset.getLong("qtde"));
				itensProduto.add(itemProduto);
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
		return itensProduto;
	}
	
	public List<ItensProduto> getAllByOrdem(Long cod_ordem) {
		List<ItensProduto> itensProduto = new ArrayList<ItensProduto>();

		String sql = "select * from itens_produto"
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
				ItensProduto itemProduto = new ItensProduto();

				itemProduto.setOrdemServico(this.ordemDAO.get(rset.getLong("cod_ordem")));
				itemProduto.setProduto(this.produtoDAO.get(rset.getLong("cod_prod")));
				itemProduto.setQtdeProd(rset.getLong("qtde"));
				itensProduto.add(itemProduto);
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
		return itensProduto;
	}
	
	@Override
	public int save(ItensProduto itensProduto) {
		String sql = "insert into itens_produto ( cod_ordem, cod_prod, qtde)" + " values (?,?,?)";
		
		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, itensProduto.getOrdemServico().getCodOrdem());
			stm.setLong(2, itensProduto.getProduto().getCodProd());
			stm.setLong(3, itensProduto.getQtdeProd());
			
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
	public boolean update(ItensProduto itensProduto, String[] params) {
		String sql = "update itens_produto set qtde = ? "
				   + " where cod_ordem = ?"
				   + "   and cod_prod = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, itensProduto.getQtdeProd());
			stm.setLong(2, itensProduto.getOrdemServico().getCodOrdem());
			stm.setLong(3, itensProduto.getProduto().getCodProd());

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
	public boolean delete(ItensProduto itensProduto) {
		String sql = "delete from itens_produto "
				   + " where cod_ordem = ?"
				   + "   and cod_prod = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, itensProduto.getOrdemServico().getCodOrdem());
			stm.setLong(2, itensProduto.getProduto().getCodProd());
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

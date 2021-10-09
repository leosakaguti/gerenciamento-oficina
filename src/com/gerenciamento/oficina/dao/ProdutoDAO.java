package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.Produto;

public class ProdutoDAO implements DAO<Produto>{

	@Override
	public Produto get(Long id) {
		Produto produto = new Produto();
		String sql = "select * from produto"
				   + " where cod_prod = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				produto = new Produto();

				produto.setCodProd(rset.getLong("cod_prod"));
				produto.setVlrUnit(rset.getDouble("vlr_unit"));
				produto.setNomeProduto(rset.getString("nome"));
				produto.setFornecedor(rset.getString("fornecedor"));
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

		return produto;
	}

	@Override
	public List<Produto> getAll() {
		List<Produto> produtos = new ArrayList<Produto>();

		String sql = "select * from produto";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Produto produto = new Produto();

				produto.setCodProd(rset.getLong("cod_prod"));
				produto.setVlrUnit(rset.getDouble("vlr_unit"));
				produto.setNomeProduto(rset.getString("nome"));
				produto.setFornecedor(rset.getString("fornecedor"));
				produtos.add(produto);
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
		return produtos;
	}

	@Override
	public int save(Produto produto) {
		String sql = "insert into produto (vlr_unit, nome, fornecedor)" + " values (?,?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setDouble(1, produto.getVlrUnit());
			stm.setString(2, produto.getNomeProduto());
			stm.setString(3, produto.getFornecedor());
			
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
	public boolean update(Produto produto, String[] params) {
		String sql = "update produto set vlr_unit=?, nome=?, fornecedor=?"
				   + " where cod_prod = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setDouble(1, produto.getVlrUnit());
			stm.setString(2, produto.getNomeProduto());
			stm.setString(3, produto.getFornecedor());
			stm.setLong(4, produto.getCodProd());

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
	public boolean delete(Produto produto) {
		String sql = "delete from produto where cod_prod = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, produto.getCodProd());
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

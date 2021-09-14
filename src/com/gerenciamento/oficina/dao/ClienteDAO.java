package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.Cliente;

public class ClienteDAO implements DAO<Cliente>{

	@Override
	public Cliente get(Long id) {
		Cliente cliente = new Cliente();
		String sql = "select * from cliente"
				   + " where cod_cliente = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				cliente = new Cliente();

				cliente.setCodCliente(rset.getLong("cod_cliente"));
				cliente.setNomeCliente(rset.getString("nome"));
				cliente.setCpf(Long.parseLong(rset.getString("cpf_cnpj")));
				cliente.setUnidadeFederativa(rset.getString("uf"));
				cliente.setNumContato(rset.getString("num_contato"));
				cliente.setEnderecoCliente(rset.getString("endereco"));
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

		return cliente;
	}

	@Override
	public List<Cliente> getAll() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		String sql = "select * from cliente";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Cliente cliente = new Cliente();

				cliente.setCodCliente(rset.getLong("cod_cliente"));
				cliente.setNomeCliente(rset.getString("nome"));
				cliente.setCpf(Long.parseLong(rset.getString("cpf_cnpj")));
				cliente.setUnidadeFederativa(rset.getString("uf"));
				cliente.setNumContato(rset.getString("num_contato"));
				cliente.setEnderecoCliente(rset.getString("endereco"));
				clientes.add(cliente);
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
		return clientes;
	}

	@Override
	public int save(Cliente cliente) {
		String sql = "insert into cliente (cod_cliente, nome, cpf_cnpj, uf, num_contato, endereco)" + " values (?,?,?,?,?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, cliente.getCodCliente());
			stm.setString(2, cliente.getNomeCliente());
			stm.setLong(3, cliente.getCpf());
			stm.setString(4, cliente.getUnidadeFederativa());
			stm.setString(5, cliente.getNumContato());
			stm.setString(6, cliente.getEnderecoCliente());
			
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
	public boolean update(Cliente cliente, String[] params) {
		String sql = "update cliente set cod_cliente=?, nome=?, cpf_cnpj=?, uf=?, num_contato=?, endereco=?"
				   + " where cod_cliente = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, cliente.getCodCliente());
			stm.setString(2, cliente.getNomeCliente());
			stm.setLong(3, cliente.getCpf());
			stm.setString(4, cliente.getUnidadeFederativa());
			stm.setString(5, cliente.getNumContato());
			stm.setString(6, cliente.getEnderecoCliente());
			stm.setLong(7, cliente.getCodCliente());

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
	public boolean delete(Cliente cliente) {
		String sql = "delete from cliente where cod_cliente = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, cliente.getCodCliente());
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

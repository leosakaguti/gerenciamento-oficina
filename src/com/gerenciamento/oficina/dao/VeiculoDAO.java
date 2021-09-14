package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gerenciamento.oficina.entity.Veiculo;

public class VeiculoDAO implements DAO<Veiculo>{

	private ClienteDAO clienteDAO;
	
	public VeiculoDAO() {
		this.clienteDAO = new ClienteDAO();
	}
	
	@Override
	public Veiculo get(Long id) {
		Veiculo veiculo = new Veiculo();
		String sql = "select * from veiculo"
				   + " where cod_veiculo = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id.intValue());
			rset = stm.executeQuery();

			while (rset.next()) {
				veiculo = new Veiculo();

				veiculo.setCodVeiculo(rset.getLong("cod_veiculo"));
				veiculo.setCliente(this.clienteDAO.get(rset.getLong("cod_cliente")));
				veiculo.setPlacaVeiculo(rset.getString("placa_carro"));
				veiculo.setCorVeiculo(rset.getString("cor_carro"));
				veiculo.setModeloVeiculo(rset.getString("modelo_carro"));
				veiculo.setAnoVeiculo(rset.getDate("ano"));
				veiculo.setMarcaVeiculo(rset.getString("marca"));
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

		return veiculo;
	}

	@Override
	public List<Veiculo> getAll() {
		List<Veiculo> veiculos = new ArrayList<Veiculo>();

		String sql = "select * from veiculo";

		Connection conexao = null;

		PreparedStatement stm = null;

		ResultSet rset = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			rset = stm.executeQuery();

			while (rset.next()) {
				Veiculo veiculo = new Veiculo();

				veiculo.setCodVeiculo(rset.getLong("cod_veiculo"));
				veiculo.setCliente(this.clienteDAO.get(rset.getLong("cod_cliente")));
				veiculo.setPlacaVeiculo(rset.getString("placa_carro"));
				veiculo.setCorVeiculo(rset.getString("cor_carro"));
				veiculo.setModeloVeiculo(rset.getString("modelo_carro"));
				veiculo.setAnoVeiculo(rset.getDate("ano"));
				veiculo.setMarcaVeiculo(rset.getString("marca"));
				veiculos.add(veiculo);
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
		return veiculos;
	}

	@Override
	public int save(Veiculo veiculo) {
		String sql = "insert into veiculo (cod_veiculo, cod_cliente, placa_carro, cor_carro, modelo_carro, ano, marca)" + " values (?,?,?,?,?,?,?)";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {

			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);

			stm.setLong(1, veiculo.getCodVeiculo());
			stm.setLong(2, veiculo.getCliente().getCodCliente());
			stm.setString(3, veiculo.getPlacaVeiculo());
			stm.setString(4, veiculo.getCorVeiculo());
			stm.setString(5, veiculo.getModeloVeiculo());
			stm.setDate(6, veiculo.getAnoVeiculo());
			stm.setString(7, veiculo.getMarcaVeiculo());
			
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
	public boolean update(Veiculo veiculo, String[] params) {
		String sql = "update veiculo set cod_veiculo = ?, cod_cliente = ?, placa_carro = ?, "
				   + "cor_carro = ?, modelo_carro = ?, ano = ?, marca = ? where cod_veiculo = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, veiculo.getCodVeiculo());
			stm.setLong(2, veiculo.getCliente().getCodCliente());
			stm.setString(3, veiculo.getPlacaVeiculo());
			stm.setString(4, veiculo.getCorVeiculo());
			stm.setString(5, veiculo.getModeloVeiculo());
			stm.setDate(6, veiculo.getAnoVeiculo());
			stm.setString(7, veiculo.getMarcaVeiculo());
			stm.setLong(8, veiculo.getCodVeiculo());

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
	public boolean delete(Veiculo veiculo) {
		String sql = "delete from veiculo where cod_veiculo = ?";

		Connection conexao = null;

		PreparedStatement stm = null;

		try {
			conexao = new Conexao().getConnection();

			stm = conexao.prepareStatement(sql);
			stm.setLong(1, veiculo.getCodVeiculo());
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

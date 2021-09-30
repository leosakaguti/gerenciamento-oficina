package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.entity.Cliente;
import com.gerenciamento.oficina.entity.Veiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class VeiculoEditController implements Initializable{
	
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private ComboBox<Cliente> cbxCliente;

    @FXML
    private Label lblClienteResp;

    @FXML
    private Label lblPlaca;

    @FXML
    private TextField fieldPlaca;

    @FXML
    private TextField fieldCor;

    @FXML
    private TextField fieldModelo;
    
    @FXML
    private TextField fieldAno;
    
    @FXML
    private Label lblAno;

    @FXML
    private Label lblModelo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    
    private Stage janelaVeiculoEdit;
    
    private Veiculo veiculo;

	private boolean okClick = false;
	
	private ObservableList<Cliente> obsListClientes = FXCollections.observableArrayList();
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.janelaVeiculoEdit.close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.veiculo.setCliente(this.cbxCliente.getSelectionModel().getSelectedItem());
			this.veiculo.setPlacaVeiculo(this.fieldPlaca.getText());
			this.veiculo.setAnoVeiculo(Long.parseLong(this.fieldAno.getText()));
			this.veiculo.setMarcaModeloVeiculo(this.fieldModelo.getText());
			this.veiculo.setCorVeiculo(this.fieldCor.getText());
			this.okClick = true;
			this.getJanelaVeiculoEdit().close();
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.carregarComboBoxClientes();
		obsListClientes.setAll(carregarComboBoxClientes());
		cbxCliente.setConverter(new StringConverter<Cliente>() {
			
			@Override
			public String toString(Cliente cliente) {
				if (cliente == null) {
                    return "Select";
                } else {
                    return String.valueOf(cliente.getCodCliente())+" - "+cliente.getNomeCliente();
                }
			}
			
			@Override
			public Cliente fromString(String string) {
				return null;
			}
		});
		cbxCliente.setItems(obsListClientes);
	}

	public Stage getJanelaVeiculoEdit() {
		return janelaVeiculoEdit;
	}

	public void setJanelaVeiculoEdit(Stage janelaVeiculoEdit) {
		this.janelaVeiculoEdit = janelaVeiculoEdit;
	}
	
	public void populaTela(Veiculo veiculo) {
		this.veiculo = veiculo;

		if (this.veiculo.getCliente() != null) {
			this.cbxCliente.setValue(this.veiculo.getCliente());
		}

		if (this.veiculo.getPlacaVeiculo() != null) {
			this.fieldPlaca.setText(this.veiculo.getPlacaVeiculo());
		}
		
		if (this.veiculo.getAnoVeiculo() != null) {
			this.fieldAno.setText(String.valueOf(this.veiculo.getAnoVeiculo()));
		}
		
		if (this.veiculo.getPlacaVeiculo() != null) {
			this.fieldModelo.setText(this.veiculo.getMarcaModeloVeiculo());
		}
		
		if (this.veiculo.getCorVeiculo() != null) {
			this.fieldCor.setText(this.veiculo.getCorVeiculo());
		}
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.cbxCliente.getValue() == null) {
			mensagemErros += "Informe o cliente responsável pelo veículo!\n";
		}
		
		if (this.fieldPlaca.getText() == null) {
			mensagemErros += "Informe a placa do veículo!\n";
		}
		
		if (this.fieldAno.getText() == null) {
			mensagemErros += "Informe o ano do veículo!\n";
		}
		
		if (this.fieldModelo.getText() == null) {
			mensagemErros += "Informe a marca e o modelo do veículo!\n";
		}
		
		if (this.fieldCor.getText() == null) {
			mensagemErros += "Informe a cor do veículo!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaVeiculoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
	
	public List<Cliente> carregarComboBoxClientes() {
		List<Cliente> items = new ArrayList<>();

		String query = "SELECT cod_cliente, nome FROM cliente";

		try {
			connection = new Conexao().getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodCliente(rs.getLong("cod_cliente"));
				cliente.setNomeCliente(rs.getString("nome"));

				items.add(cliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return items;
	}

}

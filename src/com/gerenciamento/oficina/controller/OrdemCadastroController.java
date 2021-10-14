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
import com.gerenciamento.oficina.entity.OrdemServico;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class OrdemCadastroController implements Initializable{
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<Veiculo> cbxVeiculo;

    @FXML
    private Label lblVeiculoOrdem;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaOrdemCad;
    
    private OrdemServico ordemServico;

	private boolean okClick = false;
	
	private ObservableList<Veiculo> obsListVeiculos = FXCollections.observableArrayList();
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.janelaOrdemCad.close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.ordemServico.setVeiculo(this.cbxVeiculo.getSelectionModel().getSelectedItem());
			
			this.okClick = true;
			this.getJanelaOrdemCad().close();
    	}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.carregarComboBoxVeiculo();
		obsListVeiculos.setAll(carregarComboBoxVeiculo());
		cbxVeiculo.setConverter(new StringConverter<Veiculo>() {
			
			@Override
			public String toString(Veiculo veiculo) {
				if (veiculo == null) {
                    return "Select";
                } else {
                    return String.valueOf(veiculo.getCodVeiculo())+" - "+veiculo.getPlacaVeiculo();
                }
			}
			
			@Override
			public Veiculo fromString(String string) {
				return null;
			}
		});
		cbxVeiculo.setItems(obsListVeiculos);		
	}

	public Stage getJanelaOrdemCad() {
		return janelaOrdemCad;
	}

	public void setJanelaOrdemCad(Stage janelaOrdemCad) {
		this.janelaOrdemCad = janelaOrdemCad;
	}
	
	public void populaTela(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
		
		if (this.ordemServico.getVeiculo() != null) {
			this.cbxVeiculo.setValue(this.ordemServico.getVeiculo());
		}
	}
	
	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.cbxVeiculo.getValue() == null) {
			mensagemErros += "Informe o veículo da ordem de serviço!\n";
		}
		
		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaOrdemCad);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações:");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
	
	public List<Veiculo> carregarComboBoxVeiculo() {
		List<Veiculo> items = new ArrayList<>();

		String query = "SELECT cod_veiculo, placa_carro FROM veiculo";

		try {
			connection = new Conexao().getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while (rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setCodVeiculo(rs.getLong("cod_veiculo"));
				veiculo.setPlacaVeiculo(rs.getString("placa_carro"));

				items.add(veiculo);
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

package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable{
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Label lblNomeSistema;

    @FXML
    private GridPane pnlBotoes;

    @FXML
    private Button btnVeiculo;

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnFornecedor;

    @FXML
    private Button btnProduto;

    @FXML
    private Button btnOrdem;
    
    @FXML
    private Button btnUsuarios;
    
    private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
    
    @FXML
    void onClickBtnCliente(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/ClienteMenu.fxml"));
			Parent clienteListaXML = loader.load();

			ListaClientesController listaClienteController = loader.getController();
			Scene clienteListaLayout = new Scene(clienteListaXML);

			this.getStage().setScene(clienteListaLayout);
			this.getStage().setTitle("Consulta de Clientes");

			this.getStage().setOnCloseRequest(e -> {
				if (listaClienteController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClickBtnFornecedor(ActionEvent event) {

    }

    @FXML
    void onClickBtnOrdem(ActionEvent event) {

    }

    @FXML
    void onClickBtnProduto(ActionEvent event) {
    
    }
    
    @FXML
    void onClickBtnUsuarios(ActionEvent event) {

    }

    @FXML
    void onClickBtnVeiculo(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configuraStage();		
	}
	
	public void configuraStage() {
		this.setStage(new Stage());
		this.getStage().initModality(Modality.APPLICATION_MODAL);
		this.getStage().resizableProperty().setValue(Boolean.FALSE);
	}
}

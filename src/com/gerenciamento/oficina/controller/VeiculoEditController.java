package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class VeiculoEditController implements Initializable{
	
	
	private Stage janelaVeiculoEdit;

	//private Veiculo veiculo;

	//private boolean okClick = false;
	
	//private ObservableList<Cliente> obsListClientes = FXCollections.observableArrayList();
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public Stage getJanelaVeiculoEdit() {
		return janelaVeiculoEdit;
	}

	public void setJanelaVeiculoEdit(Stage janelaVeiculoEdit) {
		this.janelaVeiculoEdit = janelaVeiculoEdit;
	}

}

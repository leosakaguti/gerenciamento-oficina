package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginMenuController implements Initializable{
	
	@FXML
    private TextField fieldUsuario;
	
    @FXML
    private PasswordField fieldSenha;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblSenha;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Button btnCancelar;
    
    private Stage stage;
	
    @FXML
    void onClickBtnCancelar(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    void onClickBtnLogin(ActionEvent event) throws SQLException {
    	String user = fieldUsuario.getText();
    	String password = fieldSenha.getText();
    	
    	if(user.equals("") || password.equals("")) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usu·rio ou senha em branco!");
            alert.showAndWait();

            fieldUsuario.setText("");
            fieldSenha.setText("");
            this.fieldUsuario.requestFocus();
    	}else {
    		Connection conn1 = null;
    		PreparedStatement stm1 = null;
    		ResultSet rs1 = null;
    		
    		String sql1 = "select cod_usuario from usuario"
		   				+ "	where usuario = ?"
		   				+ "   and senha = SHA2(?, 256)";
    		
	    	try {
				conn1 = new Conexao().getConnection();
				
				stm1 = conn1.prepareStatement(sql1);
				stm1.setString(1, user);
				stm1.setString(2, password);
				
				rs1 = stm1.executeQuery();
				
				if (rs1.next()) {
					Connection conn2 = null;
		    		PreparedStatement stm2 = null;
		    		
		    		System.setProperty("usuario", user);
		    		System.out.println(System.getProperty("usuario"));
		    		String sql2 = "update usuario set logado = 1 "
		    					+ "where usuario = ?";
		    		
		    		try{
		    			conn2 = new Conexao().getConnection();
		    			
		    			stm2 = conn2.prepareStatement(sql2);
			    		stm2.setString(1, user);
			    		
			    		stm2.execute();
			    		stm2.close();
		    		}catch (Exception e) {
		    			e.printStackTrace();
		    		}
		    		
		    		
		    				
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Menu.fxml"));
	                Parent menuXML = loader.load();
	
	                Scene menuLayout = new Scene(menuXML);

	                this.getStage().setScene(menuLayout);
	                this.getStage().setTitle("Gerenciamento de Oficina");
	                this.getStage().initModality(Modality.WINDOW_MODAL);
	                this.getStage().setResizable(true);
	                this.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
	                    @Override
	                    public void handle(WindowEvent event) {
	                    	Connection conn1 = null;
	                		PreparedStatement stm1 = null;
	                		
	                		String sql1 = "update usuario set logado = 0 "
	                					+ "where usuario = ?";
	                		
	                		try{
	                			conn1 = new Conexao().getConnection();
	                			
	                			stm1 = conn1.prepareStatement(sql1);
	                			stm1.setString(1, System.getProperty("usuario"));
	                			
	            	    		stm1.execute();
	            	    		stm1.close();
	            	    		Platform.exit();
		                        System.exit(0);
	                		}catch (Exception e) {
	                			e.printStackTrace();
	                		}
	                        
	                    }
	                });
	                
	                this.stage.show();
	                stm1.close();
	            } else {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Erro");
	                alert.setHeaderText("Usu√°rio ou senha incorretos!");
	                alert.showAndWait();
	
	                fieldUsuario.setText("");
	                fieldSenha.setText("");
	                this.fieldUsuario.requestFocus();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
    }
    
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void configuraStage() {
        this.setStage(new Stage());
        this.getStage().initModality(Modality.APPLICATION_MODAL);
        this.getStage().resizableProperty().setValue(Boolean.FALSE);
    }
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        this.configuraStage();
    }
}

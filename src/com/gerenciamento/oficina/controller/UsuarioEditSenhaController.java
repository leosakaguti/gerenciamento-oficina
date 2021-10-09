package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.entity.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsuarioEditSenhaController implements Initializable{
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private PasswordField fieldSenhaAtual;

    @FXML
    private PasswordField fieldSenhaNova;

    @FXML
    private Label lblSenhaAtual;

    @FXML
    private Label lblSenhaNova;

    @FXML
    private Label lblUsuarioSenhaAlt;

    @FXML
    private Label lblUsuarioSenhaAltValue;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaUsuarioEditSenha;

	private Usuario usuario;

	private boolean okClick = false;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaUsuarioEditSenha().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarSenhaAtual()) {
    		System.out.println("senha nova: "+fieldSenhaNova.getText());
			this.usuario.setSenhaUsuario(this.fieldSenhaNova.getText());
			System.out.println("setou senha usuario");
			this.okClick = true;
			this.getJanelaUsuarioEditSenha().close();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public Stage getJanelaUsuarioEditSenha() {
		return janelaUsuarioEditSenha;
	}

	public void setJanelaUsuarioEditSenha(Stage janelaUsuarioEditSenha) {
		this.janelaUsuarioEditSenha = janelaUsuarioEditSenha;
	}

	public boolean isOkClick(){
		return okClick;
	}
	
	private boolean validarSenhaAtual() {
		String sql = "select 1 from usuario "
					+"	where usuario = ? "
					+"	 and  senha = SHA2(?,256)";
	
		try {
			connection = new Conexao().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getUsuario());
			preparedStatement.setString(2, fieldSenhaAtual.getText());
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				System.out.println("senha válida");
				return true;
			}else {
				Alert alerta = new Alert(Alert.AlertType.ERROR);
				alerta.initOwner(this.janelaUsuarioEditSenha);
				alerta.setTitle("Dados Inválidos!");
				alerta.setHeaderText("A senha atual está incorreta!");
				alerta.setContentText("Digite a senha atual corretamente para alterar a senha.");
				alerta.showAndWait();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setUsuarioTelaSenha(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair da tela de alteração da senha?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}
}

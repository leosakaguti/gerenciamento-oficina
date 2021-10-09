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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsuarioEditController implements Initializable{
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField fieldIsAdmin;

    @FXML
    private TextField fieldNomeUsuario;

    @FXML
    private TextField fieldUsuario;

    @FXML
    private Label lblIsAdmin;

    @FXML
    private Label lblNomeUsuario;

    @FXML
    private Label lblUsuario;
    
    @FXML
    private PasswordField fieldSenha;
    
    @FXML
    private Label lblSenha;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaUsuarioEdit;

	private Usuario usuario;

	private boolean okClick = false;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaUsuarioEdit().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.usuario.setUsuario(this.fieldUsuario.getText());
			this.usuario.setNomeUsuario(this.fieldNomeUsuario.getText());
			this.usuario.setIsAdmin(Long.parseLong(this.fieldIsAdmin.getText()));
			this.usuario.setSenhaUsuario(this.fieldSenha.getText());

			this.okClick = true;
			this.getJanelaUsuarioEdit().close();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public Stage getJanelaUsuarioEdit() {
		return janelaUsuarioEdit;
	}

	public void setJanelaUsuarioEdit(Stage janelaUsuarioEdit) {
		this.janelaUsuarioEdit = janelaUsuarioEdit;
	}
	
	public void setUsuarioTelaCadastro(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void populaTela(Usuario usuario) {
		this.usuario = usuario;

		this.fieldUsuario.setText(usuario.getUsuario());
		this.fieldNomeUsuario.setText(usuario.getNomeUsuario());
		this.fieldIsAdmin.setText(String.valueOf(usuario.getIsAdmin()));
	}

	public boolean isOkClick(){
		return okClick;
	}
	
	private boolean validarUsuario() {
		String sql = "select 1 from usuario "
					+"where usuario = ?";
		
		try {
			connection = new Conexao().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, fieldUsuario.getText());
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				return false;
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean validarNomeUsuario() {
		String sql = "select 1 from usuario "
					+"where nome = ?";
		
		try {
			connection = new Conexao().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, fieldNomeUsuario.getText());
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				return false;
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.fieldUsuario.getText() == null || this.fieldUsuario.getText().trim().length() == 0) {
			mensagemErros += "Informe o usuário!\n";
		}
		if (this.fieldNomeUsuario.getText() == null || this.fieldNomeUsuario.getText().trim().length() == 0) {
			mensagemErros += "Informe o nome do Usuário!\n";
		}
		if(!validarUsuario()) {
			mensagemErros += "Este usuário já existe!\n";
		}
		if(!validarNomeUsuario()) {
			mensagemErros += "Este nome de usuário já existe!\n";
		}
		if (this.fieldIsAdmin.getText() == null || this.fieldIsAdmin.getText().trim().length() == 0) {
			mensagemErros += "Informe se o usuário é admin ou não!\n";
		}
		if (this.fieldSenha.getText() == null || this.fieldSenha.getText().trim().length() < 4) {
			mensagemErros += "Informe a senha antiga ou uma nova de ao menos 4 dígitos!\n";
		}
		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaUsuarioEdit);
			alerta.setTitle("Dados Inválidos!");
			alerta.setHeaderText("Favor corrigir as seguintes informações");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
	
	public void verificaOperacao(String operacao) {
		if(operacao.equals(" - Editar")){
			fieldSenha.setEditable(false);
			fieldSenha.setDisable(true);
		}
	}
	
	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair da tela de edição?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}
}

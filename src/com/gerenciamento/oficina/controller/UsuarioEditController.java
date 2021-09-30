package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private PasswordField fieldSenha;

    @FXML
    private TextField fieldUsuario;

    @FXML
    private Label lblIsAdmin;

    @FXML
    private Label lblNomeUsuario;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblUsuario;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaUsuarioEdit;

	private Usuario usuario;

	private boolean okClick = false;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaUsuarioEdit().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.usuario.setUsuario(this.fieldUsuario.getText());
			this.usuario.setSenhaUsuario(this.fieldSenha.getText());
			this.usuario.setNomeUsuario(this.fieldNomeUsuario.getText());
			this.usuario.setIsAdmin(Long.parseLong(this.fieldIsAdmin.getText()));

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

	public void populaTela(Usuario usuario) {
		this.usuario = usuario;

		this.fieldUsuario.setText(usuario.getUsuario());
		this.fieldSenha.setText(usuario.getSenhaUsuario());
		this.fieldNomeUsuario.setText(usuario.getNomeUsuario());
		this.fieldIsAdmin.setText(String.valueOf(usuario.getIsAdmin()));
	}
	
	public boolean isOkClick(){
		return okClick;
	}
	
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.fieldUsuario.getText() == null || this.fieldUsuario.getText().trim().length() == 0) {
			mensagemErros += "Informe o usuário!\n";
		}
		if (this.fieldSenha.getText() == null || this.fieldSenha.getText().trim().length() == 0) {
			mensagemErros += "Informe a senha!\n";
		}
		if (this.fieldNomeUsuario.getText() == null || this.fieldNomeUsuario.getText().trim().length() == 0) {
			mensagemErros += "Informe o nome do Usuário!\n";
		}
		if (this.fieldIsAdmin.getText() == null || this.fieldIsAdmin.getText().trim().length() == 0) {
			mensagemErros += "Informe se o usuário é admin ou não!\n";
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

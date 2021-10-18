package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.entity.Cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClienteEditController implements Initializable{
	
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TextField fieldNome;

    @FXML
    private Label lblNome;

    @FXML
    private TextField fieldCPF;

    @FXML
    private TextField fieldUF;

    @FXML
    private TextField fieldNumContato;

    @FXML
    private TextField fieldEndereco;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblUF;

    @FXML
    private Label lblNumContato;

    @FXML
    private Label lblEndereco;
    
    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    
    private Stage janelaClienteEdit;

	private Cliente cliente;

	private boolean okClick = false;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaClienteEdit().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.cliente.setNomeCliente(this.fieldNome.getText());
			this.cliente.setCpf(Long.parseLong(this.fieldCPF.getText()));
			this.cliente.setUnidadeFederativa(this.fieldUF.getText());
			this.cliente.setNumContato(this.fieldNumContato.getText());
			this.cliente.setEnderecoCliente(this.fieldEndereco.getText());

			this.okClick = true;
			this.getJanelaClienteEdit().close();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}
	
	public Stage getJanelaClienteEdit() {
		return janelaClienteEdit;
	}

	public void setJanelaClienteEdit(Stage janelaClienteEdit) {
		this.janelaClienteEdit = janelaClienteEdit;
	}

	public void populaTela(Cliente cliente) {
		this.cliente = cliente;

		this.fieldNome.setText(cliente.getNomeCliente());
		this.fieldCPF.setText(String.valueOf(cliente.getCpf()));
		this.fieldUF.setText(cliente.getUnidadeFederativa());
		this.fieldNumContato.setText(cliente.getNumContato());
		this.fieldEndereco.setText(cliente.getEnderecoCliente());
	}
	
	public boolean isOkClick(){
		return okClick;
	}
	
	public void setClienteTelaEdit(Cliente cliente) {
		this.cliente = cliente;
	}
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.fieldNome.getText() == null || this.fieldNome.getText().trim().length() == 0) {
			mensagemErros += "Informe o nome!\n";
		}
		if (this.fieldCPF.getText() == null || this.fieldCPF.getText().trim().length() == 0) {
			mensagemErros += "Informe o CPF!\n";
		}
		if (this.fieldUF.getText() == null || this.fieldUF.getText().trim().length() == 0) {
			mensagemErros += "Informe a UF!\n";
		}
		if (this.fieldNumContato.getText() == null || this.fieldNumContato.getText().trim().length() == 0) {
			mensagemErros += "Informe o número de contato!\n";
		}
		if (this.fieldEndereco.getText() == null || this.fieldEndereco.getText().trim().length() == 0) {
			mensagemErros += "Informe o endereço!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaClienteEdit);
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

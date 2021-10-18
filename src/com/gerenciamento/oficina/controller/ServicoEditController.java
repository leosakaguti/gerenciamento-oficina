package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.entity.Servico;

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

public class ServicoEditController implements Initializable{
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField fieldDescServico;

    @FXML
    private TextField fieldVlrServico;

    @FXML
    private Label lblDescServico;

    @FXML
    private Label lblVlrServ;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaServicoEdit;

	private Servico servico;

	private boolean okClick = false;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaServicoEdit().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.servico.setDescServico(this.fieldDescServico.getText());
			this.servico.setVlrServico(Double.parseDouble(this.fieldVlrServico.getText().replaceAll(",", ".")));

			this.okClick = true;
			this.getJanelaServicoEdit().close();
		}
    }
    
	public Stage getJanelaServicoEdit() {
		return janelaServicoEdit;
	}

	public void setJanelaServicoEdit(Stage janelaServicoEdit) {
		this.janelaServicoEdit = janelaServicoEdit;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	public void populaTelaCadastro(Servico servico) {
		this.servico = servico;
		this.fieldVlrServico.setText(String.valueOf(0.00));
	}
	public void populaTela(Servico servico) {
		this.servico = servico;

		this.fieldDescServico.setText(servico.getDescServico());
		this.fieldVlrServico.setText(String.valueOf(servico.getVlrServico()));
	}
	
	public boolean isOkClick(){
		return okClick;
	}
	
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.fieldDescServico.getText() == null || this.fieldDescServico.getText().trim().length() == 0) {
			mensagemErros += "Informe a descrição do serviço!\n";
		}
		if (this.fieldVlrServico.getText() == null || this.fieldVlrServico.getText().trim().length() == 0 && Double.parseDouble(this.fieldVlrServico.getText()) == 0) {
			mensagemErros += "Informe o valor do serviço!\n";
		}
		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaServicoEdit);
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
		alerta.setHeaderText("Deseja sair da tela de edição/inclusão do serviço?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}
}

package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.entity.Produto;

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

public class ProdutoEditController implements Initializable{
	
	@FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField fieldFornecedor;

    @FXML
    private TextField fieldNomeProduto;

    @FXML
    private TextField fieldVlrUnit;

    @FXML
    private Label lblFornecedor;

    @FXML
    private Label lblNomeProduto;

    @FXML
    private Label lblVlrUnit;

    @FXML
    private AnchorPane pnlPrincipal;
    
    private Stage janelaProdutoEdit;

	private Produto produto;

	private boolean okClick = false;

    @FXML
    void onClickBtnCancelar(ActionEvent event) {
    	this.getJanelaProdutoEdit().close();
    }

    @FXML
    void onClickBtnSalvar(ActionEvent event) {
    	if (validarCampos()) {
			this.produto.setFornecedor(this.fieldFornecedor.getText());
			this.produto.setVlrUnit(Double.parseDouble(this.fieldVlrUnit.getText().replaceAll(",", ".")));
			this.produto.setNomeProduto(this.fieldNomeProduto.getText());

			this.okClick = true;
			this.getJanelaProdutoEdit().close();
		}
    }
    
	public Stage getJanelaProdutoEdit() {
		return janelaProdutoEdit;
	}

	public void setJanelaProdutoEdit(Stage janelaProdutoEdit) {
		this.janelaProdutoEdit = janelaProdutoEdit;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	public void populaTelaCadastro(Produto produto) {
		this.produto = produto;
		this.fieldVlrUnit.setText(String.valueOf(0.00));
	}
	public void populaTela(Produto produto) {
		this.produto = produto;

		this.fieldFornecedor.setText(produto.getFornecedor());
		this.fieldVlrUnit.setText(String.valueOf(produto.getVlrUnit()));
		this.fieldNomeProduto.setText(produto.getNomeProduto());
	}
	
	public boolean isOkClick(){
		return okClick;
	}
	
	private boolean validarCampos() {
		String mensagemErros = new String();

		if (this.fieldFornecedor.getText() == null || this.fieldFornecedor.getText().trim().length() == 0) {
			mensagemErros += "Informe o fornecedor do produto!\n";
		}
		if (this.fieldVlrUnit.getText() == null || this.fieldVlrUnit.getText().trim().length() == 0 && Double.parseDouble(this.fieldVlrUnit.getText()) == 0) {
			mensagemErros += "Informe o valor unitário do produto!\n";
		}
		if (this.fieldNomeProduto.getText() == null || this.fieldNomeProduto.getText().trim().length() == 0) {
			mensagemErros += "Informe o nome do produto!\n";
		}
		if (mensagemErros.length() == 0) {
			return true;
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaProdutoEdit);
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
		alerta.setHeaderText("Deseja sair da tela de edição/inclusão do produto?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alerta.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alerta.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}
	
}

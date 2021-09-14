package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.VeiculoDAO;
import com.gerenciamento.oficina.entity.Veiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListaVeiculosController implements Initializable{
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private SplitPane splitPaneVeiculos;

    @FXML
    private AnchorPane pnlCima;

    @FXML
    private Label lblPlaca;

    @FXML
    private TextField fieldPlaca;

    @FXML
    private Button btnFiltrarPlaca;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private AnchorPane pnlBaixo;
    
    private List<Veiculo> listaVeiculos;

	private ObservableList<Veiculo> observableListaVeiculos = FXCollections.observableArrayList();

	private VeiculoDAO veiculoDAO;

	public static final String VEICULO_EDITAR = " - Editar";

	public static final String VEICULO_INCLUIR = " - Incluir";

	private Stage stage;
	
    @FXML
    private TableView<Veiculo> tbvVeiculos;

    @FXML
    private TableColumn<Veiculo, Long> tbcCodVeic;

    @FXML
    private TableColumn<Veiculo, Long> tbcCodCliente;

    @FXML
    private TableColumn<Veiculo, String> tbcNomeCliente;

    @FXML
    private TableColumn<Veiculo, String> tbcPlaca;

    @FXML
    private TableColumn<Veiculo, String> tbcModelo;

    @FXML
    private TableColumn<Veiculo, String> tbcCor;

    @FXML
    private TableColumn<Veiculo, Date> tbcAno;

    @FXML
    private TableColumn<Veiculo, String> tbcMarca;

    @FXML
    void onClickBtnEditar(ActionEvent event) {

    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {

    }

    @FXML
    void onClickBtnFiltrarPlaca(ActionEvent event) {

    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {

    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}

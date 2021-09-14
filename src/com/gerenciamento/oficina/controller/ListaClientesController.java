package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.ClienteDAO;
import com.gerenciamento.oficina.entity.Cliente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListaClientesController implements Initializable{
	
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private SplitPane splitPaneClientes;

    @FXML
    private AnchorPane pnlCima;

    @FXML
    private Label lblCPF;

    @FXML
    private TextField fieldCPF;

    @FXML
    private Button btnFiltrarCPF;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private AnchorPane pnlBaixo;

    @FXML
    private TableView<Cliente> tbvClientes;

    @FXML
    private TableColumn<Cliente, Long> tbcCodCliente;

    @FXML
    private TableColumn<Cliente, String> tbcNomeCliente;

    @FXML
    private TableColumn<Cliente, Long> tbcCPF;

    @FXML
    private TableColumn<Cliente, String> tbcUF;

    @FXML
    private TableColumn<Cliente, String> tbcContato;

    @FXML
    private TableColumn<Cliente, String> tbcEndereco;
    
    private List<Cliente> listaClientes;

	private ObservableList<Cliente> observableListaClientes = FXCollections.observableArrayList();

	private ClienteDAO clienteDAO;

	public static final String CLIENTE_EDITAR = " - Editar";

	public static final String CLIENTE_INCLUIR = " - Incluir";

	private Stage stage;

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			boolean btnConfirmarClick = this.onShowTelaClienteEditar(cliente, ListaClientesController.CLIENTE_EDITAR);

			if (btnConfirmarClick) {
				this.getClienteDAO().update(cliente, null);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente na lista para editar!");
		}
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Cliente cliente = this.tbvClientes.getSelectionModel().getSelectedItem();

		if (cliente != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do cliente " + cliente.getNomeCliente() + " ?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getClienteDAO().delete(cliente);
				this.carregarTableViewClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um cliente na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnFiltrarCPF(ActionEvent event) {
    	
    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	Cliente cliente = new Cliente();

		boolean btnConfirmarClick = this.onShowTelaClienteEditar(cliente, ListaClientesController.CLIENTE_INCLUIR);

		if (btnConfirmarClick) {
			this.clienteDAO.save(cliente);
			this.carregarTableViewClientes();
		}
    }
    
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ObservableList<Cliente> getObservableListaClientes() {
		return observableListaClientes;
	}

	public void setObservableListaClientes(ObservableList<Cliente> observableListaClientes) {
		this.observableListaClientes = observableListaClientes;
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setClienteDAO(new ClienteDAO());
		this.carregarTableViewClientes();
		
		/*fieldCPF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tbvClientes.setItems(getObservableListaClientes());
            }
            String value = newValue.toLowerCase();
            ObservableList<Cliente> subentries = FXCollections.observableArrayList();

            long count = tbvClientes.getColumns().stream().count();
            for (int i = 0; i < tbvClientes.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tbvClientes.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tbvClientes.getItems().get(i));
                        break;
                    }
                }
            }
            tbvClientes.setItems(subentries);
        });*/
		this.configuraStage();		
	}
	
	public void carregarTableViewClientes() {
		this.tbcCodCliente.setCellValueFactory(new PropertyValueFactory<>("cod_cliente"));
		this.tbcContato.setCellValueFactory(new PropertyValueFactory<>("num_contato"));
		this.tbcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf_cnpj"));
		this.tbcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		this.tbcNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
		this.tbcUF.setCellValueFactory(new PropertyValueFactory<>("uf"));

		this.setListaClientes(this.getClienteDAO().getAll());
		this.setObservableListaClientes(FXCollections.observableArrayList(this.getListaClientes()));
		this.tbvClientes.setItems(this.getObservableListaClientes());
	}

	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da tela de clientes?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaClienteEditar(Cliente cliente, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("../view/ClienteEdit.fxml"));
			Parent clienteEditXML = loader.load();

			Stage janelaClienteEditar = new Stage();
			janelaClienteEditar.setTitle("Cliente" + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene clienteEditLayout = new Scene(clienteEditXML);
			janelaClienteEditar.setScene(clienteEditLayout);

			ClienteEditController clienteEditController = loader.getController();

			clienteEditController.setJanelaClienteEdit(janelaClienteEditar);
			clienteEditController.populaTela(cliente);

			janelaClienteEditar.showAndWait();

			return clienteEditController.isOkClick();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void configuraStage() {
		this.setStage(new Stage());
		this.getStage().initModality(Modality.APPLICATION_MODAL);
		this.getStage().resizableProperty().setValue(Boolean.FALSE);
	}

	public List<Cliente> retornaListagemCliente() {
		if (this.getClienteDAO() == null) {
			this.setClienteDAO(new ClienteDAO());
		}
		return this.getClienteDAO().getAll();
	}
}

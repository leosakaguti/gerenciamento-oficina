package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.ClienteDAO;
import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.entity.Cliente;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private Label lblPesquisar;

    @FXML
    private TextField fieldPesquisar;

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
    private TableColumn<Cliente, String> tbcCodCliente;

    @FXML
    private TableColumn<Cliente, String> tbcNomeCliente;

    @FXML
    private TableColumn<Cliente, String> tbcCPF;

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
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

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
			alerta.setHeaderText("Confirma a exclusão do cliente " + cliente.getNomeCliente() + "?");

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
		this.selecionarItemTableViewClientes(null);
		
		this.tbvClientes.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
		this.configuraStage();		
	}
	
	public void carregarTableViewClientes() {
		ObservableList<Cliente> results = FXCollections.observableArrayList();

		tbcCodCliente.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodCliente().toString()));
		tbcNomeCliente.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNomeCliente()));
		tbcCPF.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCpf().toString()));
        tbcUF.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUnidadeFederativa()));
        tbcContato.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNumContato()));
        tbcEndereco.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEnderecoCliente()));

        String query = "SELECT cod_cliente, nome, cpf_cnpj, uf, num_contato, endereco FROM cliente";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Cliente clientes = new Cliente();
                clientes.setCodCliente(rs.getLong("cod_cliente"));
                clientes.setNomeCliente(rs.getString("nome"));
                clientes.setCpf(rs.getLong("cpf_cnpj"));
                clientes.setUnidadeFederativa(rs.getString("uf"));
                clientes.setNumContato(rs.getString("num_contato"));
                clientes.setEnderecoCliente(rs.getString("endereco"));

                results.add(clientes);
            }
            tbvClientes.setItems(results);

            // Filtro de pesquisa
            FilteredList<Cliente> clientesFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                clientesFilteredList.setPredicate(clientes -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (clientes.getCpf().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Cliente> clientesSortedList = new SortedList<>(clientesFilteredList);
            clientesSortedList.comparatorProperty().bind(tbvClientes.comparatorProperty());

            tbvClientes.setItems(clientesSortedList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void selecionarItemTableViewClientes(Cliente cliente) {
		if (cliente != null) {
			this.btnEditar.setDisable(false);
			this.btnExcluir.setDisable(false);
		} else {
			this.btnEditar.setDisable(true);
			this.btnExcluir.setDisable(true);
		}
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
					getClass().getResource("/com/gerenciamento/oficina/view/ClienteEdit.fxml"));
			Parent clienteEditXML = loader.load();
			Stage janelaClienteEditar = new Stage();
			janelaClienteEditar.setTitle("Cliente" + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene clienteEditLayout = new Scene(clienteEditXML);
			janelaClienteEditar.setScene(clienteEditLayout);
			ClienteEditController clienteEditController = loader.getController();
			
			clienteEditController.setJanelaClienteEdit(janelaClienteEditar);
			if(operacao.equals(" - Editar")) clienteEditController.populaTela(cliente);
			else {
				clienteEditController.setClienteTelaEdit(cliente);
			}
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

}

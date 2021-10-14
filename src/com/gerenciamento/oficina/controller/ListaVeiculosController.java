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
import com.gerenciamento.oficina.dao.VeiculoDAO;
import com.gerenciamento.oficina.entity.Veiculo;

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

public class ListaVeiculosController implements Initializable{
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private SplitPane splitPaneVeiculos;

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
    private TableView<Veiculo> tbvVeiculos;

    @FXML
    private TableColumn<Veiculo, String> tbcCodVeic;
    
    @FXML
    private TableColumn<Veiculo, String> tbcCodCliente;

    @FXML
    private TableColumn<Veiculo, String> tbcNomeCliente;

    @FXML
    private TableColumn<Veiculo, String> tbcPlaca;

    @FXML
    private TableColumn<Veiculo, String> tbcMarcaModelo;

    @FXML
    private TableColumn<Veiculo, String> tbcCor;

    @FXML
    private TableColumn<Veiculo, String> tbcAno;
    
    public static final String VEICULO_EDITAR = " - Editar";
    public static final String VEICULO_INCLUIR = " - Incluir";
    
    private List<Veiculo> listaVeiculos;

	private ObservableList<Veiculo> observableListaVeiculos = FXCollections.observableArrayList();

	private VeiculoDAO veiculoDAO;
	
	ClienteDAO clienteDAO = new ClienteDAO();

	private Stage stage;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	Veiculo veiculo = this.tbvVeiculos.getSelectionModel().getSelectedItem();

		if (veiculo != null) {
			boolean btnConfirmarClic = this.onShowTelaVeiculoEditar(veiculo, ListaVeiculosController.VEICULO_EDITAR);

			if (btnConfirmarClic) {
				this.getVeiculoDAO().update(veiculo, null);
				this.carregarTableViewVeiculos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um veículo na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Veiculo veiculo = this.tbvVeiculos.getSelectionModel().getSelectedItem();

		if (veiculo != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do veículo de placa "+veiculo.getPlacaVeiculo()+" ?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getVeiculoDAO().delete(veiculo);
				this.carregarTableViewVeiculos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um veículo na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	Veiculo veiculo = new Veiculo();

		boolean btnConfirmarClic = this.onShowTelaVeiculoEditar(veiculo, ListaVeiculosController.VEICULO_INCLUIR);
		
		if (btnConfirmarClic) {
			this.getVeiculoDAO().save(veiculo);
			this.carregarTableViewVeiculos();
		}
    }
    
	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public void setListaVeiculos(List<Veiculo> listaVeiculos) {
		this.listaVeiculos = listaVeiculos;
	}

	public ObservableList<Veiculo> getObservableListaVeiculos() {
		return observableListaVeiculos;
	}

	public void setObservableListaVeiculos(ObservableList<Veiculo> observableListaVeiculos) {
		this.observableListaVeiculos = observableListaVeiculos;
	}

	public VeiculoDAO getVeiculoDAO() {
		return veiculoDAO;
	}

	public void setVeiculoDAO(VeiculoDAO veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setVeiculoDAO(new VeiculoDAO());
		this.carregarTableViewVeiculos();
		this.selecionarItemTableViewVeiculo(null);

		this.tbvVeiculos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewVeiculo(newValue));		
	}
	
	public void carregarTableViewVeiculos() {
		ObservableList<Veiculo> results = FXCollections.observableArrayList();
		
		tbcCodVeic.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodVeiculo().toString()));
		tbcCodCliente.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCliente().getCodCliente().toString()));
		tbcNomeCliente.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCliente().getNomeCliente()));
		tbcPlaca.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPlacaVeiculo()));
        tbcCor.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCorVeiculo()));
        tbcMarcaModelo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMarcaModeloVeiculo()));
        tbcAno.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAnoVeiculo().toString()));
        
        this.setListaVeiculos(this.getVeiculoDAO().getAll());
		this.setObservableListaVeiculos(FXCollections.observableArrayList(this.getListaVeiculos()));
		this.tbvVeiculos.setItems(this.getObservableListaVeiculos());
        String query = "SELECT cod_veiculo, cod_cliente, placa_carro, cor_carro, marca_modelo, ano  FROM veiculo";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Veiculo veiculos = new Veiculo();
                veiculos.setCodVeiculo(rs.getLong("cod_veiculo"));
                veiculos.setCliente(this.clienteDAO.get(rs.getLong("cod_cliente")));
                veiculos.setPlacaVeiculo(rs.getString("placa_carro"));
                veiculos.setCorVeiculo(rs.getString("cor_carro"));
                veiculos.setMarcaModeloVeiculo(rs.getString("marca_modelo"));
                veiculos.setAnoVeiculo(rs.getLong("ano"));

                results.add(veiculos);
            }
            tbvVeiculos.setItems(results);
        	
	        

            // Filtro de pesquisa
            FilteredList<Veiculo> veiculosFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                veiculosFilteredList.setPredicate(veiculos -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (veiculos.getPlacaVeiculo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Veiculo> veiculosSortedList = new SortedList<>(veiculosFilteredList);
            veiculosSortedList.comparatorProperty().bind(tbvVeiculos.comparatorProperty());

            tbvVeiculos.setItems(veiculosSortedList);

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
	
	public void selecionarItemTableViewVeiculo(Veiculo veiculo) {
		if (veiculo != null) {
			this.btnEditar.setDisable(false);
			this.btnExcluir.setDisable(false);
		} else {
			this.btnEditar.setDisable(true);
			this.btnExcluir.setDisable(true);
		}
	}
	
	public boolean onShowTelaVeiculoEditar(Veiculo veiculo, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/VeiculoEdit.fxml"));
			Parent veiculoEditXML = loader.load();

			Stage janelaVeiculoEditar = new Stage();
			janelaVeiculoEditar.setTitle("Cadastro de Veiculo" + operacao);
			janelaVeiculoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaVeiculoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene veiculoEditLayout = new Scene(veiculoEditXML);
			janelaVeiculoEditar.setScene(veiculoEditLayout);

			VeiculoEditController veiculoEditController = loader.getController();
			veiculoEditController.setJanelaVeiculoEdit(janelaVeiculoEditar);
			veiculoEditController.populaTela(veiculo);

			janelaVeiculoEditar.showAndWait();

			return veiculoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da tela de veículos?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

}

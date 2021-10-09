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

import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.dao.OrdemServicoDAO;
import com.gerenciamento.oficina.entity.OrdemServico;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListaOrdensController implements Initializable{
	
	@FXML
    private Button btnExcluir;

    @FXML
    private Button btnGerarRelatorio;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnProdutos;

    @FXML
    private Button btnServicos;

    @FXML
    private TextField fieldPesquisar;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblClienteValue;

    @FXML
    private Label lblCodOrdem;

    @FXML
    private Label lblCodOrdemValue;

    @FXML
    private Label lblDataEmis;

    @FXML
    private Label lblDataEmisValue;

    @FXML
    private Label lblPesquisar;

    @FXML
    private Label lblPlacaVeic;

    @FXML
    private Label lblPlacaVeicValue;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TableColumn<OrdemServico, String> tbcCodOrdem;

    @FXML
    private TableColumn<OrdemServico, String> tbcDataEmis;

    @FXML
    private TableColumn<OrdemServico, String> tbcPlacaVeic;
    
    @FXML
    private TableColumn<OrdemServico, String> tbcStatusOrdem;

    @FXML
    private TableView<OrdemServico> tbvOrdens;
    
    public static final String ORDEM_EDITAR = " - Editar";
    public static final String ORDEM_INCLUIR = " - Incluir";
    
    private List<OrdemServico> listaOrdens;

	private ObservableList<OrdemServico> observableListaOrdens = FXCollections.observableArrayList();

	private OrdemServicoDAO ordemDAO;

	private Stage stage;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	OrdemServico ordemServico = this.tbvOrdens.getSelectionModel().getSelectedItem();

		if (ordemServico != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão da ordem "+ordemServico.getCodOrdem()+" ?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getOrdemDAO().delete(ordemServico);
				this.carregarTableViewOrdens();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha uma ordem na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnGerar(ActionEvent event) {

    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	OrdemServico ordemServico = new OrdemServico();

		boolean btnConfirmarClic = this.onShowTelaOrdemCadastrar(ordemServico);
		
		if (btnConfirmarClic) {
			ordemServico.setStatusOrdem(Long.parseLong("0"));
			this.getOrdemDAO().save(ordemServico);
			this.carregarTableViewOrdens();
		}
    }

    @FXML
    void onClickBtnProdutos(ActionEvent event) {

    }

    @FXML
    void onClickBtnServicos(ActionEvent event) {

    }
    
	public List<OrdemServico> getListaOrdens() {
		return listaOrdens;
	}

	public void setListaOrdens(List<OrdemServico> listaOrdens) {
		this.listaOrdens = listaOrdens;
	}

	public ObservableList<OrdemServico> getObservableListaOrdens() {
		return observableListaOrdens;
	}

	public void setObservableListaOrdens(ObservableList<OrdemServico> observableListaOrdens) {
		this.observableListaOrdens = observableListaOrdens;
	}

	public OrdemServicoDAO getOrdemDAO() {
		return ordemDAO;
	}

	public void setOrdemDAO(OrdemServicoDAO ordemDAO) {
		this.ordemDAO = ordemDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setOrdemDAO(new OrdemServicoDAO());
		this.carregarTableViewOrdens();
		this.selecionarItemTableViewOrdem(null);

		this.tbvOrdens.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewOrdem(newValue));
	}
	
	public void carregarTableViewOrdens() {
		ObservableList<OrdemServico> results = FXCollections.observableArrayList();

		tbcCodOrdem.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodOrdem().toString()));
		tbcDataEmis.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDataEmissao().toString()));
		tbcPlacaVeic.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPlacaVeiculo()));
		tbcStatusOrdem.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatusOrdem().toString()));

        String query = "SELECT cod_ordem, data_emissao, placa_carro, status_ordem FROM ordem_servico";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                OrdemServico ordens = new OrdemServico();
                ordens.setCodOrdem(rs.getLong("cod_ordem"));
                ordens.setDataEmissao(rs.getDate("data_emissao"));
                ordens.setPlacaVeiculo(rs.getString("placa_carro"));
                ordens.setStatusOrdem(rs.getLong("status_ordem"));

                results.add(ordens);
            }
            tbvOrdens.setItems(results);

            // Filtro de pesquisa
            FilteredList<OrdemServico> ordensFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                ordensFilteredList.setPredicate(ordens -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (ordens.getPlacaVeiculo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (ordens.getCodOrdem().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (ordens.getDataEmissao().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<OrdemServico> ordensSortedList = new SortedList<>(ordensFilteredList);
            ordensSortedList.comparatorProperty().bind(tbvOrdens.comparatorProperty());

            tbvOrdens.setItems(ordensSortedList);

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
	
	public void selecionarItemTableViewOrdem(OrdemServico ordemServico) {
		if (ordemServico != null) {
			//this.lblClienteValue.setText(ordemServico.getVeiculo().getNomeCliente());
			this.lblCodOrdemValue.setText(ordemServico.getCodOrdem().toString());
			this.lblDataEmisValue.setText(ordemServico.getDataEmissao().toString());
			this.lblPlacaVeicValue.setText(ordemServico.getPlacaVeiculo());
			this.btnServicos.setDisable(false);
			this.btnExcluir.setDisable(false);
			this.btnGerarRelatorio.setDisable(false);
			this.btnProdutos.setDisable(false);
		} else {
			this.lblClienteValue.setText("");
			this.lblCodOrdemValue.setText("");
			this.lblDataEmisValue.setText("");
			this.lblPlacaVeicValue.setText("");
			this.btnServicos.setDisable(true);
			this.btnExcluir.setDisable(true);
			this.btnGerarRelatorio.setDisable(true);
			this.btnProdutos.setDisable(true);
		}
	}
	
	public boolean onShowTelaOrdemCadastrar(OrdemServico ordemServico) {
		/*try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/OrdemCad.fxml"));
			Parent ordemServicoEditXML = loader.load();

			Stage janelaOrdemEditar = new Stage();
			janelaOrdemEditar.setTitle("Emissão de Ordem de Serviço");
			janelaOrdemEditar.initModality(Modality.APPLICATION_MODAL);
			janelaOrdemEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene ordemServicoEditLayout = new Scene(ordemServicoEditXML);
			janelaOrdemEditar.setScene(ordemServicoEditLayout);

			OrdemServicoCadController ordemServicoCadController = loader.getController();
			ordemServicoCadController.setJanelaVeiculoEdit(janelaOrdemEditar);
			ordemServicoCadController.populaTela(ordemServico);

			janelaOrdemEditar.showAndWait();

			return ordemServicoCadController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return false;
	}
	public boolean onCloseQuery() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Pergunta");
		alert.setHeaderText("Deseja sair da tela de ordens de serviço?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

}

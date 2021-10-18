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
import com.gerenciamento.oficina.dao.ServicoDAO;
import com.gerenciamento.oficina.entity.Produto;
import com.gerenciamento.oficina.entity.Servico;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListaServicosController implements Initializable{
	
	@FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnNovo;

    @FXML
    private TextField fieldPesquisar;

    @FXML
    private Label lblPesquisar;

    @FXML
    private AnchorPane pnlBaixo;

    @FXML
    private AnchorPane pnlCima;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private SplitPane splitPaneClientes;

    @FXML
    private TableColumn<Servico, String> tbcCodServico;

    @FXML
    private TableColumn<Servico, String> tbcDescServico;

    @FXML
    private TableColumn<Servico, String> tbcVlrUnit;

    @FXML
    private TableView<Servico> tbvServicos;
    
    private List<Produto> listaProdutos;

	private ObservableList<Servico> observableListaServicos = FXCollections.observableArrayList();

	private ServicoDAO servicoDAO;

	public static final String SERVICO_EDITAR = " - Editar";

	public static final String SERVICO_INCLUIR = " - Incluir";

	private Stage stage;
	
	Connection conn = null;
    Statement statement = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	Servico servico = this.tbvServicos.getSelectionModel().getSelectedItem();

		if (servico != null) {
			boolean btnConfirmarClick = this.onShowTelaServicoEditar(servico, ListaServicosController.SERVICO_EDITAR);

			if (btnConfirmarClick) {
				this.getServicoDAO().update(servico, null);
				this.carregarTableViewServicos();
			}
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um serviço na lista para editar!");
		}
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Servico servico = this.tbvServicos.getSelectionModel().getSelectedItem();

		if (servico != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do serviço " + servico.getDescServico() + "?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getServicoDAO().delete(servico);
				this.carregarTableViewServicos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um serviço na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	Servico servico = new Servico();
    	
		boolean btnConfirmarClick = this.onShowTelaServicoEditar(servico, ListaServicosController.SERVICO_INCLUIR);

		if (btnConfirmarClick) {
			this.servicoDAO.save(servico);
			this.carregarTableViewServicos();
		}
    }
    
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public ObservableList<Servico> getObservableListaServicos() {
		return observableListaServicos;
	}

	public void setObservableListaServicos(ObservableList<Servico> observableListaServicos) {
		this.observableListaServicos = observableListaServicos;
	}

	public ServicoDAO getServicoDAO() {
		return servicoDAO;
	}

	public void setServicoDAO(ServicoDAO servicoDAO) {
		this.servicoDAO = servicoDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setServicoDAO(new ServicoDAO());
		this.carregarTableViewServicos();
		this.selecionarItemTableViewServicos(null);
		
		this.tbvServicos.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarItemTableViewServicos(newValue));
		this.configuraStage();
	}
	
	public void carregarTableViewServicos() {
		ObservableList<Servico> results = FXCollections.observableArrayList();

		tbcCodServico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodServico().toString()));
		tbcVlrUnit.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getVlrServico().toString()));
		tbcDescServico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescServico()));

        String query = "SELECT cod_servico, valor_servico, descricao_servico FROM servico";

        try {
            conn = new Conexao().getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Servico servicos = new Servico();
                servicos.setCodServico(rs.getLong("cod_servico"));
                servicos.setVlrServico(rs.getDouble("valor_servico"));
                servicos.setDescServico(rs.getString("descricao_servico"));

                results.add(servicos);
            }
            tbvServicos.setItems(results);

            // Filtro de pesquisa
            FilteredList<Servico> servicosFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                servicosFilteredList.setPredicate(servicos -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (servicos.getDescServico().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Servico> servicosSortedList = new SortedList<>(servicosFilteredList);
            servicosSortedList.comparatorProperty().bind(tbvServicos.comparatorProperty());

            tbvServicos.setItems(servicosSortedList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void selecionarItemTableViewServicos(Servico servico) {
		if (servico != null) {
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
		alert.setHeaderText("Deseja sair da tela de serviços?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaServicoEditar(Servico servico, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/ServicoEdit.fxml"));
			Parent servicoEditXML = loader.load();
			Stage janelaServicoEditar = new Stage();
			janelaServicoEditar.setTitle("Serviço" + operacao);
			janelaServicoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaServicoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene servicoEditLayout = new Scene(servicoEditXML);
			janelaServicoEditar.setScene(servicoEditLayout);
			ServicoEditController servicoEditController = loader.getController();
			
			servicoEditController.setJanelaServicoEdit(janelaServicoEditar);
			if(operacao.equals(" - Editar")) servicoEditController.populaTela(servico);
			else servicoEditController.populaTelaCadastro(servico);
			janelaServicoEditar.showAndWait();

			return servicoEditController.isOkClick();
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

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
import com.gerenciamento.oficina.dao.ProdutoDAO;
import com.gerenciamento.oficina.entity.Produto;

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

public class ListaProdutosController implements Initializable{
	
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
    private TableColumn<Produto, String> tbcCodProd;

    @FXML
    private TableColumn<Produto, String> tbcFornecedor;

    @FXML
    private TableColumn<Produto, String> tbcNomeProduto;

    @FXML
    private TableColumn<Produto, String> tbcVlrUnit;

    @FXML
    private TableView<Produto> tbvProdutos;
    
    private List<Produto> listaProdutos;

	private ObservableList<Produto> observableListaProdutos = FXCollections.observableArrayList();

	private ProdutoDAO produtoDAO;

	public static final String PRODUTO_EDITAR = " - Editar";

	public static final String PRODUTO_INCLUIR = " - Incluir";

	private Stage stage;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	Produto produto = this.tbvProdutos.getSelectionModel().getSelectedItem();

		if (produto != null) {
			boolean btnConfirmarClick = this.onShowTelaProdutoEditar(produto, ListaProdutosController.PRODUTO_EDITAR);

			if (btnConfirmarClick) {
				this.getProdutoDAO().update(produto, null);
				this.carregarTableViewProdutos();
			}
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um produto na lista para editar!");
		}
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Produto produto = this.tbvProdutos.getSelectionModel().getSelectedItem();

		if (produto != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do produto " + produto.getNomeProduto() + "?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getProdutoDAO().delete(produto);
				this.carregarTableViewProdutos();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um produto na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	Produto produto = new Produto();
    	
		boolean btnConfirmarClick = this.onShowTelaProdutoEditar(produto, ListaProdutosController.PRODUTO_INCLUIR);

		if (btnConfirmarClick) {
			this.produtoDAO.save(produto);
			this.carregarTableViewProdutos();
		}
    }
    
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public ObservableList<Produto> getObservableListaProdutos() {
		return observableListaProdutos;
	}

	public void setObservableListaProdutos(ObservableList<Produto> observableListaProdutos) {
		this.observableListaProdutos = observableListaProdutos;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setProdutoDAO(new ProdutoDAO());
		this.carregarTableViewProdutos();
		this.selecionarItemTableViewProdutos(null);
		
		this.tbvProdutos.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarItemTableViewProdutos(newValue));
		this.configuraStage();
	}
	
	public void carregarTableViewProdutos() {
		ObservableList<Produto> results = FXCollections.observableArrayList();

		tbcCodProd.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodProd().toString()));
		tbcVlrUnit.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getVlrUnit().toString()));
		tbcNomeProduto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNomeProduto()));
        tbcFornecedor.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFornecedor()));

        String query = "SELECT cod_prod, vlr_unit, nome, fornecedor FROM produto";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Produto produtos = new Produto();
                produtos.setCodProd(rs.getLong("cod_prod"));
                produtos.setVlrUnit(rs.getDouble("vlr_unit"));
                produtos.setNomeProduto(rs.getString("nome"));
                produtos.setFornecedor(rs.getString("fornecedor"));

                results.add(produtos);
            }
            tbvProdutos.setItems(results);

            // Filtro de pesquisa
            FilteredList<Produto> produtosFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                produtosFilteredList.setPredicate(produtos -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (produtos.getNomeProduto().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (produtos.getVlrUnit().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (produtos.getFornecedor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Produto> produtosSortedList = new SortedList<>(produtosFilteredList);
            produtosSortedList.comparatorProperty().bind(tbvProdutos.comparatorProperty());

            tbvProdutos.setItems(produtosSortedList);

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
	
	public void selecionarItemTableViewProdutos(Produto produto) {
		if (produto != null) {
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
		alert.setHeaderText("Deseja sair da tela de produtos?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaProdutoEditar(Produto produto, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/ProdutoEdit.fxml"));
			Parent produtoEditXML = loader.load();
			Stage janelaProdutoEditar = new Stage();
			janelaProdutoEditar.setTitle("Produto" + operacao);
			janelaProdutoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaProdutoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene produtoEditLayout = new Scene(produtoEditXML);
			janelaProdutoEditar.setScene(produtoEditLayout);
			ProdutoEditController produtoEditController = loader.getController();
			
			produtoEditController.setJanelaProdutoEdit(janelaProdutoEditar);
			if(operacao.equals(" - Editar")) produtoEditController.populaTela(produto);
			else produtoEditController.populaTelaCadastro(produto);
			janelaProdutoEditar.showAndWait();

			return produtoEditController.isOkClick();
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

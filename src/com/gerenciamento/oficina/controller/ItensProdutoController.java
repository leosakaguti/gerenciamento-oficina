package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.dao.ItensProdutoDAO;
import com.gerenciamento.oficina.dao.ProdutoDAO;
import com.gerenciamento.oficina.entity.ItensProduto;
import com.gerenciamento.oficina.entity.OrdemServico;
import com.gerenciamento.oficina.entity.Produto;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ItensProdutoController implements Initializable{
	
	@FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox<Produto> cbxProduto;

    @FXML
    private TextField fieldQtdeProd;

    @FXML
    private Label lblProduto;

    @FXML
    private Label lblQuantidadeProd;

    @FXML
    private TableColumn<ItensProduto, String> tbcCodProduto;

    @FXML
    private TableColumn<ItensProduto, String> tbcNomeProduto;

    @FXML
    private TableColumn<ItensProduto, String> tbcValorProduto;

    @FXML
    private TableView<ItensProduto> tbvItensProdutos;
    
    @FXML
    private Label lblOrdemServico;

    @FXML
    private Label lblOrdemServicoValue;
    
    private Stage janelaItensProd;
    
    private ItensProduto itensProduto;
    
    private ItensProdutoDAO itensProdutoDAO = new ItensProdutoDAO();
	
	private ObservableList<ItensProduto> obsListItensProdutos = FXCollections.observableArrayList();
	
	private ObservableList<Produto> obsListProdutos = FXCollections.observableArrayList();
	
	private List<ItensProduto> listaItensProd;
	
	private ProdutoDAO produtoDAO = new ProdutoDAO();
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
    
    @FXML
    void onClickBtnAdicionar(ActionEvent event) {
    	
    }

    @FXML
    void onClickBtnRemover(ActionEvent event) {

    }
    
    @FXML
    void onClickBtnVoltar(ActionEvent event) {
    	this.getJanelaItensProd().close();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("initialize");
		this.carregarTableViewItensProduto();
		this.carregarComboBoxProduto();
		
		obsListProdutos.setAll(carregarComboBoxProduto());
		cbxProduto.setConverter(new StringConverter<Produto>() {
			
			@Override
			public String toString(Produto produto) {
				if (produto == null) {
                    return "Select";
                } else {
                    return String.valueOf(produto.getCodProd())+" - "+produto.getNomeProduto();
                }
			}
			
			@Override
			public Produto fromString(String string) {
				return null;
			}
		});
		cbxProduto.setItems(obsListProdutos);
	}
	
	public ObservableList<Produto> getObsListProdutos() {
		return obsListProdutos;
	}

	public void setObsListProdutos(ObservableList<Produto> obsListProdutos) {
		this.obsListProdutos = obsListProdutos;
	}

	public List<ItensProduto> getListaItensProd() {
		return listaItensProd;
	}

	public void setListaItensProd(List<ItensProduto> listaItensProd) {
		this.listaItensProd = listaItensProd;
	}

	public Stage getJanelaItensProd() {
		return janelaItensProd;
	}

	public void setJanelaItensProd(Stage janelaItensProd) {
		this.janelaItensProd = janelaItensProd;
	}
	
	public ItensProdutoDAO getItensProdutoDAO() {
		return itensProdutoDAO;
	}

	public void setItensProdutoDAO(ItensProdutoDAO itensProdutoDAO) {
		this.itensProdutoDAO = itensProdutoDAO;
	}
	
	public ObservableList<ItensProduto> getObsListItensProdutos() {
		return obsListItensProdutos;
	}

	public void setObsListItensProdutos(ObservableList<ItensProduto> obsListItensProdutos) {
		this.obsListItensProdutos = obsListItensProdutos;
	}

	public List<Produto> carregarComboBoxProduto() {
		List<Produto> items = new ArrayList<>();

		String query = "SELECT cod_produto, nome FROM produto";

		try {
			connection = new Conexao().getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setCodProd(rs.getLong("cod_produto"));
				produto.setNomeProduto(rs.getString("nome"));

				items.add(produto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return items;
	}
	
	public void setOrdemServico(OrdemServico ordemServico) {
		System.out.println("setOrdem");
		itensProduto.setOrdemServico(ordemServico);
		this.lblOrdemServicoValue.setText(itensProduto.getOrdemServico().getCodOrdem().toString());
	}
	
	public void carregarTableViewItensProduto() {
		ObservableList<ItensProduto> results = FXCollections.observableArrayList();

		tbcCodProduto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduto().getCodProd().toString()));
		tbcNomeProduto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduto().getNomeProduto()));
		tbcValorProduto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduto().getVlrUnit().toString()));
		System.out.println("codOrdem: "+itensProduto.getOrdemServico().getCodOrdem());
		this.setListaItensProd(this.getItensProdutoDAO().getAllByOrdem(itensProduto.getOrdemServico().getCodOrdem()));
		this.setObsListItensProdutos(FXCollections.observableArrayList(this.getListaItensProd()));
		this.tbvItensProdutos.setItems(this.getObsListItensProdutos());
		
        String query = "SELECT cod_produto, nome, vlr_unit FROM itens_produto";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                ItensProduto itensProduto = new ItensProduto();
                itensProduto.setProduto(this.produtoDAO.get(rs.getLong("cod_ordem")));

                results.add(itensProduto);
            }
            tbvItensProdutos.setItems(results);

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
}

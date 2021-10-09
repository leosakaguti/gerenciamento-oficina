package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable{
	@FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Label lblNomeSistema;

    @FXML
    private GridPane pnlBotoes;

    @FXML
    private Button btnVeiculo;

    @FXML
    private Button btnCliente;

    @FXML
    private Button btnServico;

    @FXML
    private Button btnProduto;

    @FXML
    private Button btnOrdem;
    
    @FXML
    private Button btnUsuarios;
    
    @FXML
    private Label lblUsuarioLogado;

    @FXML
    private Label lblUsuarioLogadoValue;
    
    private Stage stage;
    
    private String usuario = System.getProperty("usuario");
    
    private Long isAdmin;
    
    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
    
    @FXML
    void onClickBtnCliente(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/ClienteMenu.fxml"));
			Parent clienteListaXML = loader.load();

			ListaClientesController listaClienteController = loader.getController();
			Scene clienteListaLayout = new Scene(clienteListaXML);

			this.getStage().setScene(clienteListaLayout);
			this.getStage().setTitle("Consulta de Clientes");

			this.getStage().setOnCloseRequest(e -> {
				if (listaClienteController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClickBtnServico(ActionEvent event) {

    }

    @FXML
    void onClickBtnOrdem(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/OrdemServicoMenu.fxml"));
			Parent ordemListaXML = loader.load();

			ListaOrdensController listaOrdemController = loader.getController();
			Scene ordemListaLayout = new Scene(ordemListaXML);

			this.getStage().setScene(ordemListaLayout);
			this.getStage().setTitle("Consulta de Ordens de Serviço");

			this.getStage().setOnCloseRequest(e -> {
				if (listaOrdemController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClickBtnProduto(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/ProdutoMenu.fxml"));
			Parent produtoListaXML = loader.load();

			ListaProdutosController listaProdutoController = loader.getController();
			Scene produtoListaLayout = new Scene(produtoListaXML);

			this.getStage().setScene(produtoListaLayout);
			this.getStage().setTitle("Consulta de Produtos");

			this.getStage().setOnCloseRequest(e -> {
				if (listaProdutoController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onClickBtnUsuarios(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/UsuarioMenu.fxml"));
			Parent usuarioListaXML = loader.load();

			ListaUsuariosController listaUsuarioController = loader.getController();
			Scene usuarioListaLayout = new Scene(usuarioListaXML);

			this.getStage().setScene(usuarioListaLayout);
			this.getStage().setTitle("Consulta de Usuários");

			this.getStage().setOnCloseRequest(e -> {
				if (listaUsuarioController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClickBtnVeiculo(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/VeiculoMenu.fxml"));
			Parent veiculoListaXML = loader.load();

			ListaVeiculosController listaVeiculosController = loader.getController();
			Scene veiculoListaLayout = new Scene(veiculoListaXML);

			this.getStage().setScene(veiculoListaLayout);
			this.getStage().setTitle("Consulta de Veiculos");

			this.getStage().setOnCloseRequest(e -> {
				if (listaVeiculosController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});
			this.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configuraStage();
		this.lblUsuarioLogadoValue.setText(System.getProperty("nomeLogado"));
		
		String query = "select is_admin from usuario "
					+ "where usuario = ?";
		try {
            connection = new Conexao().getConnection();
            preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, usuario);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
            	isAdmin = rs.getLong("is_admin");
            }
            
            if(isAdmin == 1) {
            	btnUsuarios.setVisible(true);
            }else btnUsuarios.setVisible(false);
		}catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
				
		
	}
	
	public void configuraStage() {
		this.setStage(new Stage());
		this.getStage().initModality(Modality.APPLICATION_MODAL);
		this.getStage().resizableProperty().setValue(Boolean.FALSE);
	}
}

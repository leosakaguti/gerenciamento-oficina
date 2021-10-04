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
import com.gerenciamento.oficina.dao.UsuarioDAO;
import com.gerenciamento.oficina.entity.Usuario;

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

public class ListaUsuariosController implements Initializable{
	
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
    private SplitPane splitPaneUsuarios;

    @FXML
    private TableColumn<Usuario, String> tbcCodUsuario;

    @FXML
    private TableColumn<Usuario, String> tbcIsAdmin;

    @FXML
    private TableColumn<Usuario, String> tbcNomeUsuario;

    @FXML
    private TableColumn<Usuario, String> tbcUsuario;

    @FXML
    private TableView<Usuario> tbvUsuarios;
    
    private List<Usuario> listaUsuarios;

	private ObservableList<Usuario> observableListaUsuarios = FXCollections.observableArrayList();

	private UsuarioDAO usuarioDAO;

	public static final String USUARIO_EDITAR = " - Editar";

	public static final String USUARIO_INCLUIR = " - Incluir";

	private Stage stage;
	
	Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    @FXML
    void onClickBtnEditar(ActionEvent event) {
    	Usuario usuario = this.tbvUsuarios.getSelectionModel().getSelectedItem();
		if (usuario != null) {
			boolean btnConfirmarClick = this.onShowTelaUsuarioEditar(usuario, ListaUsuariosController.USUARIO_EDITAR);

			if (btnConfirmarClick) {
				this.getUsuarioDAO().update(usuario, null);
				this.carregarTableViewUsuarios();
			}
		} else {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um usuário na lista para editar!");
		}
    }

    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Usuario usuario = this.tbvUsuarios.getSelectionModel().getSelectedItem();

		if (usuario != null) {

			Alert alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do usuário " + usuario.getUsuario() + "?");

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();
			
			if (!usuario.getUsuario().equals(System.getProperty("usuario")) && resultado.get() == botaoSim) {
				this.getUsuarioDAO().delete(usuario);
				this.carregarTableViewUsuarios();
			}else {
				Alert alerta2 = new Alert(AlertType.WARNING);
				alerta2.setTitle("Erro");
				alerta2.setHeaderText("Você não pode excluir o seu usuário");
				alerta2.show();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, escolha um usuário na tabela!");
			alerta.show();
		}
    }

    @FXML
    void onClickBtnNovo(ActionEvent event) {
    	Usuario usuario = new Usuario();
		boolean btnConfirmarClick = this.onShowTelaUsuarioEditar(usuario, ListaUsuariosController.USUARIO_INCLUIR);

		if (btnConfirmarClick) {
			this.usuarioDAO.save(usuario);
			this.carregarTableViewUsuarios();
		}
    }

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ObservableList<Usuario> getObservableListaUsuarios() {
		return observableListaUsuarios;
	}

	public void setObservableListaUsuarios(ObservableList<Usuario> observableListaUsuarios) {
		this.observableListaUsuarios = observableListaUsuarios;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setUsuarioDAO(new UsuarioDAO());
		this.carregarTableViewUsuarios();
		this.selecionarItemTableViewUsuarios(null);
		
		this.tbvUsuarios.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarItemTableViewUsuarios(newValue));
		this.configuraStage();		
	}
	
	public void carregarTableViewUsuarios() {
		ObservableList<Usuario> results = FXCollections.observableArrayList();

		tbcCodUsuario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodUsuario().toString()));
		tbcNomeUsuario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNomeUsuario()));
		tbcUsuario.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUsuario()));
        tbcIsAdmin.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getIsAdmin().toString()));

        String query = "SELECT cod_usuario, nome, usuario, is_admin FROM usuario";

        try {
            connection = new Conexao().getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                Usuario usuarios = new Usuario();
                usuarios.setCodUsuario(rs.getLong("cod_usuario"));
                usuarios.setNomeUsuario(rs.getString("nome"));
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setIsAdmin(rs.getLong("is_admin"));

                results.add(usuarios);
            }
            if(results.size() > 0) {
            	tbvUsuarios.setItems(results);
            }

            // Filtro de pesquisa
            FilteredList<Usuario> usuariosFilteredList = new FilteredList<>(results, b -> true);
            fieldPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
                usuariosFilteredList.setPredicate(usuarios -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (usuarios.getNomeUsuario().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (usuarios.getUsuario().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (usuarios.getIsAdmin().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (usuarios.getCodUsuario().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<Usuario> usuariosSortedList = new SortedList<>(usuariosFilteredList);
            usuariosSortedList.comparatorProperty().bind(tbvUsuarios.comparatorProperty());

            tbvUsuarios.setItems(usuariosSortedList);

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
	
	public void selecionarItemTableViewUsuarios(Usuario usuario) {
		if (usuario != null) {
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
		alert.setHeaderText("Deseja sair da tela de usuários?");
		ButtonType buttonTypeNO = ButtonType.NO;
		ButtonType buttonTypeYES = ButtonType.YES;
		alert.getButtonTypes().setAll(buttonTypeYES, buttonTypeNO);
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == buttonTypeYES ? true : false;
	}

	public boolean onShowTelaUsuarioEditar(Usuario usuario, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/com/gerenciamento/oficina/view/UsuarioEdit.fxml"));
			Parent usuarioEditXML = loader.load();
			Stage janelaUsuarioEditar = new Stage();
			janelaUsuarioEditar.setTitle("Usuário" + operacao);
			janelaUsuarioEditar.initModality(Modality.APPLICATION_MODAL);
			janelaUsuarioEditar.resizableProperty().setValue(Boolean.FALSE);
			
			System.setProperty("operacao", operacao);
			Scene usuarioEditLayout = new Scene(usuarioEditXML);
			janelaUsuarioEditar.setScene(usuarioEditLayout);
			UsuarioEditController usuarioEditController = loader.getController();

			usuarioEditController.setJanelaUsuarioEdit(janelaUsuarioEditar);
			usuarioEditController.populaTela(usuario);
			janelaUsuarioEditar.showAndWait();

			return usuarioEditController.isOkClick();
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

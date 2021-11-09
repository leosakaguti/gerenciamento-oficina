package com.gerenciamento.oficina.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.gerenciamento.oficina.dao.Conexao;
import com.gerenciamento.oficina.dao.ItensServicoDAO;
import com.gerenciamento.oficina.dao.OrdemServicoDAO;
import com.gerenciamento.oficina.dao.ServicoDAO;
import com.gerenciamento.oficina.entity.ItensServico;
import com.gerenciamento.oficina.entity.OrdemServico;
import com.gerenciamento.oficina.entity.Servico;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ItensServicoController implements Initializable{
	
	@FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox<Servico> cbxServicos;

    @FXML
    private TextField fieldQtde;
    
    @FXML
    private Label lblOrdemServico;

    @FXML
    private Label lblOrdemServicoValue;

    @FXML
    private Label lblQuantidadeServ;

    @FXML
    private Label lblServicos;

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TableColumn<ItensServico, String> tbcCodServico;

    @FXML
    private TableColumn<ItensServico, String> tbcDescServico;
    
    @FXML
    private TableColumn<ItensServico, String> tbcQtde;

    @FXML
    private TableColumn<ItensServico, String> tbcValorServico;

    @FXML
    private TableView<ItensServico> tbvItensServicos;
    
    private Stage janelaItensServ;
    
    private ItensServico itensServico;
    
    private ItensServicoDAO itensServicoDAO = new ItensServicoDAO();
    
    private OrdemServicoDAO ordemDAO = new OrdemServicoDAO();
	
	private ObservableList<ItensServico> obsListItensServicos = FXCollections.observableArrayList();
	
	private ObservableList<Servico> obsListServicos = FXCollections.observableArrayList();
	
	private List<ItensServico> listaItensServ;
	
	private ServicoDAO servicoDAO = new ServicoDAO();
	
	Connection conn = null;
	Statement stm = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;

    @FXML
    void onClickBtnAdicionar(ActionEvent event) {
    	Servico servico = cbxServicos.getSelectionModel().getSelectedItem();
    	
    	if(itensServico.getOrdemServico().getStatusOrdem() == 0) {
    		if(fieldQtde.getText().toString() != "" || cbxServicos.getSelectionModel().getSelectedItem() != null) {
    			itensServico.setOrdemServico(ordemDAO.get(Long.parseLong(lblOrdemServicoValue.getText())));
    	    	itensServico.setServico(servicoDAO.get(servico.getCodServico()));
    	    	itensServico.setQtde(Long.parseLong(fieldQtde.getText()));
    			if(itensServicoDAO.VerificaServicoNaTabela(itensServico)) {
            		if(itensServicoDAO.update(itensServico, null)) carregarTableViewItensServico();
            	}else {
        	    	if(itensServicoDAO.save(itensServico) == 1) carregarTableViewItensServico();
            	}
        		fieldQtde.setText("");
    		}else {
    			Alert alertVazio = new Alert(Alert.AlertType.ERROR);
        		alertVazio.setTitle("Aviso!");
        		alertVazio.setHeaderText("Campo vazio!");
        		alertVazio.setContentText("Preencha todos os campos!");
        		alertVazio.show();
    		}
    	}else {
    		Alert alertBaixado = new Alert(Alert.AlertType.ERROR);
    		alertBaixado.setTitle("Aviso!");
    		alertBaixado.setHeaderText("A ordem está baixada!");
    		alertBaixado.setContentText("Após baixar a ordem, não é permitido alterar itens.");
    		alertBaixado.show();
    	}
    	
    	
    }

    @FXML
    void onClickBtnRemover(ActionEvent event) {
    	ItensServico itensServico = tbvItensServicos.getSelectionModel().getSelectedItem();
    	
    	if(itensServico != null) {
    		Alert alerta = new Alert(AlertType.CONFIRMATION);
	    	itensServico.setOrdemServico(ordemDAO.get(Long.parseLong(lblOrdemServicoValue.getText())));
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirma a exclusão do item de serviço "+itensServico.getServico().getDescServico()+" ?");
	
			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();
	
			if (resultado.get() == botaoSim) {
				this.getItensServicoDAO().delete(itensServico);
				this.carregarTableViewItensServico();
			}
    	} else {
    		Alert alertaNulo = new Alert(AlertType.ERROR);
    		alertaNulo.setTitle("Erro");
    		alertaNulo.setHeaderText("Para remover, selecione um item na lista!");
    		alertaNulo.show();
    	}
    }
    
    @FXML
    void onClickBtnVoltar(ActionEvent event) {
    	ListaOrdensController listaOrdensController = new ListaOrdensController();
    	this.getJanelaItensServ().close();
    	listaOrdensController.carregarTableViewOrdens();
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setItensServicoDAO(new ItensServicoDAO());
		this.carregarComboBoxServico();
		
		obsListServicos.setAll(carregarComboBoxServico());
		cbxServicos.setConverter(new StringConverter<Servico>() {
			
			@Override
			public String toString(Servico servico) {
				if (servico == null) {
                    return "Select";
                } else {
                    return String.valueOf(servico.getCodServico())+" - "+servico.getDescServico();
                }
			}
			
			@Override
			public Servico fromString(String string) {
				return null;
			}
		});
		cbxServicos.setItems(obsListServicos);
	
	}
	
	public ObservableList<Servico> getObsListServicos() {
		return obsListServicos;
	}

	public void setObsListServicos(ObservableList<Servico> obsListServicos) {
		this.obsListServicos = obsListServicos;
	}

	public List<ItensServico> getListaItensServ() {
		return listaItensServ;
	}

	public void setListaItensServ(List<ItensServico> listaItensServ) {
		this.listaItensServ = listaItensServ;
	}

	public Stage getJanelaItensServ() {
		return janelaItensServ;
	}

	public void setJanelaItensServ(Stage janelaItensServ) {
		this.janelaItensServ = janelaItensServ;
	}
	
	public ItensServicoDAO getItensServicoDAO() {
		return itensServicoDAO;
	}

	public void setItensServicoDAO(ItensServicoDAO itensServicoDAO) {
		this.itensServicoDAO = itensServicoDAO;
	}
	
	public ObservableList<ItensServico> getObsListItensServicos() {
		return obsListItensServicos;
	}

	public void setObsListItensServicos(ObservableList<ItensServico> obsListItensServicos) {
		this.obsListItensServicos = obsListItensServicos;
	}

	public List<Servico> carregarComboBoxServico() {
		List<Servico> items = new ArrayList<>();

		String query = "SELECT cod_servico, descricao_servico FROM servico";

		try {
			conn = new Conexao().getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(query);

			while (rs.next()) {
				Servico servico = new Servico();
				servico.setCodServico(rs.getLong("cod_servico"));
				servico.setDescServico(rs.getString("descricao_servico"));

				items.add(servico);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return items;
	}
	
	public void setOrdemServico(OrdemServico ordemServico) {
		itensServico = new ItensServico();
		this.itensServico.setOrdemServico(ordemServico);
		this.lblOrdemServicoValue.setText(itensServico.getOrdemServico().getCodOrdem().toString());
		this.carregarTableViewItensServico();
	}
	
	public void carregarTableViewItensServico() {
		ObservableList<ItensServico> results = FXCollections.observableArrayList();
		
		tbcCodServico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getServico().getCodServico().toString()));
		tbcDescServico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getServico().getDescServico()));
		tbcValorServico.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getVlrServico().toString()));
		tbcQtde.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getQtde().toString()));
		
		this.setListaItensServ(this.getItensServicoDAO().getAllByOrdem(itensServico.getOrdemServico().getCodOrdem()));
		this.setObsListItensServicos(FXCollections.observableArrayList(this.getListaItensServ()));
		this.tbvItensServicos.setItems(this.getObsListItensServicos());
		
        String query = "SELECT cod_servico, qtde, vlr_servico FROM itens_servico "
        			 + "where cod_ordem = ?";

        try {
            conn = new Conexao().getConnection();
            pstm = conn.prepareStatement(query);
            
            pstm.setLong(1, itensServico.getOrdemServico().getCodOrdem());
            
            rs = pstm.executeQuery();

            while (rs.next()) {
                ItensServico itensServico = new ItensServico();
                itensServico.setServico(this.servicoDAO.get(rs.getLong("cod_servico")));
                itensServico.setQtde(rs.getLong("qtde"));
                itensServico.setVlrServico(rs.getDouble("vlr_servico"));

                results.add(itensServico);
            }
            tbvItensServicos.setItems(results);

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
}

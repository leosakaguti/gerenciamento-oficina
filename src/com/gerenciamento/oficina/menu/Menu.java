package com.gerenciamento.oficina.menu;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Menu extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerenciamento/oficina/view/LoginMenu.fxml"));
			Parent menuXML = loader.load();

			Scene menuLayout = new Scene(menuXML);

			Stage loginMenuJanela = new Stage();
			loginMenuJanela.initModality(Modality.APPLICATION_MODAL);
			loginMenuJanela.resizableProperty().setValue(Boolean.FALSE);
			loginMenuJanela.setScene(menuLayout);
			loginMenuJanela.setResizable(false);
			loginMenuJanela.centerOnScreen();
			loginMenuJanela.setTitle("Tela de login do sistema");

			loginMenuJanela.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

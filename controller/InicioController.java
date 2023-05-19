package fes.aragon.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private TableColumn<?, ?> clmAMaterno;

    @FXML
    private TableColumn<?, ?> clmAPaterno;

    @FXML
    private TableColumn<?, ?> clmDueno;

    @FXML
    private TableColumn<?, ?> clmFechaNacimiento;

    @FXML
    private TableColumn<?, ?> clmIDSocio;

    @FXML
    private TableColumn<?, ?> clmNombres;

    @FXML
    private TableColumn<?, ?> clmNumeroCelular;

    @FXML
    private MenuItem mnuAbrirBarcos;

    @FXML
    private MenuItem mnuAbrirDestinos;

    @FXML
    private MenuItem mnuAbrirPatrones;

    @FXML
    private MenuItem mnuAbrirSocios;

    @FXML
    private TableView<?> tblTabla;

    @FXML
    private TextField txtPatron;

    @FXML
    void abrirBarcos(ActionEvent event) {

    }

    @FXML
    void abrirDestinos(ActionEvent event) {

    }

    @FXML
    void abrirPatrones(ActionEvent event) {
		try {
			Pane rootP = (Pane)FXMLLoader.load(getClass().getResource("/fes/aragon/fxml/Patrones.fxml"));
	    	Scene escenaP = new Scene(rootP);
	    	Stage escenarioP = new Stage();
	    	escenarioP.setScene(escenaP);
	    	escenarioP.initModality(Modality.APPLICATION_MODAL);
	    	escenarioP.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void abrirSocios(ActionEvent event) {
		try {
			Pane rootS = (Pane)FXMLLoader.load(getClass().getResource("/fes/aragon/fxml/Socios.fxml"));
	    	Scene escenaS = new Scene(rootS);
	    	Stage escenarioS = new Stage();
	    	escenarioS.setScene(escenaS);
	    	escenarioS.initModality(Modality.APPLICATION_MODAL);
	    	escenarioS.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

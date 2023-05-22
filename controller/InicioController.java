package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Registros;
import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InicioController extends BaseController implements Initializable{
	
	Conexion cn = this.conexionSQL();

    @FXML
    private Button btnSalir;  
    
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnActualizar;
    
    @FXML
    private TableColumn<Registros, Barcos> clmIDBarco;
    
    @FXML
    private TableColumn<Registros, String> clmNombresBarco;

    @FXML
    private TableColumn<Registros, Patrones> clmIDPatron;

    @FXML
    private TableColumn<Registros, String> clmNombrePatron;

    @FXML
    private TableColumn<Registros, Socios> clmIDSocio;
    
    @FXML
    private TableColumn<Registros, String> clmNombreSocio;

    @FXML
    private TableColumn<Registros, String> clmLlegada;

    @FXML
    private TableColumn<Registros, String> clmSalida;
    
    @FXML
    private TableColumn<Registros, Destinos> clmIDDestino;
    
    @FXML
    private TableColumn<Registros, String> clmDestino;
    
    @FXML
    private Menu muAbrir;

    @FXML
    private MenuItem muItemBarcos;

    @FXML
    private MenuItem muItemDestinos;

    @FXML
    private MenuItem muItemPatrones;

    @FXML
    private MenuItem muItemSocios;

    @FXML
    private TableView<Registros> tblTabla = new TableView<>();

    @FXML
    private TextField txtPatron;

	@FXML
    private void actualizarRegistro(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		this.indice = indice;
    		this.tabla = this.tblTabla;
    		this.nuevaVentana("ActualizarRegistro");
    	}
    }
	
	@FXML
    void insertarRegistro(ActionEvent event) {
		ListaDeRegistros.getObjeto().getGrupoRegistros().add(new Registros('c'));
    	this.nuevaVentana("InsertarRegistro");
    }

    @FXML
    private void abrirBarcos(ActionEvent event) {
    	this.nuevaVentana("Barcos");
    }

    @FXML
    private void abrirDestinos(ActionEvent event) {
    	this.nuevaVentana("Destinos");
    }

    @FXML
    private void abrirPatrones(ActionEvent event) {
    	this.nuevaVentana("Patrones");	
    }

    @FXML
    private void abrirSocios(ActionEvent event) {
		this.nuevaVentana("Socios");
    }
    

    @FXML
    void abrirMas(ActionEvent event) {

    }

    @FXML
    private void salir(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    }
    
    private Conexion conexionSQL() {
		try {
			return new Conexion();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.cn.llenarTablaRegistros();
		} catch (SQLException e) {
		}
		this.clmIDBarco.setCellValueFactory(new PropertyValueFactory<>("noSerieBarco"));
		this.clmNombresBarco.setCellValueFactory(new PropertyValueFactory<>("barco"));
		this.clmIDSocio.setCellValueFactory(new PropertyValueFactory<>("matriculaSocio"));
		this.clmNombreSocio.setCellValueFactory(new PropertyValueFactory<>("socio"));
		this.clmIDPatron.setCellValueFactory(new PropertyValueFactory<>("matriculaPatron"));
		this.clmNombrePatron.setCellValueFactory(new PropertyValueFactory<>("patron"));
		this.clmIDDestino.setCellValueFactory(new PropertyValueFactory<>("puertoDest"));
		this.clmDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
		this.clmSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
		this.clmLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
		this.tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
		
		try {
			this.txtPatron.textProperty().addListener(new ChangeListener<Object>() {
				Conexion cn = new Conexion();
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					try {
						ListaDeRegistros.getObjeto().setGrupoRegistros(this.cn.buscarRegistros(txtPatron.getText()));
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
						if(txtPatron.getText().equals("") || txtPatron.getText().equals(null)) {
						cn.llenarTablaRegistros();
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
						}
					} catch (SQLException e) {
					}
				}
			});
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}

	public TableView<Registros> getTblTabla() {
		return tblTabla;
	}

}

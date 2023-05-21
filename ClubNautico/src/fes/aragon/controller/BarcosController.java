package fes.aragon.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BarcosController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Barcos b;
	
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private TableColumn<Barcos, String> clmCuota;

    @FXML
    private TableColumn<Barcos, Integer> clmIDBarco;

    @FXML
    private TableColumn<Barcos, String> clmNombresBarcos;

    @FXML
    private TableColumn<Barcos, Integer> clmNumeroAmarre;

    @FXML
    private TableView<Barcos> tblTabla;

    @FXML
    private TextField txtPatron;

    @FXML
    void actualizarBarco(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		this.indice = indice;
    		this.tabla = this.tblTabla;
    		this.nuevaVentana("ActualizarBarcos");
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    }

    @FXML
    void eliminarBarco(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0){
    	ListaDeRegistros.getObjeto().getGrupoBarcos().remove(indice);
    	}
    }

    @FXML
    void insertarBarco(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoBarcos().add(new Barcos());
    	this.nuevaVentana("InsertarBarcos");
    }
    
    public Conexion conexionSQL() {
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
		this.clmIDBarco.setCellValueFactory(new PropertyValueFactory<>("noSerieBarco"));
		this.clmNombresBarcos.setCellValueFactory(new PropertyValueFactory<>("nomBarco"));
		this.clmCuota.setCellValueFactory(new PropertyValueFactory<>("cuota"));
		this.clmNumeroAmarre.setCellValueFactory(new PropertyValueFactory<>("noAgarre"));
		String query = "select * from barcos";
		Statement st;
		try {
			st = this.cn.conexion.createStatement();
			ResultSet rs = st.executeQuery(query);
			ListaDeRegistros.getObjeto().getGrupoBarcos().clear();
			while(rs.next()) {
				Barcos b = new Barcos();
				b.setNoSerieBarco(rs.getInt(1));
				b.setNomBarco(rs.getString(2));
				b.setCuota(rs.getString(3));
				b.setNoAgarre(rs.getInt(4));
				ListaDeRegistros.getObjeto().getGrupoBarcos().add(b);
			}
			rs.close();
			st.close();
		} catch (SQLException e1) {
		}
		this.tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoBarcos());
		
		try {
			this.txtPatron.textProperty().addListener(new ChangeListener<Object>() {
				Conexion cn = new Conexion();
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					try {
						ListaDeRegistros.getObjeto().setGrupoBarcos(this.cn.buscarBarco(txtPatron.getText()));
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoBarcos());
						if(txtPatron.getText().equals("") || txtPatron.getText().equals(null)) {
						cn.llenarTablaBarcos();
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoBarcos());
						}
					} catch (SQLException e) {
					}
				}
			});
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}

}

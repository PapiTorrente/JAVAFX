package fes.aragon.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import fes.aragon.modelo.Destinos;
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

public class DestinosController extends BaseController implements Initializable{
	private Conexion cn = this.conexionSQL();
	
	private Destinos d;
	
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private TableColumn<Destinos, String> clmDestino;

    @FXML
    private TableColumn<Destinos, Integer> clmIDDestino;

    @FXML
    private TableView<Destinos> tblTabla;

    @FXML
    private TextField txtPatron;

    @FXML
    void actualizarDestino(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		this.indice = indice;
    		this.tabla = this.tblTabla;
    		this.nuevaVentana("ActualizarDestinos");
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    }

    @FXML
    void eliminarDestino(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0){
    	ListaDeRegistros.getObjeto().getGrupoDestinos().remove(indice);
    	}
    }

    @FXML
    void insertarDestino(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoDestinos().add(new Destinos());
    	this.nuevaVentana("InsertarDestinos");
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
		this.clmIDDestino.setCellValueFactory(new PropertyValueFactory<>("noSerieDestino"));
		this.clmDestino.setCellValueFactory(new PropertyValueFactory<>("puertoDest"));
		String query = "select * from destinos";
		Statement st;
		try {
			st = this.cn.conexion.createStatement();
			ResultSet rs = st.executeQuery(query);
			ListaDeRegistros.getObjeto().getGrupoDestinos().clear();
			while(rs.next()) {
				Destinos d = new Destinos();
				d.setNoSerieDestino(rs.getInt(1));
				d.setPuertoDest(rs.getString(2));
				ListaDeRegistros.getObjeto().getGrupoDestinos().add(d);
			}
			rs.close();
			st.close();
		} catch (SQLException e1) {
			System.out.println("OK");
		}
		this.tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoDestinos());
		
		try {
			this.txtPatron.textProperty().addListener(new ChangeListener<Object>() {
				Conexion cn = new Conexion();
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					try {
						ListaDeRegistros.getObjeto().setGrupoDestinos(this.cn.buscarDestino(txtPatron.getText()));
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoDestinos());
						if(txtPatron.getText().equals("") || txtPatron.getText().equals(null)) {
						cn.llenarTablaDestinos();;
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoDestinos());
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

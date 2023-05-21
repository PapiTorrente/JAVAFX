package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import fes.aragon.controller.BaseController;
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

public class SociosController extends BaseController implements Initializable{

	private Conexion cn = this.conexionSQL();
	
	private Socios s;
	
    @FXML
    private TableColumn<Socios, String> clmAMaterno;

    @FXML
    private TableColumn<Socios, String> clmAPaterno;

    @FXML
    private TableColumn<Socios, Boolean> clmDueno;

    @FXML
    private TableColumn<Socios, Date> clmFechaNacimiento;

    @FXML
    private TableColumn<Socios, Integer> clmIDSocio;

    @FXML
    private TableColumn<Socios, String> clmNombres;

    @FXML
    private TableColumn<Socios, String> clmNumeroCelular;

    @FXML
    private TableView<Socios> tblTabla;
    
    @FXML
    private TextField txtPatron;
    
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    void eliminarSocio(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0){
    	ListaDeRegistros.getObjeto().getGrupoSocios().remove(indice);
    	}
    }
    
    @FXML
    void actualizarSocio(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		this.indice = indice;
    		this.tabla = this.tblTabla;
    		this.nuevaVentana("ActualizarSocios");
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(this.btnSalir);
    	this.cn.cerrarConexion();
    }

    @FXML
    void insertarSocio(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoSocios().add(new Socios());
    	this.nuevaVentana("InsertarSocios");
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
		this.clmIDSocio.setCellValueFactory(new PropertyValueFactory<>("matriculaSocio"));
		this.clmNombres.setCellValueFactory(new PropertyValueFactory<>("nombresSocios"));
		this.clmAPaterno.setCellValueFactory(new PropertyValueFactory<>("apPaternoSocio"));
		this.clmAMaterno.setCellValueFactory(new PropertyValueFactory<>("apMaternoSocio"));
		this.clmDueno.setCellValueFactory(new PropertyValueFactory<>("esSocioDueno"));
		this.clmFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimientoSocio"));
		this.clmNumeroCelular.setCellValueFactory(new PropertyValueFactory<>("noCelularSocio"));
		String query = "select * from socios";
		Statement st;
		try {
			st = this.cn.conexion.createStatement();
			ResultSet rs = st.executeQuery(query);
			ListaDeRegistros.getObjeto().getGrupoSocios().clear();
			while(rs.next()) {
				Socios s = new Socios();
				s.setMatriculaSocio(rs.getInt(1));
				s.setNombresSocios(rs.getString(2));
				s.setApPaternoSocio(rs.getString(3));
				s.setApMaternoSocio(rs.getString(4));
				s.setEsSocioDueno(rs.getBoolean(5));
				s.setFechaNacimientoSocio(rs.getDate(6));
				s.setNoCelularSocio(rs.getString(7));
				ListaDeRegistros.getObjeto().getGrupoSocios().add(s);
			}
			rs.close();
			st.close();
		} catch (SQLException e1) {
		}
		this.tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoSocios());
		
		try {
			this.txtPatron.textProperty().addListener(new ChangeListener<Object>() {
				Conexion cn = new Conexion();
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					try {
						ListaDeRegistros.getObjeto().setGrupoSocios(this.cn.buscarSocio(txtPatron.getText()));
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoSocios());
						if(txtPatron.getText().equals("") || txtPatron.getText().equals(null)) {
						cn.llenarTablaSocios();
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoSocios());
						}
					} catch (SQLException e) {
					}
				}
			});
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
	
	public void eliminarSocio() throws SQLException{
	}

}

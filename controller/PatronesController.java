package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
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

public class PatronesController extends BaseController implements Initializable{

	private Conexion cn = conexionSQL();
	
	private Patrones p;
	
    @FXML
    private TableColumn<Patrones, String> clmAMaterno;

    @FXML
    private TableColumn<Patrones, String> clmAPaterno;

    @FXML
    private TableColumn<Patrones, Boolean> clmDueno;

    @FXML
    private TableColumn<Patrones, Date> clmFechaNacimiento;

    @FXML
    private TableColumn<Patrones, Integer> clmIDPatron;

    @FXML
    private TableColumn<Patrones, String> clmNombres;

    @FXML
    private TableColumn<Patrones, String> clmNumeroCelular;

    @FXML
    private TableView<Patrones> tblTabla;
    
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
    void eliminarPatron(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0){
    	ListaDeRegistros.getObjeto().getGrupoPatrones().remove(indice);
    	}
    }
    
    @FXML
    void actualizarPatron(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		this.indice = indice;
    		this.tabla = this.tblTabla;
    		this.nuevaVentana("ActualizarPatrones");
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(this.btnSalir);
    }

    @FXML
    void insertarPatron(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoPatrones().add(new Patrones());
    	this.nuevaVentana("InsertarPatrones");
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
		this.clmIDPatron.setCellValueFactory(new PropertyValueFactory<>("matriculaPatron"));
		this.clmNombres.setCellValueFactory(new PropertyValueFactory<>("nombresPatron"));
		this.clmAPaterno.setCellValueFactory(new PropertyValueFactory<>("apPaternoPatron"));
		this.clmAMaterno.setCellValueFactory(new PropertyValueFactory<>("apMaternoPatron"));
		this.clmDueno.setCellValueFactory(new PropertyValueFactory<>("esPatronDueno"));
		this.clmFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimientoPatron"));
		this.clmNumeroCelular.setCellValueFactory(new PropertyValueFactory<>("noCelularPatron"));
		String query = "select * from patrones";
		Statement st;
		try {
			st = this.cn.conexion.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Patrones p = new Patrones();
				p.setMatriculaPatron(rs.getInt(1));
				p.setNombresPatron(rs.getString(2));
				p.setApPaternoPatron(rs.getString(3));
				p.setApMaternoPatron(rs.getString(4));
				p.setEsPatronDueno(rs.getBoolean(5));
				p.setFechaNacimientoPatron(rs.getDate(6));
				p.setNoCelularPatron(rs.getString(7));
				ListaDeRegistros.getObjeto().getGrupoPatrones().add(p);
			}
			rs.close();
			st.close();
		} catch (SQLException e1) {
		}
		this.tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoPatrones());
		
		try {
			this.txtPatron.textProperty().addListener(new ChangeListener<Object>() {
				Conexion cn = new Conexion();
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
					try {
						ListaDeRegistros.getObjeto().setGrupoPatrones(this.cn.buscarPatrones(txtPatron.getText()));
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoPatrones());
						if(txtPatron.getText().equals("") || txtPatron.getText().equals(null)) {
						cn.llenarTablaPatrones();;
						tblTabla.setItems(ListaDeRegistros.getObjeto().getGrupoPatrones());
						}
					} catch (SQLException e) {
					}
				}
			});
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
	
	public void eliminarPatrones() throws SQLException{
	}

}

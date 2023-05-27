package fes.aragon.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.TipoError;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ActualizarDestinosController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Destinos d;
	
	String mensaje = "";
	
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtIDDestino;

    @FXML
    private TextField txtNombres;

    @FXML
    void actualizarDestinos(ActionEvent event) throws SQLException {
    	if(this.verificar()) {
    	this.actualizarDestinos();
    	ListaDeRegistros.getObjeto().getGrupoDestinos().set(this.indice, this.d);
    	this.cn.llenarTablaRegistros();
    	BaseController.tablaController.getTblTabla().setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
    	this.cerrarVentana(this.btnActualizar);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
    	this.cn.cerrarConexion();
    	}else {
    		this.ventanaEmergente("Error", "Error de guardado", this.mensaje);
    		this.mensaje = "";
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
    	this.cn.cerrarConexion();
    }
    
    public Conexion conexionSQL() {
		try {
			return new Conexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.d = ListaDeRegistros.getObjeto().getGrupoDestinos().get(indice);
		this.txtIDDestino.setText(String.valueOf(d.getNoSerieDestino()));
		this.txtNombres.setText(d.getPuertoDest());
		this.verificarEntrada(txtNombres, TipoError.NOMBREDESTINO);
	}
	
	public void actualizarDestinos() throws SQLException {
		String queryUno = "update destinos set puerto_dest ='";
		String queryDos = " where id_puerto='" + this.d.getNoSerieDestino() + "'";
		boolean puertoDest = false;
		
		if(!(this.d.getPuertoDest().equals(this.txtNombres.getText()))) {
			queryUno = queryUno + this.txtNombres.getText() + "'";
			this.d.setPuertoDest(this.txtNombres.getText());
			puertoDest = true;
		}

		if(puertoDest) {
		queryUno = queryUno + queryDos;
		PreparedStatement pr = this.cn.conexion.prepareStatement(queryUno);
		pr.execute();
		pr.close();
		}
	}
	
	private boolean verificar() {
		boolean valido = true;

		if ((this.txtNombres.getText() == null)
				|| (this.txtNombres.getText() != null && this.txtNombres.getText().isEmpty())) {
			this.mensaje += "- El nombre del destino no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtNombres.getText().length() > 30) {
			this.mensaje += "- El nombre no es valido, debe tener máximo 30 caracteres.\n";
			valido = false;
		}
		
		if (!this.nombreValido) {
			this.mensaje += "- El nombre del destino solo debe contener letras.\n";
			valido = false;
		}
		
		return valido;
	}


}

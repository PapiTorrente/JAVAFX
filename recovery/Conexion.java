package fes.aragon.recovery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fes.aragon.ayuda.BaseControllerAyuda;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Socios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion extends BaseControllerAyuda{
	private String url="jdbc:mariadb://127.0.0.1:3306/clubnautico?serverTimeZone=UTC";
	private String usuario="root";
	private String clave="C00Mand#";
	public  Connection conexion;
	
	public Conexion() throws ClassNotFoundException, SQLException{
			Class.forName("org.mariadb.jdbc.Driver");
			conexion=DriverManager.getConnection(url, usuario, clave);	
	}
	
	public void llenarTablaSocios() throws SQLException {
		String query = "select * from socios";
		Statement st;
		st = conexion.createStatement();
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
	}
	
	public int insertarSocios(Socios socios) throws SQLException {
		String query = "insert into socios values (null,?,?,?,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, socios.getNombresSocios());
		pr.setString(2, socios.getApPaternoSocio());
		pr.setString(3, socios.getApMaternoSocio());
		pr.setBoolean(4, socios.isEsSocioDueno());
		pr.setDate(5, socios.getFechaNacimientoSocio());
		pr.setString(6, socios.getNoCelularSocio());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}
	
	public void actualizarSocio(Socios s, String nombreActualizar) throws SQLException{
		String query = "update socios set nombres_socio = '" + nombreActualizar + "' where nombres_socio = '" + s.getNombresSocios() + "'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.close();
	}
	
	public ObservableList<Socios> buscarSocio(String socio) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from socios where nombres_socio like '%"+socio+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Socios> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Socios s = new Socios();
			s.setMatriculaSocio(rs.getInt(1));
			s.setNombresSocios(rs.getString(2));
			s.setApPaternoSocio(rs.getString(3));
			s.setApMaternoSocio(rs.getString(4));
			s.setEsSocioDueno(rs.getBoolean(5));
			s.setFechaNacimientoSocio(rs.getDate(6));
			s.setNoCelularSocio(rs.getString(7));
			datos.add(s);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public int insertarPatrones(Patrones patrones) throws SQLException {
		String query = "insert into socios values (null,?,?,?,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, patrones.getNombresPatron());
		pr.setString(2, patrones.getApPaternoPatron());
		pr.setString(3, patrones.getApMaternoPatron());
		pr.setBoolean(4, patrones.isEsPatronDueno());
		pr.setDate(5, patrones.getFechaNacimientoPatron());
		pr.setString(6, patrones.getNoCelularPatron());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}
	
	public void actualizarPatron(Patrones p, String nombreActualizar) throws SQLException{
		String query = "update socios set nombres_socio = '" + nombreActualizar + "' where nombres_socio = '" + p.getNombresPatron() + "'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		rs.close();
	}
	
	public ObservableList<Patrones> buscarPatron(String patron) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from socios where nombres_socio like '%"+patron+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Patrones> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			datos.add(p);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public void cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

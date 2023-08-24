package models.admin;

import models.user.DaoUser;
import models.user.Incidencia;
import utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoAdmin {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Incidencia> buscarIncidenciasAceptadas(){
        List<Incidencia> incidencias = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM incidencias where estado = 'ACEPTADO';";
            pstm  =conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                Incidencia incidencia = new Incidencia();
                incidencia.setId(rs.getLong("id"));
                incidencia.setTitulo(rs.getString("titulo"));
                incidencia.setDescripcion(rs.getString("descripcion"));
                incidencia.setEstado(rs.getString("estado"));
                incidencias.add(incidencia);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE,"ERROR findAll"+e.getMessage());
        }finally {
            close();
        }
        return incidencias;
    }

    public boolean IncidenciaAprobada(Long id){
        try{
            conn = new MySQLConnection().connect();
            String query = "UPDATE incidencias SET estado = 'APROBADO' WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, Long.parseLong(String.valueOf(id)));
            return  pstm.executeUpdate() > 0;
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error save" + e.getMessage());
        }finally {
            close();
        }
        return false;
    }

    public boolean IncidenciaRechazada(Long id){
        try{
            conn = new MySQLConnection().connect();
            String query = "UPDATE incidencias SET estado = 'RECHAZADO' WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, Long.parseLong(String.valueOf(id)));
            return  pstm.executeUpdate() > 0;
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error save" + e.getMessage());
        }finally {
            close();
        }
        return false;
    }


    public void close(){
        try {
            if (conn != null) conn.close();
            if (pstm != null) pstm.close();
            if (rs != null) rs.close();
        }catch (SQLException e){

        }
    }
}

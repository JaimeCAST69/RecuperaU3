package models.charge;

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

public class DaoCharge {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Incidencia> buscarIncidenciasPendientes(){
        List<Incidencia> incidencias = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM incidencias where estado = 'PENDIENTE';";
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


    public void close(){
        try {
            if (conn != null) conn.close();
            if (pstm != null) pstm.close();
            if (rs != null) rs.close();
        }catch (SQLException e){

        }
    }
}

package models.user;


import models.crud.DaoRepository;
import utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUser implements DaoRepository<User> {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM usuarios;";
            pstm  =conn.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE,"ERROR findAll"+e.getMessage());
        }finally {
            close();
        }
        return users;
    }

    public List<Incidencia> buscarIncidencias(){
        List<Incidencia> incidencias = new ArrayList<>();
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT * FROM incidencias;";
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

    public User loadUserByUsernameAndPassword(String username,
                                              String password) {
        try {
            conn = new MySQLConnection().connect();
            String query = "SELECT id, usuario, role FROM usuarios WHERE usuario = ? AND password = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, username);
            pstm.setString(2, password);
            rs = pstm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("usuario"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUser.class.getName())
                    .log(Level.SEVERE,
                            "Credentials mismatch: " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    @Override
    public boolean save(User object) {
        try{
            conn = new MySQLConnection().connect();
            String query = "INSERT INTO usuarios (usuario,password,role)" +
                    "VALUES (?,?,?);";
            pstm = conn.prepareStatement(query);
            pstm.setString(1,object.getUsername());
            pstm.setString(2,object.getPassword());
            pstm.setString(3,object.getRole());
            return  pstm.executeUpdate() > 0;
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error save" + e.getMessage());
        }finally {
            close();
        }
        return false;
    }

    public boolean saveIncidencia(Incidencia object){
        try{
            conn = new MySQLConnection().connect();
            String query = "INSERT INTO incidencias (titulo,descripcion)" +
                    "VALUES (?,?);";
            pstm = conn.prepareStatement(query);
            pstm.setString(1,object.getTitulo());
            pstm.setString(2,object.getDescripcion());
            return  pstm.executeUpdate() > 0;
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error save" + e.getMessage());
        }finally {
            close();
        }
        return false;
    }

    public boolean IncidenciaAceptada(Long id){
        try{
            conn = new MySQLConnection().connect();
            String query = "UPDATE incidencias SET estado = 'ACEPTADO' WHERE id = ?;";
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

    @Override
    public boolean delete(Long id) {

        try {
            conn = new MySQLConnection().connect();
            String query = "DELETE FROM usuarios WHERE id = ?;";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1,id);
            return pstm.executeUpdate() == 1;
        }catch (SQLException e){
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error delete" + e.getMessage());
        }finally{
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

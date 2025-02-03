package org.example.model.dao;

import org.example.model.connection.MySQLConnection;
import org.example.model.entities.Usuario;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario, Integer>{
    private static String INSERT =" INSERT INTO usuario (nombre, email, contraseña) VALUES (?,?,?)";
    private static String UPDATE = "UPDATE usuario SET nombre = ?, email = ?, contraseña = ? WHERE id=? ";
    private static String FINDBYID = "SELECT x.id, x.nombre, x.email FROM usuario AS x WHERE x.id=?";
    private static String FINDUSERBYEMAIL = "SELECT x.id, x.nombre, x.email FROM usuario AS x WHERE x.email = ?";
    private static String FINDALLUSER = "SELECT x.id, x.nombre, x.email FROM usuario AS x";
    private static String DELETE = "DELETE FROM usuario WHERE id = ?";

    private final Connection con;

    public UsuarioDAO(Connection con) {
        this.con = MySQLConnection.getConnection();
    }

    @Override
    public Usuario save(Usuario entity) throws SQLException {
        Usuario result = entity;
        if (entity == null) return result;
        if (entity.getId() == null) {
            try (PreparedStatement pst = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getNombre());
                pst.setString(2, entity.getEmail());
                pst.setString(3, entity.getContraseña());
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = con.prepareStatement(UPDATE)) {
                pst.setString(1, entity.getNombre() != null ? entity.getNombre() : "");
                pst.setString(2, entity.getEmail() != null ? entity.getEmail() : "");
                pst.setString(3, entity.getContraseña() != null ? entity.getContraseña() : "");
                pst.setInt(4, entity.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Usuario delete(Usuario entity) throws SQLException {
        if (entity == null || entity.getId() == null) return entity;
        try (PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

    @Override
    public Usuario findById(Integer key) throws SQLException {
        Usuario result = null;
        try (PreparedStatement pst = con.prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()){
                result = new Usuario();
                result.setId(res.getInt("id"));
                result.setNombre(res.getString("nombre"));
                result.setEmail(res.getString("email"));
            }
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public Usuario findByEmail(String key) throws SQLException {
        Usuario result = null;
        try (PreparedStatement pst = con.prepareStatement(FINDUSERBYEMAIL)) {
            pst.setString(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()){
                result = new Usuario();
                result.setId(res.getInt("id"));
                result.setNombre(res.getString("nombre"));
                result.setEmail(res.getString("email"));
            }
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List findAll() throws SQLException {
        List<Usuario> result = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(FINDALLUSER)) {
            ResultSet res = pst.executeQuery();
            while (res.next()){
                Usuario u = new Usuario();
                u.setId(res.getInt("id"));
                u.setNombre(res.getString("nombre"));
                u.setEmail(res.getString("email"));
                result.add(u);
            }
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static UsuarioDAO buildUsuario() {
        return new UsuarioDAO(MySQLConnection.getConnection());
    }
}

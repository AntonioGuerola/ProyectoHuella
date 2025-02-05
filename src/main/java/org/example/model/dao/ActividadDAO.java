package org.example.model.dao;

import org.example.model.connection.MySQLConnection;
import org.example.model.entities.Actividad;
import org.example.model.entities.Categoria;
import org.example.model.entities.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO implements DAO<Actividad, Integer>{
    private static String FINDBYID = "SELECT x.id_actividad, x.nombre, x.id_categoria FROM actividad AS x WHERE x.id_actividad=?";
    private static String FINDALL = "SELECT x.id_actividad, x.nombre, x.id_categoria FROM actividad AS x";
    private static String FINDBYNAME = "SELECT x.id_actividad, x.nombre, x.id_categoria FROM actividad AS x WHERE x.nombre=?";

    private final Connection con;

    public ActividadDAO(Connection con) {
        this.con = MySQLConnection.getConnection();
    }

    @Override
    public Actividad save(Actividad entity) throws SQLException {
        return null;
    }

    @Override
    public Actividad delete(Actividad entity) throws SQLException {
        return null;
    }

    @Override
    public Actividad findById(Integer key) throws SQLException {
        Actividad result = null;
        Categoria categoria = null;

        try (PreparedStatement pst = con.prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();

            if (res.next()){
                result = new Actividad();
                result.setId(res.getInt("id_actividad"));
                result.setNombre(res.getString("nombre"));
                int idCategoria = res.getInt("id_categoria");
                if (!res.wasNull()){
                    categoria = CategoriaDAO.buildCategoria().findById(idCategoria);
                }
                result.setIdCategoria(categoria);
            }
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List findAll() throws SQLException {
        List<Actividad> result = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()){
                Actividad a = new Actividad();
                a.setId(res.getInt("id_actividad"));
                a.setNombre(res.getString("nombre"));
                int idCategoria = res.getInt("id_categoria");
                if (!res.wasNull()){
                    Categoria cat = CategoriaDAO.buildCategoria().findById(idCategoria);
                    a.setIdCategoria(cat);
                }
                result.add(a);
            }
            res.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public Actividad findByName(String key) throws SQLException {
        Actividad result = null;
        Categoria categoria = null;

        try (PreparedStatement pst = con.prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            ResultSet res = pst.executeQuery();

            if (res.next()){
                result = new Actividad();
                result.setId(res.getInt("id_actividad"));
                result.setNombre(res.getString("nombre"));
                int idCategoria = res.getInt("id_categoria");
                if (!res.wasNull()){
                    categoria = CategoriaDAO.buildCategoria().findById(idCategoria);
                }
                result.setIdCategoria(categoria);
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

    public static ActividadDAO buildActividadDAO() {
        return new ActividadDAO(MySQLConnection.getConnection());
    }
}

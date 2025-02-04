package org.example.model.dao;

import org.example.model.connection.MySQLConnection;
import org.example.model.entities.Actividad;
import org.example.model.entities.Categoria;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDAO implements DAO<Categoria, Integer>{
    private static String FINDBYID = "SELECT x.id_categoria, x.nombre, x.factor_emision, x.unidad FROM categoria AS x WHERE x.id_categoria=?";

    private final Connection con;

    public CategoriaDAO(Connection con) {
        this.con = MySQLConnection.getConnection();
    }

    @Override
    public Categoria save(Categoria entity) throws SQLException {
        return null;
    }

    @Override
    public Categoria delete(Categoria entity) throws SQLException {
        return null;
    }

    @Override
    public Categoria findById(Integer key) throws SQLException {
        Categoria result = null;
        try(PreparedStatement pst = con.prepareStatement(FINDBYID)){
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()){
                result = new Categoria();
                result.setId(res.getInt("id_categoria"));
                result.setNombre(res.getString("nombre"));
                result.setFactorEmision(res.getBigDecimal("factor_emision"));
                result.setUnidad(res.getString("unidad"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Categoria> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }

    public static CategoriaDAO buildCategoria() {
        return new CategoriaDAO(MySQLConnection.getConnection());
    }
}

package org.example.model.dao;

import org.example.model.connection.MySQLConnection;
import org.example.model.entities.Huella;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HuellaDAO implements DAO<Huella, Integer> {
    private static final String INSERT = "INSERT INTO huella (id_usuario, id_actividad, valor, unidad, fecha) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE huella SET id_usuario = ?, id_actividad = ?, valor = ?, unidad = ?, fecha = ? WHERE id_registro = ?";
    private static final String DELETE = "DELETE FROM huella WHERE id_registro = ?";
    private static final String FINDBYID = "SELECT * FROM huella WHERE id_registro = ?";
    private static final String FINDALL = "SELECT * FROM huella";

    private final Connection con;

    public HuellaDAO(Connection con) {
        this.con = MySQLConnection.getConnection();
    }

    @Override
    public Huella save(Huella entity) throws SQLException {
        if (entity == null) return null;
        if (entity.getId() == null) {
            try (PreparedStatement pst = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getIdUsuario().getId());
                pst.setInt(2, entity.getIdActividad().getId());
                pst.setBigDecimal(3, entity.getValor());
                pst.setString(4, entity.getUnidad());
                pst.setTimestamp(5, entity.getFecha() != null ? Timestamp.from(entity.getFecha()) : null);
                pst.executeUpdate();
                ResultSet resultSet = pst.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getInt(1));
                }
            }
        } else {
            try (PreparedStatement pst = con.prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getIdUsuario().getId());
                pst.setInt(2, entity.getIdActividad().getId());
                pst.setBigDecimal(3, entity.getValor());
                pst.setString(4, entity.getUnidad());
                pst.setTimestamp(5, entity.getFecha() != null ? Timestamp.from(entity.getFecha()) : null);
                pst.setInt(6, entity.getId());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    @Override
    public Huella delete(Huella entity) throws SQLException {
        if (entity == null || entity.getId() == null) return entity;
        try (PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Huella findById(Integer key) throws SQLException {
        Huella result = null;
        try (PreparedStatement pst = con.prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = new Huella();
                result.setId(res.getInt("id_registro"));
                result.setValor(res.getBigDecimal("valor"));
                result.setUnidad(res.getString("unidad"));
                result.setFecha(res.getTimestamp("fecha") != null ? res.getTimestamp("fecha").toInstant() : null);
            }
        }
        return result;
    }

    @Override
    public List<Huella> findAll() throws SQLException {
        List<Huella> result = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Huella h = new Huella();
                h.setId(res.getInt("id_registro"));
                h.setValor(res.getBigDecimal("valor"));
                h.setUnidad(res.getString("unidad"));
                h.setFecha(res.getTimestamp("fecha") != null ? res.getTimestamp("fecha").toInstant() : null);
                result.add(h);
            }
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            throw new IOException("Error cerrando la conexión", e);
        }
    }

    public static HuellaDAO buildHuella() {
        return new HuellaDAO(MySQLConnection.getConnection());
    }
}

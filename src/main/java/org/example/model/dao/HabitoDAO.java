package org.example.model.dao;

import org.example.model.connection.MySQLConnection;
import org.example.model.entities.Habito;
import org.example.model.entities.HabitoId;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitoDAO implements DAO<Habito, HabitoId> {
    private static final String INSERT = "INSERT INTO habito (id_usuario, id_actividad, frecuencia, tipo, ultima_fecha) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE habito SET frecuencia = ?, tipo = ?, ultima_fecha = ? WHERE id_usuario = ? AND id_actividad = ?";
    private static final String DELETE = "DELETE FROM habito WHERE id_usuario = ? AND id_actividad = ?";
    private static final String FINDBYID = "SELECT id_usuario, id_actividad, frecuencia, tipo, ultima_fecha FROM habito WHERE id_usuario = ? AND id_actividad = ?";
    private static final String FINDALL = "SELECT id_usuario, id_actividad, frecuencia, tipo, ultima_fecha FROM habito";

    private final Connection con;

    public HabitoDAO(Connection con) {
        this.con = MySQLConnection.getConnection();
    }

    @Override
    public Habito save(Habito entity) throws SQLException {
        if (entity == null) return null;
        if (findById(entity.getId()) == null) {
            try (PreparedStatement pst = con.prepareStatement(INSERT)) {
                pst.setInt(1, entity.getId().getIdUsuario());
                pst.setInt(2, entity.getId().getIdActividad());
                pst.setInt(3, entity.getFrecuencia());
                pst.setString(4, entity.getTipo());
                pst.setTimestamp(5, entity.getUltimaFecha() != null ? Timestamp.from(entity.getUltimaFecha()) : null);
                pst.executeUpdate();
            }
        } else {
            try (PreparedStatement pst = con.prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getFrecuencia());
                pst.setString(2, entity.getTipo());
                pst.setTimestamp(3, entity.getUltimaFecha() != null ? Timestamp.from(entity.getUltimaFecha()) : null);
                pst.setInt(4, entity.getId().getIdUsuario());
                pst.setInt(5, entity.getId().getIdActividad());
                pst.executeUpdate();
            }
        }
        return entity;
    }

    @Override
    public Habito delete(Habito entity) throws SQLException {
        if (entity == null) return null;
        try (PreparedStatement pst = con.prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId().getIdUsuario());
            pst.setInt(2, entity.getId().getIdActividad());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Habito findById(HabitoId key) throws SQLException {
        Habito result = null;
        try (PreparedStatement pst = con.prepareStatement(FINDBYID)) {
            pst.setInt(1, key.getIdUsuario());
            pst.setInt(2, key.getIdActividad());
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result = new Habito();
                result.setId(new HabitoId(res.getInt("id_usuario"), res.getInt("id_actividad")));
                result.setFrecuencia(res.getInt("frecuencia"));
                result.setTipo(res.getString("tipo"));
                Timestamp timestamp = res.getTimestamp("ultima_fecha");
                result.setUltimaFecha(timestamp != null ? timestamp.toInstant() : null);
            }
        }
        return result;
    }

    @Override
    public List<Habito> findAll() throws SQLException {
        List<Habito> result = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(FINDALL)) {
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Habito h = new Habito();
                h.setId(new HabitoId(res.getInt("id_usuario"), res.getInt("id_actividad")));
                h.setFrecuencia(res.getInt("frecuencia"));
                h.setTipo(res.getString("tipo"));
                Timestamp timestamp = res.getTimestamp("ultima_fecha");
                h.setUltimaFecha(timestamp != null ? timestamp.toInstant() : null);
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

    public static HabitoDAO buildHabito() {
        return new HabitoDAO(MySQLConnection.getConnection());
    }
}

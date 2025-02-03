package org.example.model.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HabitoDAO implements DAO{
    @Override
    public Object save(Object entity) throws SQLException {
        return null;
    }

    @Override
    public Object delete(Object entity) throws SQLException {
        return null;
    }

    @Override
    public Object findById(Object key) throws SQLException {
        return null;
    }

    @Override
    public List findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void close() throws IOException {

    }
}

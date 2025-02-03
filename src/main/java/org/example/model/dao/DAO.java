package org.example.model.dao;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> extends Closeable {
    /**
     * Saves the given entity to the database. If the entity is new, it will be inserted;
     * otherwise, the existing entity will be updated.
     *
     * @param entity the entity to save
     * @return the saved entity with any database-generated fields populated (e.g., ID)
     */
    T save(T entity) throws SQLException;

    /**
     * Deletes the given entity from the database.
     *
     * @param entity the entity to delete
     * @return the deleted entity
     * @throws SQLException if a database access error occurs
     */
    T delete(T entity) throws SQLException;

    /**
     * Finds an entity by its primary key.
     *
     * @param key the primary key of the entity to find
     * @return the found entity, or null if no entity was found with the given key
     */
    T findById(K key) throws SQLException;

    /**
     * Finds all entities of the given type in the database.
     *
     * @return a list of all entities
     */
    List<T> findAll() throws SQLException;
}

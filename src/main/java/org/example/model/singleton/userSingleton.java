package org.example.model.singleton;

import org.example.model.entities.Usuario;

/**
 * Singleton class to manage the current user in the system.
 */
public class userSingleton {
    private static userSingleton _instance;
    private Usuario currentUser;

    /**
     * Private constructor to prevent direct instantiation.
     * @param user The user to be set as the current user.
     */
    private userSingleton(Usuario user) {
        this.currentUser = user;
    }

    /**
     * Returns the instance of userSingleton. If not initialized, returns null.
     * @return The instance of userSingleton.
     */
    public static userSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the Singleton instance with the provided user.
     * If already initialized, returns the existing instance.
     * @param userToUse The user to be set as the current user.
     * @return The instance of userSingleton.
     */
    public static userSingleton initialize(Usuario userToUse) {
        if (_instance == null && userToUse != null) {
            _instance = new userSingleton(userToUse);
        }
        return _instance;
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current user.
     * @return The current user.
     */
    public Usuario getCurrentUser() {
        return currentUser;
    }
}

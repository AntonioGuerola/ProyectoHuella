package org.example.model.singleton;

import org.example.model.entities.Usuario;


/**
 * Singleton class to manage the current modeler in the system.
 */
public class userSingleton {
    private static userSingleton _instance;
    private Usuario currentUser;

    /**
     * Initializes a new instance of the userSingleton class with the provided user.
     *
     * @param user The user to be set as the current user.
     */
    private userSingleton(Usuario user) {
        currentUser = user;
    }

    /**
     * Retrieves the instance of the UserSingleton class.
     * @return The instance of UserSingleton.
     */
    public static userSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the UserSingleton instance with the provided user.
     * @param userToUse The user to be set as the current user.
     */
    public static void getInstance(Usuario userToUse) {
        if (userToUse != null) {
            _instance = new userSingleton(userToUse);
        }
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

package org.example.model.service;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registerUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null ||
                user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        }

        Usuario existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            JavaFXUtils.showErrorAlert("ERROR AL REGISTRAR EMAIL", "El email ya existe");
            return false;
        }

        user.setContraseña(user.getContraseña());
        usuarioDAO.insertUsuario(user);
        userSingleton.getInstance().initialize(user);
        return true;
    }

    public boolean updateUser(Usuario user, String nuevaContraseña) {
        try {
            // Verifica si la contraseña ha cambiado
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                if (nuevaContraseña.length() > 16) {
                    JavaFXUtils.showErrorAlert("ERROR", "La contraseña no puede tener más de 16 caracteres.");
                    return false;
                }
                if (JavaFXUtils.hashPassword(nuevaContraseña).equals(user.getContraseña())) {
                    JavaFXUtils.showErrorAlert("ERROR", "La nueva contraseña no puede ser igual a la actual.");
                    return false;
                }
                // Si la contraseña cambia, se hashea
                user.setContraseña(JavaFXUtils.hashPassword(nuevaContraseña));
            }

            usuarioDAO.updateUsuario(user);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteUser(Usuario user) {
        try {
            usuarioDAO.deleteUsuario(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Usuario getUserById(Integer userId) {
        try {
            return usuarioDAO.findUserByID(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario getUserByEmail(String email) {
        try {
            return usuarioDAO.findUserByEmail(email);
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Manejamos la excepción aquí devolviendo null si no se encuentra el usuario
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> getAllUsers() {
        try {
            return usuarioDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario getUserWithFootprints(Integer userId) {
        try {
            return usuarioDAO.getUsuarioConHuellas(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> getAllUsersWithFootprints() {
        try {
            return usuarioDAO.getAllUsersWithFootprints();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UsuarioService buildUsuarioService() {
        return new UsuarioService();
    }
}

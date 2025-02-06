package org.example.model.service;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entities.Usuario;
import org.example.model.singleton.userSingleton;
import org.example.model.utils.JavaFXUtils;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean loginUser(Usuario user) {
        if (user.getEmail() == null || user.getContraseña() == null || user.getEmail().isEmpty() || user.getContraseña().isEmpty()) {
            return false;
        }

        try {
            Usuario foundUser = usuarioDAO.findUserByEmail(user.getEmail());
            if (foundUser != null) {
                String hashedInputPassword = JavaFXUtils.hashPassword(user.getContraseña());
                if (hashedInputPassword.equals(foundUser.getContraseña())) {
                    userSingleton.getInstance().getCurrentUser().setEmail(user.getEmail());
                    System.out.println(foundUser);
                    return true;
                }
                System.out.println("CONTRASEÑA INCORRECTA");
            } else {
                System.out.println("EMAIL NO ENCONTRADO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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

    public boolean updateUser(Usuario user) {
        try {
            if (user.getContraseña() != null) {
                user.setContraseña(JavaFXUtils.hashPassword(user.getContraseña()));
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

    public List<BigDecimal> getEmissionFactor(Usuario user) {
        try {
            return usuarioDAO.getFactorEmision(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UsuarioService buildUsuarioService() {
        return new UsuarioService();
    }
}

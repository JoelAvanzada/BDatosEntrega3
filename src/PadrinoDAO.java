package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class PadrinoDAO {

    public void insertarPadrino(String dni, String nombre, String apellido, String direccion, String codPostal,
            String email, String facebook, String telFijo, String celular, java.util.Date fechaNacimiento) {
        String sql = "INSERT INTO Padrino (dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, direccion);
            stmt.setString(5, codPostal);
            stmt.setString(6, email);
            stmt.setString(7, facebook);
            stmt.setString(8, telFijo);
            stmt.setString(9, celular);
            stmt.setDate(10, new Date(fechaNacimiento.getTime()));

            stmt.executeUpdate();
            System.out.println("Padrino insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar padrino: " + e.getMessage());
        }
    }
}

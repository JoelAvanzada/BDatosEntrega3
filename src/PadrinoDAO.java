package src;

import java.sql.*;

public class PadrinoDAO {

    public void insertarPadrino(String dni, String nombre, String apellido, String direccion, String codPostal,
            String email, String facebook, String telFijo, String celular, java.util.Date fechaNacimiento)
            throws SQLException {
        String sql = "INSERT INTO Padrino (dni, nombre, apellido, direccion, cod_postal, email, facebook, tel_fijo, celular, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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
            System.out.println("\nPadrino insertado correctamente.");
        }
    }

    public boolean existeDNI(String dni) throws SQLException {
        String sql = "SELECT dni FROM Padrino WHERE dni = ?";
        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // true si ya existe
            }
        }
    }

}
package src;

import java.sql.*;

public class DonanteDAO {

    public void insertarDonante(String dni, String cuil, String ocupacion, Integer idPago) throws SQLException {
        String sql = "INSERT INTO Donante (dni, cuil, ocupacion, id_pago) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);
            stmt.setString(2, cuil);
            stmt.setString(3, ocupacion);
            if (idPago != null) {
                stmt.setInt(4, idPago);
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("Donante insertado correctamente.");
        }
    }

    public void eliminarDonante(String dni) throws SQLException {
        String sql = "DELETE FROM Donante WHERE dni = ?";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Donante eliminado correctamente.");
            } else {
                System.out.println("No se encontró un donante con ese DNI.");
            }
        }
    }
}

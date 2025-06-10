package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonanteDAO {

    public void eliminarDonante(String dni) {
        String sql = "DELETE FROM Donante WHERE dni = ?";

        try (Connection conn = ConexionDB.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Donante eliminado correctamente.");
            } else {
                System.out.println("No se encontró donante con DNI: " + dni);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar donante: " + e.getMessage());
        }
    }
}

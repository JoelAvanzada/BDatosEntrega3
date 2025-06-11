package src;

import java.sql.*;

public class ProgramaDAO {

    public void listarProgramas() {
        String sql = "SELECT id_programa, nombre FROM Programa ORDER BY nombre";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Programas disponibles ---");
            while (rs.next()) {
                String id = rs.getString("id_programa");
                String nombre = rs.getString("nombre");
                System.out.printf("%s - %s%n", id, nombre);
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            System.err.println("Error al listar programas: " + e.getMessage());
        }
    }
}

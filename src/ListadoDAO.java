package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListadoDAO {

    public void listarPadrinosConProgramas() {
        String sql = "SELECT p.dni, p.apellido, p.nombre, " +
                "pr.nombre AS nombre_programa, a.monto, a.frecuencia " +
                "FROM Padrino p " +
                "JOIN Donante d ON p.dni = d.dni " +
                "JOIN Aporta a ON d.dni = a.dni_d " +
                "JOIN Programa pr ON a.id_programa = pr.id_programa " +
                "ORDER BY p.apellido, p.nombre";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println("DNI | Apellido, Nombre | Programa | Monto | Frecuencia");
            System.out.println("--------------------------------------------------------");

            while (rs.next()) {
                String dni = rs.getString("dni");
                String apellido = rs.getString("apellido");
                String nombre = rs.getString("nombre");
                String programa = rs.getString("nombre_programa");
                double monto = rs.getDouble("monto");
                String frecuencia = rs.getString("frecuencia");

                System.out.printf("%s | %s, %s | %s | %.2f | %s%n", dni, apellido, nombre, programa, monto, frecuencia);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar padrinos con programas: " + e.getMessage());
        }
    }
}

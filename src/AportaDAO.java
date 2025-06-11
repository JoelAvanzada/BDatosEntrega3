package src;

import java.sql.*;

public class AportaDAO {

    public void insertarAporte(String dniDonante, String idPrograma, double monto, String frecuencia)
            throws SQLException {
        String sql = "INSERT INTO Aporta (dni_d, id_programa, monto, frecuencia) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dniDonante);
            stmt.setString(2, idPrograma);
            stmt.setDouble(3, monto);
            stmt.setString(4, frecuencia);

            stmt.executeUpdate();
            System.out.println("Aporte registrado correctamente.");
        }
    }
}

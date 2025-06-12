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
    Connection conn = null;
    try {
        conn = ConexionDB.conectar();
        conn.setAutoCommit(false);

        // 1. Verificar y registrar auditoría
        registrarAuditoria(conn, dni);
        
        // 2. Eliminar aportes
        try {
            eliminarAportes(conn, dni);
        } catch (SQLException e) {
            System.out.println("Advertencia: No se encontraron aportes para eliminar");
        }
        
        // 3. Eliminar donante
        eliminarDonanteDirecto(conn, dni);
        
        conn.commit();
        System.out.println("Donante eliminado correctamente.");
    } catch (SQLException e) {
        if (conn != null) {
            conn.rollback();
        }
        throw new SQLException("Error al eliminar donante: " + e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}

    private void registrarAuditoria(Connection conn, String dni) throws SQLException {
    // Solo verificar que exista el padrino, el trigger hará el registro
    String sql = "SELECT 1 FROM Padrino WHERE dni = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, dni);
        try (ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("No se encontró el padrino con DNI: " + dni);
            }
        }
    }
}

    private void eliminarAportes(Connection conn, String dni) throws SQLException {
        String sql = "DELETE FROM Aporta WHERE dni_d = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
        }
    }

    private void eliminarDonanteDirecto(Connection conn, String dni) throws SQLException {
        String sql = "DELETE FROM Donante WHERE dni = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró un donante con ese DNI");
            }
        }
    }
}
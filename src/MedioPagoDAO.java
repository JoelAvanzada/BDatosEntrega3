package src;

import java.sql.*;

public class MedioPagoDAO {

    // 👉 Inserta en MedioPago y retorna el id_pago generado
    public int insertarMedioPago(String nombreTitular) throws SQLException {
        String sql = "INSERT INTO MedioPago (nombre_titular) VALUES (?)";
        int idPagoGenerado = -1;

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nombreTitular);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idPagoGenerado = rs.getInt(1);
                }
            }
        }

        return idPagoGenerado;
    }

    // 👉 Inserta en Credito
    public void insertarCredito(int idPago, String nombreTarjeta, String nroTarjeta, java.util.Date fechaVenc)
            throws SQLException {
        String sql = "INSERT INTO Credito (id_pago, nombre_tarjeta, nro_tarjeta, fecha_venc) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPago);
            stmt.setString(2, nombreTarjeta);
            stmt.setString(3, nroTarjeta);
            stmt.setDate(4, new Date(fechaVenc.getTime()));

            stmt.executeUpdate();
            System.out.println("Medio de pago con tarjeta de crédito insertado correctamente.");
        }
    }

    // 👉 Inserta en DebitoTransferencia
    public void insertarDebito(int idPago, String banco, String sucursal, String tipoCuenta, String nroCuenta,
            String cbu) throws SQLException {
        String sql = "INSERT INTO DebitoTransferencia (id_pago, nombre_sucursal, tipo_cuenta, nro_cuenta, cbu) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPago);
            stmt.setString(2, banco + " - " + sucursal); // esto arma un string tipo "nacion - rio cuarto"
            stmt.setString(3, tipoCuenta);
            stmt.setString(4, nroCuenta);
            stmt.setString(5, cbu); // este es el último

            stmt.executeUpdate();
            System.out.println("Medio de pago con débito o transferencia insertado correctamente.");
        }
    }

}

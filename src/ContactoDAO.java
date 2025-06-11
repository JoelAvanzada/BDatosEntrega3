package src;

import java.sql.*;

public class ContactoDAO {

    public void insertarContacto(String dni, java.util.Date f1, java.util.Date f2,
            java.util.Date f3, java.util.Date f4, String estado) throws SQLException {

        String sql = "INSERT INTO Contacto (dni, fecha_primer_contacto, fecha_de_alta, fecha_de_baja, fecha_rechazo_adhesion, estado) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dni);
            stmt.setDate(2, (f1 != null) ? new Date(f1.getTime()) : null);
            stmt.setDate(3, (f2 != null) ? new Date(f2.getTime()) : null);
            stmt.setDate(4, (f3 != null) ? new Date(f3.getTime()) : null);
            stmt.setDate(5, (f4 != null) ? new Date(f4.getTime()) : null);
            stmt.setString(6, estado);

            stmt.executeUpdate();
            System.out.println("Contacto insertado correctamente.");
        }
    }
}

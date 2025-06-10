package src;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        PadrinoDAO padrinoDAO = new PadrinoDAO();
        DonanteDAO donanteDAO = new DonanteDAO();
        ListadoDAO listadoDAO = new ListadoDAO();

        // Insertar un padrino de ejemplo
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaNacimiento = sdf.parse("1980-05-15");

            padrinoDAO.insertarPadrino(
                    "12345678",
                    "Ignacio",
                    "Pérez",
                    "Calle Falsa 123",
                    "5000",
                    "ignacio@email.com",
                    "ignacio_fb",
                    "1234567",
                    "987654321",
                    fechaNacimiento);
        } catch (ParseException e) {
            System.err.println("Error en formato de fecha: " + e.getMessage());
        }

        // Eliminar donante (prueba con un DNI que exista o no)
        donanteDAO.eliminarDonante("87654321");

        // Listar padrinos con programas
        listadoDAO.listarPadrinosConProgramas();
    }
}

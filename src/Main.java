package src;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Insertar Padrino");
            System.out.println("2. Eliminar Donante");
            System.out.println("3. Listar Padrinos con Programas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    insertarPadrinoDesdeInput(scanner);
                    break;
                case "2":
                    eliminarDonanteDesdeInput(scanner);
                    break;
                case "3":
                    listarPadrinosConProgramas();
                    break;
                case "4":
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    continue; // no mostrar "Presiona Enter..." si está saliendo
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    continue; // no mostrar "Presiona Enter..." si hubo error
            }

            // Esperar Enter antes de volver a mostrar el menú
            System.out.print("\nPresiona Enter para volver al menú...");
            scanner.nextLine();
        }

        scanner.close();
    }

    private static void insertarPadrinoDesdeInput(Scanner scanner) {
        try {
            PadrinoDAO padrinoDAO = new PadrinoDAO();
            String dni;

            while (true) {
                System.out.print("DNI: ");
                dni = scanner.nextLine();
                if (padrinoDAO.existeDNI(dni)) {
                    System.out.println("DNI ya registrado. Intente con otro.");
                } else {
                    break;
                }
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();
            System.out.print("Código Postal: ");
            String codPostal = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Facebook: ");
            String facebook = scanner.nextLine();
            System.out.print("Teléfono fijo: ");
            String telFijo = scanner.nextLine();
            System.out.print("Celular: ");
            String celular = scanner.nextLine();
            System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
            String fechaStr = scanner.nextLine();
            Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);

            padrinoDAO.insertarPadrino(dni, nombre, apellido, direccion, codPostal, email, facebook, telFijo, celular,
                    fechaNacimiento);

            String tipo;
            while (true) {
                System.out.println("\nGuardar como:");
                System.out.println("1. Donante");
                System.out.println("2. Contacto");
                System.out.print("Opción: ");
                tipo = scanner.nextLine();
                if (tipo.equals("1") || tipo.equals("2"))
                    break;
                System.out.println("Opción inválida. Ingrese 1 o 2.");
            }

            if (tipo.equals("1")) {
                // Donante
                System.out.print("CUIL: ");
                String cuil = scanner.nextLine();
                System.out.print("Ocupación: ");
                String ocupacion = scanner.nextLine();

                // Medio de pago
                String medioPagoOpcion;
                while (true) {
                    System.out.println("\n¿Qué medio de pago desea usar?");
                    System.out.println("1. Tarjeta de crédito");
                    System.out.println("2. Débito o transferencia bancaria");
                    System.out.print("Opción: ");
                    medioPagoOpcion = scanner.nextLine();
                    if (medioPagoOpcion.equals("1") || medioPagoOpcion.equals("2"))
                        break;
                    System.out.println("Opción inválida. Ingrese 1 o 2.");
                }

                MedioPagoDAO medioPagoDAO = new MedioPagoDAO();
                int idPago = -1;

                if (medioPagoOpcion.equals("1")) {
                    System.out.print("Nombre del titular: ");
                    String titular = scanner.nextLine();
                    System.out.print("Nombre de la tarjeta: ");
                    String nombreTarjeta = scanner.nextLine();
                    System.out.print("Número de tarjeta: ");
                    String nroTarjeta = scanner.nextLine();
                    System.out.print("Fecha de vencimiento (yyyy-MM-dd): ");
                    Date fechaVto = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());

                    idPago = medioPagoDAO.insertarMedioPago(titular);
                    medioPagoDAO.insertarCredito(idPago, nombreTarjeta, nroTarjeta, fechaVto);

                } else {
                    System.out.print("Nombre del titular: ");
                    String titular = scanner.nextLine();
                    System.out.print("Nombre del banco: ");
                    String banco = scanner.nextLine();
                    System.out.print("Sucursal: ");
                    String sucursal = scanner.nextLine();
                    System.out.print("Tipo de cuenta: ");
                    String tipoCuenta = scanner.nextLine();
                    System.out.print("Número de cuenta: ");
                    String nroCuenta = scanner.nextLine();
                    System.out.print("CBU: ");
                    String cbu = scanner.nextLine();

                    idPago = medioPagoDAO.insertarMedioPago(titular);
                    medioPagoDAO.insertarDebito(idPago, banco, sucursal, tipoCuenta, nroCuenta, cbu);
                }

                DonanteDAO donanteDAO = new DonanteDAO();
                donanteDAO.insertarDonante(dni, cuil, ocupacion, idPago);

                AportaDAO aportaDAO = new AportaDAO();
                ProgramaDAO programaDAO = new ProgramaDAO();

                while (true) {
                    programaDAO.listarProgramas(); // muestra id y nombre
                    System.out.print("Ingrese ID del programa: ");
                    String idPrograma = scanner.nextLine();

                    System.out.print("Monto a aportar: ");
                    int monto = Integer.parseInt(scanner.nextLine());

                    String frecuencia;
                    while (true) {
                        System.out.print("Frecuencia (1 mensual, 2 semestral): ");
                        String f = scanner.nextLine();
                        if (f.equals("1")) {
                            frecuencia = "mensual";
                            break;
                        } else if (f.equals("2")) {
                            frecuencia = "semestral";
                            break;
                        } else {
                            System.out.println("Opción inválida.");
                        }
                    }

                    aportaDAO.insertarAporte(dni, idPrograma, monto, frecuencia);

                    System.out.print("¿Desea aportar a otro programa? (1 Sí / 2 No): ");
                    String seguir = scanner.nextLine();
                    if (!seguir.equals("1"))
                        break;
                }

                System.out.println("Donante registrado con sus aportes.");

            } else {
                // Contacto
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);

                System.out.print("Fecha primer contacto (yyyy-MM-dd) o Enter: ");
                String f1str = scanner.nextLine();
                Date f1 = f1str.isEmpty() ? null : sdf.parse(f1str);

                System.out.print("Fecha de alta (yyyy-MM-dd) o Enter: ");
                String f2str = scanner.nextLine();
                Date f2 = f2str.isEmpty() ? null : sdf.parse(f2str);

                System.out.print("Fecha de baja (yyyy-MM-dd) o Enter: ");
                String f3str = scanner.nextLine();
                Date f3 = f3str.isEmpty() ? null : sdf.parse(f3str);

                System.out.print("Fecha rechazo adhesión (yyyy-MM-dd) o Enter: ");
                String f4str = scanner.nextLine();
                Date f4 = f4str.isEmpty() ? null : sdf.parse(f4str);

                String estado = null;
                while (estado == null) {
                    System.out.println("Estado del contacto:");
                    System.out.println("1. Sin llamar");
                    System.out.println("2. ERROR");
                    System.out.println("3. En gestión");
                    System.out.println("4. Adherido");
                    System.out.println("5. Amigo");
                    System.out.println("6. No acepta");
                    System.out.println("7. Baja");
                    System.out.println("8. Voluntario");
                    System.out.print("Seleccione una opción (1-8): ");
                    String opcionEstado = scanner.nextLine();

                    switch (opcionEstado) {
                        case "1":
                            estado = "Sin llamar";
                            break;
                        case "2":
                            estado = "ERROR";
                            break;
                        case "3":
                            estado = "En gestión";
                            break;
                        case "4":
                            estado = "Adherido";
                            break;
                        case "5":
                            estado = "Amigo";
                            break;
                        case "6":
                            estado = "No acepta";
                            break;
                        case "7":
                            estado = "Baja";
                            break;
                        case "8":
                            estado = "Voluntario";
                            break;
                        default:
                            System.out.println("Opción inválida. Intente nuevamente.");
                    }
                }
                ContactoDAO contactoDAO = new ContactoDAO();
                contactoDAO.insertarContacto(dni, f1, f2, f3, f4, estado);
            }

        } catch (Exception e) {
            System.out.println("Error al insertar padrino: " + e.getMessage());
        }
    }

    private static void eliminarDonanteDesdeInput(Scanner scanner) {
    try {
        System.out.print("Ingrese el DNI del donante a eliminar: ");
        String dni = scanner.nextLine().trim();
        
        if (dni.isEmpty()) {
            System.out.println("Error: El DNI no puede estar vacío");
            return;
        }

        DonanteDAO donanteDAO = new DonanteDAO();
        donanteDAO.eliminarDonante(dni);
        
    } catch (SQLException e) {
    System.out.println("\n[ERROR] " + e.getMessage());
    System.out.println("Operación cancelada. Razón:");
    if (e.getMessage().contains("foreign key constraint")) {
        System.out.println("- Existen datos asociados que no pudieron eliminarse");
    } else {
        System.out.println("- Error en la base de datos: " + e.getSQLState());
    }
    }
}

    private static void listarPadrinosConProgramas() {
        try {
            ListadoDAO listadoDAO = new ListadoDAO();
            listadoDAO.listarPadrinosConProgramas();
        } catch (Exception e) {
            System.out.println("Error al listar padrinos: " + e.getMessage());
        }
    }

}

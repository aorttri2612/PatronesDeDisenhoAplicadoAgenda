package app;
import java.util.List;
import java.util.Scanner;

import agendaConPatrones.Agenda;
import agendaConPatrones.Contacto;
import agendaConPatrones.TipoTelefono;
import agendaConPatrones.TipoVia;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {

        // CAMBIO: En lugar de "new Agenda()", llamamos al Singleton
        Agenda agenda = Agenda.getInstance();
        
        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");

            if (opcion == 1) {
                Contacto nuevo = crearContacto(agenda);
                System.out.println("Contacto añadido con ID " + nuevo.getId());            	

            } else if (opcion == 2) {
                List<Contacto> contactos = agenda.listarContactos();
                if (contactos.isEmpty()) {
                    System.out.println("La agenda está vacía.");
                } else {
                    for (Contacto c : contactos) {
                        System.out.println(c);
                        System.out.println("---------------------------------");
                    }
                }

            } else if (opcion == 3) {
                String texto = leerTextoNoVacio("Buscar por nombre/apellidos: ");
                List<Contacto> resultados = agenda.buscarPorNombre(texto);

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron contactos.");
                } else {
                    for (Contacto c : resultados) {
                        System.out.println(c);
                        System.out.println("---------------------------------");
                    }
                }

            } else if (opcion == 4) {
                int id = leerEntero("ID del contacto a borrar: ");
                boolean borrado = agenda.eliminarContactoPorId(id);
                if (borrado) {
                    System.out.println("Contacto borrado.");
                } else {
                    System.out.println("No existe un contacto con ese ID.");
                }

            } else if (opcion == 5) {
                int id = leerEntero("ID del contacto al que añadir teléfono: ");
                Contacto c = agenda.obtenerPorId(id);

                if (c == null) {
                    System.out.println("No existe un contacto con ese ID.");
                } else {
                    String numTel = leerTextoNoVacio("Número de teléfono: ");
                    TipoTelefono tipoTel = elegirTipoTelefono();
                    c.agregarTelefono(numTel, tipoTel);                	
                    System.out.println("Teléfono añadido.");
                }

            } else if (opcion == 0) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        SC.close();
    }

    // El resto de los métodos auxiliares (mostrarMenu, crearContacto, leerTexto, etc.) 
    // se mantienen exactamente igual a tu código original.
    private static void mostrarMenu() {
        System.out.println("\n====== AGENDA (Consola) ======");
        System.out.println("1) Añadir contacto");
        System.out.println("2) Listar contactos");
        System.out.println("3) Buscar contacto");
        System.out.println("4) Borrar contacto por ID");
        System.out.println("5) Añadir teléfono a contacto");
        System.out.println("0) Salir");
        System.out.println("==============================");
    }

    private static Contacto crearContacto(Agenda agenda) {
        String nombre = leerTextoNoVacio("Nombre: ");
        String apellidos = leerTextoNoVacio("Apellidos: ");
        String email = leerTexto("Email (opcional): ");

        Contacto nuevoContacto = agenda.crearContacto(nombre, apellidos, email);
        crearDireccion(nuevoContacto);
        crearTelefonos(nuevoContacto);
        
        return nuevoContacto;
    }

    private static void crearDireccion(Contacto contacto) {
        System.out.println("\n--- Dirección ---");
        TipoVia tipoVia = elegirTipoVia();
        int numero = leerEntero("Número: ");
        String bloque = leerTexto("Bloque (opcional): ");
        String escalera = leerTexto("Escalera (opcional): ");
        String portal = leerTexto("Portal (opcional): ");
        String letra = leerTexto("Letra (opcional): ");
        contacto.definirDireccion(tipoVia, numero, bloque, escalera, portal, letra);
    }

    private static void crearTelefonos(Contacto contacto) {
        int cuantos = leerEntero("¿Cuántos teléfonos quieres añadir ahora? (0..n): ");
        int i = 0;
        while (i < cuantos) {
            System.out.println("\n--- Teléfono ---");
            String numTel = leerTextoNoVacio("Número de teléfono: ");
            TipoTelefono tipoTel = elegirTipoTelefono();
            contacto.agregarTelefono(numTel, tipoTel);
            i++;
        }
    }
    
    private static TipoVia elegirTipoVia() {
        System.out.println("Tipo de vía:");
        TipoVia[] valores = TipoVia.values();
        int i = 0;
        while (i < valores.length) {
            System.out.println((i + 1) + ") " + valores[i]);
            i++;
        }
        int opcion = leerEnteroRango("Elige tipo (1-" + valores.length + "): ", 1, valores.length);
        return valores[opcion - 1];
    }

    private static TipoTelefono elegirTipoTelefono() {
        System.out.println("Tipo de teléfono:");
        TipoTelefono[] valores = TipoTelefono.values();
        int i = 0;
        while (i < valores.length) {
            System.out.println((i + 1) + ") " + valores[i]);
            i++;
        }
        int opcion = leerEnteroRango("Elige tipo (1-" + valores.length + "): ", 1, valores.length);
        return valores[opcion - 1];
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = SC.nextLine();
        return texto.trim();
    }

    private static String leerTextoNoVacio(String mensaje) {
        String texto = "";
        while (texto.isBlank()) {
            System.out.print(mensaje);
            texto = SC.nextLine();
            texto = texto.trim();
            if (texto.isBlank()) {
                System.out.println("ERROR - No puede estar vacío.");
            }
        }
        return texto;
    }

    private static int leerEntero(String mensaje) {
        int numero = 0;
        boolean ok = false;
        while (!ok) {
            System.out.print(mensaje);
            String texto = SC.nextLine();
            texto = texto.trim();
            try {
                numero = Integer.parseInt(texto);
                ok = true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR - Introduce un número entero válido.");
            }
        }
        return numero;
    }

    private static int leerEnteroRango(String mensaje, int min, int max) {
        int numero = leerEntero(mensaje);
        while (numero < min || numero > max) {
            System.out.println("ERROR - Debe estar entre " + min + " y " + max + ".");
            numero = leerEntero(mensaje);
        }
        return numero;
    }
}
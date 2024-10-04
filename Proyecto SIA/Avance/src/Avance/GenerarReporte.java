package Avance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GenerarReporte {

    public static void generarReporte(String rutaArchivo, Agenda agenda) {
        // Obtener todos los eventos de la agenda
        List<Object[]> eventos = agenda.mostrarTodosLosEventos();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            // Título del reporte
            bw.write("═════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            bw.newLine();
            bw.write("                                 REPORTE DE EVENTOS DE LA AGENDA: " + agenda.getNombreAgenda().toUpperCase());
            bw.newLine();
            bw.write("═════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            bw.newLine();
            bw.write("Este reporte muestra todos los eventos ordenados por fecha y hora.");
            bw.newLine();
            bw.newLine();

            // Encabezado de columnas
            bw.write("╔═════╦════════════════════════════════╦══════════════════════════════════════════════════╦═══════════════╦══════════╦══════════╗");
            bw.newLine();
            bw.write(String.format("║ %-3s ║ %-30s ║ %-48s ║ %-13s ║ %-8s ║ %-13s ║", "ID", "Nombre", "Descripción", "Etiqueta", "Hora", "Fecha"));
            bw.newLine();
            bw.write("╠═════╬════════════════════════════════╬══════════════════════════════════════════════════╬═══════════════╬══════════╬══════════╣");
            bw.newLine();

            // Escribir cada evento con formato claro
            for (Object[] evento : eventos) {
                // Ajustar cada campo a su tamaño límite
                String nombre = ajustarLongitud(evento[1].toString(), 30);
                String descripcion = ajustarLongitud(evento[2].toString(), 48);
                String etiqueta = ajustarLongitud(evento[3].toString(), 13);
                String hora = ajustarLongitud(evento[4].toString(), 8);
                String fecha = ajustarLongitud(evento[5].toString(), 13);

                // Escribir los datos del evento con formato
                bw.write(String.format("║ %-3s ║ %-30s ║ %-48s ║ %-13s ║ %-8s ║ %-13s ║",
                        evento[0], // ID
                        nombre, // Nombre ajustado
                        descripcion, // Descripción ajustada
                        etiqueta, // Etiqueta ajustada
                        hora, // Hora ajustada
                        fecha // Fecha ajustada
                ));
                bw.newLine();
                bw.write("╠═════╬════════════════════════════════╬══════════════════════════════════════════════════╬═══════════════╬══════════╬══════════╣");
                bw.newLine();
            }

            // Final del reporte
            bw.write("╚═════╩════════════════════════════════╩══════════════════════════════════════════════════╩═══════════════╩══════════╩══════════╝");
            bw.newLine();

            System.out.println("Reporte generado con éxito en: " + rutaArchivo);

        } catch (IOException e) {
            System.err.println("Ocurrió un error al generar el reporte: " + e.getMessage());
        }
    }

    // Método para ajustar la longitud de los campos
    private static String ajustarLongitud(String texto, int longitudMaxima) {
        if (texto.length() > longitudMaxima) {
            return texto.substring(0, longitudMaxima - 3) + "..."; // Cortar si es demasiado largo
        } else {
            return String.format("%-" + longitudMaxima + "s", texto); // Rellenar con espacios si es muy corto
        }
    }
}

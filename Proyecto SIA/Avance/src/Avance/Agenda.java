//Bibliotecas
package Avance;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;

import java.util.*;

public class Agenda {
    private String nombreAgenda;
    protected TreeMap<LocalDate, ArrayList<Evento>> eventos;
    private Scanner scanner;

    public Agenda(String nombreAgenda) {
        this.nombreAgenda = nombreAgenda;
        this.eventos = new TreeMap<>();
        this.scanner = new Scanner(System.in);
    }

    public TreeMap<LocalDate, ArrayList<Evento>> getEventos() {
        return eventos;
    }

    public String getNombreAgenda() { return nombreAgenda; }
    public void setNombreAgenda(String nombreAgenda) { this.nombreAgenda = nombreAgenda; }

    public void setEventos(TreeMap<LocalDate, ArrayList<Evento>> eventos) {
        this.eventos = eventos;
    }

    public void agregarEvento(String fecha, Evento evento) {
        LocalDate dia = LocalDate.parse(fecha);  //'Casting'
        //
        if (!eventos.containsKey(dia)) {
            eventos.put(dia, new ArrayList<>());
        }
        eventos.get(dia).add(evento);
    }

    public ArrayList<Object[]> mostrarEventos(String fecha) {
        LocalDate dia = LocalDate.parse(fecha);
        ArrayList<Object[]> eventosData = new ArrayList<>();

        // Obtener los eventos del día
        ArrayList<Evento> eventosEnDia = eventos.getOrDefault(dia, new ArrayList<>());

        // Si hay eventos, llenar la lista con sus datos
        for (Evento evento : eventosEnDia) {
            Object[] eventoData = {
                    evento.getIdEvento(),
                    evento.getNombreEvento(),
                    evento.getDescripcionEvento(),
                    evento.getEtiqueta(),
                    evento.getHoraEvento()
            };
            eventosData.add(eventoData);
        }

        return eventosData; // Retornar la lista de eventos como arreglos de objetos
    }


    public void mostrarEventos(String fecha, String etiqueta) {
        LocalDate dia = LocalDate.parse(fecha);
        ArrayList<Evento> eventosEnDia = eventos.getOrDefault(dia, new ArrayList<>());
        eventosEnDia.sort((e1, e2) -> e1.getHoraEvento().compareTo(e2.getHoraEvento()));

        if (eventosEnDia.isEmpty()) {
            System.out.println("No hay eventos en esta fecha.");
        } else {
            // Filtrar eventos por etiqueta
            List<Evento> eventosFiltrados = new ArrayList<>();
            for (Evento evento : eventosEnDia) {
                if (evento.getEtiqueta().equalsIgnoreCase(etiqueta)) {
                    eventosFiltrados.add(evento);
                }
            }

            if (eventosFiltrados.isEmpty()) {
                System.out.println("No hay eventos con la etiqueta '" + etiqueta + "' en la fecha " + fecha + ".");
            } else {
                // Imprimir encabezado
                System.out.println("═══════════════════════════════════════════════════════════════════════════════");
                System.out.println("                  EVENTOS DEL " + fecha + " - ETIQUETA: " + etiqueta.toUpperCase() + "                  ");
                System.out.println("═══════════════════════════════════════════════════════════════════════════════");
                System.out.println("╔═════════════╦═════════════════════════════╦════════════════════════════════╗");
                System.out.println("║    HORA     ║        NOMBRE               ║         DESCRIPCIÓN            ║");
                System.out.println("╠═════════════╬═════════════════════════════╬════════════════════════════════╣");

                // Imprimir los eventos filtrados
                for (Evento evento : eventosFiltrados) {
                    String descripcionCorta = evento.getDescripcionEvento();
                    if (descripcionCorta.length() > 30) {
                        descripcionCorta = descripcionCorta.substring(0, 27) + "...";  // Limitar a 30 caracteres
                    }

                    System.out.printf("║ %11s ║ %-27s ║ %-28s ║%n",
                            evento.getHoraEvento(),
                            evento.getNombreEvento(),
                            descripcionCorta);

                    // Añadir línea separadora entre eventos
                    System.out.println("╠═════════════╬═════════════════════════════╬════════════════════════════════╣");
                }

                System.out.println("╚═════════════╩═════════════════════════════╩════════════════════════════════╝");
            }
        }
    }

    public List<Object[]> mostrarTodosLosEventos() {
        List<Object[]> listaEventos = new ArrayList<>();

        if (eventos.isEmpty()) {
            return listaEventos; // Retorna lista vacía si no hay eventos
        }

        // Llenar la lista con los datos de los eventos
        for (Map.Entry<LocalDate, ArrayList<Evento>> entrada : eventos.entrySet()) {
            LocalDate dia = entrada.getKey();
            ArrayList<Evento> eventosEnDia = entrada.getValue();

            eventosEnDia.sort((e1, e2) -> e1.getHoraEvento().compareTo(e2.getHoraEvento()));

            for (Evento evento : eventosEnDia) {
                Object[] data = {
                        evento.getIdEvento(),
                        evento.getNombreEvento(),
                        evento.getDescripcionEvento(),
                        evento.getEtiqueta(),
                        evento.getHoraEvento(),
                        dia.toString()
                };
                listaEventos.add(data);
            }
        }

        return listaEventos; // Retorna la lista de eventos
    }





    public List<Object[]> mostrarTodosLosEventos(String etiqueta) {
        List<Object[]> listaEventos = new ArrayList<>();

        // Verificar si la agenda tiene eventos
        if (eventos.isEmpty()) {
            return listaEventos; // Retorna lista vacía si no hay eventos
        }

        // Llenar la lista con los datos de los eventos que coincidan con la etiqueta
        for (Map.Entry<LocalDate, ArrayList<Evento>> entrada : eventos.entrySet()) {
            LocalDate dia = entrada.getKey();
            ArrayList<Evento> eventosEnDia = entrada.getValue();

            eventosEnDia.sort((e1, e2) -> e1.getHoraEvento().compareTo(e2.getHoraEvento()));

            for (Evento evento : eventosEnDia) {
                if (evento.getEtiqueta().equalsIgnoreCase(etiqueta)) {
                    Object[] data = {
                            evento.getIdEvento(),
                            evento.getNombreEvento(),
                            evento.getDescripcionEvento(),
                            evento.getEtiqueta(),
                            evento.getHoraEvento(),
                            dia.toString()
                    };
                    listaEventos.add(data);
                }
            }
        }

        return listaEventos; // Retorna la lista de eventos con la etiqueta especificada
    }



    public void eliminarEvento(String fecha, int id) {
        LocalDate dia = LocalDate.parse(fecha);  // Convertir la fecha a LocalDate
        if (!eventos.containsKey(dia)) {
            System.out.println("No hay eventos en esta fecha: " + fecha);
        } else {
            ArrayList<Evento> eventosEnDia = this.eventos.get(dia);

            // Buscar el evento con el ID proporcionado
            Evento eventoAEliminar = null;
            for (Evento evento : eventosEnDia) {
                if (evento.getIdEvento() == id) {
                    eventoAEliminar = evento;
                    break;
                }
            }

            if (eventoAEliminar != null) {
                eventosEnDia.remove(eventoAEliminar);
                System.out.println("Evento con ID " + id + " eliminado.");
            } else {
                System.out.println("No se encontró un evento con ID " + id + " en la fecha " + fecha + ".");
            }
        }
    }


    public void modificarEvento(String fecha, int id){
        LocalDate dia = LocalDate.parse(fecha);
        if (!eventos.containsKey(dia)) {
            System.out.println("No hay eventos en esta fecha: " + fecha);
        } else {
            ArrayList<Evento> eventosEnDia = this.eventos.get(dia);

            // Buscar el evento con el ID proporcionado
            int indice = -1;
            for (int i = 0; i < eventosEnDia.size(); i++) {
                if (eventosEnDia.get(i).getIdEvento() == id) {
                    indice = i;
                    break;
                }
            }

            if (indice != -1) {
                Evento evento = eventosEnDia.get(indice);

                int opcion = 0;
                do {
                    System.out.println("¿Qué desea modificar en el evento?");
                    System.out.println("1. Modificar nombre.");
                    System.out.println("2. Modificar descripcion.");
                    System.out.println("3. Modificar etiqueta.");
                    System.out.println("4. Modificar hora del evento.");
                    System.out.println("5. Modificar fecha del evento.");
                    System.out.println("6. Volver al menú principal.");  // Agregar esta opción en el menú
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese nuevo nombre para el evento " + id + ":");
                            String nuevoNombre = scanner.nextLine();
                            evento.setNombreEvento(nuevoNombre);  // Usar el setter para cambiar el nombre
                            System.out.println("Nombre del evento actualizado.");
                            break;

                        case 2:
                            System.out.println("Ingrese nueva descripción para el evento " + id + ":");
                            String nuevaDescripcion = scanner.nextLine();
                            evento.setDescripcionEvento(nuevaDescripcion);
                            System.out.println("Descripción del evento actualizada.");
                            break;

                        case 3:
                            System.out.println("Ingrese nueva etiqueta para el evento " + id + ":");
                            String nuevaEtiqueta = scanner.nextLine();
                            evento.setEtiqueta(nuevaEtiqueta);
                            System.out.println("Etiqueta del evento actualizada.");
                            break;

                        case 4:
                            System.out.println("Ingrese nueva hora (HH:mm) para el evento " + id + ":");
                            String nuevaHora = scanner.nextLine();
                            evento.setHoraEvento(nuevaHora);
                            System.out.println("Hora del evento actualizada.");
                            break;

                        case 5:
                            System.out.println("Ingrese nueva fecha (YYYY-MM-DD) para el evento " + id + ":");
                            String nuevaFecha = scanner.nextLine();
                            LocalDate nuevaFechaEvento = LocalDate.parse(nuevaFecha);
                            eventosEnDia.remove(evento); // Remover el evento de la fecha original
                            agregarEvento(nuevaFecha, evento); // Agregar el evento en la nueva fecha
                            System.out.println("Fecha del evento actualizada.");
                            break;
                        case 6:
                            System.out.println("Volviendo al menú principal...");  // Manejar la opción de volver al menú principal
                            break;

                        default:
                            System.out.println("Opción no válida.");
                    }

                } while (opcion != 6);  // Se sale del ciclo si se elige la opción 6

            } else {
                System.out.println("No se encontró un evento con ID " + id + " en la fecha " + fecha + ".");
            }
        }
    }

    public void guardarEventosCSV(String archivoCSV) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(archivoCSV))) {
            // Escribir encabezados
            String[] encabezados = {"ID", "Nombre", "Descripción", "Etiqueta", "Hora", "Fecha"};
            writer.writeNext(encabezados);

            // Escribir cada evento
            for (Map.Entry<LocalDate, ArrayList<Evento>> entrada : eventos.entrySet()) {
                for (Evento evento : entrada.getValue()) {
                    String[] datosEvento = {
                            String.valueOf(evento.getIdEvento()),
                            evento.getNombreEvento(),
                            evento.getDescripcionEvento(),
                            evento.getEtiqueta(),
                            evento.getHoraEvento(),
                            evento.getFechaEvento()
                    };
                    writer.writeNext(datosEvento);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los eventos en el archivo CSV.");
        }
    }


    public void cargarEventosCSV(String archivoCSV) {
        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            String[] siguienteLinea;
            reader.readNext(); // Saltar encabezados

            int maxId = 0;

            while ((siguienteLinea = reader.readNext()) != null) {
                if (siguienteLinea.length < 6) continue; // Validar línea

                int id = Integer.parseInt(siguienteLinea[0]);
                String nombre = siguienteLinea[1];
                String descripcion = siguienteLinea[2];
                String etiqueta = siguienteLinea[3];
                String hora = siguienteLinea[4];
                String fecha = siguienteLinea[5];

                Evento evento = new Evento(nombre, descripcion, etiqueta, hora, fecha);
                evento.setIdEvento(id);

                agregarEvento(fecha, evento);

                if (id > maxId) {
                    maxId = id;
                }
            }

            Evento.setContador(maxId);
        } catch (IOException | CsvValidationException e) {
            System.out.println("Archivo CSV no encontrado. Se creará uno nuevo al guardar.");
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el ID del evento.");
        }
    }

//    public void mostrarTodosLosEventos() {
//        if (eventos.isEmpty()) {
//            System.out.println("No hay eventos en la agenda.");
//            return;
//        }
//        for (Map.Entry<LocalDate, ArrayList<Evento>> entrada : eventos.entrySet()) {
//            LocalDate dia = entrada.getKey();
//            ArrayList<Evento> eventosEnDia = entrada.getValue();
//x
//            eventosEnDia.sort((e1, e2) -> e1.getHoraEvento().compareTo(e2.getHoraEvento()));
//            System.out.println("Eventos en " + dia + ":");
//            for (Evento evento : eventosEnDia) {
//                System.out.println(evento);
//            }
//        }
//    }

}

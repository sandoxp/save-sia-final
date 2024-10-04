package Avance;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import Avance.Agenda;

public class Menu {

    private Scanner scanner;
    private Agenda agenda;
    private String archivoCSV;


    public Menu(Agenda agenda, String archivoCSV) {
        this.agenda = agenda;
        this.archivoCSV = archivoCSV;
        this.scanner = new Scanner(System.in);
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public void iniciarMenu() {
        identificarPersona();

        int opcion = 0;
        do {
            System.out.println("╔══════════════════════════════════════════════════════╗");
            System.out.println("║                    MENÚ PRINCIPAL                    ║");
            System.out.println("╠══════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Agregar Evento                                    ║");
            System.out.println("║ 2. Mostrar todos los Eventos                         ║");
            System.out.println("║ 3. Mostrar Eventos por fecha                         ║");
            System.out.println("║ 4. Mostrar todos los eventos por etiqueta            ║");
            System.out.println("║ 5. Mostrar eventos del dia fecha y por etiqueta      ║");
            System.out.println("║ 6. Eliminar Evento por ID                            ║");
            System.out.println("║ 7. Consultar cuánto falta para llegada de Evento     ║");
            System.out.println("║ 8. Modificar evento                                  ║");
            System.out.println("║ 9. Mostrar eventos de la semana                      ║");
            System.out.println("║ 10. Mostrar eventos del mes                          ║");
            System.out.println("║ 11. Salir del programa                               ║");
            System.out.println("╚══════════════════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarEvento();
                    break;
                case 2:
                    mostrarTodosLosEventos();
                    break;
                case 3:
                    mostrarEventos();
                    break;
                case 4:
                    mostrarTodosLosEventosEtiqueta();
                    break;
                case 5:
                    mostrarEventosEtiqueta();
                    break;
                case 6:
                    eliminarEvento();
                    break;
                case 7:
                    consultarLlegadaEvento();
                    break;
                case 8:
                    modificarEvento();  // Aquí se llama al método modificarEvento
                    break;
                case 9:
                    mostrarEventosDeLaSemana();  // LLAMADA A NUEVO MÉTODO
                    break;
                case 10:
                    mostrarEventosDelMes();  // LLAMADA A NUEVO MÉTODO
                    break;
                case 11:
                    System.out.print("Saliendo del programa.....");
                    break;
                default:
                    System.out.print("Ingrese una opción válida");
            }
        } while (opcion != 11);  // Cambia el valor de 8 a 9 para salir del programa con la opción correcta
    }


    public void identificarPersona(){

        System.out.println("Porfavor, ingrese su nombre");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su edad");
        int edad = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese su RUT, con puntos y guión");
        String rut = scanner.nextLine();

        System.out.println("Ingrese su rol ¿Es usted empleado o jefe?");
        String cargo = scanner.nextLine();

        Persona persona = new Persona(nombre, edad, rut, cargo);

        persona.mostrarSaludo(cargo);

    }

    public void agregarEvento() {
        try {
            System.out.println("Ingrese el nombre del evento: ");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese la descripción del evento: ");
            String descripcion = scanner.nextLine();

            System.out.println("Ingrese tipo de evento: Reunión o Actividad: ");
            String etiqueta = scanner.nextLine();

            System.out.println("Ingrese la hora del evento en formato 24 horas (00:00): ");
            String horaEvento = scanner.nextLine();
            validarHora(horaEvento);

            System.out.println("Ingrese la fecha del evento: 'Formato YYYY-MM-DD': ");
            String fechaEvento = scanner.nextLine();


            // Verificar formato y validez de la fecha
            LocalDate fecha = LocalDate.parse(fechaEvento);
            if (fecha.isBefore(LocalDate.now())) {
                throw new FechaInvalidaException("La fecha no puede ser anterior a hoy.");
            }

            // Crear y agregar el evento
            Evento evento = new Evento(nombre, descripcion, etiqueta, horaEvento, fechaEvento);
            agenda.agregarEvento(fechaEvento, evento);

            agenda.guardarEventosCSV(archivoCSV);
            System.out.println("Evento agregado a la agenda con ID: " + evento.getIdEvento());

        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto. Use el formato YYYY-MM-DD.");
        } catch (FechaInvalidaException | HoraInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void validarHora(String hora) throws HoraInvalidaException {
        try {
            // Intentar parsear la hora
            LocalTime parsedHora = LocalTime.parse(hora);

        } catch (DateTimeParseException e) {
            // Si el formato de la hora es incorrecto, lanzar una excepción.
            throw new HoraInvalidaException("Formato de hora incorrecto. Use el formato HH:mm (ej. 14:30) y en un rango correcto (ej. 00:00 - 23:59)");
        }
    }

    public void mostrarEventos() //por fecha
    {
        System.out.print("Ingrese fecha  Formato YYYY-MM-DD para consultar eventos: ");
        String fecha = scanner.nextLine();
        this.agenda.mostrarEventos(fecha);
    }

    public void mostrarEventosEtiqueta() //por fecha
    {
        System.out.print("Ingrese fecha  Formato YYYY-MM-DD para consultar eventos: ");
        String fecha = scanner.nextLine();
        System.out.println("Aplique filtro: 'Reunion/Actividad': ");
        String etiqueta = scanner.nextLine();
        this.agenda.mostrarEventos(fecha, etiqueta);
    }

    public void mostrarEventosDeLaSemana() {
        System.out.print("Ingrese la fecha (Formato YYYY-MM-DD) para mostrar los eventos de la semana: ");
        String fecha = scanner.nextLine();

        AgenditaSemanal agenditaSemanal = new AgenditaSemanal("Agenda Semanal");
        agenditaSemanal.eventos = agenda.eventos;  // Compartir los eventos de la agenda
        agenditaSemanal.mostrarEventos(fecha);  // Llama al método sobrescrito
    }

    public void mostrarEventosDelMes() {
        System.out.print("Ingrese la fecha (Formato YYYY-MM-DD) para mostrar los eventos del mes: ");
        String fecha = scanner.nextLine();

        AgenditaMensual agenditaMensual = new AgenditaMensual("Agenda Mensual");
        agenditaMensual.eventos = agenda.eventos;  // Compartir los eventos de la agenda
        agenditaMensual.mostrarEventos(fecha);  // Llama al método sobrescrito
    }






    public void eliminarEvento() {
        System.out.println("Ingrese fecha para buscar evento a eliminar: ");
        String fecha = scanner.nextLine();
        System.out.println("Ingrese id de evento a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        this.agenda.eliminarEvento(fecha, id);

        agenda.guardarEventosCSV(archivoCSV);
    }

    public void modificarEvento(){
        System.out.println("Ingrese fecha para buscar evento a editar: ");
        String fecha = scanner.nextLine();
        System.out.println("Ingrese id de evento a editar: ");
        int id = scanner.nextInt();
        this.agenda.modificarEvento(fecha,id);
        agenda.guardarEventosCSV(archivoCSV);
    }

    public void mostrarTodosLosEventos() {
        agenda.mostrarTodosLosEventos();
    }

    public void mostrarTodosLosEventosEtiqueta() {
        System.out.println("Ingrese etiqueta para filtrar los eventos: ");
        String etiqueta = scanner.nextLine();
        agenda.mostrarTodosLosEventos(etiqueta);
    }

    public void consultarLlegadaEvento() {
        System.out.println("Ingrese la fecha del evento (Formato YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.println("Ingrese el ID del evento para consultar el tiempo restante: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Evento> eventosEnDia = agenda.getEventos().get(LocalDate.parse(fecha));
        if (eventosEnDia != null) {
            for (Evento evento : eventosEnDia) {
                if (evento.getIdEvento() == id) {
                    Notificacion notificacion = new Notificacion();

                    System.out.println("¿Desea ver el tiempo restante completo (1) o solo en días (2)?");
                    int opcion = scanner.nextInt();
                    scanner.nextLine();

                    if (opcion == 1) {
                        notificacion.consultarTiempoRestante(evento);  // Método original
                    } else if (opcion == 2) {
                        notificacion.consultarTiempoRestante(evento, true);  // Método sobrecargado (solo días)
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    return;
                }
            }
            System.out.println("No se encontró un evento con el ID proporcionado.");
        } else {
            System.out.println("No hay eventos en la fecha indicada.");
        }
    }

}
package Avance;
import Avance.Agenda;
import Avance.Evento;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.*;


public class AgenditaSemanal extends Agenda {

    public AgenditaSemanal(String nombreAgenda) {
        super(nombreAgenda);
    }

    @Override
    public ArrayList<Object[]> mostrarEventos(String fechaInicial) {
        ArrayList<Object[]> listaEventos = new ArrayList<>();
        LocalDate diaInicial = LocalDate.parse(fechaInicial);

        // Obtener el lunes de la semana de la fecha dada
        LocalDate lunes = diaInicial.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));

        // Recorrer los 7 días de la semana
        for (int i = 0; i < 7; i++) {
            LocalDate diaSemana = lunes.plusDays(i);

            // Obtener eventos del día actual de la semana
            ArrayList<Evento> eventosEnDia = eventos.getOrDefault(diaSemana, new ArrayList<>());
            for (Evento evento : eventosEnDia) {
                Object[] eventoData = {
                        evento.getIdEvento(),
                        evento.getNombreEvento(),
                        evento.getDescripcionEvento(),
                        evento.getEtiqueta(),
                        evento.getHoraEvento(),
                        diaSemana.toString() // Agregar la fecha del evento
                };
                listaEventos.add(eventoData);
            }
        }
        return listaEventos; // Retornar la lista de eventos de toda la semana
    }



//    public void mostrarEventos(String fecha) {
//        LocalDate dia = LocalDate.parse(fecha);
//
//        // Obtener el lunes de la semana de la fecha proporcionada
//        LocalDate lunes = dia.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//
//        // Mostrar eventos desde el lunes hasta el domingo
//        System.out.println("Mostrando eventos de la semana del " + lunes + " al " + lunes.plusDays(6) + ":");
//
//        for (int i = 0; i < 7; i++) {
//            LocalDate diaSemana = lunes.plusDays(i);
//            System.out.println("\nEventos para el día: " + diaSemana);
//
//            if (eventos.containsKey(diaSemana)) {
//                ArrayList<Evento> eventosEnDia = eventos.get(diaSemana);
//                for (Evento evento : eventosEnDia) {
//                    System.out.println(evento);  // Imprime los eventos del día
//                }
//            } else {
//                System.out.println("No hay eventos para este día.");
//            }
//        }
//    }
}

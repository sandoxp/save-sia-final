package Avance;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.*;

public class AgenditaMensual extends Agenda {

    public AgenditaMensual(String nombreAgenda) {
        super(nombreAgenda);
    }

    @Override
    public ArrayList<Object[]> mostrarEventos(String fechaInicial) {
        ArrayList<Object[]> listaEventos = new ArrayList<>();
        LocalDate diaInicial = LocalDate.parse(fechaInicial);

        // Obtener el primer y último día del mes de la fecha dada
        LocalDate primerDiaMes = diaInicial.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate ultimoDiaMes = diaInicial.with(TemporalAdjusters.lastDayOfMonth());

        // Recorrer todos los días del mes
        for (LocalDate dia = primerDiaMes; !dia.isAfter(ultimoDiaMes); dia = dia.plusDays(1)) {
            // Obtener eventos del día actual
            ArrayList<Evento> eventosEnDia = eventos.getOrDefault(dia, new ArrayList<>());
            for (Evento evento : eventosEnDia) {
                Object[] eventoData = {
                        evento.getIdEvento(),
                        evento.getNombreEvento(),
                        evento.getDescripcionEvento(),
                        evento.getEtiqueta(),
                        evento.getHoraEvento(),
                        dia.toString() // Agregar la fecha del evento
                };
                listaEventos.add(eventoData);
            }
        }
        return listaEventos; // Retornar la lista de eventos del mes
    }
}
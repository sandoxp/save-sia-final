package Ventanas;

import Avance.Agenda;
import Avance.Evento;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VentanaVisualizarTiempoRestante {
    private Agenda agenda;

    public VentanaVisualizarTiempoRestante(Agenda agenda) {
        this.agenda = agenda;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Visualizar Tiempo Restante");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel para contener la lista de fechas
        JPanel panelFechas = new JPanel();
        panelFechas.setLayout(new BorderLayout());
        panelFechas.setBorder(BorderFactory.createTitledBorder("Fechas Disponibles"));

        JLabel labelFecha = new JLabel("Seleccione una Fecha:");
        panelFechas.add(labelFecha, BorderLayout.NORTH);

        // Obtener todas las fechas en orden cronológico
        List<LocalDate> fechasOrdenadas = new ArrayList<>(agenda.getEventos().keySet());
        fechasOrdenadas.sort(LocalDate::compareTo);

        // Modelo y lista para mostrar las fechas
        DefaultListModel<String> modelFechas = new DefaultListModel<>();
        for (LocalDate fecha : fechasOrdenadas) {
            modelFechas.addElement(fecha.toString());
        }
        JList<String> listaFechas = new JList<>(modelFechas);
        listaFechas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        listaFechas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelFechas.add(new JScrollPane(listaFechas), BorderLayout.CENTER);

        // Panel con CardLayout para mostrar eventos por fecha
        JPanel panelEventos = new JPanel(new CardLayout());
        panelEventos.setBorder(BorderFactory.createTitledBorder("Eventos de la Fecha Seleccionada"));
        Map<LocalDate, ArrayList<Evento>> eventosAgenda = agenda.getEventos();

        // Modelo y lista para mostrar los eventos
        DefaultListModel<String> modelEventos = new DefaultListModel<>();
        JList<String> listaEventos = new JList<>(modelEventos);
        listaEventos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelEventos.add(new JScrollPane(listaEventos), "eventos");

        // Botón para calcular tiempo restante
        JButton botonCalcularTiempo = new JButton("Calcular Tiempo Restante");

        // Panel para botón
        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCalcularTiempo);

        // Añadir paneles a la ventana principal
        frame.add(panelFechas, BorderLayout.WEST);
        frame.add(panelEventos, BorderLayout.CENTER);
        frame.add(panelBoton, BorderLayout.SOUTH);

        // Listener para seleccionar una fecha y mostrar los eventos
        listaFechas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Evitar llamadas dobles
                String fechaSeleccionada = listaFechas.getSelectedValue();
                LocalDate fecha = LocalDate.parse(fechaSeleccionada);

                // Limpiar lista de eventos
                modelEventos.clear();

                // Añadir eventos correspondientes a la fecha seleccionada
                for (Evento evento : eventosAgenda.get(fecha)) {
                    modelEventos.addElement("ID: " + evento.getIdEvento() + " - " + evento.getNombreEvento() + " (" + evento.getHoraEvento() + ")");
                }

                // Mostrar panel de eventos
                CardLayout cl = (CardLayout) panelEventos.getLayout();
                cl.show(panelEventos, "eventos");
            }
        });

        // Acción del botón "Calcular Tiempo Restante"
        botonCalcularTiempo.addActionListener(e -> {
            // Obtener el evento seleccionado
            int selectedIndex = listaEventos.getSelectedIndex();
            if (selectedIndex != -1) {
                String fechaSeleccionada = listaFechas.getSelectedValue();
                LocalDate fecha = LocalDate.parse(fechaSeleccionada);

                Evento eventoSeleccionado = eventosAgenda.get(fecha).get(selectedIndex);

                // Calcular el tiempo restante
                LocalDateTime fechaHoraEvento = LocalDateTime.of(fecha, LocalTime.parse(eventoSeleccionado.getHoraEvento()));
                LocalDateTime ahora = LocalDateTime.now();

                if (ahora.isBefore(fechaHoraEvento)) {
                    long diasRestantes = ChronoUnit.DAYS.between(ahora, fechaHoraEvento);
                    long horasRestantes = ChronoUnit.HOURS.between(ahora, fechaHoraEvento) % 24;
                    long minutosRestantes = ChronoUnit.MINUTES.between(ahora, fechaHoraEvento) % 60;

                    // Mostrar tiempo restante
                    JOptionPane.showMessageDialog(frame,
                            "Faltan " + diasRestantes + " días, " + horasRestantes + " horas y " + minutosRestantes + " minutos para el evento '" + eventoSeleccionado.getNombreEvento() + "'.",
                            "Tiempo Restante",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "El evento '" + eventoSeleccionado.getNombreEvento() + "' ya ha ocurrido.", "Evento Pasado", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione un evento para calcular el tiempo restante.", "Ningún Evento Seleccionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Configuración final de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

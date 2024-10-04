package Ventanas;

import Avance.Agenda;
import Avance.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VentanaEliminarEvento {
    private Agenda agenda;
    private String archivoCSV; // Ruta del archivo CSV
    private DefaultListModel<String> modelFechas; // Modelo para la lista de fechas

    public VentanaEliminarEvento(Agenda agenda, String archivoCSV) {
        this.agenda = agenda;
        this.archivoCSV = archivoCSV;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Eliminar Evento");
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
        modelFechas = new DefaultListModel<>();
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

        // Llenar el panelEventos con listas de eventos por fecha
        for (LocalDate fecha : fechasOrdenadas) {
            DefaultListModel<String> modelEventos = new DefaultListModel<>();
            for (Evento evento : eventosAgenda.get(fecha)) {
                String detalleEvento = "<html><strong>ID:</strong> " + evento.getIdEvento() +
                        "<br><strong>Nombre:</strong> " + evento.getNombreEvento() +
                        "<br><strong>Descripción:</strong> " + evento.getDescripcionEvento() +
                        "<br><strong>Hora:</strong> " + evento.getHoraEvento() +
                        "<br><strong>Etiqueta:</strong> " + evento.getEtiqueta() +
                        "</html>";
                modelEventos.addElement(detalleEvento);
            }

            JList<String> listaEventos = new JList<>(modelEventos);
            listaEventos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            listaEventos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            panelEventos.add(new JScrollPane(listaEventos), fecha.toString());

            // Listener para seleccionar y eliminar un evento
            listaEventos.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) { // Evitar llamadas dobles
                    // Obtener el índice seleccionado
                    int selectedIndex = listaEventos.getSelectedIndex();

                    // Verificar que haya un índice válido seleccionado
                    if (selectedIndex != -1) {
                        // Obtener el evento directamente del modelo de eventos
                        Evento eventoSeleccionado = eventosAgenda.get(fecha).get(selectedIndex);

                        // Confirmar eliminación
                        int respuesta = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea eliminar el evento seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);

                        if (respuesta == JOptionPane.YES_OPTION) {
                            // Intentar eliminar el evento de la agenda
                            agenda.eliminarEvento(fecha.toString(), eventoSeleccionado.getIdEvento());

                            // Guardar los cambios en el archivo CSV
                            agenda.guardarEventosCSV(archivoCSV);

                            // Mostrar mensaje de éxito
                            JOptionPane.showMessageDialog(frame, "Evento eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                            // Actualizar la lista de eventos
                            modelEventos.removeElementAt(selectedIndex);

                            // Si no quedan eventos, eliminar la fecha del listado
                            if (modelEventos.isEmpty()) {
                                modelFechas.removeElement(fecha.toString());
                                CardLayout cl = (CardLayout) panelEventos.getLayout();
                                cl.show(panelEventos, ""); // Limpiar la vista de eventos
                            }
                        }
                    }
                }
            });

        }

        // Listener para cambiar el CardLayout al seleccionar una fecha
        listaFechas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Evitar llamadas dobles
                String fechaSeleccionada = listaFechas.getSelectedValue();
                CardLayout cl = (CardLayout) (panelEventos.getLayout());
                cl.show(panelEventos, fechaSeleccionada);
            }
        });

        // Añadir paneles a la ventana principal
        frame.add(panelFechas, BorderLayout.WEST);
        frame.add(panelEventos, BorderLayout.CENTER);

        // Mejoras visuales
        panelFechas.setPreferredSize(new Dimension(200, 300));
        listaFechas.setBackground(new Color(245, 245, 245));
        panelEventos.setBackground(Color.WHITE);

        // Configuración final de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}

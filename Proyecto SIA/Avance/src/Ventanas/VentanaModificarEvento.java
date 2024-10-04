package Ventanas;

import Avance.Agenda;
import Avance.Evento;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VentanaModificarEvento {
    private Agenda agenda;
    private String archivoCSV; // Ruta del archivo CSV

    public VentanaModificarEvento(Agenda agenda, String archivoCSV) {
        this.agenda = agenda;
        this.archivoCSV = archivoCSV;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Modificar Evento");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel para contener la lista de fechas
        JPanel panelFechas = new JPanel(new BorderLayout());
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

        // Añadir paneles a la ventana principal
        frame.add(panelFechas, BorderLayout.WEST);
        frame.add(panelEventos, BorderLayout.CENTER);

        // Panel para modificar los detalles del evento
        JPanel panelModificaciones = new JPanel(new GridLayout(3, 2, 10, 10));
        panelModificaciones.setBorder(BorderFactory.createTitledBorder("Modificar Evento"));

        // ComboBox para seleccionar el atributo a modificar
        JLabel labelAtributo = new JLabel("Seleccione Atributo a Modificar:");
        String[] opciones = {"Nombre", "Descripción", "Etiqueta", "Hora", "Fecha"};
        JComboBox<String> comboAtributos = new JComboBox<>(opciones);

        JTextField campoModificacion = new JTextField();
        JLabel labelModificacion = new JLabel("Nuevo Valor:");

        JButton botonGuardarCambios = new JButton("Guardar Cambios");

        panelModificaciones.add(labelAtributo);
        panelModificaciones.add(comboAtributos);
        panelModificaciones.add(labelModificacion);
        panelModificaciones.add(campoModificacion);
        panelModificaciones.add(new JLabel()); // Espacio vacío
        panelModificaciones.add(botonGuardarCambios);

        // Añadir panel de modificaciones a la ventana
        frame.add(panelModificaciones, BorderLayout.EAST);

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

        // Acción del botón "Guardar Cambios"
        botonGuardarCambios.addActionListener(e -> {
            // Obtener el evento seleccionado
            int selectedIndex = listaEventos.getSelectedIndex();
            if (selectedIndex != -1) {
                String fechaSeleccionada = listaFechas.getSelectedValue();
                LocalDate fecha = LocalDate.parse(fechaSeleccionada);

                // Obtener el evento seleccionado para modificar
                Evento eventoSeleccionado = eventosAgenda.get(fecha).get(selectedIndex);

                // Obtener el nuevo valor del campo de modificación
                String nuevoValor = campoModificacion.getText();
                String atributoSeleccionado = (String) comboAtributos.getSelectedItem();

                // Validar la entrada antes de modificar
                boolean modificacionValida = true;

                // Modificar el evento según el atributo seleccionado
                switch (atributoSeleccionado) {
                    case "Nombre":
                        if (!nuevoValor.isEmpty()) {
                            eventoSeleccionado.setNombreEvento(nuevoValor);
                        }
                        break;
                    case "Descripción":
                        if (!nuevoValor.isEmpty()) {
                            eventoSeleccionado.setDescripcionEvento(nuevoValor);
                        }
                        break;
                    case "Etiqueta":
                        if (!nuevoValor.isEmpty()) {
                            eventoSeleccionado.setEtiqueta(nuevoValor);
                        }
                        break;
                    case "Hora":
                        if (!nuevoValor.isEmpty()) {
                            // Validar formato de hora
                            if (nuevoValor.matches("\\d{2}:\\d{2}")) {
                                eventoSeleccionado.setHoraEvento(nuevoValor);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Formato de hora incorrecto. Use HH:mm (24 horas).", "Error", JOptionPane.ERROR_MESSAGE);
                                modificacionValida = false;
                            }
                        }
                        break;
                    case "Fecha":
                        if (!nuevoValor.isEmpty()) {
                            // Validar formato de fecha
                            try {
                                LocalDate nuevaFecha = LocalDate.parse(nuevoValor);
                                agenda.eliminarEvento(fecha.toString(), eventoSeleccionado.getIdEvento());
                                agenda.agregarEvento(nuevaFecha.toString(), eventoSeleccionado);

                                // Si no quedan eventos en la fecha original, eliminar la entrada
                                if (eventosAgenda.get(fecha).isEmpty()) {
                                    modelFechas.removeElement(fecha.toString());
                                }
                            } catch (DateTimeParseException ex) {
                                JOptionPane.showMessageDialog(frame, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                                modificacionValida = false;
                            }
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Atributo no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        modificacionValida = false;
                }

                // Guardar los cambios si la modificación fue válida
                if (modificacionValida) {
                    agenda.guardarEventosCSV(archivoCSV);
                    JOptionPane.showMessageDialog(frame, "Evento modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar campo después de modificar
                    campoModificacion.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione un evento para modificar.", "Ningún Evento Seleccionado", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Configuración final de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

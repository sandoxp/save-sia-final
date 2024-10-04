package Ventanas;
import Avance.Agenda;
import Avance.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;


public class VentanaPorFecha {
    private Agenda agenda;

    public VentanaPorFecha(Agenda agenda) {
        this.agenda = agenda;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Mostrar Eventos por Fecha");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel superior para ingresar la fecha
        JPanel panelSuperior = new JPanel();
        JLabel labelFecha = new JLabel("Ingrese la fecha (YYYY-MM-DD):");
        JTextField campoFecha = new JTextField(10);
        JButton botonBuscar = new JButton("Buscar");

        panelSuperior.add(labelFecha);
        panelSuperior.add(campoFecha);
        panelSuperior.add(botonBuscar);

        // Panel para mostrar la tabla de eventos
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));

        // Modelo de la tabla
        String[] columnNames = {"ID", "Nombre", "Descripción", "Etiqueta", "Hora"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable tablaEventos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);

        panelTabla.add(scrollPane);

        // Añadir paneles al frame
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelTabla, BorderLayout.CENTER);

        // Acción del botón "Buscar"
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fecha ingresada
                String fechaIngresada = campoFecha.getText();
                LocalDate fecha;
                try {
                    fecha = LocalDate.parse(fechaIngresada);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Limpiar la tabla
                tableModel.setRowCount(0);

                // Obtener los eventos de la fecha ingresada
                ArrayList<Evento> eventosEnFecha = agenda.getEventos().getOrDefault(fecha, new ArrayList<>());
                if (eventosEnFecha.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No hay eventos para la fecha " + fechaIngresada + ".", "Sin Eventos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Llenar la tabla con los eventos
                    for (Evento evento : eventosEnFecha) {
                        Object[] eventoData = {
                                evento.getIdEvento(),
                                evento.getNombreEvento(),
                                evento.getDescripcionEvento(),
                                evento.getEtiqueta(),
                                evento.getHoraEvento()
                        };
                        tableModel.addRow(eventoData);
                    }
                }
            }
        });

        // Configuración final de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


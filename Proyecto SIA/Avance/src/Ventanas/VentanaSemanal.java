package Ventanas;

import Avance.Agenda;
import Avance.AgenditaSemanal;
import Avance.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaSemanal {
    private AgenditaSemanal agenditaSemanal;

    public VentanaSemanal(Agenda agenda) {
        // Crear la instancia de AgenditaSemanal y compartir los eventos de la agenda principal
        this.agenditaSemanal = new AgenditaSemanal("Agenda Semanal");
        this.agenditaSemanal.setEventos(agenda.getEventos()); // Compartir eventos

        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Mostrar Eventos Semanales");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel superior para ingresar la fecha inicial de la semana
        JPanel panelSuperior = new JPanel();
        JLabel labelFecha = new JLabel("Ingrese la fecha inicial (YYYY-MM-DD):");
        JTextField campoFecha = new JTextField(10);
        JButton botonBuscar = new JButton("Buscar");

        panelSuperior.add(labelFecha);
        panelSuperior.add(campoFecha);
        panelSuperior.add(botonBuscar);

        // Panel para mostrar la tabla de eventos
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));

        // Modelo de la tabla
        String[] columnNames = {"ID", "Nombre", "Descripción", "Etiqueta", "Hora", "Fecha"};
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
                LocalDate fechaInicial;
                try {
                    fechaInicial = LocalDate.parse(fechaIngresada);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Limpiar la tabla
                tableModel.setRowCount(0);

                // Obtener los eventos de la semana usando AgenditaSemanal
                List<Object[]> eventosData = agenditaSemanal.mostrarEventos(fechaIngresada);

                // Verificar si hay eventos
                if (eventosData.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No hay eventos para la semana de la fecha " + fechaIngresada + ".", "Sin Eventos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Llenar la tabla con los eventos
                    for (Object[] eventoData : eventosData) {
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

package Ventanas;

import Avance.Agenda;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaPorEtiqueta {
    private Agenda agenda;

    public VentanaPorEtiqueta(Agenda agenda) {
        this.agenda = agenda;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Mostrar Eventos por Etiqueta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        // Panel superior para seleccionar la etiqueta
        JPanel panelSuperior = new JPanel();
        JLabel labelEtiqueta = new JLabel("Seleccione la etiqueta:");
        JComboBox<String> etiquetaComboBox = new JComboBox<>(new String[]{"Reunion", "Actividad"});
        JButton botonFiltrar = new JButton("Filtrar");

        panelSuperior.add(labelEtiqueta);
        panelSuperior.add(etiquetaComboBox);
        panelSuperior.add(botonFiltrar);

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

        // Acción del botón "Filtrar"
        botonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la etiqueta seleccionada
                String etiquetaSeleccionada = (String) etiquetaComboBox.getSelectedItem();

                // Limpiar la tabla
                tableModel.setRowCount(0);

                // Obtener los eventos filtrados por la etiqueta seleccionada
                List<Object[]> eventosFiltrados = agenda.mostrarTodosLosEventos(etiquetaSeleccionada);

                if (eventosFiltrados.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No hay eventos con la etiqueta " + etiquetaSeleccionada + ".", "Sin Eventos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Llenar la tabla con los eventos filtrados
                    for (Object[] eventoData : eventosFiltrados) {
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

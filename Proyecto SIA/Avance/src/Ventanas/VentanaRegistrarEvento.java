package Ventanas;

import Avance.Agenda;
import Avance.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistrarEvento {
    private Agenda agenda;
    private String archivoCSV; // Ruta del archivo CSV

    public VentanaRegistrarEvento(Agenda agenda, String archivoCSV) {
        this.agenda = agenda;
        this.archivoCSV = archivoCSV;
        crearVentana();
    }

    private void crearVentana() {
        // Crear el frame de la ventana
        JFrame frame = new JFrame("Registrar Nuevo Evento");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel principal para ingresar datos del evento
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(6, 2, 10, 10));

        // Componentes para ingresar los detalles del evento
        JLabel labelNombre = new JLabel("Nombre del Evento:");
        JTextField campoNombre = new JTextField();

        JLabel labelDescripcion = new JLabel("Descripción del Evento:");
        JTextField campoDescripcion = new JTextField();

        JLabel labelEtiqueta = new JLabel("Etiqueta:");
        JComboBox<String> comboBoxEtiqueta = new JComboBox<>(new String[]{"Reunión", "Actividad"});

        JLabel labelHora = new JLabel("Hora (HH:mm):");
        JTextField campoHora = new JTextField();

        JLabel labelFecha = new JLabel("Fecha (YYYY-MM-DD):");
        JTextField campoFecha = new JTextField();

        JButton botonRegistrar = new JButton("Registrar");

        // Añadir componentes al panel principal
        panelPrincipal.add(labelNombre);
        panelPrincipal.add(campoNombre);
        panelPrincipal.add(labelDescripcion);
        panelPrincipal.add(campoDescripcion);
        panelPrincipal.add(labelEtiqueta);
        panelPrincipal.add(comboBoxEtiqueta);
        panelPrincipal.add(labelHora);
        panelPrincipal.add(campoHora);
        panelPrincipal.add(labelFecha);
        panelPrincipal.add(campoFecha);
        panelPrincipal.add(new JLabel()); // Espacio vacío
        panelPrincipal.add(botonRegistrar);

        // Añadir panel al frame
        frame.add(panelPrincipal, BorderLayout.CENTER);

        // Acción del botón "Registrar"
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos ingresados
                String nombre = campoNombre.getText();
                String descripcion = campoDescripcion.getText();
                String etiqueta = (String) comboBoxEtiqueta.getSelectedItem();
                String hora = campoHora.getText();
                String fecha = campoFecha.getText();

                // Validar y registrar el evento
                try {
                    // Crear y agregar evento a la agenda
                    Evento nuevoEvento = new Evento(nombre, descripcion, etiqueta, hora, fecha);
                    agenda.agregarEvento(fecha, nuevoEvento);

                    // Guardar los cambios en el archivo CSV
                    agenda.guardarEventosCSV(archivoCSV);

                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(frame, "Evento registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar campos después de registrar
                    campoNombre.setText("");
                    campoDescripcion.setText("");
                    campoHora.setText("");
                    campoFecha.setText("");
                } catch (Exception ex) {
                    // Manejar errores de formato o datos inválidos
                    JOptionPane.showMessageDialog(frame, "Error al registrar el evento. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configuración final de la ventana
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

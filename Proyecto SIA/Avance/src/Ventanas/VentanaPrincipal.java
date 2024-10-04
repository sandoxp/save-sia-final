package Ventanas;

import Avance.Agenda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal {
    private JButton registrarEventoButton;
    private JPanel panel1;
    private JButton visualizarEventoSButton;
    private JButton modificarEventoButton;
    private JButton eliminarEventoButton;
    private JButton verTiempoRestanteButton;
    private JPanel panelContenedor; // Panel que contiene todas las tarjetas
    private CardLayout cardLayout; // CardLayout para cambiar entre tarjetas

    private String archivoCSV = "Avance/DatosGenerados.csv"; // Ruta del archivo CSV
    private Agenda agenda;

    public VentanaPrincipal(Agenda agenda) {
        // Inicializar el CardLayout y el panel contenedor
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);



        // Crear instancia de VentanaOpcionesMostrar
        VentanaOpcionesMostrar opcionesMostrar = new VentanaOpcionesMostrar(agenda);

        // Crear placeholders para cada funcionalidad
        JPanel panelRegistrarEvento = new JPanel();
        panelRegistrarEvento.add(new JLabel("Formulario para registrar un evento."));

        JPanel panelModificarEvento = new JPanel();
        panelModificarEvento.add(new JLabel("Vista para modificar un evento."));

        JPanel panelEliminarEvento = new JPanel();
        panelEliminarEvento.add(new JLabel("Vista para eliminar un evento."));

        JPanel panelVerTiempoRestante = new JPanel();
        panelVerTiempoRestante.add(new JLabel("Vista para consultar el tiempo restante de un evento."));

        // Añadir los paneles al contenedor con identificadores
        panelContenedor.add(panel1, "PanelPrincipal");
        panelContenedor.add(opcionesMostrar.getPanel(), "PanelOpciones");
        panelContenedor.add(panelRegistrarEvento, "PanelRegistrarEvento");
        panelContenedor.add(panelModificarEvento, "PanelModificarEvento");
        panelContenedor.add(panelEliminarEvento, "PanelEliminarEvento");
        panelContenedor.add(panelVerTiempoRestante, "PanelVerTiempoRestante");

        // Acción para abrir la ventana de registro de eventos
        // Acción para abrir la ventana de registro de eventos
        registrarEventoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear e instanciar VentanaRegistrarEvento con la agenda y el archivo CSV actual
                new VentanaRegistrarEvento(agenda, archivoCSV);
            }
        });

        // Acción para cambiar a la vista de opciones para visualizar eventos
        visualizarEventoSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "PanelOpciones"); // Cambiar a la tarjeta de opciones
            }
        });


        // Acción para modificar un evento
        modificarEventoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instanciar la ventana para modificar eventos
                new VentanaModificarEvento(agenda, archivoCSV);
            }
        });

        // Acción para abrir la ventana de eliminación de eventos
        eliminarEventoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear e instanciar VentanaEliminarEvento con la agenda y el archivo CSV actual
                new VentanaEliminarEvento(agenda, archivoCSV);
            }
        });

        // Acción para ver tiempo restante de un evento
        verTiempoRestanteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instanciar la ventana para visualizar el tiempo restante
                new VentanaVisualizarTiempoRestante(agenda);
            }
        });
    }

    public JPanel getPanelContenedor() {
        return panelContenedor;
    }

    public static void main(String[] args) {
        // Instancia de la agenda
        Agenda miAgenda = new Agenda("Mi Agenda");
        String archivoCSV = "Avance/DatosGenerados.csv";
        miAgenda.cargarEventosCSV(archivoCSV);

        // Crear la ventana principal y pasarle la agenda
        JFrame frame = new JFrame("VentanaPrincipal");
        frame.setContentPane(new VentanaPrincipal(miAgenda).getPanelContenedor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}

package Ventanas;
import Avance.Agenda;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class VentanaMostrarTodo {
    private Agenda agenda;

    public VentanaMostrarTodo(Agenda agenda) {
        this.agenda = agenda;
    }


    public void mostrarTablaEventos() {
        // Encabezados de la tabla

        String[] columnNames = {"ID", "Nombre", "Descripción", "Etiqueta", "Hora", "Fecha"};

        // Obtener los datos de los eventos desde la agenda
        List<Object[]> eventosData = agenda.mostrarTodosLosEventos();

        // Crear el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Llenar el modelo con los datos de los eventos
        for (Object[] eventoData : eventosData) {
            tableModel.addRow(eventoData);
        }

        // Crear la tabla con el modelo
        JTable tablaEventos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);

        // Ajustar el ancho de las columnas
        tablaEventos.getColumnModel().getColumn(0).setPreferredWidth(10); // ID
        tablaEventos.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tablaEventos.getColumnModel().getColumn(2).setPreferredWidth(350); // Descripción
        tablaEventos.getColumnModel().getColumn(3).setPreferredWidth(30); // Etiqueta
        tablaEventos.getColumnModel().getColumn(4).setPreferredWidth(30); // Hora
        tablaEventos.getColumnModel().getColumn(5).setPreferredWidth(30); // Fecha

        // Crear un nuevo panel para mostrar la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.add(scrollPane);

        // Mostrar la tabla en un cuadro de diálogo (o agregarla a la ventana actual)
        JFrame frame = new JFrame("Todos los Eventos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panelTabla);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

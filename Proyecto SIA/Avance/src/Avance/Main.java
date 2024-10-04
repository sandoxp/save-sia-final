package Avance;
import Avance.Agenda;
import Ventanas.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //Creación de agenda.
        Agenda nuevaAgenda = new Agenda("Agenda Empresa");


//        //Creacion de eventos
//        Evento evento1 = new Evento("Capacitación en Seguridad", "Sesión de capacitación en normas de seguridad laboral", "Actividad", "11:30", "2024-01-01");
//        Evento evento2 = new Evento("Reunión de Estrategia", "Discusión sobre las estrategias de ventas para el próximo trimestre", "Reunión", "09:00", "2024-01-01");
//        Evento evento3 = new Evento("Revisión de Desempeño", "Evaluación del desempeño del equipo de desarrollo", "Reunión", "14:00","2024-03-11");
//        Evento evento4 = new Evento("Presentación de Resultados", "Presentación de resultados financieros a la gerencia", "Reunión", "16:00", "2024-08-29");
//        Evento evento5 = new Evento("Evento Corporativo", "Celebración anual de la empresa con todos los empleados", "Actividad", "19:00", "2024-09-21");
//

//
//
//        //Agregar eventos a nuevaAgenda utilizando metodo agregarEvento();
//        nuevaAgenda.agregarEvento("2024-10-01", evento1);
//        nuevaAgenda.agregarEvento("2024-10-01", evento2);
//        nuevaAgenda.agregarEvento("2024-11-11", evento3);
//        nuevaAgenda.agregarEvento("2024-08-29", evento4);
//        nuevaAgenda.agregarEvento("2024-09-21", evento5);
        
        String archivoCSV = "Avance/DatosGenerados.csv";
        nuevaAgenda.cargarEventosCSV(archivoCSV);
        //Menú
        //Menu menu = new Menu(nuevaAgenda,archivoCSV);
        //menu.iniciarMenu();

        nuevaAgenda.guardarEventosCSV(archivoCSV);


        // Crear la ventana principal y pasarle la agenda cargada
        JFrame frame = new JFrame("Agenda - Ventana Principal");
        frame.setContentPane(new VentanaPrincipal(nuevaAgenda).getPanelContenedor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        String rutaArchivo = "reporte.txt"; // Nombre y ruta donde se guardará el archivo .txt
        GenerarReporte.generarReporte(rutaArchivo, nuevaAgenda);
    }
}

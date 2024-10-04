package Avance;

public class Evento {
    private String nombreEvento;
    private String descripcionEvento;
    private String etiqueta;
    private String horaEvento;
    private String fechaEvento;
    private static int contador = 0;
    private int idEvento;

    public Evento() {
    }

    public Evento(String nombreEvento, String descripcionEvento, String etiqueta, String horaEvento, String fechaEvento)  {
        this.idEvento = ++contador;
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
        this.etiqueta = etiqueta;
        this.horaEvento = horaEvento;
        this.fechaEvento = fechaEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getHoraEvento() {return horaEvento;}

    public void setHoraEvento(String horaEvento) {this.horaEvento=horaEvento;}


    public String getEtiqueta() {return etiqueta;}

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    public int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Evento.contador = contador;
    }
    public int getIdEvento() {return idEvento;}

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
        if (idEvento > contador) {
            contador = idEvento;
        }
    }


    @Override
    public String toString() {
        return "Evento{" +
                "Nombre evento='" + nombreEvento + '\'' +
                ", Descripción='" + descripcionEvento + '\'' +
                ", Etiqueta='" + etiqueta + '\'' +
                ", Hora del evento='" + horaEvento + '\'' +
                ", ID Evento ='" + idEvento + '\'' +
                '}';
    }


}


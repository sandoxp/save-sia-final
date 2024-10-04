package Avance;
public class Persona {

    private String nombre;
    private int edad;
    private String RUT;
    private String cargo;

    public Persona(){

    }

    public Persona(String nombre, int edad, String RUT, String cargo) {
        this.nombre = nombre;
        this.edad = edad;
        this.RUT = RUT;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", RUT='" + RUT + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }

    public void mostrarSaludo(){
        System.out.println("Hola, " + nombre + "!");
    }

    public void mostrarSaludo(String cargo){
        if (cargo.equalsIgnoreCase("jefe")){
            System.out.println("Hola Jefe! Buenos dias");
        }
        else{
            System.out.println("Bienvenido compañero " + nombre + "!");
        }
    }
}

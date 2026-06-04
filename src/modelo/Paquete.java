package modelo;

public class Paquete {
    private String Id;
    private String descripcion; 
    private double peso;    
    private EstadoPaquete estadoPaquete;
    
    //Constructor de estado minimo
    public Paquete(String Id, double peso) {
        this.Id = Id;
        this.descripcion = "Sin descripcion";
        this.peso = peso;
        this.estadoPaquete = EstadoPaquete.RECIBIDO;
    }
    //Constructor completo
    public Paquete(String Id, String descripcion, double peso) {
        this.Id = Id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.estadoPaquete = EstadoPaquete.RECIBIDO;
    }
    //Si esta en estado recibido, se puede preparar
    public void preparar () {
        if (estadoPaquete == EstadoPaquete.RECIBIDO) {
            estadoPaquete = EstadoPaquete.EN_PREPARACION;
        }
    }
    //Si esta en estado preparado, se puede distribuir
    public void distribuir () {
        if (estadoPaquete == EstadoPaquete.EN_PREPARACION) {
            estadoPaquete = EstadoPaquete.EN_DISTRIBUCION;
        }
    }
    //Si esta en estado distribuido, se puede entregar
    public void entregar () {
        if (estadoPaquete == EstadoPaquete.EN_DISTRIBUCION) {
            estadoPaquete = EstadoPaquete.ENTREGADO;
        }
    }

    public double getPeso() {
        return peso;
    }
 
    //Metodo toString para mostrar la informacion del paquete
    public String toString() {
        return "Paquete [Id=" + Id + ", descripcion=" + descripcion + ", peso=" + peso + ", estadoPaquete="
                + estadoPaquete + "]";
    }
}

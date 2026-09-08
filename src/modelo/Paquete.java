package modelo;

public class Paquete {
    private String Id;
    private String descripcion;
    private double peso;
    private EstadoPaquete estadoPaquete;

    // CONSTRUCTOR BASICO, SI NO SE PROPORCIONA UNA DESCRIPCION, SE ASIGNA "Sin
    // descripcion" POR DEFECTO. EL ESTADO INICIAL DEL PAQUETE ES RECIBIDO.
    public Paquete(String Id, double peso) {
        this.Id = Id;
        this.descripcion = "Sin descripcion";
        this.peso = peso;
        this.estadoPaquete = EstadoPaquete.RECIBIDO;
    }

    // CONSTRUCTOR COMPLETO, PERMITE ASIGNAR UNA DESCRIPCION PERSONALIZADA AL
    // PAQUETE. EL ESTADO INICIAL DEL PAQUETE ES RECIBIDO.
    public Paquete(String Id, String descripcion, double peso) {
        this.Id = Id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.estadoPaquete = EstadoPaquete.RECIBIDO;
    }

    // SI ESTA EN ESTADO RECIBIDO, SE PUEDE PREPARAR PARA SU DISTRIBUCION
    public void preparar() {
        if (estadoPaquete == EstadoPaquete.RECIBIDO) {
            estadoPaquete = EstadoPaquete.EN_PREPARACION;
        }
    }

    // SI ESTA EN ESTADO PREPARADO, SE PUEDE DISTRIBUIR
    public void distribuir() {
        if (estadoPaquete == EstadoPaquete.EN_PREPARACION) {
            estadoPaquete = EstadoPaquete.EN_DISTRIBUCION;
        }
    }

    // SI ESTA EN ESTADO DISTRIBUIDO, SE PUEDE ENTREGAR
    public void entregar() {
        if (estadoPaquete == EstadoPaquete.EN_DISTRIBUCION) {
            estadoPaquete = EstadoPaquete.ENTREGADO;
        }
    }

    public double getPeso() {
        return peso;
    }

    // METODO TO STRING PARA REPRESENTAR EL PAQUETE, INCLUYENDO SU ID, DESCRIPCION,
    // PESO Y ESTADO ACTUAL.
    public String toString() {
        return "Paquete [Id=" + Id + ", descripcion=" + descripcion + ", peso=" + peso + ", estadoPaquete="
                + estadoPaquete + "]";
    }
}

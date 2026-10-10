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

    // EL PAQUETE RECIBE MENSAJES DE NEGOCIO, NO CAMBIOS DE ESTADO DIRECTOS.
    // SI LA TRANSICION NO ES VALIDA, SE LANZA UNA EXCEPCION.
    public void preparar() {
        cambiarEstado(EstadoPaquete.EN_PREPARACION);
    }

    public void distribuir() {
        cambiarEstado(EstadoPaquete.EN_DISTRIBUCION);
    }

    public void entregar() {
        cambiarEstado(EstadoPaquete.ENTREGADO);
    }

        public EstadoPaquete getEstado() {
        return estadoPaquete;
    }

    // METODO PRIVADO AUXILIAR: EL PAQUETE ES EL UNICO RESPONSABLE DE SU ESTADO.
    // NADIE DE AFUERA PUEDE CAMBIARLO NI NECESITA CONOCER QUE TRANSICIONES SON
    // VALIDAS; LA REGLA SE LE PREGUNTA AL PROPIO ESTADO.
    private void cambiarEstado(EstadoPaquete nuevoEstado) {
        if (!estadoPaquete.puedeAvanzarA(nuevoEstado)) {
            throw new TransicionInvalidaException("El paquete " + Id + " no puede pasar del estado " + estadoPaquete
                    + " al estado " + nuevoEstado + ". Estados posibles desde " + estadoPaquete + ": "
                    + estadoPaquete.getSiguientes() + ".");
        }
        estadoPaquete = nuevoEstado;
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

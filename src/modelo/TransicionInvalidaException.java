package modelo;

// SE LANZA CUANDO UN OBJETO DEL DOMINIO INTENTA PASAR A UN ESTADO QUE NO ES
// ALCANZABLE DESDE SU ESTADO ACTUAL. EL OBJETO RESPONSABLE DEL ESTADO ES QUIEN
// CONOCE Y PROTEGE ESTA REGLA.
public class TransicionInvalidaException extends OperacionInvalidaException {

    private static final long serialVersionUID = 1L;

    public TransicionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
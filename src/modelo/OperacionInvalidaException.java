package modelo;

// EXCEPCION BASE DEL DOMINIO: REPRESENTA UNA OPERACION QUE NO RESPETA LAS
// REGLAS DE NEGOCIO DEL SISTEMA DE ENVIOS. LAS DEMAS EXCEPCIONES DEL SISTEMA
// HEREDAN DE ESTA, ASI EL APP PUEDE ATRAPARLAS TODAS JUNTAS SI LO NECESITA.
public class OperacionInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
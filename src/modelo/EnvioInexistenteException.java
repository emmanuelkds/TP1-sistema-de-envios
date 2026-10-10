package modelo;

// SE LANZA CUANDO SE INTENTA OPERAR SOBRE UN ENVIO QUE NO ESTA EN LA SUCURSAL
// DONDE SE QUIERE DESPACHARLO O ENTREGARLO, ES DECIR, CUANDO EL ENVIO NO
// EXISTE PARA ESA SUCURSAL.
public class EnvioInexistenteException extends OperacionInvalidaException {

    private static final long serialVersionUID = 1L;

    public EnvioInexistenteException(String mensaje) {
        super(mensaje);
    }
}
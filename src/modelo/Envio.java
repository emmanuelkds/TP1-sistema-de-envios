package modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Envio {
    private static final int MAXIMO_PAQUETES = 3;
    private String id;
    private List<Paquete> paquetes;
    private Cliente cliente;
    private List<Movimiento> movimientosHistorial;

    @SuppressWarnings("this-escape")
    public Envio(String id, Cliente cliente) {
        this.cliente = cliente;
        this.id = id;
        this.paquetes = new ArrayList<>();
        this.movimientosHistorial = new ArrayList<>();
        // EL CLIENTE SE REGISTRA A SI MISMO EL ENVIO
        cliente.agregarEnvio(this);
    }

    // AGREGA UN PAQUETE AL ENVIO, SIEMPRE Y CUANDO NO HAYA SIDO RECIBIDO,
    // DESPACHADO O ENTREGADO Y SI NO SE HA ALCANZADO EL MAXIMO DE 3 PAQUETES.
    public void agregarPaquete(Paquete paquete) {
        if (!todaviaNoSalio()) {
            throw new TransicionInvalidaException(
                    "No se pueden agregar paquetes a un envio que ya ha sido recibido, despachado o entregado.");
        }
        if (!hayLugar()) {
            throw new OperacionInvalidaException(
                    "El envio " + id + " ya tiene el maximo de " + MAXIMO_PAQUETES + " paquetes.");
        }
        paquetes.add(paquete);
    }

    // EXPONE LA LISTA DE PAQUETES DE FORMA INMODIFICABLE PARA EVITAR QUE SE
    // MODIFIQUE DESDE FUERA DE LA CLASE.
    public List<Paquete> getPaquetes() {
        return Collections.unmodifiableList(paquetes);
    }

    public String getId() {
        return id;
    }

    // SE REGISTRA EL MOVIMIENTO DE RECIBIDO EN EL HISTORIAL DEL ENVIO Y SE PREPARAN
    // LOS PAQUETES PARA SU DISTRIBUCION.
    public void recibir(Sucursal sucursal) {
        if (!tienePaquetes()) {
            throw new OperacionInvalidaException("El envio " + id + " no puede ser recibido sin paquetes.");
        }
        if (!estaEnEtapaDeRecepcion()) {
            throw new TransicionInvalidaException("El envio " + id + " no se puede recibir en este momento.");
        }
        for (Paquete paquete : paquetes) {
            paquete.preparar();
        }
        registrarMovimiento(TipoMovimiento.RECIBIDO, sucursal);
    }

    public void despachar(Sucursal sucursal) {
        if (!puedeDespacharse()) {
            throw new TransicionInvalidaException("El envio " + id + " no puede ser despachado.");
        }
        for (Paquete paquete : paquetes) {
            paquete.distribuir();
        }
        registrarMovimiento(TipoMovimiento.DESPACHADO, sucursal);
    }

    // ENTREGA EL ENVIO, SIEMPRE Y CUANDO HAYA SIDO RECIBIDO Y NO HAYA SIDO
    // ENTREGADO ANTES. SE REGISTRA EL MOVIMIENTO DE ENTREGADO EN EL HISTORIAL DEL
    // ENVIO.
    public void entregar(Sucursal sucursal) {
        if (!puedeEntregarse()) {
            throw new TransicionInvalidaException("El envio " + id + " no puede ser entregado.");
        }
        for (Paquete paquete : paquetes) {
            paquete.entregar();
        }
        registrarMovimiento(TipoMovimiento.ENTREGADO, sucursal);
    }

    // METODO ABSTRACTO PARA CALCULAR EL COSTO DEL ENVIO, QUE DEBE SER IMPLEMENTADO
    // POR LAS CLASES HIJAS.
    public abstract double calcularCosto();

    // DEVUELVE EL HISTORIAL COMPLETO COMO DATO, SIN IMPRIMIR NADA. QUIEN LO
    // NECESITE (POR EJEMPLO EL APP) LO RECIBE Y DECIDE QUE HACER CON EL.
    public List<Movimiento> getHistorial() {
        return Collections.unmodifiableList(movimientosHistorial);
    }

    // SE OBTIENE LA LISTA DE SUCURSALES EN EL HISTORIAL DEL ENVIO, FILTRANDO SOLO
    // AQUELLAS QUE HAN RECIBIDO EL ENVIO.
    public List<Sucursal> getSucursalesEnHistorial() {
        List<Sucursal> sucursales = new ArrayList<>();
        for (Movimiento movimiento : movimientosHistorial) {
            if (movimiento.getTipoMovimiento() == TipoMovimiento.RECIBIDO) {
                Sucursal s = movimiento.getSucursal();
                sucursales.add(s);
            }
        }
        return sucursales;
    }

    public Movimiento getUltimoMovimiento() {
        if (movimientosHistorial.isEmpty()) {
            return null;
        }
        return movimientosHistorial.get(movimientosHistorial.size() - 1);
    }

    // ==================================================================
    // METODOS PRIVADOS AUXILIARES
    // DAN NOMBRE A LAS REGLAS DEL NEGOCIO, SE REUTILIZAN ENTRE LAS OPERACIONES Y
    // EVITAN REPETIR CONDICIONES DENTRO DE CADA METODO PUBLICO.
    // ==================================================================

    private boolean hayLugar() {
        return paquetes.size() < MAXIMO_PAQUETES;
    }

    // UN ENVIO QUE YA TIENE MOVIMIENTOS YA SALIO DE LA SUCURSAL DE ORIGEN.
    private boolean todaviaNoSalio() {
        return getUltimaEtapa() == null;
    }

    private boolean tienePaquetes() {
        return !paquetes.isEmpty();
    }

    // ETAPA DEL ULTIMO MOVIMIENTO, O NULA SI EL ENVIO TODAVIA NO TIENE HISTORIAL.
    private TipoMovimiento getUltimaEtapa() {
        Movimiento ultimo = getUltimoMovimiento();
        return ultimo == null ? null : ultimo.getTipoMovimiento();
    }

    // LA COHERENCIA DE CADA MOVIMIENTO LA CONOCE EL PROPIO TIPO DE MOVIMIENTO:
    // EL ENVIO SOLO LE PREGUNTA SI PUEDE REGISTRARSE DESPUES DE SU ULTIMA ETAPA.
    private boolean estaEnEtapaDeRecepcion() {
        return TipoMovimiento.RECIBIDO.puedeRegistrarseDespuesDe(getUltimaEtapa());
    }

    // SOLO SE DESPACHA LO QUE ACABA DE LLEGAR A LA SUCURSAL.
    private boolean puedeDespacharse() {
        return TipoMovimiento.DESPACHADO.puedeRegistrarseDespuesDe(getUltimaEtapa());
    }

    // SOLO SE ENTREGA LO QUE ESTA EN UNA SUCURSAL: SI YA SE DESPACHO O YA SE
    // ENTREGO, LA ULTIMA ETAPA NO ES RECIBIDO Y LA OPERACION NO CORRESPONDE.
    private boolean puedeEntregarse() {
        return TipoMovimiento.ENTREGADO.puedeRegistrarseDespuesDe(getUltimaEtapa());
    }

    // UNICO LUGAR DONDE SE AGREGA UN MOVIMIENTO AL HISTORIAL: ASI EL RECORRIDO DEL
    // ENVIO SIEMPRE SE CONSTRUYE DE LA MISMA MANERA Y NO SE DUPLICA EL CODIGO.
    private void registrarMovimiento(TipoMovimiento tipoMovimiento, Sucursal sucursal) {
        movimientosHistorial.add(new Movimiento(tipoMovimiento, sucursal));
    }

    // METODO TO STRING PARA REPRESENTAR EL ENVIO, INCLUYENDO SU ID, COSTO, PAQUETES
    // Y CLIENTE ASOCIADO.
    public String toString() {
        return "Envio [id=" + id + ", costo=" + calcularCosto() + ", paquetes=" + paquetes + ", cliente=" + cliente
                + "]";
    }
}

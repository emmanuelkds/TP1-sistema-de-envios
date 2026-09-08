package modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Envio {
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
        Movimiento ultimo = getUltimoMovimiento();
        if (ultimo != null) {
            throw new IllegalStateException(
                    "No se pueden agregar paquetes a un envio que ya ha sido recibido, despachado o entregado.");
        }
        if (paquetes.size() < 3) {
            paquetes.add(paquete);
        } else {
            throw new IllegalStateException("El envio " + id + " ya tiene el maximo de 3 paquetes.");
        }
    }

    // EXPONE LA LISTA DE PAQUETES DE FORMA INMODIFICABLE PARA EVITAR QUE SE
    // MODIFIQUE DESDE FUERA DE LA CLASE.
    public List<Paquete> getPaquetes() {
        return Collections.unmodifiableList(paquetes);
    }

    // SE REGISTRA EL MOVIMIENTO DE RECIBIDO EN EL HISTORIAL DEL ENVIO Y SE PREPARAN
    // LOS PAQUETES PARA SU DISTRIBUCION.
    public void recibir(Sucursal sucursal) {
        // EVITA QUE SE RECIBA UN ENVIO SIN PAQUETES O QUE YA HAYA SIDO RECIBIDO O
        // ENTREGADO.
        if (paquetes.isEmpty()) {
            throw new IllegalStateException("El envio " + id + " no puede ser recibido sin paquetes.");
        }
        Movimiento ultimo = getUltimoMovimiento();
        if (ultimo != null && (ultimo.getTipoMovimiento() == TipoMovimiento.RECIBIDO
                || ultimo.getTipoMovimiento() == TipoMovimiento.ENTREGADO)) {
            throw new IllegalStateException("El envio " + id + " no se puede recibir en este momento.");
        }
        for (Paquete paquete : paquetes) {
            paquete.preparar();
        }
        movimientosHistorial.add(new Movimiento(TipoMovimiento.RECIBIDO, sucursal));
        System.out.println("Envio " + id + " recibido.");
    }

    public void despachar(Sucursal sucursal) {
        Movimiento ultimo = getUltimoMovimiento();
        if (ultimo == null || ultimo.getTipoMovimiento() != TipoMovimiento.RECIBIDO) {
            throw new IllegalStateException("El envio " + id + " no puede ser despachado.");
        }
        for (Paquete paquete : paquetes) {
            paquete.distribuir();
        }
        movimientosHistorial.add(new Movimiento(TipoMovimiento.DESPACHADO, sucursal));
        System.out.println("Envio " + id + " despachado.");
    }

    // ENTREGA EL ENVIO, SIEMPRE Y CUANDO HAYA SIDO RECIBIDO Y NO HAYA SIDO
    // ENTREGADO ANTES. SE REGISTRA EL MOVIMIENTO DE ENTREGADO EN EL HISTORIAL DEL
    // ENVIO.
    public void entregar(Sucursal sucursal) {
        Movimiento ultimo = getUltimoMovimiento();
        if (ultimo == null || ultimo.getTipoMovimiento() != TipoMovimiento.RECIBIDO) {
            throw new IllegalStateException("El envio " + id + " no puede ser entregado.");
        }
        for (Paquete paquete : paquetes) {
            paquete.entregar();
        }
        movimientosHistorial.add(new Movimiento(TipoMovimiento.ENTREGADO, sucursal));
        System.out.println("Envio " + id + " entregado.");
    }

    // METODO ABSTRACTO PARA CALCULAR EL COSTO DEL ENVIO, QUE DEBE SER IMPLEMENTADO
    // POR LAS CLASES HIJAS.
    public abstract double calcularCosto();

    // MUESTRA EL HISTORIAL DE MOVIMIENTOS DEL ENVIO, INCLUYENDO EL TIPO DE
    // MOVIMIENTO Y LA SUCURSAL ASOCIADA.
    public void mostrarHistorial() {
        System.out.println("Historial de movimientos del envio " + id + ":");
        for (Movimiento movimiento : movimientosHistorial) {
            System.out.println(movimiento);
        }
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

    // METODO TO STRING PARA REPRESENTAR EL ENVIO, INCLUYENDO SU ID, COSTO, PAQUETES
    // Y CLIENTE ASOCIADO.
    public String toString() {
        return "Envio [id=" + id + ", costo=" + calcularCosto() + ", paquetes=" + paquetes + ", cliente=" + cliente
                + "]";
    }
}

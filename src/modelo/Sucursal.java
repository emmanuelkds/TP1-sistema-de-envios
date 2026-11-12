package modelo;

import java.util.LinkedList;
import java.util.Queue;

public class Sucursal {
    private Queue<Envio> colaDeDespacho; // CAMBIO: Ahora es una cola FIFO
    private String id;
    private String localidad;
    private String direccion;
    private String telefono;

    public Sucursal(String id, String localidad, String direccion, String telefono) {
        this.id = id;
        this.localidad = localidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.colaDeDespacho = new LinkedList<>(); // LinkedList implementa Queue
    }

    // El envío entra al final de la cola
    public void recibirEnvio(Envio envio) {
        envio.recibir(this);
        colaDeDespacho.add(envio);
    }

    // Despachar una cantidad de envíos respetando el orden FIFO
    public void despachar(int cantidad) {
        if (colaDeDespacho.isEmpty()) {
            System.out.println("La cola de despacho de " + localidad + " está vacía. No hay envíos para despachar.");
            return;
        }

        int despachados = 0;

        while (!colaDeDespacho.isEmpty() && despachados < cantidad) {
            Envio envio = colaDeDespacho.poll(); // Saca el primero (el que llegó primero)
            envio.despachar(this);
            System.out.println("Envío " + envio.getId() + " despachado desde " + localidad + ".");
            despachados++;
        }

        if (despachados < cantidad) {
            System.out.println("Se despacharon " + despachados + " envíos. La cola quedó vacía.");
        }
    }

    // Para entregar, el envío tiene que estar en la cola (fue recibido y no despachado)
    public void entregarEnvio(Envio envio) {
        if (!colaDeDespacho.contains(envio)) {
            throw new EnvioInexistenteException(
                    "El envío " + envio.getId() + " no se encuentra en la sucursal " + localidad + ".");
        }
        envio.entregar(this);
        colaDeDespacho.remove(envio);
    }

    // Para que el Main consulte cuántos envíos hay en espera
    public int cantidadEnviosEnEspera() {
        return colaDeDespacho.size();
    }

    // Para que el Main verifique si hay envíos (opcional)
    public boolean tieneEnvios() {
        return !colaDeDespacho.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String toString() {
        return "Sucursal [id=" + id + ", localidad=" + localidad + ", direccion=" + direccion
                + ", telefono=" + telefono + ", envíos en espera=" + colaDeDespacho.size() + "]";
    }
}
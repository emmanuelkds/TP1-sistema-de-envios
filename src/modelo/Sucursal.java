package modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Sucursal {
    private List<Envio> enviosEnSucursal;
    private String id;
    private String localidad;
    private String direccion;
    private String telefono;

    public Sucursal(String id, String localidad, String direccion, String telefono) {
        this.id = id;
        this.localidad = localidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.enviosEnSucursal = new ArrayList<>();
    }

    public void recibirEnvio(Envio envio) {
        envio.recibir(this);
        enviosEnSucursal.add(envio);
    }

    public void despacharEnvio(Envio envio) {
        if (!enviosEnSucursal.contains(envio)) {
            throw new EnvioInexistenteException(
                    "El envio " + envio.getId() + " no se encuentra en la sucursal " + localidad + ".");
        }
        envio.despachar(this);
        enviosEnSucursal.remove(envio);
    }

    public void entregarEnvio(Envio envio) {
        if (!enviosEnSucursal.contains(envio)) {
            throw new EnvioInexistenteException(
                    "El envio " + envio.getId() + " no se encuentra en la sucursal " + localidad + ".");
        }
        envio.entregar(this);
        enviosEnSucursal.remove(envio);
    }

    // OPERACION DE BUSQUEDA POR CODIGO: LA SUCURSAL ES LA QUE CONOCE LOS ENVIOS
    // QUE TIENE EN CUSTODIA, ASI QUE ES ELLA LA QUE RESPONDE SI EL ENVIO BUSCADO
    // ESTA O NO ESTA EN ELLA.
    public Envio buscarEnvio(String idEnvio) {
        for (Envio envio : enviosEnSucursal) {
            if (envio.getId().equalsIgnoreCase(idEnvio)) {
                return envio;
            }
        }
        throw new EnvioInexistenteException(
                "No se encontró ningún envío con el código " + idEnvio + " en la sucursal " + localidad + ".");
    }

    public String getId() {
        return id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public List<Envio> getEnviosEnSucursal() {
        return Collections.unmodifiableList(enviosEnSucursal);
    }

    public String toString() {
        return "Sucursal [id=" + id + ", localidad=" + localidad + ", direccion=" + direccion
                + ", telefono=" + telefono + "]";
    }
}
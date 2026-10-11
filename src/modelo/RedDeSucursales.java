package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RedDeSucursales {
    private List<Sucursal> sucursales;

    public RedDeSucursales() {
        this.sucursales = new ArrayList<>();
    }

    public void agregarSucursal(Sucursal sucursal) {
        sucursales.add(sucursal);
    }

    // BUSCA UN ENVIO SIN QUE EL USUARIO TENGA QUE SABER EN QUE SUCURSAL ESTA:
    // LA RED LE PREGUNTA A CADA SUCURSAL, QUE ES LA QUE CONOCE SU CUSTODIA.
    public Envio buscarEnvio(String idEnvio) {
        for (Sucursal sucursal : sucursales) {
            try {
                return sucursal.buscarEnvio(idEnvio);
            } catch (EnvioInexistenteException e) {
                // NO ESTA EN ESTA SUCURSAL: SE CONSULTA LA SIGUIENTE
            }
        }
        throw new EnvioInexistenteException(
                "El envío " + idEnvio + " no se encuentra en custodia de ninguna sucursal.");
    }

    public List<Sucursal> getSucursales() {
        return Collections.unmodifiableList(sucursales);
    }
}
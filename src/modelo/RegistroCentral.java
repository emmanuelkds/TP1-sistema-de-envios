package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroCentral {
    private Map<Integer, Envio> envios;       // HashMap para envíos (clave = código)
    private List<Cliente> clientes;          // ArrayList para clientes
    private Map<String, Sucursal> sucursales; // HashMap para sucursales

    public RegistroCentral() {
        this.envios = new HashMap<>();
        this.clientes = new ArrayList<>();
        this.sucursales = new HashMap<>();
    }

    // =====================================================
    // ENVÍOS (HashMap)
    // =====================================================

    public void registrarEnvio(Envio envio) {
        int codigo = envio.getId();

        if (envios.containsKey(codigo)) {
            System.out.println("Error: ya existe un envío con el código " + codigo + ". No se registró.");
            return;
        }

        envios.put(codigo, envio);
        System.out.println("Envío " + codigo + " registrado correctamente.");
    }

    public Envio buscarEnvio(int codigo) {
        Envio envio = envios.get(codigo);
        if (envio == null) {
            System.out.println("No se encontró ningún envío con el código " + codigo + ".");
        }
        return envio;
    }

    // =====================================================
    // CLIENTES (Lista y búsqueda secuencial)
    // =====================================================

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cliente> listarClientes() {
        return clientes;
    }

    // Búsqueda secuencial por DNI
    public Cliente buscarClienteSecuencial(int dni) {
        for (Cliente c : clientes) {
            if (c.getDni() == dni) {
                return c;
            }
        }
        System.out.println("No se encontró ningún cliente con el DNI " + dni + ".");
        return null;
    }

    // =====================================================
    // SUCURSALES (HashMap)
    // =====================================================

    public void registrarSucursal(Sucursal sucursal) {
        sucursales.put(sucursal.getId(), sucursal);
    }

    public Sucursal buscarSucursal(String id) {
        return sucursales.get(id);
    }

    public List<Sucursal> listarSucursales() {
        return new ArrayList<>(sucursales.values());
    }
}
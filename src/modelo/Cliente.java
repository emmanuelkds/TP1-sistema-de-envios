package modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Cliente {
    private List<Envio> envios;
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private int dni;

    public Cliente(String id, String nombre, String direccion, String telefono, int dni) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = dni;
        this.envios = new ArrayList<>();
    }

    public int getDni() {
        return dni;
    }

    public void agregarEnvio(Envio envio) {
        envios.add(envio);
    }

    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
                + "]";
    }

    public List<Envio> getEnvios() {
        return Collections.unmodifiableList(envios);
    }

}

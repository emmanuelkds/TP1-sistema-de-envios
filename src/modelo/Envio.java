package modelo;
import java.util.List;  
import java.util.ArrayList;

public abstract class Envio {
    private String id;
    private List<Paquete> paquetes;
    public Envio(String id) {
        this.id = id;
        this.paquetes = new ArrayList<>();
    }
    public void agregarPaquete (Paquete paquete) {
        if (paquetes.size () < 3) {
            paquetes.add(paquete);
        }else {
            System.out.println("El envio ya tiene el maximo de 3 paquetes.");
        }
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }
    public void iniciar() {
        for (Paquete paquete : paquetes) {
            paquete.preparar();
        }
        System.out.println("Envio " + id + " iniciado.");
    }
    public void finalizar() {
        for (Paquete paquete : paquetes) {
            paquete.distribuir();
            paquete.entregar();
        }
        System.out.println("Envio " + id + " finalizado.");
    }
    
    public abstract double calcularCosto();
    
    public String toString() {
        return "Envio [id=" + id + ", costo=" + calcularCosto() + ", paquetes=" + paquetes + "]";
    }
}


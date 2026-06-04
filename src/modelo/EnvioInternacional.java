package modelo;

public class EnvioInternacional extends Envio {
    public EnvioInternacional(String id) {
        super(id);
    }
    //Se sobreescribe el método calcularCosto para aplicar un costo adicional del 100% al costo base de cada paquete.
    @Override
    public double calcularCosto() {
        double costo = 0;
        for (Paquete p : getPaquetes()) {
            costo += p.getPeso() * 100;
        }
        return costo * 2; // Aplicar costo adicional del 100%
    }   

    
}

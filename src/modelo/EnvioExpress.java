package modelo;

public class EnvioExpress extends Envio {
    public EnvioExpress(String id) {
        super(id);
    }
   //Se sobreescribe el método calcularCosto para aplicar un costo adicional del 50% al costo base de cada paquete.
    @Override
    public double calcularCosto() {
        double costo = 0;
        for (Paquete p : getPaquetes()) {
            costo += p.getPeso() * 100;
        }
        return costo * 1.5; // Aplicar costo adicional del 50%
    }
}
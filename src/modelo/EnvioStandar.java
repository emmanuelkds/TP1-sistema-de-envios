package modelo;

public class EnvioStandar extends Envio {
    public EnvioStandar(String id) {
        super(id);
    }
    //Se sobreescribe el método calcularCosto para calcular el costo base de cada paquete sin aplicar ningún costo adicional.
    @Override
    public double calcularCosto() {
        double costo = 0;
        for (Paquete p : getPaquetes()) {
            costo += p.getPeso() * 100;
        }
        return costo; // Costo base sin aplicar ningún costo adicional  
    }
    
}

package modelo;

public class EnvioStandar extends Envio {
    public EnvioStandar(String id, Cliente cliente) {
        super(id, cliente);
    }

    // SE SOBREESCRIBE EL METODO CALCULARCOSTO PARA APLICAR EL COSTO BASE DEL ENVIO,
    // QUE SE CALCULA MULTIPLICANDO EL PESO DE CADA PAQUETE POR 100.
    @Override
    public double calcularCosto() {
        double costo = 0;
        for (Paquete p : getPaquetes()) {
            costo += p.getPeso() * 100;
        }
        return costo; // Costo base sin aplicar ningún costo adicional
    }

}

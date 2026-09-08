package modelo;

public class EnvioExpress extends Envio {
    public EnvioExpress(String id, Cliente cliente) {
        super(id, cliente);
    }

    // SE SOBREESCRIBE EL METODO CALCULARCOSTO PARA APLICAR UN COSTO ADICIONAL DEL
    // 50% AL COSTO BASE DEL ENVIO, QUE SE CALCULA MULTIPLICANDO EL PESO DE CADA
    // PAQUETE POR 100.
    @Override
    public double calcularCosto() {
        double costo = 0;
        for (Paquete p : getPaquetes()) {
            costo += p.getPeso() * 100;
        }
        return costo * 1.5; // Aplicar costo adicional del 50%
    }
}
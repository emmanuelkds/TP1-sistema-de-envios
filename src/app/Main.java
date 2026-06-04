package app;
import java.util.List;
import java.util.ArrayList;
import modelo.*;

public class Main {
    public static void main(String[] args) {
       Envio envioExpress = new EnvioExpress("ENV-007"); 
       envioExpress.agregarPaquete(new Paquete("PQ-001", "Paquete de libros", 10.5));
       envioExpress.agregarPaquete(new Paquete("PQ-002", "Paquete de juguetes", 5.2));
       envioExpress.agregarPaquete(new Paquete("PQ-003", "Paquete de camisas", 8.0));
       envioExpress.iniciar();
       envioExpress.finalizar();
       System.out.println("Costo total: " + envioExpress.calcularCosto());

       Envio envioInternacional = new EnvioInternacional("ENV-008");
       envioInternacional.agregarPaquete(new Paquete("PQ-004", "Paquete de electrónicos", 15.0));
       envioInternacional.agregarPaquete(new Paquete("PQ-005", "Paquete de ropa", 12.0));
       envioInternacional.agregarPaquete(new Paquete("PQ-006", "Paquete de accesorios", 8.0));
       envioInternacional.iniciar();
       envioInternacional.finalizar();
       System.out.println("Costo total: " + envioInternacional.calcularCosto());

       Envio envioStandar = new EnvioStandar("ENV-009");
       envioStandar.agregarPaquete(new Paquete("PQ-007", "Paquete de alimentos", 20.0));
       envioStandar.agregarPaquete(new Paquete("PQ-008", "Paquete de bebidas", 18.0));
       envioStandar.agregarPaquete(new Paquete("PQ-009", "Paquete de medicamentos", 16.0));
       envioStandar.iniciar();
       envioStandar.finalizar();
       System.out.println("Costo total: " + envioStandar.calcularCosto());

       List<Envio> envios = new ArrayList<>();
       envios.add(envioExpress);
       envios.add(envioInternacional);
       envios.add(envioStandar);

       for (Envio envio : envios) {
           System.out.println(envio.toString());
       }
    }
}
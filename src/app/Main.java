package app;

import modelo.Cliente;
import modelo.Paquete;
import modelo.EnvioStandar;
import modelo.EnvioExpress;
import modelo.EnvioInexistenteException;
import modelo.EnvioInternacional;
import modelo.Sucursal;
import modelo.Envio;
import modelo.Movimiento;
import modelo.OperacionInvalidaException;
import modelo.RegistroCentral;

public class Main {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       SISTEMA DE ENVÍOS - PRUEBA");
        System.out.println("======================================");

        // ==========================================
        // 1. CREAR CLIENTES
        // ==========================================

        Cliente cliente1 = new Cliente("C001", "Juan Perez", "Av. Argentina 123", "299-1234567", 43559966);
        Cliente cliente2 = new Cliente("C002", "Maria Gomez", "San Martin 456", "299-7654321", 40111222);
        Cliente cliente3 = new Cliente("C003", "Carlos López", "Calle Falsa 123", "299-9876543", 38999888);

        System.out.println("\n--- CLIENTES ---");
        System.out.println(cliente1);
        System.out.println(cliente2);
        System.out.println(cliente3);

        // ==========================================
        // 2. CREAR PAQUETES
        // ==========================================

        Paquete paquete1 = new Paquete("P001", "Ropa", 2.5);
        Paquete paquete2 = new Paquete("P002", "Electronica", 5.0);
        Paquete paquete3 = new Paquete("P003", "Libros", 3.0);
        Paquete paquete4 = new Paquete("P004", "Juguetes", 4.0);

        System.out.println("\n--- PAQUETES ---");
        System.out.println(paquete1);
        System.out.println(paquete2);
        System.out.println(paquete3);
        System.out.println(paquete4);

        // ==========================================
        // 3. CREAR ENVÍOS
        // ==========================================

        EnvioStandar envioStandar = new EnvioStandar(cliente1);
        EnvioExpress envioExpress = new EnvioExpress(cliente1);
        EnvioInternacional envioInternacional = new EnvioInternacional(cliente2);

        System.out.println("\n--- ENVÍOS CREADOS (ID automático) ---");
        System.out.println("Envío estándar ID: " + envioStandar.getId());
        System.out.println("Envío express ID: " + envioExpress.getId());
        System.out.println("Envío internacional ID: " + envioInternacional.getId());

        // ==========================================
        // 4. AGREGAR PAQUETES
        // ==========================================

        System.out.println("\n--- AGREGANDO PAQUETES ---");

        envioStandar.agregarPaquete(paquete1);
        envioStandar.agregarPaquete(paquete2);
        envioExpress.agregarPaquete(paquete3);
        envioInternacional.agregarPaquete(paquete4);

        System.out.println("Paquetes agregados correctamente.");

        // ==========================================
        // 5. PROBAR LÍMITE DE 3 PAQUETES
        // ==========================================

        System.out.println("\n--- PRUEBA LÍMITE DE PAQUETES ---");

        envioStandar.agregarPaquete(new Paquete("P005", "Zapatos", 2));

        try {
            envioStandar.agregarPaquete(new Paquete("P006", "Campera", 3));
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        // ==========================================
        // 6. MOSTRAR ENVÍOS
        // ==========================================

        System.out.println("\n--- ENVÍOS ---");
        System.out.println(envioStandar);
        System.out.println(envioExpress);
        System.out.println(envioInternacional);

        // ==========================================
        // 7. PROBAR CÁLCULO DE COSTOS
        // ==========================================

        System.out.println("\n--- COSTOS ---");
        System.out.println("Costo envío estándar: $" + envioStandar.calcularCosto());
        System.out.println("Costo envío express: $" + envioExpress.calcularCosto());
        System.out.println("Costo envío internacional: $" + envioInternacional.calcularCosto());

        // ==========================================
        // 8. CREAR SUCURSALES
        // ==========================================

        Sucursal sucursalAllen = new Sucursal("S001", "Allen", "Av. Roca 789", "299-1111111");
        Sucursal sucursalCipolletti = new Sucursal("S002", "Cipolletti", "Av. San Martin 321", "299-2222222");
        Sucursal sucursalNeuquen = new Sucursal("S003", "Neuquén", "Av. Argentina 456", "299-3333333");

        System.out.println("\n--- SUCURSALES ---");
        System.out.println(sucursalAllen);
        System.out.println(sucursalCipolletti);
        System.out.println(sucursalNeuquen);

        // ==========================================
        // 9. REGISTRO CENTRAL
        // ==========================================

        System.out.println("\n--- REGISTRO CENTRAL ---");

        RegistroCentral registro = new RegistroCentral();

        // Registrar clientes
        registro.registrarCliente(cliente1);
        registro.registrarCliente(cliente2);
        registro.registrarCliente(cliente3);

        // Registrar sucursales
        registro.registrarSucursal(sucursalAllen);
        registro.registrarSucursal(sucursalCipolletti);
        registro.registrarSucursal(sucursalNeuquen);

        // Registrar envíos
        registro.registrarEnvio(envioStandar);
        registro.registrarEnvio(envioExpress);
        registro.registrarEnvio(envioInternacional);

        // ==========================================
        // 10. RECIBIR ENVÍOS EN SUCURSAL
        // ==========================================

        System.out.println("\n--- RECIBIENDO ENVÍOS EN ALLEN ---");

        sucursalAllen.recibirEnvio(envioStandar);
        sucursalAllen.recibirEnvio(envioExpress);

        System.out.println("Envíos en espera en Allen: " + sucursalAllen.cantidadEnviosEnEspera());

        System.out.println("\n--- RECIBIENDO ENVÍO EN CIPOLLETTI ---");
        sucursalCipolletti.recibirEnvio(envioInternacional);

        // ==========================================
        // 11. BUSCAR ENVÍO POR CÓDIGO
        // ==========================================

        System.out.println("\n--- BUSQUEDA POR CÓDIGO ---");
        buscar(registro, envioStandar.getId());
        buscar(registro, 999); // No existe

        // ==========================================
        // 12. DESPACHAR ENVÍOS
        // ==========================================

        System.out.println("\n--- DESPACHANDO ENVÍOS DE ALLEN (capacidad 1) ---");
        sucursalAllen.despachar(1);

        System.out.println("Envíos en espera en Allen después de despachar: " + sucursalAllen.cantidadEnviosEnEspera());

        // ==========================================
        // 13. RECIBIR ENVÍO DESPACHADO EN OTRA SUCURSAL
        // ==========================================

        System.out.println("\n--- RECIBIENDO ENVÍO EN CIPOLLETTI ---");
        sucursalCipolletti.recibirEnvio(envioStandar);

        // ==========================================
        // 14. DESPACHAR EL RESTO DE ALLEN
        // ==========================================

        System.out.println("\n--- DESPACHANDO EL RESTO DE ALLEN (capacidad 5) ---");
        sucursalAllen.despachar(5);

        // ==========================================
        // 15. ENTREGAR ENVÍO EN DESTINO
        // ==========================================

        System.out.println("\n--- ENTREGANDO ENVÍO INTERNACIONAL EN CIPOLLETTI ---");
        entregar(sucursalCipolletti, envioInternacional);

        // ==========================================
        // 16. MOSTRAR HISTORIALES
        // ==========================================

        System.out.println("\n--- HISTORIAL DE MOVIMIENTOS ---");
        mostrarHistorial(envioStandar);
        mostrarHistorial(envioExpress);
        mostrarHistorial(envioInternacional);

        System.out.println("\n--- ÚLTIMO MOVIMIENTO DE CADA ENVÍO ---");
        System.out.println("Último movimiento de envío estándar: " + envioStandar.getUltimoMovimiento());
        System.out.println("Último movimiento de envío express: " + envioExpress.getUltimoMovimiento());
        System.out.println("Último movimiento de envío internacional: " + envioInternacional.getUltimoMovimiento());

        // ==========================================
        // 17. PRUEBAS DE EXCEPCIONES
        // ==========================================

        System.out.println("\n--- VALIDAR RESTRICCIONES ---");

        // Intentar despachar un envío que ya no está en la sucursal
        try {
            sucursalNeuquen.despachar(1); // La cola de Neuquén está vacía
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        // Intentar registrar un envío duplicado
        System.out.println("\n--- INTENTAR REGISTRAR ENVÍO DUPLICADO ---");
        registro.registrarEnvio(envioStandar); // Debería fallar

        // Intentar agregar paquete a un envío ya despachado
        System.out.println("\n--- AGREGAR PAQUETE A ENVÍO YA DESPACHADO ---");
        try {
            envioStandar.agregarPaquete(new Paquete("P008", "Tablet", 1.5));
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        // Búsqueda secuencial de cliente por DNI
        System.out.println("\n--- BÚSQUEDA DE CLIENTE POR DNI ---");
        Cliente encontrado = registro.buscarClienteSecuencial(43559966);
        if (encontrado != null) {
            System.out.println("Cliente encontrado: " + encontrado);
        }
        registro.buscarClienteSecuencial(99999999); // No existe

        System.out.println("\n======================================");
        System.out.println("       FIN DE LA PRUEBA");
        System.out.println("======================================");
    }

    // ==================================================================
    // METODOS AUXILIARES DEL MAIN
    // ==================================================================

    private static void mostrarHistorial(Envio envio) {
        System.out.println("Historial de movimientos del envio " + envio.getId() + ":");
        for (Movimiento movimiento : envio.getHistorial()) {
            System.out.println(movimiento);
        }
    }

    private static void recibir(Sucursal sucursal, Envio envio) {
        sucursal.recibirEnvio(envio);
        System.out.println("Envio " + envio.getId() + " recibido en la sucursal " + sucursal.getLocalidad() + ".");
    }

    private static void entregar(Sucursal sucursal, Envio envio) {
        sucursal.entregarEnvio(envio);
        System.out.println("Envio " + envio.getId() + " entregado en la sucursal " + sucursal.getLocalidad() + ".");
    }

    private static void buscar(RegistroCentral registro, int codigo) {
        try {
            Envio envio = registro.buscarEnvio(codigo);
            if (envio != null) {
                Movimiento ultimo = envio.getUltimoMovimiento();
                String estado = (ultimo == null)
                        ? "sin movimientos"
                        : ultimo.getTipoMovimiento() + " en " + ultimo.getSucursal().getLocalidad();
                System.out.println("Envío " + envio.getId() + " | paquetes: " + envio.getPaquetes().size()
                        + " | último movimiento: " + estado);
            }
        } catch (EnvioInexistenteException e) {
            System.out.println(e.getMessage());
        }
    }
}
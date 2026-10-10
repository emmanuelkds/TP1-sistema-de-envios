package app;

import modelo.Cliente;
import modelo.Paquete;
import modelo.EnvioStandar;
import modelo.EnvioExpress;
import modelo.EnvioInternacional;
import modelo.Sucursal;
import modelo.Envio;
import modelo.Movimiento;
import modelo.OperacionInvalidaException;

public class Main {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("       SISTEMA DE ENVÍOS - PRUEBA");
        System.out.println("======================================");

        // ==========================================
        // 1. CREAR CLIENTES
        // ==========================================

        Cliente cliente1 = new Cliente(
                "C001",
                "Juan Perez",
                "Av. Argentina 123",
                "299-1234567");

        Cliente cliente2 = new Cliente(
                "C002",
                "Maria Gomez",
                "San Martin 456",
                "299-7654321");

        System.out.println("\n--- CLIENTES ---");
        System.out.println(cliente1);
        System.out.println(cliente2);

        // ==========================================
        // 2. CREAR PAQUETES
        // ==========================================

        Paquete paquete1 = new Paquete(
                "P001",
                "Ropa",
                2.5);

        Paquete paquete2 = new Paquete(
                "P002",
                "Electronica",
                5.0);

        Paquete paquete3 = new Paquete(
                "P003",
                "Libros",
                3.0);

        Paquete paquete4 = new Paquete(
                "P004",
                "Juguetes",
                4.0);

        System.out.println("\n--- PAQUETES ---");
        System.out.println(paquete1);
        System.out.println(paquete2);
        System.out.println(paquete3);
        System.out.println(paquete4);

        // ==========================================
        // 3. CREAR ENVÍOS
        // ==========================================

        EnvioStandar envioStandar = new EnvioStandar(
                "E001",
                cliente1);

        EnvioExpress envioExpress = new EnvioExpress(
                "E002",
                cliente1);

        EnvioInternacional envioInternacional = new EnvioInternacional(
                "E003",
                cliente2);

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

        // Este debería mostrar el mensaje de máximo 3
        try {
            envioStandar.agregarPaquete(new Paquete("P006", "Campera", 3));
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        try {
            envioStandar.agregarPaquete(new Paquete("P007", "Celular", 1));
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
        // 8. CREAR SUCURSAL
        // ==========================================

        Sucursal sucursalAllen = new Sucursal(
                "S001",
                "Allen",
                "Av. Roca 789",
                "299-1111111");

        Sucursal sucursalCipolletti = new Sucursal(
                "S002",
                "Cipolletti",
                "Av. San Martin 321",
                "299-2222222");
        Sucursal sucursalNeuquen = new Sucursal(
                "S003",
                "Neuquén",
                "Av. Argentina 456",
                "299-3333333");

        System.out.println("\n--- SUCURSAL ---");
        System.out.println(sucursalAllen);
        System.out.println(sucursalCipolletti);
        System.out.println(sucursalNeuquen);

        // ==========================================
        // 9. RECIBIR ENVÍOS EN SUCURSAL
        // ==========================================

        System.out.println("\n--- RECIBIENDO ENVÍOS ---");

        recibir(sucursalAllen, envioStandar);
        recibir(sucursalAllen, envioExpress);
        recibir(sucursalCipolletti, envioInternacional);

        // ==========================================
        // 10. MOSTRAR ESTADO DE LOS PAQUETES
        // ==========================================

        System.out.println("\n--- DESPUÉS DE RECIBIR ---");

        System.out.println(paquete1);
        System.out.println(paquete2);
        System.out.println(paquete3);
        System.out.println(paquete4);

        // EL APP CONSULTA LOS ENVIOS DE LA SUCURSAL Y LOS MUESTRA
        System.out.println("Envios en la sucursal " + sucursalAllen.getLocalidad() + ": "
                + sucursalAllen.getEnviosEnSucursal());

        // ==========================================
        // 11. DESPACHAR ENVÍOS
        // ==========================================

        System.out.println("\n--- DESPACHANDO ENVÍOS ---");

        despachar(sucursalAllen, envioStandar);
        despachar(sucursalAllen, envioExpress);
        despachar(sucursalCipolletti, envioInternacional);

        // ==========================================
        // 12. RECIBIR ENVIO EN OTRA SUCURSAL
        // ==========================================

        System.out.println("\n--- RECIBIENDO ENVÍOS EN OTRA SUCURSAL ---");

        recibir(sucursalCipolletti, envioStandar);
        recibir(sucursalCipolletti, envioExpress);
        recibir(sucursalNeuquen, envioInternacional);

        // ===========================================
        // 13. MOSTRAR ESTADO DE LOS PAQUETES DESPUÉS DE RECIBIR EN OTRA SUCURSAL.
        // ===========================================

        System.out.println("\n--- DESPUÉS DE RECIBIR EN OTRA SUCURSAL ---");

        System.out.println(paquete1);
        System.out.println(paquete2);
        System.out.println(paquete3);
        System.out.println(paquete4);

        // ==========================================
        // 14. DESPACHAR ENVÍOS EN OTRA SUCURSAL
        // ==========================================

        System.out.println("\n--- DESPACHANDO ENVÍOS EN OTRA SUCURSAL ---");

        despachar(sucursalCipolletti, envioStandar);
        despachar(sucursalCipolletti, envioExpress);

        // ============================================
        // 15. ENTREGAR ENVÍOS EN UNA SUCURSAL
        // ============================================

        System.out.println("\n--- ENTREGANDO ENVÍOS EN UNA SUCURSAL ---");

        entregar(sucursalNeuquen, envioInternacional);

        // ==========================================
        // 16. RECIBIR ENVÍOS EN UNA TERCERA SUCURSAL
        // ==========================================

        System.out.println("\n--- RECIBIR ENVÍOS EN UNA TERCERA SUCURSAL ---");

        recibir(sucursalNeuquen, envioStandar);
        recibir(sucursalNeuquen, envioExpress);

        // ===========================================
        // 17. ENTREGAR ENVÍOS EN OTRA SUCURSAL
        // ===========================================

        System.out.println("\n--- ENTREGANDO ENVÍOS EN OTRA SUCURSAL ---");

        entregar(sucursalNeuquen, envioStandar);
        entregar(sucursalNeuquen, envioExpress);

        System.out.println("\n--- HISTORIAL DE MOVIMIENTOS ---");

        // EL APP PIDE EL HISTORIAL COMO DATO Y ES EL APP QUIEN LO MUESTRA
        mostrarHistorial(envioStandar);
        mostrarHistorial(envioExpress);
        mostrarHistorial(envioInternacional);

        System.out.println("\n--- SUCURSALES EN HISTORIAL DE ENVÍOS ---");

        System.out.println("Sucursales en historial de envío estándar: " + envioStandar.getSucursalesEnHistorial());
        System.out.println("Sucursales en historial de envío express: " + envioExpress.getSucursalesEnHistorial());
        System.out.println(
                "Sucursales en historial de envío internacional: " + envioInternacional.getSucursalesEnHistorial());

        System.out.println("\n--- ÚLTIMO MOVIMIENTO DE CADA ENVÍO ---");

        System.out.println("Último movimiento de envío estándar: " + envioStandar.getUltimoMovimiento());
        System.out.println("Último movimiento de envío express: " + envioExpress.getUltimoMovimiento());
        System.out.println("Último movimiento de envío internacional: " + envioInternacional.getUltimoMovimiento());

        // ==========================================
        // 18. VALIDAR RESTRICCIONES DE ESTADO DE ENVÍO
        // ==========================================

        System.out.println("\n--- VALIDAR RESTRICCIONES DE ESTADO DE ENVÍO ---");

        // Intentar despachar un paquete ya entregado
        try {
            envioStandar.despachar(sucursalNeuquen);
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- NUEVO CLIENTE ---");
        // Creo un cliente, envio y paquete extra para probar el despachar sin recibir
        Cliente cliente3 = new Cliente(
                "C003",
                "Carlos López",
                "Calle Falsa 123",
                "299-9876543");

        System.out.println(cliente3);

        System.out.println("\n--- NUEVO PAQUETE ---");

        Paquete paqueteExtra = new Paquete("P005", "Zapatos", 2);

        System.out.println(paqueteExtra);

        System.out.println("\n--- NUEVO ENVÍO ---");

        EnvioStandar envioExtra = new EnvioStandar("E004", cliente3);

        envioExtra.agregarPaquete(paqueteExtra);

        System.out.println(envioExtra);

        System.out.println("\n--- DESPACHAR SIN RECIBIR ---");

        // INTENTAR DESPACHAR EL ENVÍO SIN HABERLO RECIBIDO EN LA SUCURSAL
        try {
            despachar(sucursalAllen, envioExtra);
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- RECIBIR ENVÍO ---");

        // RECIBIR EL ENVÍO EN LA SUCURSAL
        try {
            recibir(sucursalAllen, envioExtra);
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- INTENTAR RECIBIR ENVÍO NUEVAMENTE ---");

        // INTENTAR RECIBIR EL ENVÍO NUEVAMENTE
        try {
            recibir(sucursalAllen, envioExtra);
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- DESPACHAR ENVÍO ---");

        // DESPACHAR EL ENVÍO
        try {
            despachar(sucursalAllen, envioExtra);
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- INTENTO DE AGREGAR PAQUETE A UN ENVIO YA DESPACHADO ---");

        try {
            envioExtra.agregarPaquete(new Paquete("P006", "Campera", 3));
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- INTENTO DE ENTREGAR UN PAQUETE QUE NO FUE DESPACHADO ---");

        // UN PAQUETE RECIEN CREADO ESTA EN ESTADO RECIBIDO: TODAVIA NO SALIO DE
        // LA SUCURSAL, POR LO TANTO NO PUEDE ENTREGARSE.
        Paquete paqueteSinDespachar = new Paquete("P007", "Reloj", 1);

        try {
            paqueteSinDespachar.entregar();
        } catch (OperacionInvalidaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n======================================");
        System.out.println("       FIN DE LA PRUEBA");
        System.out.println("======================================");
    }

    // EL APP ES EL UNICO RESPONSABLE DE IMPRIMIR EN CONSOLA: LE PIDE EL DATO AL
    // ENVIO Y LO RECORRE. EL ENVIO NO SABE NADA DE LA CONSOLA.
    private static void mostrarHistorial(Envio envio) {
        System.out.println("Historial de movimientos del envio " + envio.getId() + ":");
        for (Movimiento movimiento : envio.getHistorial()) {
            System.out.println(movimiento);
        }
    }

    // ==================================================================
    // METODOS DEL APP
    // EL APP OPERA A TRAVES DE LAS SUCURSALES Y DESPUES MUESTRA EL
    // RESULTADO CON LOS DATOS QUE LE DEVUELVEN LOS OBJETOS DEL MODELO.
    // ==================================================================

    private static void recibir(Sucursal sucursal, Envio envio) {
        sucursal.recibirEnvio(envio);
        System.out.println("Envio " + envio.getId() + " recibido en la sucursal " + sucursal.getLocalidad() + ".");
    }

    private static void despachar(Sucursal sucursal, Envio envio) {
        sucursal.despacharEnvio(envio);
        System.out.println("Envio " + envio.getId() + " despachado desde la sucursal " + sucursal.getLocalidad() + ".");
    }

    private static void entregar(Sucursal sucursal, Envio envio) {
        sucursal.entregarEnvio(envio);
        System.out.println("Envio " + envio.getId() + " entregado en la sucursal " + sucursal.getLocalidad() + ".");
    }
}

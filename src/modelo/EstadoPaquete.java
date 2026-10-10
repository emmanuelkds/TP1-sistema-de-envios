package modelo;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum EstadoPaquete {
    RECIBIDO,
    EN_PREPARACION,
    EN_DISTRIBUCION,
    ENTREGADO;

    // TABLA DE TRANSICIONES VALIDAS: CADA ESTADO DECLARA HACIA QUE ESTADOS PUEDE
    // AVANZAR. LA REGLA VIVE EN EL PROPIO ESTADO, DECLARADA COMO DATO, SIN
    // CADENAS DE IF/ELSE NI SWITCH, Y NINGUN OTRO OBJETO NECESITA CONOCERLA.
    private static final Map<EstadoPaquete, Set<EstadoPaquete>> TRANSICIONES_VALIDAS = new EnumMap<>(
            EstadoPaquete.class);

    static {
        // UN PAQUETE RECIEN CREADO SE PREPARA EN LA SUCURSAL DONDE ESTA.
        TRANSICIONES_VALIDAS.put(RECIBIDO, EnumSet.of(EN_PREPARACION));
        // PREPARADO PUEDE SALIR EN REPARTO O ENTREGARSE EN ESA MISMA SUCURSAL.
        TRANSICIONES_VALIDAS.put(EN_PREPARACION, EnumSet.of(EN_DISTRIBUCION, ENTREGADO));
        // DESPACHADO PUEDE ENTREGARSE O VOLVER A PREPARARSE SI LLEGA A OTRA
        // SUCURSAL, Y ASI EL PAQUETE PUEDE RECORRER VARIAS SUCURSALES.
        TRANSICIONES_VALIDAS.put(EN_DISTRIBUCION, EnumSet.of(EN_PREPARACION, ENTREGADO));
        // UN PAQUETE ENTREGADO ES UN ESTADO FINAL: NO AVANZA A NINGUN OTRO.
        TRANSICIONES_VALIDAS.put(ENTREGADO, EnumSet.noneOf(EstadoPaquete.class));
    }

    // INDICA SI DESDE ESTE ESTADO SE PUEDE PASAR AL ESTADO DESTINO.
    public boolean puedeAvanzarA(EstadoPaquete destino) {
        return getSiguientes().contains(destino);
    }

    // ESTADOS ALCANZABLES DESDE ESTE ESTADO, DE SOLO LECTURA PARA QUE NADIE LOS
    // MODIFIQUE DESDE AFUERA.
    public Set<EstadoPaquete> getSiguientes() {
        Set<EstadoPaquete> siguientes = TRANSICIONES_VALIDAS.get(this);
        if (siguientes == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(siguientes);
    }
}
package modelo;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum TipoMovimiento {
    RECIBIDO,
    DESPACHADO,
    ENTREGADO;

    // TABLA DE COHERENCIA: CADA MOVIMIENTO DECLARA DESPUES DE QUE ETAPAS PUEDE
    // REGISTRARSE. LA REGLA VIVE EN EL PROPIO TIPO DE MOVIMIENTO, DECLARADA COMO
    // DATO, SIN CADENAS DE IF/ELSE NI SWITCH.
    private static final Map<TipoMovimiento, Set<TipoMovimiento>> ETAPAS_ANTERIORES_VALIDAS = new EnumMap<>(
            TipoMovimiento.class);

    static {
        // UN ENVIO SE RECIBE AL PRINCIPIO (SIN ETAPA ANTERIOR) O DESPUES DE HABER
        // SIDO DESPACHADO DESDE OTRA SUCURSAL.
        ETAPAS_ANTERIORES_VALIDAS.put(RECIBIDO, EnumSet.of(DESPACHADO));
        // SOLO SE DESPACHA LO QUE ACABA DE LLEGAR A UNA SUCURSAL.
        ETAPAS_ANTERIORES_VALIDAS.put(DESPACHADO, EnumSet.of(RECIBIDO));
        // SOLO SE ENTREGA LO QUE ESTA EN UNA SUCURSAL.
        ETAPAS_ANTERIORES_VALIDAS.put(ENTREGADO, EnumSet.of(RECIBIDO));
    }

    // INDICA SI ESTE MOVIMIENTO ES COHERENTE DESPUES DE LA ETAPA INDICADA. UNA
    // ETAPA ANTERIOR NULA SIGNIFICA QUE EL ENVIO TODAVIA NO TIENE MOVIMIENTOS.
    public boolean puedeRegistrarseDespuesDe(TipoMovimiento etapaAnterior) {
        if (etapaAnterior == null) {
            return puedeSerElPrimero();
        }
        return getEtapasAnterioresValidas().contains(etapaAnterior);
    }

    // UNICAMENTE LA RECEPCION PUEDE SER EL PRIMER MOVIMIENTO DE UN ENVIO.
    public boolean puedeSerElPrimero() {
        return this == RECIBIDO;
    }

    // ETAPAS DESPUES DE LAS CUALES ESTE MOVIMIENTO ES VALIDO, DE SOLO LECTURA.
    public Set<TipoMovimiento> getEtapasAnterioresValidas() {
        Set<TipoMovimiento> etapas = ETAPAS_ANTERIORES_VALIDAS.get(this);
        if (etapas == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(etapas);
    }
}
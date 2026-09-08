package modelo;

import java.time.LocalDateTime;

public class Movimiento {
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private Sucursal sucursal;

    public Movimiento(TipoMovimiento tipo, Sucursal sucursal) {
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.sucursal = sucursal;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public String toString() {
        return "Movimiento [fecha=" + fecha + ", tipo=" + tipo + ", sucursal=" + sucursal + "]";
    }
}

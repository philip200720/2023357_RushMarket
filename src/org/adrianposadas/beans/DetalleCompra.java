package org.adrianposadas.beans;

public class DetalleCompra {
    private int detalleCompraId;
    private double costoUnitario;
    private int cantidad;
    private int productoId;
    private int compraId;

    public DetalleCompra() {
    }

    public DetalleCompra(int detalleCompraId, double costoUnitario, int cantidad, int productoId, int compraId) {
        this.detalleCompraId = detalleCompraId;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.productoId = productoId;
        this.compraId = compraId;
    }

    public int getDetalleCompraId() {
        return detalleCompraId;
    }

    public void setDetalleCompraId(int detalleCompraId) {
        this.detalleCompraId = detalleCompraId;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }
}

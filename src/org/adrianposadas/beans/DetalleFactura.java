package org.adrianposadas.beans;


public class DetalleFactura {
    private int detalleFacturaId;
    private double precioUnitario;
    private int cantidad;
    private int facturaId;
    private int productoId;

    public DetalleFactura() {
    }

    public DetalleFactura(int detalleFacturaId, double precioUnitario, int cantidad, int facturaId, int productoId) {
        this.detalleFacturaId = detalleFacturaId;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.facturaId = facturaId;
        this.productoId = productoId;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}

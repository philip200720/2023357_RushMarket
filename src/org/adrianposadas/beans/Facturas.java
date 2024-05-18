package org.adrianposadas.beans;

import java.time.LocalDate;

public class Facturas {
    private int facturaId;
    private String estado;
    private double totalFactura;
    private LocalDate fechaFactura;
    private int codigoCliente;
    private int empleadoId;

    public Facturas() {
    }
    
    public Facturas(int facturaId, String estado, double totalFactura, LocalDate fechaFactura, int codigoCliente, int empleadoId) {
        this.facturaId = facturaId;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.empleadoId = empleadoId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
}

package org.adrianposadas.beans;

public class TelefonoProveedor {
    private int telefonoProveedorId;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int codigoProveedor;

    public TelefonoProveedor() {
    }

    public TelefonoProveedor(int telefonoProveedorId, String numeroPrincipal, String numeroSecundario, String observaciones, int codigoProveedor) {
        this.telefonoProveedorId = telefonoProveedorId;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.codigoProveedor = codigoProveedor;
    }

    public int getTelefonoProveedorId() {
        return telefonoProveedorId;
    }

    public void setTelefonoProveedorId(int telefonoProveedorId) {
        this.telefonoProveedorId = telefonoProveedorId;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
    
    
}

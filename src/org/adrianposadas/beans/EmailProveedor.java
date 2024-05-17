package org.adrianposadas.beans;

public class EmailProveedor {
    private int emailProveedorId;
    private String emailProveedor;
    private String descripcion;
    private int codigoProveedor;

    public EmailProveedor() {
    }

    public EmailProveedor(int emailProveedorId, String emailProveedor, String descripcion, int codigoProveedor) {
        this.emailProveedorId = emailProveedorId;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    public int getEmailProveedorId() {
        return emailProveedorId;
    }

    public void setEmailProveedorId(int emailProveedorId) {
        this.emailProveedorId = emailProveedorId;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
}

package org.adrianposadas.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.adrianposadas.system.Main;

/* Herencia multiple concepto, interfaz. POO
*/
public class MenuPrincipalController implements Initializable{
    private Main escenarioPrincipal;
    @FXML private  MenuItem btnClientes;
    @FXML private MenuItem btnSalir;
    @FXML private MenuItem btnProgramador;
    @FXML private MenuItem btnProveedores;
    @FXML private MenuItem btnTelefonoProveedor;
    @FXML private MenuItem btnTipoProducto;
    @FXML private MenuItem btnCargoEmpleado;
    @FXML private MenuItem btnEmailProveedor;
    @FXML private MenuItem btnCompras;
    @FXML private MenuItem btnProductos;
    @FXML private MenuItem btnDetalleCompra;
    @FXML private MenuItem btnEmpleados;
    @FXML private MenuItem btnFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clickPrincipalController (ActionEvent event){
        if (event.getSource() == btnClientes){
            escenarioPrincipal.menuClientesView();
        }else if (event.getSource() == btnSalir){
            System.exit(0);
        }else if (event.getSource() == btnProgramador){
            escenarioPrincipal.programadorView();
        }else if (event.getSource() == btnProveedores){
            escenarioPrincipal.menuProveedoresView();
        }else if (event.getSource() == btnTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }else if (event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoView();
        }else if (event.getSource() == btnTelefonoProveedor){
            escenarioPrincipal.menuTelefonoProveedorView();
        }else if (event.getSource() == btnEmailProveedor){
            escenarioPrincipal.menuEmailProveedorView();
        }else if (event.getSource() == btnCompras){
            escenarioPrincipal.menuCompraView();
        }else if (event.getSource() == btnProductos){
            escenarioPrincipal.menuProductoView();
        }else if (event.getSource() == btnDetalleCompra){
            escenarioPrincipal.menuDetalleCompraView();
        }else if (event.getSource() == btnEmpleados){
            escenarioPrincipal.menuEmpleadosView();
        }else if (event.getSource() == btnFactura){
            escenarioPrincipal.menuFacturaView();
        }
    }
}

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
    @FXML private MenuItem btnTipoProducto;
    @FXML private MenuItem btnCargoEmpleado;

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
        }
    }
}

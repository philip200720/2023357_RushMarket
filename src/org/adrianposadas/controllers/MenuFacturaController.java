package org.adrianposadas.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.adrianposadas.report.GenerarReporte;
import org.adrianposadas.system.Main;

public class MenuFacturaController implements Initializable{
    @FXML
    private Button btnHome;
    @FXML
    private Button btnReportes;
    private Main escenarioPrincipal;
    
    public void reportes(){
                imprimirReporte();
     }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("facturaId", null);
        GenerarReporte.mostrarReportes("reporteFacturas.jasper", "Reporte de Factura", parametros);
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void home(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}

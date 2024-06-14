/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adrianposadas.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.adrianposadas.beans.Clientes;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.report.GenerarReporte;
import org.adrianposadas.system.Main;

public class MenuClientesController implements Initializable {

    private Main escenarioClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList<Clientes> listaClientes;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarCliente;
    @FXML
    private Button btnEditarCliente;
    @FXML
    private Button btnEliminarCliente;
    @FXML
    private Button btnReportesCliente;
    @FXML
    private TextField txtCodigoCliente;
    @FXML
    private TextField txtNitCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtTelefonoCliente;
    @FXML
    private TextField txtCorreoCliente;
    @FXML
    private ImageView imgAgregarIcono;
    @FXML
    private ImageView imgEditarIcono;
    @FXML
    private ImageView imgEliminarIcono;
    @FXML
    private ImageView imgReportesIcono;
    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colNitCliente;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colApellidoCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colTelefonoCliente;
    @FXML
    private TableColumn colCorreoCliente;
    @FXML
    private TableView tblClientes;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNitCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitCliente"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperacion) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnAgregarCliente.setText("Guardar");
                btnEliminarCliente.setText("Cancelar");
                btnEditarCliente.setDisable(true);
                btnReportesCliente.setDisable(true);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                imgEditarIcono.setOpacity(0.5);
                imgReportesIcono.setOpacity(0.5);
                tipoOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminarCliente.setText("Eliminar");
                btnEditarCliente.setDisable(false);
                btnReportesCliente.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                imgEditarIcono.setOpacity(1);
                imgReportesIcono.setOpacity(1);
                tipoOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void editar(){
        switch (tipoOperacion){
            case NINGUNO:
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    seleccionarElemento();
                    btnEditarCliente.setText("Actualizar");
                    btnReportesCliente.setText("Cancelar");
                    btnEliminarCliente.setDisable(true);
                    btnAgregarCliente.setDisable(true);
                    imgEditarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportesIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    imgAgregarIcono.setOpacity(0.5);
                    imgEliminarIcono.setOpacity(0.5);
                    activarControles();
                    txtCodigoCliente.setEditable(false);
                    tipoOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarCliente.setText("Editar");
                btnReportesCliente.setText("Reportes");
                btnAgregarCliente.setDisable(false);
                btnEliminarCliente.setDisable(false);
                imgEditarIcono.setImage(new Image("/org/adrianposadas/images/editar.png"));
                imgReportesIcono.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                imgAgregarIcono.setOpacity(1);
                imgEliminarIcono.setOpacity(1);
                tipoOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
     public void eliminar(){
        switch(tipoOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCliente.setText("Agregar");
                btnEliminarCliente.setText("Eliminar");
                btnEditarCliente.setDisable(false);
                btnReportesCliente.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                imgEditarIcono.setOpacity(1);
                imgReportesIcono.setOpacity(1);
                tipoOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    int ans = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este registro?" + "\n" + "Se eliminará todos los registros relacionados.",
                            "Elminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(ans == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            limpiarControles();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
     
     public void reportes(){
         switch(tipoOperacion){
             case NINGUNO:
                imprimirReporte();
                break;
             case ACTUALIZAR:
                 desactivarControles();
                 limpiarControles();
                 btnEditarCliente.setText("Editar");
                 btnReportesCliente.setText("Reportes");
                 btnAgregarCliente.setDisable(false);
                 btnEliminarCliente.setDisable(false);
                 imgEditarIcono.setImage(new Image("/org/adrianposadas/images/editar.png"));
                 imgReportesIcono.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                 imgAgregarIcono.setOpacity(1);
                 imgEliminarIcono.setOpacity(1);
                 tipoOperacion = operaciones.NINGUNO;
                 break;
         }
     }
     
     public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReporte.mostrarReportes("ReporteCliente.jasper", "Reporte de Clientes", parametros);
    }
     
     public void guardar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?)}");
            Clientes registro = new Clientes();
            registro.setNitCliente(txtNitCliente.getText());
            registro.setNombreCliente(txtNombreCliente.getText());
            registro.setApellidoCliente(txtApellidoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());
            procedimiento.setString(1, registro.getNitCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getCorreoCliente());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
            registro.setCodigoCliente(generatedKeys.getInt(1));
            }
            listaClientes.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNitCliente(txtNitCliente.getText());
            registro.setNombreCliente(txtNombreCliente.getText());
            registro.setApellidoCliente(txtApellidoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void seleccionarElemento (){
        txtCodigoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNitCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNitCliente());
        txtNombreCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtDireccionCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
    }

    public void desactivarControles() {
        txtCodigoCliente.setEditable(false);
        txtNitCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }
    
    public void activarControles() {
        txtNitCliente.setEditable(true);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoCliente.clear();
        txtNitCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtDireccionCliente.clear();
        txtTelefonoCliente.clear();
        txtCorreoCliente.clear();
    }

    public void setEscenarioPrincipal(Main escenarioClientes) {
        this.escenarioClientes = escenarioClientes;
    }

    @FXML
    public void clickClientesController(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioClientes.menuPrincipalView();
        }

    }
}
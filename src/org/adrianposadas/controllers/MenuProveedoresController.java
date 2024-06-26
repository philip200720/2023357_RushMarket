package org.adrianposadas.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
import org.adrianposadas.beans.Proveedores;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.report.GenerarReporte;
import org.adrianposadas.system.Main;

public class MenuProveedoresController implements Initializable{
    
    private Main escenarioClientes;
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList<Proveedores> listaProveedores;
    
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarProveedor;
    @FXML
    private Button btnEliminarProveedor;
    @FXML
    private Button btnEditarProveedor;
    @FXML
    private Button btnReportesProveedor;
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TextField txtNitProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtApellidoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
    @FXML
    private ImageView imgAgregarIcono;
    @FXML
    private ImageView imgEliminarIcono;
    @FXML
    private ImageView imgEditarIcono;
    @FXML
    private ImageView imgReportesIcono;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colNitProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colApellidoProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoPrincipal;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableView tblProveedores;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    public void agregar() {
        switch (tipoOperacion) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Guardar");
                btnEliminarProveedor.setText("Cancelar");
                btnEditarProveedor.setDisable(true);
                btnReportesProveedor.setDisable(true);
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
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
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
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    seleccionarElemento();
                    btnEditarProveedor.setText("Actualizar");
                    btnReportesProveedor.setText("Cancelar");
                    btnEliminarProveedor.setDisable(true);
                    btnAgregarProveedor.setDisable(true);
                    imgEditarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportesIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    imgAgregarIcono.setOpacity(0.5);
                    imgEliminarIcono.setOpacity(0.5);
                    activarControles();
                    txtCodigoProveedor.setEditable(false);
                    tipoOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarProveedor.setText("Editar");
                btnReportesProveedor.setText("Reportes");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
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
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReportesProveedor.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                imgEditarIcono.setOpacity(1);
                imgReportesIcono.setOpacity(1);
                tipoOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int ans = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este registro?" + "\n" + "Se eliminará todos los registros relacionados.",
                            "Elminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(ans == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            limpiarControles();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                        } catch (SQLIntegrityConstraintViolationException e) {
                            JOptionPane.showMessageDialog(null, "No puedes eliminar un registro que está siendo referenciado");
                        } catch (SQLException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Se produjo un error: " + e.getMessage());
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
                 btnEditarProveedor.setText("Editar");
                 btnReportesProveedor.setText("Reportes");
                 btnAgregarProveedor.setDisable(false);
                 btnEliminarProveedor.setDisable(false);
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
        parametros.put("codigoProveedor", null);
        GenerarReporte.mostrarReportes("reporteProveedor.jasper", "Reporte de Proveedores", parametros);
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNitProveedor(txtNitProveedor.getText());
            registro.setNombreProveedor(txtNombreProveedor.getText());
            registro.setApellidoProveedor(txtApellidoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoPrincipal.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void guardar(){
        Proveedores registro = new Proveedores();
        registro.setNitProveedor(txtNitProveedor.getText());
        registro.setNombreProveedor(txtNombreProveedor.getText());
        registro.setApellidoProveedor(txtApellidoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoPrincipal.getText());
        registro.setContactoPrincipal(txtPaginaWeb.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNitProveedor());
            procedimiento.setString(2, registro.getNombreProveedor());
            procedimiento.setString(3, registro.getApellidoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.setString(6, registro.getContactoPrincipal());
            procedimiento.setString(7, registro.getPaginaWeb());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
            registro.setCodigoProveedor(generatedKeys.getInt(1));
            }
            listaProveedores.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarElemento (){
        txtCodigoProveedor.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNitProveedor());
        txtNombreProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
        txtApellidoProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocial.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoPrincipal.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWeb.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    public void desactivarControles() {
        txtCodigoProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtNombreProveedor.setEditable(false);
        txtApellidoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }
    
    public void activarControles() {
        txtNitProveedor.setEditable(true);
        txtNombreProveedor.setEditable(true);
        txtApellidoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoProveedor.clear();
        txtNitProveedor.clear();
        txtNombreProveedor.clear();
        txtApellidoProveedor.clear();
        txtDireccionProveedor.clear();
        txtRazonSocial.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
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

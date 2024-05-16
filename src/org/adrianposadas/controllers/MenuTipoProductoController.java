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
import org.adrianposadas.beans.TipoProducto;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

/**
 *
 * @author adria
 */
public class MenuTipoProductoController implements Initializable{
    private Main escenarioClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList<TipoProducto> listaTipoProducto;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarTipoProducto;
    @FXML
    private Button btnEditarTipoProducto;
    @FXML
    private Button btnEliminarTipoProducto;
    @FXML
    private Button btnReportesTipoProducto;
    @FXML
    private TextField txtCodigoTipoProducto;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ImageView imgAgregarIcono;
    @FXML
    private ImageView imgEditarIcono;
    @FXML
    private ImageView imgEliminarIcono;
    @FXML
    private ImageView imgReportesIcono;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableView tblTipoProducto;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoOperacion) {
            case NINGUNO:
                activarControles();
                btnAgregarTipoProducto.setText("Guardar");
                btnEliminarTipoProducto.setText("Cancelar");
                btnEditarTipoProducto.setDisable(true);
                btnReportesTipoProducto.setDisable(true);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                tipoOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eliminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                tipoOperacion = operaciones.NINGUNO;
                break;
        }
    }
    public void editar(){
        switch (tipoOperacion){
            case NINGUNO:
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                    btnEditarTipoProducto.setText("Actualizar");
                    btnReportesTipoProducto.setText("Cancelar");
                    btnEliminarTipoProducto.setDisable(true);
                    btnAgregarTipoProducto.setDisable(true);
                    imgEditarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportesIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    activarControles();
                    txtCodigoTipoProducto.setEditable(false);
                    tipoOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarTipoProducto.setText("Editar");
                btnReportesTipoProducto.setText("Reportes");
                btnAgregarTipoProducto.setDisable(false);
                btnEliminarTipoProducto.setDisable(false);
                imgEditarIcono.setImage(new Image("/org/adrianposadas/images/editar.png"));
                imgReportesIcono.setImage(new Image("/org/adrianposadas/images/reportes.png"));
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
                btnAgregarTipoProducto.setText("Agregar");
                btnEliminarTipoProducto.setText("Eliminar");
                btnEditarTipoProducto.setDisable(false);
                btnReportesTipoProducto.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                tipoOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                    int ans = JOptionPane.showConfirmDialog(null, "Confirmar eliminacion",
                            "Elminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(ans == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            limpiarControles();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                        }catch (Exception e){
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
             case ACTUALIZAR:
                 desactivarControles();
                 limpiarControles();
                 btnEditarTipoProducto.setText("Editar");
                 btnReportesTipoProducto.setText("Reportes");
                 btnAgregarTipoProducto.setDisable(false);
                 btnEliminarTipoProducto.setDisable(false);
                 imgEditarIcono.setImage(new Image("/org/adrianposadas/images/editar.png"));
                 imgReportesIcono.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                 tipoOperacion = operaciones.NINGUNO;
                 break;
         }
         
     }
     
     public void guardar(){
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTipoProducto.getText()));
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarTipoProducto(?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarTipoProducto(?, ?)}");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void seleccionarElemento (){
        txtCodigoTipoProducto.setText(String.valueOf(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcion.setText(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion());
    }

    public void desactivarControles() {
        txtCodigoTipoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles() {
        txtCodigoTipoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoTipoProducto.clear();
        txtDescripcion.clear();
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

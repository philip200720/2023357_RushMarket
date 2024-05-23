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
import org.adrianposadas.beans.CargoEmpleado;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

/**
 *
 * @author adria
 */
public class MenuCargoEmpleadoController implements Initializable{
    private Main escenarioClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList<CargoEmpleado> listaClientes;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarCargoEmpleado;
    @FXML
    private Button btnEditarCargoEmpleado;
    @FXML
    private Button btnEliminarCargoEmpleado;
    @FXML
    private Button btnReportesCargoEmpleado;
    @FXML
    private TextField txtCodigoCargoEmpleado;
    @FXML
    private TextField txtNombreCargoEmpleado;
    @FXML
    private TextField txtDescripcionCargoEmpleado;
    @FXML
    private ImageView imgAgregarIcono;
    @FXML
    private ImageView imgEditarIcono;
    @FXML
    private ImageView imgEliminarIcono;
    @FXML
    private ImageView imgReportesIcono;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
    @FXML
    private TableColumn colNombreCargo;
    @FXML
    private TableColumn colDescripcionCargo;
    @FXML
    private TableView tblCargoEmpleado;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCargoEmpleado.setItems(getClientes());
        colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }

    public ObservableList<CargoEmpleado> getClientes() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
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
                btnAgregarCargoEmpleado.setText("Guardar");
                btnEliminarCargoEmpleado.setText("Cancelar");
                btnEditarCargoEmpleado.setDisable(true);
                btnReportesCargoEmpleado.setDisable(true);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                tipoOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCargoEmpleado.setText("Agregar");
                btnEliminarCargoEmpleado.setText("Eliminar");
                btnEditarCargoEmpleado.setDisable(false);
                btnReportesCargoEmpleado.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                tipoOperacion = operaciones.NINGUNO;
                break;
        }
    }
    public void editar(){
        switch (tipoOperacion){
            case NINGUNO:
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem() != null){
                    btnEditarCargoEmpleado.setText("Actualizar");
                    btnReportesCargoEmpleado.setText("Cancelar");
                    btnEliminarCargoEmpleado.setDisable(true);
                    btnAgregarCargoEmpleado.setDisable(true);
                    imgEditarIcono.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportesIcono.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    activarControles();
                    txtCodigoCargoEmpleado.setEditable(false);
                    tipoOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarCargoEmpleado.setText("Editar");
                btnReportesCargoEmpleado.setText("Reportes");
                btnAgregarCargoEmpleado.setDisable(false);
                btnEliminarCargoEmpleado.setDisable(false);
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
                btnAgregarCargoEmpleado.setText("Agregar");
                btnEliminarCargoEmpleado.setText("Eliminar");
                btnEditarCargoEmpleado.setDisable(false);
                btnReportesCargoEmpleado.setDisable(false);
                imgAgregarIcono.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminarIcono.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                tipoOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem() != null){
                    int ans = JOptionPane.showConfirmDialog(null, "Confirmar eliminacion",
                            "Elminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(ans == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            limpiarControles();
                            listaClientes.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
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
                 btnEditarCargoEmpleado.setText("Editar");
                 btnReportesCargoEmpleado.setText("Reportes");
                 btnAgregarCargoEmpleado.setDisable(false);
                 btnEliminarCargoEmpleado.setDisable(false);
                 imgEditarIcono.setImage(new Image("/org/adrianposadas/images/editar.png"));
                 imgReportesIcono.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                 tipoOperacion = operaciones.NINGUNO;
                 break;
         }
         
     }
     
     public void guardar(){
        CargoEmpleado registro = new CargoEmpleado();
        registro.setNombreCargo(txtNombreCargoEmpleado.getText());
        registro.setDescripcionCargo(txtDescripcionCargoEmpleado.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarCargoEmpleado(?, ?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.setString(2, registro.getDescripcionCargo());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
            registro.setCodigoCargoEmpleado(generatedKeys.getInt(1));
            }
            listaClientes.add(registro);
            cargarDatos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarCargoEmpleado(?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCargoEmpleado.getText());
            registro.setDescripcionCargo(txtDescripcionCargoEmpleado.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     public void seleccionarElemento (){
        txtCodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCargoEmpleado.setText(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCargoEmpleado.setText(((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }

    public void desactivarControles() {
        txtCodigoCargoEmpleado.setEditable(false);
        txtNombreCargoEmpleado.setEditable(false);
        txtDescripcionCargoEmpleado.setEditable(false);
    }
    
    public void activarControles() {
        txtNombreCargoEmpleado.setEditable(true);
        txtDescripcionCargoEmpleado.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoCargoEmpleado.clear();
        txtNombreCargoEmpleado.clear();
        txtDescripcionCargoEmpleado.clear();
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

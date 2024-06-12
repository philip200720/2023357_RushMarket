package org.adrianposadas.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.adrianposadas.beans.Productos;
import org.adrianposadas.beans.Proveedores;
import org.adrianposadas.beans.TipoProducto;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

public class MenuProductosController implements Initializable{
    private Main escenarioPrincipal;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList <Productos> listaProducto;
    private ObservableList <Proveedores> listaProveedor;
    private ObservableList <TipoProducto> listaTipoProducto;
    @FXML private TextField txtProductoId;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox cmbProveedor;
    @FXML private ComboBox cmbTipoProducto;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colDescProd;
    @FXML private TableColumn colProductoId;
    @FXML private TableColumn colDescripcionProducto; 
    @FXML private TableColumn colPrecioUnitario; 
    @FXML TableView tblProductos; 
    @FXML TableView tblCompras; 
    @FXML private TableColumn colCodProv; 
    @FXML private Button btnAgregar; 
    @FXML private Button btnEliminar; 
    @FXML private Button btnModificar; 

    
    @Override
public void initialize(URL location, ResourceBundle resources) {
}
public void cargarDatos() {
    tblProductos.setItems(getProductos());

    colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigProducto"));
    colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
    colPreciol.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
    colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
    colMayor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("precioMayor"));
}

private void seleccionarElementos() {
    txtProductoId.setText(((Productos)cbProductos.getSelectedItem()).getIdProducto().toString());
    txtDescripcion.setText(((Productos)cbProductos.getSelectedItem()).getDescripcionProducto());
    txtPrecio.setText(String.valueOf(((Productos)cbProductos.getSelectedItem()).getPrecioUnitario()));
    txtPrecioD.setText(String.valueOf(((Productos)cbProductos.getSelectedItem()).getPrecioCosto()));
    txtExistencia.setText(String.valueOf(((Productos)cbProductos.getSelectedItem()).getExistencia()));
}


    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{aaaaaaaaaa}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                s
            }
        } catch (SQLException ex) {
            
        }
        return null;
    }
    
    public TipoProducto buscarTipoProducto(int codigoTipoProducto){
        TipoProducto reultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{aaaaaaaaaaa}");
            procedimiento.setInt(1, codigoTipoProducto);
            
        }
    }
    
    public void desactivarControles(){
    txtProductoId.setEditable(false);
    txtDescripcion.setEditable(false);
    txtPrecio.setEditable(false);
    txtExistencia.setEditable(false);
    cmbTipoProducto.setDisable(true);
    }

    public void activarControles(){
        txtProductoId.setEditable(true);
        txtDescripcion.setEditable(true);
        txtPrecio.setEditable(true);
        txtExistencia.setEditable(true);
        cmbTipoProducto.setDisable(false); 
    }
}

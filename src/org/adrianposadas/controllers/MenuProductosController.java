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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.adrianposadas.beans.Productos;
import org.adrianposadas.beans.Proveedores;
import org.adrianposadas.beans.TipoProducto;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

public class MenuProductosController implements Initializable{
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private Main escenarioPrincipal;
    private ObservableList <TipoProducto> listaTipoProducto;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    private Button btnHome;
    @FXML
    private TextField txtProductoId;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ComboBox cmbProveedor;
    @FXML
    private ComboBox cmbTipoProducto;
    @FXML
    private TableColumn colProductoId;
    @FXML
    private TableColumn colDescripcionProducto;
    @FXML
    private TableColumn colPrecioUnitario;
    @FXML
    private TableColumn colPrecioDocena;
    @FXML
    private TableColumn colPrecioMayor;
    @FXML
    private TableColumn colImagenProducto;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colCodigoTipoProducto;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReportes;
    @FXML
    private TableView tblProductos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbProveedor.setItems(getProveedores());
        cmbTipoProducto.setItems(getTipoProducto());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblProductos.setItems(getProductos());
        colProductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("productoId"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("nitProveedor"),
                        registro.getString("nombreProveedor"),
                        registro.getString("apellidoProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_BuscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getInt("productoId"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoProveedor"),
                        resultado.getInt("codigoTipoDeProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
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
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("guardar");
                btnEliminar.setText("cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                imgEditar.setOpacity(0.5);
                imgReportes.setOpacity(0.5);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                imgEditar.setOpacity(1);
                imgReportes.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setImagenProducto("[Sin imagen]");
        registro.setCodigoProveedor(((Proveedores) cmbProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarProductos(?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getDescripcionProducto());
            procedimiento.setString(2, registro.getImagenProducto());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.setInt(4, registro.getCodigoTipoProducto());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
            registro.setProductoId(generatedKeys.getInt(1));
            }
            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/adrianposadas/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/adrianposadas/images/eliminar.png"));
                imgEditar.setOpacity(1);
                imgReportes.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este registro?" + "\n" + "Se eliminará todos los registros relacionados.", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarProducto(?)}");
                            procedimiento.setInt(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
                            procedimiento.execute();
                            limpiarControles();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    seleccionarElemento();
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportes.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtProductoId.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/adrianposadas/images/editar.png"));
                imgReportes.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                imgAgregar.setOpacity(1);
                imgEliminar.setOpacity(1);
                desactivarControles();
                limpiarControles();
                cargarDatos();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void actualizar() {
        
        try {
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarProducto (?, ?, ?, ?)}");
            registro.setProductoId(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setImagenProducto("[Sin imagen]");
            registro.setCodigoProveedor(((Proveedores) cmbProveedor.getValue()).getCodigoProveedor());
            registro.setCodigoTipoProducto(((Proveedores) cmbTipoProducto.getValue()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getProductoId());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.setInt(4, registro.getCodigoTipoProducto());
            procedimiento.execute();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/adrianposadas/images/editar.png"));
                imgReportes.setImage(new Image("/org/adrianposadas/images/reportes.png"));
                imgAgregar.setOpacity(1);
                imgEliminar.setOpacity(1);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void seleccionarElemento() {
        txtProductoId.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId()));
        txtDescripcion.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        cmbProveedor.getSelectionModel().select(buscarProveedor(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        cmbTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtProductoId.setEditable(false);
        txtDescripcion.setEditable(false);
        cmbProveedor.setDisable(true);
        cmbTipoProducto.setDisable(true);
    }

    public void activarControles() {
        txtDescripcion.setEditable(true);
        cmbProveedor.setDisable(false);
        cmbTipoProducto.setDisable(false);
    }

    public void limpiarControles() {
        txtProductoId.clear();
        txtDescripcion.clear();
        cmbProveedor.getSelectionModel().getSelectedItem();
        cmbTipoProducto.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void Home(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }

    }
}

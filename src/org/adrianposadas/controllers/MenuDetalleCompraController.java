package org.adrianposadas.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
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
import org.adrianposadas.beans.Compras;
import org.adrianposadas.beans.DetalleCompra;
import org.adrianposadas.beans.Productos;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

public class MenuDetalleCompraController  implements Initializable{
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private Main escenarioPrincipal;
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Compras> listaCompras;
    
    @FXML
    private Button btnHome;
    @FXML
    private TextField txtDetalleCompraId;
    @FXML
    private TextField txtCostoUnitario;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox cmbProductoId;
    @FXML
    private ComboBox cmbCompraId;
    @FXML
    private TableColumn colDetalleCompraId;
    @FXML
    private TableColumn colCostoUnitario;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colProductoId;
    @FXML
    private TableColumn colCompraId;
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
    private TableView tblDetalleCompra;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbProductoId.setItems(getProductos());
        cmbCompraId.setItems(getCompras());
    }
    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void cargarDatos() {
        tblDetalleCompra.setItems(getDetalleCompra());
        colDetalleCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("detalleCompraId"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("productoId"));
        colCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("compraId"));
    }
    
    public void seleccionarElemento() {
        txtDetalleCompraId.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getDetalleCompraId()));
        txtCostoUnitario.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbProductoId.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getProductoId()));
        cmbCompraId.getSelectionModel().select(buscarCompras(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCompraId()));
    }
    
    public Productos buscarProducto(int productoId) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, productoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getInt("productoId"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoProveedor"),
                        registro.getInt("codigoTipoDeProducto")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
        
        public Compras buscarCompras(int compraId) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, compraId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("compraId"),
                        registro.getDate("fechaCompra").toLocalDate(),
                        registro.getString("descripcion"),
                        registro.getDouble("totalCompra")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<Compras>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(
                        resultado.getInt("compraId"),
                        resultado.getDate("fechaCompra").toLocalDate(),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalCompra")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                        resultado.getInt("detalleCompraId"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("productoId"),
                        resultado.getInt("compraId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalleCompra = FXCollections.observableArrayList(lista);
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
                        resultado.getInt("codigoTipoDeProducto"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
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
        DetalleCompra registro = new DetalleCompra();
        registro.setProductoId(((Productos) cmbProductoId.getSelectionModel().getSelectedItem()).getProductoId());
        registro.setCompraId(((Compras) cmbCompraId.getSelectionModel().getSelectedItem()).getCompraId());
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarDetalleCompra(?, ?, ?, ?)}");
            procedimiento.setDouble(1, registro.getCostoUnitario());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getProductoId());
            procedimiento.setInt(4, registro.getCompraId());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
                registro.setDetalleCompraId(generatedKeys.getInt(1));
            }
            listaDetalleCompra.add(registro);
            cargarDatos();
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "La compra ya existe");
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar eliminacion", "Eliminar Detalle Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarDetalleCompra (?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getDetalleCompraId());
                            procedimiento.execute();
                            limpiarControles();
                            listaDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/adrianposadas/images/guardar.png"));
                    imgReportes.setImage(new Image("/org/adrianposadas/images/cancelar.png"));
                    imgAgregar.setOpacity(0.5);
                    imgEliminar.setOpacity(0.5);
                    activarControles();
                    txtDetalleCompraId.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarDetalleCompra (?, ?, ?, ?, ?)}");
            DetalleCompra registro = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
            double costoUnitario = Double.parseDouble(txtCostoUnitario.getText());
            registro.setCostoUnitario(costoUnitario);
            int cantidad = Integer.parseInt(txtCantidad.getText());
            registro.setCantidad(cantidad);
            procedimiento.setInt(1, registro.getDetalleCompraId());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getProductoId());
            procedimiento.setInt(5, registro.getCompraId());
            procedimiento.execute();
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

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtDetalleCompraId.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbProductoId.setDisable(true);
        cmbCompraId.setDisable(true);
    }

    public void activarControles() {
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbProductoId.setDisable(false);
        cmbCompraId.setDisable(false);
    }

    public void limpiarControles() {
        txtDetalleCompraId.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        cmbProductoId.getSelectionModel().getSelectedItem();
        cmbCompraId.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void home(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    } 
}

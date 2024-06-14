package org.adrianposadas.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.adrianposadas.beans.Compras;
import org.adrianposadas.db.Conexion;
import org.adrianposadas.system.Main;

public class MenuCompraController implements Initializable{
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> listaCompras;
    private Main escenarioPrincipal;

    @FXML
    private Button btnHome;
    @FXML
    private TextField txtCompraId;
    @FXML
    private DatePicker datepFechaCompra;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTotalCompra;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colCompraId;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colFechaCompra;
    @FXML
    private TableColumn colTotalCompra;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaCompra"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalCompra"));

    }

    public void seleccionarElemento() {
        txtCompraId.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getCompraId()));
        datepFechaCompra.setValue((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaCompra()));
        txtDescripcion.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalCompra.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalCompra()));
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
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
                break;
        }
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setDescripcion(txtDescripcion.getText());
        registro.setFechaCompra(datepFechaCompra.getValue());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_AgregarCompras (?, ?)}");
            procedimiento.setDate(1, java.sql.Date.valueOf(registro.getFechaCompra()));
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            ResultSet generatedKeys = procedimiento.getGeneratedKeys();
            if (generatedKeys.next()) {
            registro.setCompraId(generatedKeys.getInt(1));
            }
            listaCompras.add(registro);
            cargarDatos();
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este registro?" + "\n" + "Se eliminará todos los registros relacionados.",
                            "Eliminar compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EliminarCompras (?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getCompraId());
                            procedimiento.execute();
                            limpiarControles();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento!");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
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
                    txtCompraId.setEditable(false);
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void actualizar() {
        try {
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setFechaCompra(datepFechaCompra.getValue());
            registro.setDescripcion(txtDescripcion.getText());
            PreparedStatement procedimiento = Conexion.getInstance().getConnection().prepareCall("{call sp_EditarCompras (?, ?, ?)}");
            procedimiento.setInt(1, registro.getCompraId());
            procedimiento.setDate(2, java.sql.Date.valueOf(registro.getFechaCompra()));
            procedimiento.setString(3, registro.getDescripcion());
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

    public void desactivarControles() {
        txtCompraId.setEditable(false);
        txtDescripcion.setEditable(false);
        datepFechaCompra.setEditable(false);
        txtTotalCompra.setEditable(false);
    }

    public void activarControles() {
        txtDescripcion.setEditable(true);
        datepFechaCompra.setEditable(true);
    }

    public void limpiarControles() {
        txtCompraId.clear();
        txtDescripcion.clear();
        datepFechaCompra.setValue(null);
        txtTotalCompra.clear();
        
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
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}

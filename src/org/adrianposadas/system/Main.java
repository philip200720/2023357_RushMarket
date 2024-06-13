/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adrianposadas.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.adrianposadas.controllers.MenuCargoEmpleadoController;
import org.adrianposadas.controllers.MenuClientesController;
import org.adrianposadas.controllers.MenuCompraController;
import org.adrianposadas.controllers.MenuEmailProveedorController;
import org.adrianposadas.controllers.MenuPrincipalController;
import org.adrianposadas.controllers.MenuProductosController;
import org.adrianposadas.controllers.MenuProveedoresController;
import org.adrianposadas.controllers.MenuTelefonoProveedorController;
import org.adrianposadas.controllers.MenuTipoProductoController;
import org.adrianposadas.controllers.ProgramadorController;

/**
 *
 * @author adria
 */
public class Main extends Application {
    private final String URLVIEW = "/org/adrianposadas/views/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Rush Market");
        menuPrincipalView();
        escenarioPrincipal.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws IOException{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        escena = new Scene((AnchorPane)loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1000, 686);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 956, 537);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void programadorView() {
        try{
            ProgramadorController programadorView = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 984, 562);
            programadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProveedoresView(){
        try{
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController)cambiarEscena("MenuProveedoresView.fxml", 1021, 575);
            menuProveedoresView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTelefonoProveedorView(){
        try{
            MenuTelefonoProveedorController menuTelefonoProveedorView = (MenuTelefonoProveedorController)cambiarEscena("MenuTelefonoProveedorView.fxml", 1280, 723);
            menuTelefonoProveedorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductoView(){
        try{
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController)cambiarEscena("MenuTipoProductoView.fxml", 984, 553);
            menuTipoProductoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuCargoEmpleadoView(){
        try{
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleadoView.fxml", 984, 553);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmailProveedorView(){
        try{
            MenuEmailProveedorController menuEmailProveedorView = (MenuEmailProveedorController)cambiarEscena("MenuEmailProveedorView.fxml", 1260, 704);
            menuEmailProveedorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuCompraView(){
        try{
            MenuCompraController menuCompraView = (MenuCompraController)cambiarEscena("MenuCompraView.fxml", 1132, 634);
            menuCompraView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProductoView(){
        try{
            MenuProductosController menuProductosView = (MenuProductosController)cambiarEscena("MenuProductosView.fxml", 1275, 718);
            menuProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

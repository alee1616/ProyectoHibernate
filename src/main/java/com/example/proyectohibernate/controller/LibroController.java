package com.example.proyectohibernate.controller;

import com.example.proyectohibernate.dao.LibroDAO;
import com.example.proyectohibernate.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class LibroController {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<Libro, Integer> colAnio;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, Long> colId;

    @FXML
    private TableColumn<Libro, BigDecimal> colPrecio;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private TableView<Libro> tableLibros;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtTitulo;


    private final LibroDAO libroDAO = new LibroDAO();
    private Long idSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        cargarLibros();
    }

    //Permite  controlar la selección y visualización de los botones de FXML
    private void actualizarBotones() {
       boolean sinSeleccion = idSeleccionado == null;
       btnModificar.setDisable(sinSeleccion);
       btnEliminar.setDisable(sinSeleccion);
       btnGuardar.setDisable(!sinSeleccion);

    }
    private Libro obtenerLibroFormulario() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        int anioPublicacion = Integer.parseInt(txtAnio.getText());
        BigDecimal precio = new BigDecimal(txtPrecio.getText());
        return new Libro(
                titulo, autor, anioPublicacion, precio
        );
    }

    @FXML
    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            Libro libro = new Libro(titulo, autor, anio, BigDecimal.valueOf(precio));
            libroDAO.guardar(libro);
            cargarLibros();

            txtTitulo.clear();
            txtAutor.clear();
            txtAnio.clear();
            txtPrecio.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarLibros() {
        try {
            List<Libro> listaDao = libroDAO.listar();
            ObservableList<Libro> libros = FXCollections.observableArrayList(listaDao);
            tableLibros.setItems(libros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarLibro(ActionEvent actionEvent) {
        Libro libroSeleccionado = tableLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {
            try {
                libroDAO.eliminar(libroSeleccionado.getId());
                cargarLibros();
                limpiarCampos(actionEvent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void limpiarCampos(ActionEvent actionEvent) {
    }


    public void modificarLibro() {
        if(idSeleccionado == null){
            return;
        }

        try {
            Libro libro = obtenerLibroFormulario();
            libro.setId(idSeleccionado);
            boolean modificado = libroDAO.modificar(libro);
            if (!modificado) {
                //Alerta sobre la trasaccion no realizado
                return;
            }
            //Alerta de sis se realizo la operacion
            cargarLibros();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
package com.example.proyectohibernate.controller;

import com.example.proyectohibernate.dao.LibroDAO;
import com.example.proyectohibernate.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Libro, String> colPrecio;

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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        cargarLibros();
    }

    @FXML
    private void guardarLibros() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double precio = Double.parseDouble(txtPrecio.getText());

            Libro libro = new Libro(titulo, autor, anio, String.valueOf(precio));
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
}
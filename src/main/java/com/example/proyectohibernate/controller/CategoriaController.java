package com.example.proyectohibernate.controller;

import com.example.proyectohibernate.dao.CategoriaDAO;
import com.example.proyectohibernate.model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CategoriaController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Categoria> tableCategorias;

    @FXML
    private TableColumn<Categoria, Long> colId;

    @FXML
    private TableColumn<Categoria, String> colNombre;

    @FXML
    private TableColumn<Categoria, String> colDescripcion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Long idSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        cargarCategorias();

        tableCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idSeleccionado = newValue.getId();
                txtNombre.setText(newValue.getNombre());
                txtDescripcion.setText(newValue.getDescripcion());
            } else {
                idSeleccionado = null;
            }
        });
    }

    @FXML
    private void cargarCategorias() {
        try {
            List<Categoria> listaDao = categoriaDAO.listar();
            ObservableList<Categoria> categorias = FXCollections.observableArrayList(listaDao);
            tableCategorias.setItems(categorias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarCategoria(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();

            if (nombre.isEmpty()) {
                return;
            }

            Categoria categoria = new Categoria(nombre, descripcion);
            categoriaDAO.guardar(categoria);

            cargarCategorias();
            limpiarCampos(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void limpiarCampos(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        idSeleccionado = null;
        tableCategorias.getSelectionModel().clearSelection();
    }

    @FXML
    public void eliminarCategoria(ActionEvent event) {
        if (idSeleccionado == null) {
            return;
        }

        try {
            boolean eliminado = categoriaDAO.eliminar(idSeleccionado);
            if (!eliminado) {
                return;
            }

            cargarCategorias();
            limpiarCampos(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modificarCategoria(ActionEvent event) {
        if (idSeleccionado == null) {
            return;
        }

        try {
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();

            Categoria categoria = new Categoria(nombre, descripcion);
            categoria.setId(idSeleccionado);

            boolean modificado = categoriaDAO.modificar(categoria);
            if (!modificado) {
                return;
            }

            cargarCategorias();
            limpiarCampos(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
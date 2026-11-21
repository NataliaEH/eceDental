package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.File;
import java.io.Serializable;

import javafx.stage.FileChooser;
import mx.eceDental.entity.RegistroOdontograma;
import mx.eceDental.helper.HelperArchivo;
import mx.eceDental.entity.Archivo;

import java.util.List;

@Named("archivoUI")
@ViewScoped
public class BeanArchivo{
    private HelperArchivo helperArchivo;
    private Archivo archivo;
    private File archivoSeleccionado;

    public BeanArchivo() {
        helperArchivo = new HelperArchivo();
    }

    @PostConstruct
    public void init() {
        archivo = new Archivo();
    }

    public boolean registrar() {
        if (archivoSeleccionado == null) {
            return false;
        }
        boolean registrado = helperArchivo.registrar(archivo.getExpediente().getIdExpediente(), archivo.getNombre(), archivo.getTipo(), archivoSeleccionado);
        archivoSeleccionado = null;
        return registrado;
    }

    public boolean eliminar(int id) {
        return helperArchivo.eliminar(id);
    }

    public boolean descargar(int id) {
        Archivo a = helperArchivo.buscar(id);
        if (a == null || a.getRuta() == null) {
            return false;
        }

        String extension = "";
        int punto = a.getRuta().lastIndexOf('.');
        if (punto >= 0) {
            extension = a.getRuta().substring(punto);
        }

        FileChooser selector = new FileChooser();
        selector.setInitialFileName(a.getNombre().replaceAll("[\\\\/:*?\"<>|]", "_") + extension);
        File destino = selector.showSaveDialog(null);
        if (destino == null) {
            return false;
        }
        return helperArchivo.descargar(id,destino);
    }
    public boolean abrirArchivo(int id) { return helperArchivo.abrirArchivo(id); }

    public List<Archivo> buscarPorTipo(String tipo) {
        return helperArchivo.buscarPorTipo(tipo);
    }
    public List<Archivo> consultar(int idExpediente) {
        return helperArchivo.consultar(idExpediente);
    }
    public Archivo buscar(int id){
        return helperArchivo.buscar(id);
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public String seleccionarArchivo() {
        FileChooser selector = new FileChooser();
        File elegido = selector.showOpenDialog(null);
        if (elegido == null) {
            return "";
        }
        archivoSeleccionado = elegido;
        return elegido.getName();
    }
}
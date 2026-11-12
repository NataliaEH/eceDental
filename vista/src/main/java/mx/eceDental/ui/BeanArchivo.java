package mx.eceDental.ui;

import jakarta.annotation.PostConstruct;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import mx.eceDental.helper.HelperArchivo;
import mx.eceDental.entity.Archivo;

import java.util.List;

@Named("archivoUI")
@ViewScoped
public class BeanArchivo implements Serializable {
    private HelperArchivo helperArchivo;
    private Archivo archivo;

    public BeanArchivo() {
        helperArchivo = new HelperArchivo();
    }

    @PostConstruct
    public void init() {
        archivo = new Archivo();
    }

    public boolean registrar() {
        boolean registrado = helperArchivo.registrar(archivo);
        return registrado;
    }

    public boolean eliminar(int id) {
        return helperArchivo.eliminar(id);
    }

    public boolean descargar(int id) {
        return helperArchivo.descargar(id);
    }

    public List<Archivo> buscarPorTipo(String tipo) {
        return helperArchivo.buscarPorTipo(tipo);
    }

    public List<Archivo> consultar(int idExpediente) {
        return helperArchivo.consultar(idExpediente);
    }

    public Archivo getArchivo() {
        return archivo;
    }
}
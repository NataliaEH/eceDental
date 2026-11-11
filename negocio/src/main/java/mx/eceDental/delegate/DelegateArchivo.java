package mx.eceDental.delegate;

import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DelegateArchivo {


    public boolean registrar(int idExpediente, String nombreArchivo, String tipo, String ruta) {

        if (idExpediente <= 0
                || nombreArchivo == null || nombreArchivo.trim().isEmpty()
                || nombreArchivo.trim().length() > 100
                || tipo == null || tipo.trim().isEmpty()
                || tipo.trim().length() > 30
                || ruta == null || ruta.trim().isEmpty()
                || ruta.trim().length() > 255) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            Archivo archivo = new Archivo();
            archivo.setNombreArchivo(nombreArchivo.trim());
            archivo.setFecha(LocalDate.now());
            archivo.setTipo(tipo.trim());
            archivo.setRuta(ruta.trim());
            archivo.setExpediente(expediente);

            ServiceLocator.getInstanceArchivoDAO().save(archivo);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            Archivo archivo = ServiceLocator.getInstanceArchivoDAO().find(id).orElse(null);

            if (archivo == null) {
                return false;
            }

            ServiceLocator.getInstanceArchivoDAO().delete(archivo);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean descargar(int id) {
        if (id <= 0) {
            return false;
        }

        try {
            Archivo archivo = ServiceLocator.getInstanceArchivoDAO().find(id).orElse(null);

            if (archivo == null || archivo.getRuta() == null || archivo.getRuta().trim().isEmpty()) {
                return false;
            }

            File fisico = new File(archivo.getRuta());

            if (fisico.exists() && fisico.isFile()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }


    public List<Archivo> buscarPorTipo(String tipo) {
        List<Archivo> resultado = new ArrayList<>();

        if (tipo == null || tipo.trim().isEmpty()) {
            return resultado;
        }

        try {
            List<Archivo> todos = ServiceLocator.getInstanceArchivoDAO().findAll();

            for (Archivo archivo : todos) {
                if (archivo.getTipo() != null
                        && archivo.getTipo().equalsIgnoreCase(tipo.trim())) {
                    resultado.add(archivo);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    public List<Archivo> consultar(int idExpediente) {
        List<Archivo> resultado = new ArrayList<>();

        if (idExpediente <= 0) {
            return resultado;
        }

        try {
            List<Archivo> todos = ServiceLocator.getInstanceArchivoDAO().findAll();

            for (Archivo archivo : todos) {
                if (archivo.getExpediente() != null
                        && archivo.getExpediente().getIdExpediente() == idExpediente) {
                    resultado.add(archivo);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
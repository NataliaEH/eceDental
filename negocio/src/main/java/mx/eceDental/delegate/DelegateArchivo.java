package mx.eceDental.delegate;

import mx.eceDental.entity.Archivo;
import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DelegateArchivo {


    public boolean registrar(int idExpediente, String nombre, String tipo, File archivoOriginal) {
        if (idExpediente <= 0
                || nombre == null || nombre.trim().isEmpty()
                || nombre.trim().length() > 100
                || tipo == null || tipo.trim().isEmpty()
                || tipo.trim().length() > 30
                || archivoOriginal == null || archivoOriginal.length() == 0){
            return false;
        }
        Path rutaDestino = null;
        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                System.out.println("expediente null false");
                return false;
            }

            //CREAR RUTA PARA COPIAR EL ARCHIVO AHI
            String extension = "";
            String nombreOriginal = archivoOriginal.getName();
            int punto = nombreOriginal.lastIndexOf('.');
            if (punto >= 0) {
                extension = nombreOriginal.substring(punto);
            }

            String nombreArchivo = UUID.randomUUID() + extension;
            Path carpetaBase = Paths.get(System.getProperty("user.home"), "eceDental");
            Path carpeta = carpetaBase.resolve(Paths.get("archivos", String.valueOf(idExpediente)));
            Files.createDirectories(carpeta);

            rutaDestino = carpeta.resolve(nombreArchivo);
            Files.copy(archivoOriginal.toPath(), rutaDestino);
            String rutaRelativa = carpetaBase.relativize(rutaDestino).toString();
            System.out.println("before archivo");
            Archivo archivo = new Archivo();
            archivo.setNombre(nombre.trim());
            archivo.setFecha(LocalDate.now());
            archivo.setTipo(tipo.trim());
            archivo.setRuta(rutaRelativa);
            archivo.setExpediente(expediente);
            System.out.println("before save");
            ServiceLocator.getInstanceArchivoDAO().save(archivo);
            return true;
        } catch (Exception e) {
            if (rutaDestino != null) {
                try {
                    Files.deleteIfExists(rutaDestino);
                } catch (IOException ex) {
                    return false;
                }
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        if (id <= 0)
            return false;

        try {
            Archivo archivo = ServiceLocator.getInstanceArchivoDAO().find(id).orElse(null);

            if (archivo == null) {
                return false;
            }
            Expediente exp = archivo.getExpediente();
            if (exp != null && exp.getArchivo() != null) {
                exp.getArchivo().removeIf(d -> d.getIdArchivo() == id);
                ServiceLocator.getInstanceExpedienteDAO().update(exp);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean descargar(int id, File destino) {
        if (id <= 0 || destino == null) {
            return false;
        }
        try {
            Archivo archivo = ServiceLocator.getInstanceArchivoDAO().find(id).orElse(null);
            if (archivo == null || archivo.getRuta() == null || archivo.getRuta().trim().isEmpty()) {
                return false;
            }
            Path origen = Paths.get(System.getProperty("user.home"), "eceDental").resolve(archivo.getRuta());
            if (!Files.exists(origen) || !Files.isRegularFile(origen)) {
                return false;
            }
            byte[] bytes = Files.readAllBytes(origen);
            Files.write(destino.toPath(), bytes);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean abrirArchivo(int id){
        if (id <= 0) {
            System.out.println("abrir: id invalido " + id);
            return false;
        }

        try {
            Archivo archivo = ServiceLocator.getInstanceArchivoDAO().find(id).orElse(null);

            if (archivo == null || archivo.getRuta() == null || archivo.getRuta().trim().isEmpty()) {
                System.out.println("abrir: archivo o ruta null");
                return false;
            }

            Path carpetaBase = Paths.get(System.getProperty("user.home"), "eceDental");
            Path rutaCompleta = carpetaBase.resolve(archivo.getRuta());
            System.out.println("abrir: ruta = " + rutaCompleta);
            if (!Files.exists(rutaCompleta) || !Files.isRegularFile(rutaCompleta)) {
                System.out.println("abrir: el archivo no existe en disco");
                return false;
            }
            if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                System.out.println("abrir: Desktop NO soportado");
                return false;
            }
            //Desktop.getDesktop().open(rutaCompleta.toFile());
            File archivoFisico = rutaCompleta.toFile();
            Thread hilo = new Thread(() -> {
                try {
                    System.out.println("abrir: lanzando " + archivoFisico);
                    Desktop.getDesktop().open(archivoFisico);
                    System.out.println("abrir: listo");
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
            hilo.setDaemon(true);
            hilo.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
    public Archivo buscar(int idRegistro){
        if (idRegistro <= 0) {
            return null;
        }
        try {
            return ServiceLocator.getInstanceArchivoDAO().find(idRegistro).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
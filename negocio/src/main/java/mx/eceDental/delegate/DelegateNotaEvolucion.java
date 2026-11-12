package mx.eceDental.delegate;

import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DelegateNotaEvolucion {

    public boolean registrar(int idExpediente, String contenido) {
        if (idExpediente <= 0
                || contenido == null || contenido.trim().isEmpty()
                || contenido.trim().length() > 1000) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            NotaEvolucion nota = new NotaEvolucion();
            nota.setFecha(LocalDate.now());
            nota.setContenido(contenido.trim());
            nota.setExpediente(expediente);

            ServiceLocator.getInstanceNotaEvolucionDAO().save(nota);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idNota, String contenido) {
        if (idNota <= 0
                || contenido == null || contenido.trim().isEmpty()
                || contenido.trim().length() > 1000) {
            return false;
        }

        try {
            NotaEvolucion nota = ServiceLocator.getInstanceNotaEvolucionDAO().find(idNota).orElse(null);

            if (nota == null) {
                return false;
            }

            nota.setContenido(contenido.trim());

            ServiceLocator.getInstanceNotaEvolucionDAO().update(nota);

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
            NotaEvolucion nota = ServiceLocator.getInstanceNotaEvolucionDAO().find(id).orElse(null);

            if (nota == null) {
                return false;
            }

            ServiceLocator.getInstanceNotaEvolucionDAO().delete(nota);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public NotaEvolucion buscarUltima() {
        try {
            List<NotaEvolucion> todas = ServiceLocator.getInstanceNotaEvolucionDAO().findAll();
            NotaEvolucion ultima = null;

            for (NotaEvolucion nota : todas) {
                if (nota.getFecha() == null) {
                    continue;
                }

                if (ultima == null
                        || nota.getFecha().isAfter(ultima.getFecha())
                        || (nota.getFecha().equals(ultima.getFecha())
                        && nota.getIdNota() > ultima.getIdNota())) {
                    ultima = nota;
                }
            }

            return ultima;

        } catch (Exception e) {
            return null;
        }
    }

    public List<NotaEvolucion> consultar(int idExpediente) {
        List<NotaEvolucion> resultado = new ArrayList<>();

        if (idExpediente <= 0) {
            return resultado;
        }

        try {
            List<NotaEvolucion> todas = ServiceLocator.getInstanceNotaEvolucionDAO().findAll();

            for (NotaEvolucion nota : todas) {
                if (nota.getExpediente() != null
                        && nota.getExpediente().getIdExpediente() == idExpediente) {
                    resultado.add(nota);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
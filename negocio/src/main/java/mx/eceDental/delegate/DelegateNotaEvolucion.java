package mx.eceDental.delegate;

import mx.eceDental.entity.Expediente;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class DelegateNotaEvolucion {

    public boolean registrar(int idExpediente, Date fecha, String contenido) {
        if (idExpediente <= 0
                || fecha == null || fecha.after(new Date())
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
            nota.setFecha(fecha);
            nota.setContenido(contenido.trim());
            nota.setExpediente(expediente);

            ServiceLocator.getInstanceNotaEvolucionDAO().save(nota);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<NotaEvolucion> buscarPorFecha(Date fecha) {
        List<NotaEvolucion> resultado = new ArrayList<>();

        if (fecha == null) {
            return resultado;
        }

        try {
            Calendar buscada = Calendar.getInstance();
            buscada.setTime(fecha);

            List<NotaEvolucion> todas = ServiceLocator.getInstanceNotaEvolucionDAO().findAll();

            for (NotaEvolucion nota : todas) {
                if (nota.getFecha() == null) {
                    continue;
                }

                Calendar actual = Calendar.getInstance();
                actual.setTime(nota.getFecha());

                if (actual.get(Calendar.YEAR) == buscada.get(Calendar.YEAR)
                        && actual.get(Calendar.DAY_OF_YEAR) == buscada.get(Calendar.DAY_OF_YEAR)) {
                    resultado.add(nota);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean modificar(int idNota, Date fecha, String contenido) {
        if (idNota <= 0
                || fecha == null || fecha.after(new Date())
                || contenido == null || contenido.trim().isEmpty()
                || contenido.trim().length() > 1000) {
            return false;
        }

        try {
            NotaEvolucion nota = ServiceLocator.getInstanceNotaEvolucionDAO().find(idNota).orElse(null);

            if (nota == null) {
                return false;
            }

            nota.setFecha(fecha);
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
                        || nota.getFecha().after(ultima.getFecha())
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
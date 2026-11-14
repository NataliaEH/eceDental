package mx.eceDental.delegate;


import mx.eceDental.entity.Abono;
import mx.eceDental.entity.Expediente;
import mx.eceDental.entity.NotaEvolucion;
import mx.eceDental.persistence.integration.ServiceLocator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DelegateAbono {

    public boolean registrar(int idExpediente, double monto, String concepto) {
        if (idExpediente <= 0
                || monto <= 0
                || concepto == null || concepto.trim().isEmpty()
                || concepto.trim().length() > 100) {
            return false;
        }

        try {
            Expediente expediente = ServiceLocator.getInstanceExpedienteDAO().find(idExpediente).orElse(null);

            if (expediente == null) {
                return false;
            }

            Abono pago = new Abono();
            pago.setFecha(LocalDate.now());
            pago.setMonto(monto);
            pago.setConcepto(concepto.trim());
            pago.setExpediente(expediente);

            ServiceLocator.getInstanceAbonoDAO().save(pago);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean modificar(int idAbono, double monto, String concepto) {
        if (idAbono <= 0
                || monto <= 0
                || concepto == null || concepto.trim().isEmpty()
                || concepto.trim().length() > 100) {
            return false;
        }

        try {
            Abono pago = ServiceLocator.getInstanceAbonoDAO().find(idAbono).orElse(null);

            if (pago == null) {
                return false;
            }

            pago.setMonto(monto);
            pago.setConcepto(concepto.trim());

            ServiceLocator.getInstanceAbonoDAO().update(pago);

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
            Abono pago = ServiceLocator.getInstanceAbonoDAO().find(id).orElse(null);

            if (pago == null) {
                return false;
            }

            ServiceLocator.getInstanceAbonoDAO().delete(pago);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Abono> consultar(int idExpediente) {
        List<Abono> resultado = new ArrayList<>();

        if (idExpediente <= 0) {
            return resultado;
        }

        try {
            List<Abono> todos = ServiceLocator.getInstanceAbonoDAO().findAll();

            for (Abono pago : todos) {
                if (pago.getExpediente() != null
                        && pago.getExpediente().getIdExpediente() == idExpediente) {
                    resultado.add(pago);
                }
            }

            return resultado;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public Abono buscar(int idRegistro){
        if (idRegistro <= 0) {
            return null;
        }
        try {
            return ServiceLocator.getInstanceAbonoDAO().find(idRegistro).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
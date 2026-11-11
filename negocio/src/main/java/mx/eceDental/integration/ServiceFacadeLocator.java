package mx.eceDental.integration;

import mx.eceDental.facade.*;


public class ServiceFacadeLocator {

    private static FacadeAbono facadeAbono;
    private static FacadeArchivo facadeArchivo;
    private static FacadePaciente facadePaciente;
    private static FacadeExpediente facadeExpediente;
    private static FacadeInterrogatorio facadeInterrogatorio;
    private static FacadeDiagnosticoYPlan facadeDiagnosticoYPlan;
    private static FacadeNotaEvolucion facadeNotaEvolucion;
    private static FacadeOdontograma facadeOdontograma;

    public static FacadeAbono getInstanceFacadeAbono() {
        if (facadeAbono == null) {
            facadeAbono = new FacadeAbono();
        }
        return facadeAbono;
    }

    public static FacadeArchivo getInstanceFacadeArchivo() {
        if (facadeArchivo == null) {
            facadeArchivo = new FacadeArchivo();
        }
        return facadeArchivo;
    }

    public static FacadeExpediente getInstanceFacadeExpediente() {
        if (facadeExpediente == null) {
            facadeExpediente = new FacadeExpediente();
        }
        return facadeExpediente;
    }

    public static FacadePaciente getInstanceFacadePaciente() {
        if (facadePaciente == null) {
            facadePaciente = new FacadePaciente();
        }
        return facadePaciente;
    }
    public static FacadeInterrogatorio getInstanceFacadeInterrogatorio() {
        if (facadeInterrogatorio == null) {
            facadeInterrogatorio = new FacadeInterrogatorio();
        }
        return facadeInterrogatorio;
    }

    public static FacadeDiagnosticoYPlan getInstanceFacadeDiagnosticoYPlan() {
        if (facadeDiagnosticoYPlan == null) {
            facadeDiagnosticoYPlan = new FacadeDiagnosticoYPlan();
        }
        return facadeDiagnosticoYPlan;
    }
    public static FacadeNotaEvolucion getInstanceFacadeNotaEvolucion() {
        if (facadeNotaEvolucion == null) {
            facadeNotaEvolucion = new FacadeNotaEvolucion();
        }
        return facadeNotaEvolucion;
    }

    public static FacadeOdontograma getInstanceFacadeOdontograma() {
        if (facadeOdontograma == null) {
            facadeOdontograma = new FacadeOdontograma();
        }
        return facadeOdontograma;
    }
}

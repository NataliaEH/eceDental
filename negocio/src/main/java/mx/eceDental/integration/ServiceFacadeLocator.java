
package mx.eceDental.integration;

import mx.eceDental.facade.FacadeDiagnosticoYPlan;
import mx.eceDental.facade.FacadeExpediente;
import mx.eceDental.facade.FacadeInterrogatorio;


public class ServiceFacadeLocator {

    private static FacadeExpediente facadeExpediente;
    private static FacadeInterrogatorio facadeInterrogatorio;
    private static FacadeDiagnosticoYPlan facadeDiagnosticoYPlan;

    public static FacadeExpediente getInstanceFacadeExpediente() {
        if (facadeExpediente == null) {
            facadeExpediente = new FacadeExpediente();
        }
        return facadeExpediente;
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

}

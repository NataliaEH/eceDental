
package mx.eceDental.integration;

import mx.eceDental.facade.FacadeExpediente;


public class ServiceFacadeLocator {

    private static FacadeExpediente facadeExpediente;

    public static FacadeExpediente getInstanceFacadeExpediente() {
        if (facadeExpediente == null) {
            facadeExpediente = new FacadeExpediente();
        }
        return facadeExpediente;
    }

}

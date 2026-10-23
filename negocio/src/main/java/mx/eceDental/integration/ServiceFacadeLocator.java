package mx.eceDental.integration;


import mx.eceDental.facade.FacadeAsignacion;
import mx.eceDental.facade.FacadeProfesor;
import mx.eceDental.facade.FacadeUA;
import mx.eceDental.facade.FacadeUsuario;

public class ServiceFacadeLocator {

    private static FacadeProfesor facadeProfesor;
    private static FacadeAsignacion facadeAsignacion;
    private static FacadeUA facadeUA;
    private static FacadeUsuario facadeUsuario;

    public static FacadeProfesor getInstanceFacadeProfesor() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
            return facadeProfesor;
        } else {
            return facadeProfesor;
        }
    }

    public static FacadeAsignacion getInstanceFacadeAsignacion() {
        if (facadeAsignacion == null) {
            facadeAsignacion = new FacadeAsignacion();
            return facadeAsignacion;
        } else {
            return facadeAsignacion;
        }
    }

    public static FacadeUA getInstanceFacadeUA() {
        if (facadeUA == null) {
            facadeUA = new FacadeUA();
            return facadeUA;
        } else {
            return facadeUA;
        }
    }

    public static FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
            return facadeUsuario;
        } else {
            return facadeUsuario;
        }
    }
}



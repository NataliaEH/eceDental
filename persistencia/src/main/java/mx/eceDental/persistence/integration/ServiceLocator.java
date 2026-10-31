package mx.eceDental.persistence.integration;

import jakarta.persistence.EntityManager;
import mx.eceDental.persistence.dao.*;
import mx.eceDental.persistence.persistence.HibernateUtil;

public class ServiceLocator {

    private static PacienteDAO pacienteDAO;
    private static ExpedienteDAO expedienteDAO;
    private static InterrogatorioDAO interrogatorioDAO;
    private static DiagnosticoYPlanDAO diagnosticoYPlanDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    public static PacienteDAO getInstancePacienteDAO(){
        return new PacienteDAO(getEntityManager());
    }

    public static ExpedienteDAO getInstanceExpedienteDAO(){
        return new ExpedienteDAO(getEntityManager());
    }

    public static InterrogatorioDAO getInstanceInterrogatorioDAO() {return new InterrogatorioDAO(getEntityManager());}

    public static DiagnosticoYPlanDAO getInstanceDiagnosticoYPlanDAO () { return new DiagnosticoYPlanDAO(getEntityManager());}
    public static AlergiaDAO getInstanceAlergiaDAO() {return new AlergiaDAO(getEntityManager());}

    public static MedicacionDAO getInstanceMedicacionDAO() {return new MedicacionDAO(getEntityManager());}

    public static EnfermedadDAO getInstanceEnfermedadDAO() {return new EnfermedadDAO(getEntityManager());}

    public static TelefonosPacienteDAO getInstanceTelefonosPacienteDAO() {return new TelefonosPacienteDAO(getEntityManager());}

    /*
    Ya estaba esto, lo deje por si acaso
    public static UsuarioDAO getInstanceUsuarioDAO () {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO(getEntityManager());
            return usuarioDAO;
        } else {
            return usuarioDAO;
        }
    }
    */

}

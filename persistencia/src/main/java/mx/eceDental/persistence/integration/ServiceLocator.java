package mx.eceDental.persistence.integration;

import jakarta.persistence.EntityManager;
import mx.eceDental.persistence.DAO.*;
import mx.eceDental.persistence.persistence.HibernateUtil;

public class ServiceLocator {

    private static PacienteDAO pacienteDAO;
    private static ExpedienteDAO expedienteDAO;
    private static InterrogatorioDAO interrogatorioDAO;
    private static DiagnosticoYPlanDAO diagnosticoYPlanDAO;
    private static AlergiaDAO alergiaDAO;
    private static MedicacionDAO medicacionDAO;
    private static EnfermedadDAO enfermedadDAO;
    private static TelefonosPacienteDAO telefonosPacienteDAO;

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
    public static NotaEvolucionDAO getInstanceNotaEvolucionDAO(){
        return new NotaEvolucionDAO(getEntityManager());
    }
    public static ArchivoDAO getInstanceArchivoDAO(){
        return new ArchivoDAO(getEntityManager());
    }
    public static AbonoDAO getInstanceAbonoDAO(){
        return new AbonoDAO(getEntityManager());
    }
    public static OdontogramaDAO getInstanceOdontogramaDAO(){
        return new OdontogramaDAO(getEntityManager());
    }
    public static RegistroOdontDAO getInstanceRegistroOdntDAO(){
        return new RegistroOdontDAO(getEntityManager());
    }
}

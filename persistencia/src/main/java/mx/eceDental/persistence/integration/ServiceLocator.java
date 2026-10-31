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
    public static DiagnosticoYPlanDAO getInstanceDiagnosticoYPlanDAO () {
        return new DiagnosticoYPlanDAO(getEntityManager());
    }
}

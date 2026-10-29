/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.eceDental.persistence.integration;

import jakarta.persistence.EntityManager;
import mx.eceDental.persistence.DAO.PacienteDAO;
import mx.eceDental.persistence.DAO.*;
import mx.eceDental.persistence.persistence.HibernateUtil;

/**
 *
 * @author total
 */
public class ServiceLocator {

    private static PacienteDAO pacienteDAO;
    private static ExpedienteDAO expedienteDAO;
    private static InterrogatorioDAO interrogatorioDAO;
    private static DiagnosticoYPlanDAO diagnosticoYPlanDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }


    /**
     * se crea la instancia de profesorDAO si esta no existe
     */
    public static PacienteDAO getInstancePacienteDAO(){
        return new PacienteDAO(getEntityManager());
    }

    /**
     * se crea la instancia de horarioDAO si esta no existe
     */
    public static ExpedienteDAO getInstanceExpedienteDAO(){
        return new ExpedienteDAO(getEntityManager());
    }

    /**
     * se crea la instancia de unidad_de_aprendizajeDAO si esta no existe
     */
    public static InterrogatorioDAO getInstanceInterrogatorioDAO() {return new InterrogatorioDAO(getEntityManager());}

    /**
     * se crea la instancia para asignacionDAO si esta no existe
     */
    public static DiagnosticoYPlanDAO getInstanceDiagnosticoYPlanDAO () {
        return new DiagnosticoYPlanDAO(getEntityManager());
    }

    /**
     * se crea la instancia para usuarioDAO si esta no existe
     */
    /*
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

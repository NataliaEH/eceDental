/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.eceDental.persistence.integration;

import jakarta.persistence.EntityManager;
import mx.eceDental.persistence.dao.*;
import mx.eceDental.persistence.persistence.HibernateUtil;

/**
 *
 * @author total
 */
public class ServiceLocator {

    private static ProfesorDAO profesorDAO;
    private static UsuarioDAO usuarioDAO;
    private static AsignacionDAO asignacionDAO;
    private static UnidadAprendizajeDAO unidadAprendizajeDAO;
    private static HorarioDAO horarioDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }


    /**
     * se crea la instancia de profesorDAO si esta no existe
     */
    public static ProfesorDAO getInstanceProfesorDAO(){
        return new ProfesorDAO(getEntityManager());
    }

    /**
     * se crea la instancia de horarioDAO si esta no existe
     */
    public static HorarioDAO getInstanceHorarioDAO(){
        return new HorarioDAO(getEntityManager());
    }

    /**
     * se crea la instancia de unidad_de_aprendizajeDAO si esta no existe
     */
    public static UnidadAprendizajeDAO getInstanceUnidadAprendizajeDAO() {
        return new UnidadAprendizajeDAO(getEntityManager());
    }

    /**
     * se crea la instancia para asignacionDAO si esta no existe
     */
    public static AsignacionDAO getInstanceAsignacionDAO () {
        return new AsignacionDAO(getEntityManager());
    }

    /**
     * se crea la instancia para usuarioDAO si esta no existe
     */
    public static UsuarioDAO getInstanceUsuarioDAO () {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO(getEntityManager());
            return usuarioDAO;
        } else {
            return usuarioDAO;
        }
    }
}

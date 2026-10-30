package mx.desarrollo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enfermedad")
public class Enfermedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Enfermedad")
    private int idEnfermedad;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Id_Interrogatorio")
    private int idInterrogatorio;

    public Enfermedad() {
    }

    public int getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(int idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdInterrogatorio() {
        return idInterrogatorio;
    }

    public void setIdInterrogatorio(int idInterrogatorio) {
        this.idInterrogatorio = idInterrogatorio;
    }
}
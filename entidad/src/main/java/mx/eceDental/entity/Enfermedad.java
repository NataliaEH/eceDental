package mx.eceDental.entity;

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

    @ManyToOne
    @JoinColumn(name = "Id_Interrogatorio")
    private Interrogatorio interrogatorio;

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
    public Interrogatorio getInterrogatorio() {
        return interrogatorio;
    }
    public void setInterrogatorio(Interrogatorio interrogatorio) {
        this.interrogatorio = interrogatorio;
    }
}
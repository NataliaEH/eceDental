package mx.desarrollo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medicacion")
public class Medicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Medicacion")
    private int idMedicacion;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Id_Interrogatorio")
    private int idInterrogatorio;

    public Medicacion() {
    }

    public int getIdMedicacion() {
        return idMedicacion;
    }

    public void setIdMedicacion(int idMedicacion) {
        this.idMedicacion = idMedicacion;
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
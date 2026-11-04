package mx.eceDental.entity;

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

    @ManyToOne
    @JoinColumn(name = "Id_Interrogatorio")
    private Interrogatorio interrogatorio;

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
    public Interrogatorio getInterrogatorio() {
        return interrogatorio;
    }
    public void setInterrogatorio(Interrogatorio interrogatorio) {
        this.interrogatorio = interrogatorio;
    }
}
package mx.eceDental.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "alergia")
public class Alergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Alergia")
    private int idAlergia;

    @Column(name = "Nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "Id_Interrogatorio")
    private Interrogatorio interrogatorio;

    public Alergia() {
    }

    public int getIdAlergia() {
        return idAlergia;
    }

    public void setIdAlergia(int idAlergia) {
        this.idAlergia = idAlergia;
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

package mx.desarrollo.entity;

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

    @Column(name = "Id_Interrogatorio")
    private int idInterrogatorio;

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

    public int getIdInterrogatorio() {
        return idInterrogatorio;
    }

    public void setIdInterrogatorio(int idInterrogatorio) {
        this.idInterrogatorio = idInterrogatorio;
    }
}

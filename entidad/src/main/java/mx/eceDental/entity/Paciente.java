package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_Paciente",nullable = false)
    private int idPaciente;

    @Size(max=100)
    @NotNull
    @Column(name = "Nombre",nullable = false,length = 100)
    private String nombre;

    @Column(name = "Edad")
    private int edad;

    @Size(max=1)
    @Column(name = "Sexo",length = 1)
    private String sexo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelefonosPaciente> telefonos;

    @Size(max=100)
    @Column(name = "Correo",length = 100)
    private String correo;

    @Size(max=100)
    @Column(name = "Domicilio",length = 100)
    private String domicilio;

    @Size(max=30)
    @Column(name = "Edo_Civil",length = 30)
    private String edoCivil;

    @Size(max=50)
    @Column(name = "Ocupacion",length = 50)
    private String ocupacion;

    @Column(name = "Notas",length = 200)
    private String notas;

    @OneToOne(mappedBy = "paciente")
    private Expediente expediente;

    public int getIdPaciente() {
        return idPaciente;
    }
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<TelefonosPaciente> getTelefonos() {
        return telefonos;
    }
    public void setTelefonos(List<TelefonosPaciente> telefonos) {
        this.telefonos = telefonos;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEdoCivil() {
        return edoCivil;
    }
    public void setEdoCivil(String edoCivil) {
        this.edoCivil = edoCivil;
    }

    public String getOcupacion() {
        return ocupacion;
    }
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Expediente getExpediente() {
        return expediente;
    }
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }
}
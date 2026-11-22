package mx.eceDental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "Id_Doctor",nullable = false)
    private int idDoctor;

    @Size(max=100)
    @NotNull
    @Column(name = "Nombre",nullable = false,length = 100)
    private String nombreCompleto;

    @Size(max=50)
    @Column(name = "Titulo",length = 50)
    private String titulo;

    @Size(max=20)
    @Column(name = "Cedula",length = 20)
    private String cedula;

    @Size(max=13)
    @Column(name = "RFC",length = 13)
    private String rfc;

    @Size(max=150)
    @Column(name = "Domicilio_Est",length = 150)
    private String domicilioEstablecimiento;

    @Size(max=100)
    @Column(name = "Nombre_Est",length = 100)
    private String nombreEstablecimiento;

    @Size(max=20)
    @Column(name = "Telefono",length = 20)
    private String telefonoEstablecimiento;

    @Size(max=100)
    @Column(name = "Correo",length = 100)
    private String correo;

    public int getIdDoctor() {return idDoctor;}
    public void setIdDoctor(int idDoctor) {this.idDoctor = idDoctor;}

    public String getNombreCompleto() {return nombreCompleto;}
    public void setNombreCompleto(String nombreCompleto) {this.nombreCompleto = nombreCompleto;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getCedula() {return cedula;}
    public void setCedula(String cedula) {this.cedula = cedula;}

    public String getRfc() {return rfc;}
    public void setRfc(String rfc) {this.rfc = rfc;}

    public String getDomicilioEstablecimiento() {return domicilioEstablecimiento;}
    public void setDomicilioEstablecimiento(String domicilioEstablecimiento) {this.domicilioEstablecimiento = domicilioEstablecimiento;}

    public String getNombreEstablecimiento() {return nombreEstablecimiento;}
    public void setNombreEstablecimiento(String nombreEstablecimiento) {this.nombreEstablecimiento = nombreEstablecimiento;}

    public String getTelefonoEstablecimiento() {return telefonoEstablecimiento;}
    public void setTelefonoEstablecimiento(String telefonoEstablecimiento) {this.telefonoEstablecimiento = telefonoEstablecimiento;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}
}
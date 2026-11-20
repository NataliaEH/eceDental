function inicializarMisDatos() {
    cargarMisDatos();
    guardarMisDatos();
}

function cargarMisDatos(){
    try {
        const doctor = window.doctorUI.consultarDatos();
        if (!doctor) {
            mostrarMensaje("No se encontró el doctor.","danger");
            return;
        }

        document.getElementById("nombreCompleto").value = doctor.getNombre() || "";
        document.getElementById("titulo").value = doctor.getTitulo() || "";
        document.getElementById("cedula").value = doctor.getCedula() || "";
        document.getElementById("rfc").value = doctor.getRFC() || "";
        document.getElementById("correo").value = doctor.getCorreo() || "";
        document.getElementById("telefonoEstablecimiento").value = doctor.getTelefonoEst() || "";
        document.getElementById("nombreEstablecimiento").value = doctor.getNombreEst() || "";
        document.getElementById("domicilioEstablecimiento").value = doctor.getDomicilioEst() || "";
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del doctor.","danger");
    }
}

function guardarMisDatos() {
    const formulario = document.getElementById("formMisDatos");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();

        const datos = {
            nombre: document.getElementById("nombreCompleto").value.trim(),
            titulo: document.getElementById("titulo").value,
            cedula: document.getElementById("cedula").value.trim(),
            rfc: document.getElementById("rfc").value.trim(),
            correo: document.getElementById("correo").value.trim(),
            telEst: document.getElementById("telefonoEstablecimiento").value.trim(),
            nombreEst: document.getElementById("nombreEstablecimiento").value.trim(),
            domicilioEst: document.getElementById("domicilioEstablecimiento").value.trim(),
        };
        try {
            const doctor = window.doctorUI.consultarDatos();

            doctor.setNombre(datos.nombre);
            doctor.setTitulo(datos.titulo);
            doctor.setCedula(datos.cedula);
            doctor.setRFC(datos.rfc);
            doctor.setCorreo(datos.correo);
            doctor.setTelefono(datos.telEst);
            doctor.setNombreEst(datos.nombreEst);
            doctor.setDomicilioEst(datos.domicilioEst);

            doctorUI.setDoctor(doctor);
            const modificado = doctorUI.modificar(doctor.getIdPaciente());
            if(modificado){
                mostrarMensaje("Se han modificado los datos correctamente.","success");
            }else{
                mostrarMensaje("Ha ocurrido un error en la modificación.","danger");
            }
        } catch (error) {
            mostrarMensaje("No se pudo hacer la modificación.","danger")
        }
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        cargarMisDatos();
    });
}
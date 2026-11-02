function inicializarRegistrarPaciente() {
    const formulario = document.getElementById("formPaciente");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();

        const datos = {
            nombre: document.getElementById("nombre").value.trim(),
            edad: parseInt(document.getElementById("edad").value),
            sexo: document.querySelector('input[name="sexo"]:checked')?.value,
            estadoCivil: document.getElementById("edoCivil").value,
            ocupacion: document.getElementById("ocupacion").value.trim(),
            domicilio: document.getElementById("domicilio").value.trim(),
            telefonoPrincipal: document.getElementById("tel").value.trim(),
            telefonoAlternativo: document.getElementById("telAlt").value.trim(),
            correo: document.getElementById("correo").value.trim(),
        };
        try {
            const paciente = pacienteUI.getPaciente();

            paciente.setNombre(datos.nombre);
            paciente.setEdad(datos.edad);
            paciente.setSexo(datos.sexo);
            paciente.setEdoCivil(datos.estadoCivil);
            paciente.setOcupacion(datos.ocupacion);
            paciente.setDomicilio(datos.domicilio);
            paciente.setCorreo(datos.correo);

            const registrado = pacienteUI.registrar();
            const registradoTels = pacienteUI.registrarTelefonos(datos.telefonoPrincipal, datos.telefonoAlternativo);
            if(registrado && registradoTels){
                mostrarMensaje("Expediente registrado", "success");

                const pacienteId = pacienteUI.getPaciente().getIdPaciente();
                const expediente = expedienteUI.buscarPorPaciente(pacienteId);
                sessionStorage.setItem("expedienteActualId", expediente.getIdExpediente());
                window.location.href = "dashboardExpediente.html";
            }else{
                mostrarMensaje("No se pudo registrar el paciente.", "danger");
            }
        } catch (error) {
            mostrarMensaje("Ha ocurrido un error al registrar el paciente.", "danger");
        }
    });
}
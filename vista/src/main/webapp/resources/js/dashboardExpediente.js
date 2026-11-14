function inicializarDashboardExpediente() {
    const idExpediente = new URLSearchParams(window.location.search).get("id") || sessionStorage.getItem("expedienteActualId");
    if (!idExpediente) {
        mostrarMensaje("No se encontró el expediente seleccionado.","danger");
        return;
    }

    inicializarEliminarExpediente();

    let expediente;
    try {
        expediente = window.expedienteUI.buscar(parseInt(idExpediente));
        if (!expediente) {
            mostrarMensaje("No se encontró el expediente.","danger");
            return;
        }

        const paciente = expediente.getPaciente();
        if (!paciente) {
            mostrarMensaje("El expediente no tiene un paciente asociado.","danger");
            return;
        }

        document.getElementById("nombrePaciente").textContent = paciente.getNombre();
        sessionStorage.setItem("expedienteActualId", idExpediente);

        // Botón exportar
        document.getElementById("btnExportar").addEventListener("click", () => {
            window.expedienteUI.exportar(parseInt(idExpediente));
        });

        const hoy = new Date();
        let viejo = false;
        const fechaCreacion = expediente.getFechaCreacion().toString();

        if (fechaCreacion) {
            const fecha = new Date(fechaCreacion + "T00:00:00");
            const fechaLimite = new Date(fecha);
            fechaLimite.setFullYear(fechaLimite.getFullYear() + 5);
            if (hoy >= fechaLimite) {
                viejo = true;
                document.getElementById("btnEliminar").style.display = "flex";
            }else{
                document.getElementById("btnEliminar").style.display = "none";
            }
        }
        //notas
        inicializarNotasGenerales(paciente);
        document.getElementById("notas").value = paciente.getNotas();
        //alergias
        const alergias = expediente.getInterrogatorio().getAlergiasString();
        document.getElementById("alergias").value = alergias;

        //notaevolucion
        const tabla = document.getElementById("tablaNotas");
        tabla.innerHTML = "";
        let ultima = notaEvolucionUI.buscarUltima(idExpediente);
        if(ultima!=null){
            const fila = document.createElement("tr");
            const fechaTexto = ultima.getFecha();
            const tdFecha = document.createElement("td");
            tdFecha.textContent = fechaTexto;
            const tdContenido = document.createElement("td");
            tdContenido.textContent = ultima.getContenido();

            fila.appendChild(tdFecha);
            fila.appendChild(tdContenido);

            tabla.appendChild(fila);
        }

        guardarNotas();
    } catch (error) {
        mostrarMensaje("Ocurrió un error al cargar el expediente.","danger");
        mostrarMensaje(error, "danger")
    }
}
function inicializarEliminarExpediente(paciente){
    const formulario = document.getElementById("formEliminarExpediente");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        const texto = sessionStorage.getItem("expedienteActualId");
        expedienteUI.eliminar(parseInt(idExpediente));
        window.location.hash = "";
        window.location.href = "buscarExpediente.html";
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        window.location.hash = "";
    });
}
function inicializarNotasGenerales(paciente){
    const formulario = document.getElementById("formNotasGenerales");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        try{
            const texto = document.getElementById("notas").value.trim();
            pacienteUI.agregarNotas(paciente.getIdPaciente(), texto);
            window.location.hash = "";
            mostrarMensaje("Notas generales guardadas correctamente.","success");
        } catch (error) {
            mostrarMensaje("No fue posible guardar las notas generales.","danger");
        }
    });
}
function guardarNotas() {
    const formulario = document.getElementById("formNotaEvolucion");
    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const idExpediente = sessionStorage.getItem("expedienteActualId");
            if (!idExpediente) {
                mostrarMensaje("No se ha seleccionado ningún expediente.","danger");
                return;
            }

            const expediente = expedienteUI.buscar(parseInt(idExpediente));
            const nota = notaEvolucionUI.getNotaEvolucion();
            const contenido = document.getElementById("contenido").value.trim();

            nota.setContenido(contenido);
            nota.setExpediente(expediente);

            notaEvolucionUI.registrar();

            mostrarMensaje("Nota de evolución registrada correctamente.","success");
            cargarNotas();
            formulario.reset();
        } catch (error) {
            mostrarMensaje("No fue posible registrar la nota de evolución.","danger");
            mostrarMensaje(error, "danger")
        }
    });
}


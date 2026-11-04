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
    } catch (error) {
        mostrarMensaje("Ocurrió un error al cargar el expediente.","danger");
    }
}
function inicializarEliminarExpediente(){
    const formulario = document.getElementById("formEliminarExpediente");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        const idExpediente = sessionStorage.getItem("expedienteActualId");
        expedienteUI.eliminar(parseInt(idExpediente));
        window.location.hash = "";
        window.location.href = "buscarExpediente.html";
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        window.location.hash = "";
    });
}

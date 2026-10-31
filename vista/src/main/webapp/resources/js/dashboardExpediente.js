function inicializarDashboardExpediente() {
    const idExpediente = new URLSearchParams(window.location.search).get("id") ||
        sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        alert("No se encontró el expediente seleccionado.");
        return;
    }

    let expediente;

    try {
        expediente = window.expedienteUI.buscar(parseInt(idExpediente));
        if (!expediente) {
            alert("No se encontró el expediente.");
            return;
        }

        const paciente = expediente.getPaciente();
        if (!paciente) {
            alert("El expediente no tiene un paciente asociado.");
            return;
        }

        document.getElementById("nombrePaciente").textContent = paciente.getNombre();
        sessionStorage.setItem("expedienteActualId", idExpediente);

        // Botón exportar
        document.getElementById("btnExportar").addEventListener("click", () => {
            window.expedienteUI.exportar(parseInt(idExpediente));
        });

        // Botón eliminar
        document.getElementById("btnEliminar").addEventListener("click", (event) => {
            event.preventDefault();

            if (confirm("¿Estás segura de que deseas eliminar este expediente?")) {
                window.expedienteUI.eliminar(parseInt(idExpediente));
                window.location.href = "buscarExpediente.html";
            }
        });

    } catch (error) {
        console.error("Error al cargar el dashboard:", error);
        alert("Ocurrió un error al cargar el expediente.");
    }
}

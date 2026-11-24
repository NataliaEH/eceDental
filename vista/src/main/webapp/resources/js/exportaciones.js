function inicializarExportacion() {
    cargarExpediente();
    exportar();

    try {
        const nombre = window.doctorUI.consultarDatos().getNombreCompleto();
        document.getElementById("usuario").textContent = nombre;
    } catch (error) {
        //
    }
}

function cargarExpediente() {
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        mostrarMensaje("No se ha seleccionado ningún expediente.", "danger");
        return;
    }

    try {
        const expediente = window.expedienteUI.buscar(parseInt(idExpediente));
        if (!expediente) {
            mostrarMensaje("No se encontró el expediente.", "danger");
            return;
        }

        const paciente = expediente.getPaciente();
        if (!paciente) {
            mostrarMensaje("El expediente no tiene un paciente asociado.", "danger");
            return;
        }

        document.getElementById("nombrePaciente").textContent = paciente.getNombre();
        document.getElementById("nombrePacienteResumen").textContent = paciente.getNombre();
        document.getElementById("numeroExpediente").textContent = idExpediente;

        const desde = document.getElementById("desde")._flatpickr;
        const hasta = document.getElementById("hasta")._flatpickr;
        desde.setDate(String(expediente.getFechaCreacion()), true);
        hasta.setDate(new Date(), true);

        document.getElementById("totalNotas").textContent = expediente.getNotasEvolucion().size() + " registros";
        document.getElementById("totalDiagnosticos").textContent = expediente.getDiagnosticos().size() + " registros";
        document.getElementById("totalOdontograma").textContent = expediente.getOdontograma().getRegistros().size() + " registros";
        document.getElementById("totalPagos").textContent = expediente.getAbono().size() + " registros";
        document.getElementById("totalArchivos").textContent = expediente.getArchivo().size() + " registros";
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del expediente.","danger");
        mostrarMensaje(error,"danger");
    }
}

function exportar() {
    const formulario = document.getElementById("formExportar");
    formulario.addEventListener("submit", async function (event) {
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

            const secciones = Array.from(
                document.querySelectorAll("input.sec-check:checked")
            ).map(function (check) {
                return check.value;
            });

            const desde = document.getElementById("desde").value; // "yyyy-MM-dd" o ""
            const hasta = document.getElementById("hasta").value;

            if (secciones.length === 0) {
                mostrarMensaje("Selecciona al menos una sección para exportar.", "warning");
                return;
            }
            if (desde !== "" && hasta !== "" && desde > hasta) {
                mostrarMensaje("La fecha 'Desde' no puede ser mayor que 'Hasta'.", "warning");
                return;
            }

            let imagen = "";
            if (secciones.includes("odontograma")) {
                imagen = await odontogramaAPng(obtenerListaOdontograma(idExpediente));
            }

            const exportado = expedienteUI.exportar(idExpediente, secciones.join(","), desde, hasta, imagen);
            if(exportado){
                mostrarMensaje("Expediente exportado correctamente.","success");
                formulario.reset();
            }else
                mostrarMensaje("No fue posible exportar el expediente.","danger");
        } catch (error) {
            mostrarMensaje("No fue posible exportar el expediente.","danger");
            mostrarMensaje(error, "danger")
        }
    });

    const checks = document.querySelectorAll("input.sec-check");
    const todo = document.getElementById("seleccionarTodo");

    todo.addEventListener("change", function () {
        checks.forEach(function (c) { c.checked = todo.checked; });
    });
    checks.forEach(function (c) {
        c.addEventListener("change", function () {
            todo.checked = Array.from(checks).every(function (x) { return x.checked; });
        });
    });
}
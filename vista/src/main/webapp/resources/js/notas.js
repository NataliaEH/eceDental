function inicializarNotas() {
    cargarNotas();
    guardarNotas();
    modificarNotas();
}

function cargarNotas() {
    const idExpediente = sessionStorage.getItem("expedienteActualId");

    if (!idExpediente) {
        mostrarMensaje("No se ha seleccionado ningún expediente.","danger");
        return;
    }

    try {
        const expediente = window.expedienteUI.buscar(parseInt(idExpediente));
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
        const tabla = document.getElementById("tablaNotas");
        let registros = notasUI.consultar(idExpediente);
        tabla.innerHTML = "";

        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            const fila = document.createElement("tr");
            const fechaTexto = registro.getFecha();
            const tdFecha = document.createElement("td");
            tdFecha.textContent = fechaTexto;
            const tdContenido = document.createElement("td");
            tdContenido.textContent = registro.getContenido();

            //ACCIONES (MODIFICAR Y ELIMINAR)
            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                abrirModalModificar(registro);
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                if (!confirm("¿Deseas eliminar esta nota de evolución?")) {
                    return;
                }
                window.notasUI.eliminar(registro.getIdNota());
                cargarNotas();
            });

            tdAcciones.appendChild(botonModificar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdContenido);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
    }
}

function guardarNotas() {
    const formulario = document.getElementById("formNotas");
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
            const nota = notasUI.getNota();
            const contenido = document.getElementById("contenido").value.trim();

            nota.setContenido(contenido);
            nota.setExpediente(expediente);

            notasUI.registrar();

            mostrarMensaje("Nota de evolución registrada correctamente.","success");
            cargarNotas();
            formulario.reset();
        } catch (error) {
            mostrarMensaje("No fue posible registrar la nota de evolución.","danger");
        }
    });
}
//ESTAS SON PARA LA MODIFICACION
function abrirModalModificar(registro) {
    document.getElementById("modIdNota").value = registro.getIdNota();
    document.getElementById("modContenido").value = registro.getContenido();
    document.getElementById("modalModificar").style.display = "flex";
}

function cerrarModalModificar() {
    document.getElementById("modalModificar").style.display = "none";
}

function modificarNotas() {
    const modal = document.getElementById("modalModificar");
    const formulario = document.getElementById("formModificar");

    document.getElementById("btnCancelarModificar").addEventListener("click", cerrarModalModificar);

    modal.addEventListener("click", function (event) {
        if (event.target === modal) {
            cerrarModalModificar();
        }
    });

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const id = parseInt(document.getElementById("modIdNota").value);
            const contenido = document.getElementById("modContenido").value.trim();

            const registro = notasUI.buscar(id);
            registro.setContenido(contenido);
            notasUI.modificar(registro);

            cerrarModalModificar();
            mostrarMensaje("Nota de evolución modificada correctamente.", "success");
            cargarNotas();
        } catch (error) {
            mostrarMensaje("No fue posible modificar la nota de evolución.", "danger");
        }
    });
}
let ordenFecha = "desc";
let idNotaAEliminar = 0;
function inicializarNotas() {
    cargarNotas();
    guardarNotas();
    modificarNotas();
    inicializarOrden();
    inicializarEliminar();
}

function cargarNotas() {
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
        const tabla = document.getElementById("tablaNotas");
        const registros = notaEvolucionUI.consultar(idExpediente);
        tabla.innerHTML = "";

        const lista = [];
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            lista.push({
                id: Number(registro.getIdNota()),
                fecha: String(registro.getFecha()),
                contenido: String(registro.getContenido())
            });
        }

        lista.sort(function (a, b) {
            let resultado = 0;
            if (a.fecha < b.fecha) {
                resultado = -1;
            } else if (a.fecha > b.fecha) {
                resultado = 1;
            }
            if (ordenFecha === "desc") {
                resultado = -resultado;
            }
            return resultado;
        });

        for (let i = 0; i < lista.length; i++) {
            const nota = lista[i];

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = nota.fecha;
            const tdContenido = document.createElement("td");
            tdContenido.textContent = nota.contenido;

            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                abrirModalModificar(notaEvolucionUI.buscar(nota.id));
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                idNotaAEliminar = nota.id;
                document.getElementById("confirmar-eliminar").style.display = "flex";
            });

            tdAcciones.appendChild(botonModificar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdContenido);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
        actualizarIconoOrden();
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
        mostrarMensaje(error,"danger");
    }
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

            const registrado = notaEvolucionUI.registrar();
            if(registrado){
                mostrarMensaje("Nota de evolución registrada correctamente.","success");
                cargarNotas();
                formulario.reset();
            }else
                mostrarMensaje("No fue posible registrar la nota de evolución.","danger");
        } catch (error) {
            mostrarMensaje("No fue posible registrar la nota de evolución.","danger");
            mostrarMensaje(error, "danger")
        }
    });
}

function inicializarOrden() {
    const enlace = document.querySelector(".sort-link");
    if (!enlace) return;

    enlace.addEventListener("click", function (event) {
        event.preventDefault();
        ordenFecha = ordenFecha === "asc" ? "desc" : "asc";
        cargarNotas();
    });
}
function actualizarIconoOrden() {
    const icono = document.querySelector(".sort-link .sort-icon");
    if (icono) {
        icono.textContent = ordenFecha === "asc" ? "▲" : "▼";
    }
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

            const registro = notaEvolucionUI.getNotaEvolucion();
            registro.setIdNota(id);
            registro.setContenido(contenido);
            const modificado = notaEvolucionUI.modificar();
            if(modificado){
                cerrarModalModificar();
                mostrarMensaje("Nota de evolución modificada correctamente.", "success");
                cargarNotas();
            }else
                mostrarMensaje("No fue posible modificar la nota de evolución.", "danger");
        } catch (error) {
            mostrarMensaje("No fue posible modificar la nota de evolución.", "danger");
        }
    });
}

//ELIMINACION
function inicializarEliminar(){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.notaEvolucionUI.eliminar(idNotaAEliminar);
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarNotas();
        mostrarMensaje("Nota de Evolución eliminada correctamente.","success");
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}
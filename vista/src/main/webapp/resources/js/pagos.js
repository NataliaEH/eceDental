let ordenFecha = "desc";
let idAbonoAEliminar = 0;
function inicializarPagos() {
    cargarPagos();
    guardarPagos();
    modificarPagos();
    inicializarOrden();
    inicializarEliminar();
}

function cargarPagos() {
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
        const tabla = document.getElementById("tablaPagos");
        const registros = abonoUI.consultar(idExpediente);
        tabla.innerHTML = "";

        const lista = [];
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }

            lista.push({
                id: Number(registro.getIdAbono()),
                fecha: String(registro.getFecha()),
                monto: String(registro.getMonto()),
                concepto: String(registro.getConcepto())
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
            const pago = lista[i];

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = pago.fecha;
            const tdMonto = document.createElement("td");
            tdMonto.textContent = pago.monto;
            const tdConcepto = document.createElement("td");
            tdConcepto.textContent = pago.concepto;

            const tdAcciones = document.createElement("td");

            const botonModificar = document.createElement("button");
            botonModificar.type = "button";
            botonModificar.className = "btn btn-sm btn-primary";
            botonModificar.style.marginRight = "6px";
            botonModificar.textContent = "Modificar";
            botonModificar.addEventListener("click", function () {
                abrirModalModificar(abonoUI.buscar(pago.id));
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                idAbonoAEliminar = pago.id;
                document.getElementById("confirmar-eliminar").style.display = "flex";
            });

            tdAcciones.appendChild(botonModificar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdMonto);
            fila.appendChild(tdConcepto);
            fila.appendChild(tdAcciones);
            tabla.appendChild(fila);
        }
        actualizarIconoOrden();
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.", "danger");
        mostrarMensaje(error, "danger");
    }
}

function guardarPagos() {
    const formulario = document.getElementById("formPago");
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
            const pago = abonoUI.getAbono();
            const monto = document.getElementById("monto").value.trim();
            const concepto = document.getElementById("concepto").value.trim();

            pago.setMonto(monto);
            pago.setConcepto(concepto);
            pago.setExpediente(expediente);

            const registrado = abonoUI.registrar();
            if(registrado){
                mostrarMensaje("Pago registrado correctamente.","success");
                cargarPagos();
                formulario.reset();
            }else
                mostrarMensaje("No fue posible registrar el pago.","danger");
        } catch (error) {
            mostrarMensaje("No fue posible registrar el pago.","danger");
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
        cargarPagos();
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
    try{
        document.getElementById("modIdPago").value = registro.getIdAbono();
        document.getElementById("modMonto").value = registro.getMonto();
        document.getElementById("modConcepto").value = registro.getConcepto();
        document.getElementById("modalModificar").style.display = "flex";
    }catch (error){
        mostrarMensaje(error);
    }
}

function cerrarModalModificar() {
    document.getElementById("modalModificar").style.display = "none";
}

function modificarPagos() {
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
            const id = parseInt(document.getElementById("modIdPago").value);
            const monto = document.getElementById("modMonto").value.trim();
            const concepto = document.getElementById("modConcepto").value.trim();

            const registro = abonoUI.getAbono();
            registro.setIdAbono(id);
            registro.setMonto(monto);
            registro.setConcepto(concepto);
            const modificado = abonoUI.modificar();
            if (modificado){
                cerrarModalModificar();
                mostrarMensaje("Pago modificado correctamente.", "success");
                cargarPagos();
            }else
                mostrarMensaje("No fue posible modificar el pago.", "danger");
        } catch (error) {
            mostrarMensaje("No fue posible modificar el pago.", "danger");
        }
    });
}

//ELIMINACION
function inicializarEliminar() {
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();
        window.abonoUI.eliminar(idAbonoAEliminar);
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarPagos();
    });
    formulario.addEventListener("reset", function (event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}
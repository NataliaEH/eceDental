let ordenFecha = "desc";
let idRecordatorioAEliminar = 0;
function inicializarRecordatorios() {
    cargarRecordatorios();
    guardarRecordatorios();
    inicializarFiltro();
    inicializarOrden();
    inicializarEliminar();

    try {
        const nombre = window.doctorUI.consultarDatos().getNombreCompleto();
        document.getElementById("usuario").textContent = nombre;
    } catch (error) {
        //
    }
}

function cargarRecordatorios() {
    try {
        const tabla = document.getElementById("tablaRecordatorios");
        const urgenciaFiltro = document.getElementById("urgenciaFiltro").value;
        let registros;
        if (urgenciaFiltro === "") {
            registros = recordatorioUI.consultar();
        } else {
            registros = recordatorioUI.buscarPorUrgencia(urgenciaFiltro);
        }
        tabla.innerHTML = "";

        const lista = [];
        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);
            lista.push({
                id: Number(registro.getIdRecordatorio()),
                fecha: String(registro.getFechaLimite()),
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
            const recordatorio = lista[i];

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = recordatorio.fecha;
            const tdUrgencia = document.createElement("td");

            const fecha = new Date(recordatorio.fecha);
            const hoy = new Date();
            fecha.setHours(0, 0, 0, 0);
            hoy.setHours(0, 0, 0, 0);
            const diferencia = Math.floor((fecha - hoy) / (1000 * 60 * 60 * 24));
            if (diferencia < 0) {
                tdUrgencia.textContent = "Vencida";
            } else if (diferencia <= 2) {
                tdUrgencia.textContent = "Alta";
            } else if (diferencia > 7) {
                tdUrgencia.textContent = "Baja";
            } else {
                tdUrgencia.textContent = "Media";
            }

            const tdContenido = document.createElement("td");
            tdContenido.textContent = recordatorio.contenido;

            const tdAcciones = document.createElement("td");
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                idRecordatorioAEliminar = recordatorio.id;
                document.getElementById("confirmar-eliminar").style.display = "flex";
            });

            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdUrgencia);
            fila.appendChild(tdContenido);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
        actualizarIconoOrden();
    } catch (error) {
        mostrarMensaje("No fue posible cargar los recordatorios.","danger");
        mostrarMensaje(error,"danger");
    }
}

function guardarRecordatorios() {
    const formulario = document.getElementById("formRecordatorio");
    formulario.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!formulario.reportValidity()) {
            return;
        }

        try {
            const recordatorio = window.recordatorioUI.getRecordatorio();
            const fecha = document.getElementById("fechaLimite").value;
            const contenido = document.getElementById("contenido").value.trim();
            const fechaReg = recordatorioUI.setFechaLimite(fecha);
            recordatorio.setContenido(contenido);

            const registrado = window.recordatorioUI.registrar();
            if(registrado && fechaReg){
                mostrarMensaje("Recordatorio registrado correctamente.","success");
                cargarRecordatorios();
                formulario.reset();
            }else
                mostrarMensaje("No fue posible registrar el recordatorio.","danger");
        } catch (error) {
            mostrarMensaje("No fue posible registrar el recordatorio.","danger");
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
        cargarRecordatorios();
    });
}
function actualizarIconoOrden() {
    const icono = document.querySelector(".sort-link .sort-icon");
    if (icono) {
        icono.textContent = ordenFecha === "asc" ? "▲" : "▼";
    }
}
function inicializarFiltro(){
    const formulario = document.getElementById("formFiltroRecordatorios");
    if (!formulario) return;

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();
        cargarRecordatorios();
    });
    formulario.addEventListener("reset", function (event) {
        event.preventDefault();
        document.getElementById("urgenciaFiltro").value = "";
        cargarRecordatorios();
    });
}
//ELIMINACION
function inicializarEliminar(){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.recordatorioUI.eliminar(idRecordatorioAEliminar);
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarRecordatorios();
        mostrarMensaje("Recordatorio eliminado correctamente.","success");
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}
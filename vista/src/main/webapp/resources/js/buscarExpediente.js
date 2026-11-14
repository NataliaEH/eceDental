let expedientesCache = [];
let orden = { campo: "ultimaConsulta", direccion: "desc" };

function inicializarBuscarExpedientes() {
    cargarExpedientes();
    inicializarBuscador();
    inicializarOrden();
}

function cargarExpedientes() {
    try {
        const lista = window.expedienteUI.consultar();
        expedientesCache = [];

        for (let i = 0; i < lista.size(); i++) {
            const expediente = lista.get(i);
            const id = Number(expediente.getIdExpediente());
            const ultima = window.notaEvolucionUI.buscarUltima(id);

            expedientesCache.push({
                id: id,
                nombre: String(expediente.getPaciente().getNombre()),
                fechaConsulta: ultima != null ? String(ultima.getFecha()) : "",
                fechaCreacion: String(expediente.getFechaCreacion())
            });
        }
        mostrarTabla();
    } catch (error) {
        mostrarMensaje("No fue posible cargar los expedientes.", "danger");
    }
}

function mostrarTabla() {
    const tabla = document.getElementById("tablaExpedientes");
    const nombre = document.getElementById("nombrePaciente").value.trim().toLowerCase();

    const lista = expedientesCache.filter(function (e) {
        return e.nombre.toLowerCase().includes(nombre);
    });

    lista.sort(function (a, b) {
        let resultado = 0;

        if (orden.campo === "nombre") {
            resultado = a.nombre.localeCompare(b.nombre, "es", { sensitivity: "base" });
        } else {
            // Fechas en formato yyyy-MM-dd: se comparan como texto
            if (a.fechaConsulta < b.fechaConsulta) {
                resultado = -1;
            } else if (a.fechaConsulta > b.fechaConsulta) {
                resultado = 1;
            }
        }

        if (resultado === 0) {
            resultado = a.nombre.localeCompare(b.nombre, "es", { sensitivity: "base" });
        } else if (orden.direccion === "desc") {
            resultado = -resultado;
        }
        return resultado;
    });

    tabla.innerHTML = "";

    if (lista.length === 0) {
        const fila = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 4;
        td.textContent = expedientesCache.length === 0
            ? "No hay expedientes registrados."
            : "No se encontraron expedientes.";
        fila.appendChild(td);
        tabla.appendChild(fila);
    }

    for (let i = 0; i < lista.length; i++) {
        const e = lista[i];
        const fila = document.createElement("tr");

        const tdNombre = document.createElement("td");
        tdNombre.textContent = e.nombre;
        const tdConsulta = document.createElement("td");
        tdConsulta.textContent = e.fechaConsulta === "" ? "Sin consultas" : e.fechaConsulta;
        const tdCreacion = document.createElement("td");
        tdCreacion.textContent = e.fechaCreacion;

        const tdAcciones = document.createElement("td");
        const enlace = document.createElement("a");
        enlace.href = "dashboardExpediente.html";
        enlace.className = "btn btn-sm btn-secondary";
        enlace.textContent = "Ver";
        enlace.addEventListener("click", function () {
            sessionStorage.setItem("expedienteActualId", String(e.id));
        });
        tdAcciones.appendChild(enlace);

        fila.appendChild(tdNombre);
        fila.appendChild(tdConsulta);
        fila.appendChild(tdCreacion);
        fila.appendChild(tdAcciones);
        tabla.appendChild(fila);
    }

    actualizarIconosOrden();
}

function inicializarOrden() {
    const enlaces = document.querySelectorAll(".sort-link");
    for (let i = 0; i < enlaces.length; i++) {
        enlaces[i].addEventListener("click", function (event) {
            event.preventDefault();
            const campo = this.dataset.sort;

            if (orden.campo === campo) {
                orden.direccion = orden.direccion === "asc" ? "desc" : "asc";
            } else {
                orden.campo = campo;
                orden.direccion = this.dataset.order;
            }
            mostrarTabla();
        });
    }
}
function actualizarIconosOrden() {
    const enlaces = document.querySelectorAll(".sort-link");
    for (let i = 0; i < enlaces.length; i++) {
        const icono = enlaces[i].querySelector(".sort-icon");
        if (enlaces[i].dataset.sort === orden.campo) {
            icono.textContent = orden.direccion === "asc" ? "▲" : "▼";
        } else {
            icono.textContent = "▼";
        }
    }
}

function inicializarBuscador() {
    const formulario = document.getElementById("formBuscarExpediente");
    if (!formulario) return;

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();
        mostrarTabla();
    });

    formulario.addEventListener("reset", function (event) {
        event.preventDefault();
        document.getElementById("nombrePaciente").value = "";
        orden = { campo: "ultimaConsulta", direccion: "desc" };
        mostrarTabla();
    });
}

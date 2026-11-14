function inicializarArchivos() {
    cargarArchivos();
    guardarArchivos();
    inicializarFiltro();

    document.getElementById("archivo").addEventListener("click", function (event) {
        event.preventDefault();
        const nombreElegido = archivoUI.seleccionarArchivo();
        if (nombreElegido) {
            document.getElementById("nombreArchivoElegido").textContent = nombreElegido;
        }
    });
}

function cargarArchivos() {
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
        const tabla = document.getElementById("tablaArchivos");
        const tipoFiltro = document.getElementById("tipoFiltro").value;
        let registros;
        if (tipoFiltro === "") {
            registros = archivoUI.consultar(idExpediente);
        } else {
            registros = archivoUI.buscarPorTipo(tipoFiltro);
        }
        tabla.innerHTML = "";

        for (let i = 0; i < registros.size(); i++) {
            const registro = registros.get(i);

            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }
            if (Number(registro.getExpediente().getIdExpediente()) !== Number(idExpediente)) {
                continue;
            }
            const idArchivo = Number(registro.getIdArchivo());

            const fila = document.createElement("tr");
            const fechaTexto = registro.getFecha();
            const tdFecha = document.createElement("td");
            tdFecha.textContent = fechaTexto;
            const tdNombre = document.createElement("td");
            tdNombre.textContent = registro.getNombre();
            const tdTipo = document.createElement("td");
            tdTipo.textContent = registro.getTipo();

            //ACCIONES (MODIFICAR Y ELIMINAR)
            const tdAcciones = document.createElement("td");

            const botonVer = document.createElement("button");
            botonVer.type = "button";
            botonVer.className = "btn btn-sm btn-primary";
            botonVer.style.marginRight = "6px";
            botonVer.textContent = "Ver";
            botonVer.addEventListener("click", function () {
                try{
                    const ver = window.archivoUI.abrirArchivo(idArchivo);
                    mostrarMensaje(idArchivo.toString());
                    if(ver)
                        mostrarMensaje("Archivo abierto","success");
                    else
                        mostrarMensaje("El archivo no pudo ser abierto","danger");
                }catch(error){
                    mostrarMensaje(error);
                }
            });
            const botonDescargar = document.createElement("button");
            botonDescargar.type = "button";
            botonDescargar.className = "btn btn-sm btn-primary";
            botonDescargar.style.marginRight = "6px";
            botonDescargar.textContent = "Descargar";
            botonDescargar.addEventListener("click", function () {
                try{
                    const descargado = window.archivoUI.descargar(idArchivo);
                    mostrarMensaje(idArchivo.toString());
                    if(descargado)
                        mostrarMensaje("Archivo descargado","success");
                    else
                        mostrarMensaje("El archivo no pudo ser descargado","danger");
                }catch(error){
                    mostrarMensaje(error);
                }
            });
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-sm btn-muted";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.addEventListener("click", function () {
                document.getElementById("confirmar-eliminar").style.display = "flex";
                inicializarEliminar(registro);
            });

            tdAcciones.appendChild(botonVer);
            tdAcciones.appendChild(botonDescargar);
            tdAcciones.appendChild(botonEliminar);

            fila.appendChild(tdFecha);
            fila.appendChild(tdNombre);
            fila.appendChild(tdTipo);
            fila.appendChild(tdAcciones);

            tabla.appendChild(fila);
        }
    } catch (error) {
        mostrarMensaje("No fue posible cargar los datos del paciente.","danger");
    }
}

function guardarArchivos() {
    const formulario = document.getElementById("formArchivo");
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
            const archivo = archivoUI.getArchivo();
            const nombre = document.getElementById("nombreArchivo").value.trim();
            const tipo = document.getElementById("tipoArchivo").value.trim();

            archivo.setNombre(nombre);
            archivo.setTipo(tipo);
            archivo.setExpediente(expediente);
            const registrado = archivoUI.registrar();
            if(registrado){
                mostrarMensaje("Archivo registrado correctamente.","success");
                cargarArchivos();
                formulario.reset();
            }else{
                mostrarMensaje("No fue posible registrar el archivo.","danger");
            }
        } catch (error) {
            mostrarMensaje("No fue posible registrar el archivo.","danger");
            mostrarMensaje(error, "danger")
        }
    });
}

//ELIMINACION
function inicializarEliminar(registro){
    const formulario = document.getElementById("formEliminar");
    if (!formulario) return;

    formulario.addEventListener("submit", function(event) {
        event.preventDefault();
        window.archivoUI.eliminar(registro.getIdArchivo());
        document.getElementById("confirmar-eliminar").style.display = "none";
        cargarArchivos();
    });
    formulario.addEventListener("reset", function(event) {
        event.preventDefault();
        document.getElementById("confirmar-eliminar").style.display = "none";
        window.location.hash = "";
    });
}

function inicializarFiltro(){
    const formulario = document.getElementById("formFiltroArchivos");
    if (!formulario) return;

    formulario.addEventListener("submit", function (event) {
        event.preventDefault();
        cargarArchivos();
    });
    formulario.addEventListener("reset", function (event) {
        event.preventDefault();
        document.getElementById("tipoFiltro").value = "";
        cargarArchivos();
    });
}
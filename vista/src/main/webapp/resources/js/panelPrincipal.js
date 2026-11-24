function inicializarPanelPrincipal() {
    try {
        const nombre = window.doctorUI.consultarDatos().getNombreCompleto();
        document.getElementById("usuario").textContent = nombre;
    } catch (error) {
        //
    }

    const totalElement = document.getElementById("totalExpedientes");
    const viejosElement = document.getElementById("expedientesViejos");
    const recPendientes = document.getElementById("recordatoriosPendientes");

    if (!totalElement || !viejosElement || !recPendientes) return;

    const expedientes = expedienteUI.consultar();
    const recordatoriosUrgentes = recordatorioUI.buscarUrgentes();
    const hoy = new Date();

    let total = expedientes.size();
    let viejos = 0;

    for (let i = 0; i < expedientes.size(); i++) {
        const expediente = expedientes.get(i);
        const fechaCreacion = expediente.getFechaCreacion().toString();

        if (!fechaCreacion) continue;

        const fecha = new Date(fechaCreacion + "T00:00:00");
        const fechaLimite = new Date(fecha);
        fechaLimite.setFullYear(fechaLimite.getFullYear() + 5);

        if (hoy >= fechaLimite) {
            viejos++;
        }
    }

    const tabla = document.getElementById("tablaRecordatorios");
    tabla.innerHTML = "";
    if(recordatoriosUrgentes.size()<=0){
        mostrarMensaje("No hay recordatorios urgentes.","info");
    }else{
        for(let i = 0; i < recordatoriosUrgentes.size(); i++){
            const registro = recordatoriosUrgentes.get(i);

            const fila = document.createElement("tr");
            const tdFecha = document.createElement("td");
            tdFecha.textContent = registro.getFechaLimite();

            const tdUrgencia = document.createElement("td");

            const fecha = new Date(registro.getFechaLimite());
            const hoy = new Date();
            fecha.setHours(0, 0, 0, 0);
            hoy.setHours(0, 0, 0, 0);
            const diferencia = Math.floor((fecha - hoy) / (1000 * 60 * 60 * 24));
            let textoUrgencia;
            let claseUrgencia;
            if (diferencia < 0) {
                textoUrgencia = "Vencida";
                claseUrgencia = "urgencia-vencida";
            } else if (diferencia <= 2) {
                textoUrgencia = "Alta";
                claseUrgencia = "urgencia-alta";
            }
            const capsula = document.createElement("span");
            capsula.className = "urgencia-badge " + claseUrgencia;
            capsula.textContent = textoUrgencia;
            tdUrgencia.appendChild(capsula);

            const tdContenido = document.createElement("td");
            tdContenido.textContent = registro.getContenido();

            fila.appendChild(tdFecha);
            fila.appendChild(tdUrgencia);
            fila.appendChild(tdContenido);
            tabla.appendChild(fila);
        }
    }

    totalElement.textContent = total;
    viejosElement.textContent = viejos;
    recPendientes.textContent = window.recordatorioUI.consultar().size();
}

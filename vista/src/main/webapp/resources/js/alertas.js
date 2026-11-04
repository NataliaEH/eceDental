function mostrarMensaje(mensaje, tipo = "info", duracion = 6000) {
    const contenedor = document.getElementById("contenedorMensajes");
    if (!contenedor) return;
    const alerta = document.createElement("div");
    alerta.className = `alert alert-${tipo}`;
    alerta.textContent = mensaje;

    contenedor.appendChild(alerta);

    if (duracion > 0) {
        setTimeout(() => {
            alerta.remove();
        }, duracion);
    }
}
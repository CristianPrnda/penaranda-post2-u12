package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.PedidoProcesadoEvent;

/**
 * Puerto de dominio para el patron Observer.
 * Desacopla el servicio de aplicacion de los mecanismos concretos de notificacion.
 */
public interface ServicioNotificacion {
    void notificar(PedidoProcesadoEvent evento);
}

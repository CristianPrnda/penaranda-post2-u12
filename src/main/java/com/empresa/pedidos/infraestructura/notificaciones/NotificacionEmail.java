package com.empresa.pedidos.infraestructura.notificaciones;

import com.empresa.pedidos.dominio.PedidoProcesadoEvent;
import com.empresa.pedidos.dominio.puertos.ServicioNotificacion;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener de notificacion por email.
 * Implementa el patron Observer recibiendo PedidoProcesadoEvent
 * publicado por Spring ApplicationEventPublisher.
 */
@Component
public class NotificacionEmail implements ServicioNotificacion {

    @EventListener
    @Override
    public void notificar(PedidoProcesadoEvent evento) {
        System.out.println("Email enviado para pedido: "
                + evento.pedido().getId()
                + " | Costo: " + evento.pedido().getCosto());
    }
}

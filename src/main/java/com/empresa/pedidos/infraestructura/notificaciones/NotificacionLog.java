package com.empresa.pedidos.infraestructura.notificaciones;

import com.empresa.pedidos.dominio.PedidoProcesadoEvent;
import com.empresa.pedidos.dominio.puertos.ServicioNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener de notificacion por log.
 * Implementa el patron Observer junto a NotificacionEmail,
 * ambos responden independientemente al mismo evento de dominio.
 */
@Component
public class NotificacionLog implements ServicioNotificacion {

    private static final Logger log = LoggerFactory.getLogger(NotificacionLog.class);

    @EventListener
    @Override
    public void notificar(PedidoProcesadoEvent evento) {
        log.info("Pedido procesado: {} - Costo: {}",
                evento.pedido().getId(),
                evento.pedido().getCosto());
    }
}

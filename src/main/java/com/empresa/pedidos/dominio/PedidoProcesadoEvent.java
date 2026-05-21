package com.empresa.pedidos.dominio;

/**
 * Evento de dominio publicado cuando un pedido ha sido procesado exitosamente.
 */
public record PedidoProcesadoEvent(Pedido pedido) {}

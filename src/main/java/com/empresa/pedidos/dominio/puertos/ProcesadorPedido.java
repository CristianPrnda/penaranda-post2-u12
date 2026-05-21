package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;

/**
 * Puerto de dominio que define el contrato del patron Strategy
 * para el procesamiento de pedidos segun su tipo.
 * Cada implementacion encapsula el algoritmo de calculo de costo
 * para un tipo de pedido especifico.
 */
public interface ProcesadorPedido {
    TipoPedido getTipo();
    void procesar(Pedido pedido);
}

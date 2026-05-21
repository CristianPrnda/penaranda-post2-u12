package com.empresa.pedidos.dominio.puertos;

import com.empresa.pedidos.dominio.Pedido;
import java.util.Optional;

/**
 * Puerto de dominio para la persistencia de pedidos.
 * Desacopla la capa de aplicacion de la implementacion de infraestructura.
 */
public interface RepositorioPedidos {
    Pedido guardar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
}

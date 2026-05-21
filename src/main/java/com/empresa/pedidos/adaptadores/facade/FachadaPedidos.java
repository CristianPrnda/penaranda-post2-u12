package com.empresa.pedidos.adaptadores.facade;

import com.empresa.pedidos.aplicacion.ProcesadorPedidoFactory;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.puertos.RepositorioPedidos;
import com.empresa.pedidos.dominio.PedidoProcesadoEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Fachada que simplifica la interfaz del sistema de pedidos para el controlador REST.
 * Oculta la complejidad de coordinar Factory, Strategy, repositorio y Observer,
 * exponiendo solo las operaciones necesarias para el cliente HTTP.
 * CC = 1 (sin condicionales en el flujo principal).
 */
@Service
public class FachadaPedidos {

    private final ProcesadorPedidoFactory factory;
    private final RepositorioPedidos repositorio;
    private final ApplicationEventPublisher publisher;

    public FachadaPedidos(ProcesadorPedidoFactory factory,
                          RepositorioPedidos repositorio,
                          ApplicationEventPublisher publisher) {
        this.factory      = factory;
        this.repositorio  = repositorio;
        this.publisher    = publisher;
    }

    /**
     * Crea y procesa un pedido: selecciona la estrategia via Factory,
     * persiste el resultado y publica el evento para los listeners Observer.
     *
     * @param pedido pedido a procesar
     * @return pedido guardado con costo y estado actualizados
     */
    public Pedido crearPedido(Pedido pedido) {
        factory.obtener(pedido.getTipo()).procesar(pedido);
        var guardado = repositorio.guardar(pedido);
        publisher.publishEvent(new PedidoProcesadoEvent(guardado));
        return guardado;
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador del pedido
     * @return Optional con el pedido si existe
     */
    public Optional<Pedido> buscarPorId(Long id) {
        return repositorio.buscarPorId(id);
    }
}

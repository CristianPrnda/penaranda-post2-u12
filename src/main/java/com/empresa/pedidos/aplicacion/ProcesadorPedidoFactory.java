package com.empresa.pedidos.aplicacion;

import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Factory que selecciona dinamicamente la estrategia de procesamiento
 * segun el tipo de pedido. Spring inyecta todas las implementaciones
 * de ProcesadorPedido y la Factory las indexa por TipoPedido.
 * Aplica el patron Factory Method combinado con Strategy.
 */
@Component
public class ProcesadorPedidoFactory {

    private final Map<TipoPedido, ProcesadorPedido> procesadores;

    public ProcesadorPedidoFactory(List<ProcesadorPedido> lista) {
        this.procesadores = lista.stream().collect(
                Collectors.toMap(ProcesadorPedido::getTipo, Function.identity())
        );
    }

    /**
     * Retorna el procesador correspondiente al tipo de pedido.
     *
     * @param tipo tipo de pedido a procesar
     * @return implementacion de ProcesadorPedido para ese tipo
     * @throws IllegalArgumentException si el tipo no tiene procesador registrado
     */
    public ProcesadorPedido obtener(TipoPedido tipo) {
        return Optional.ofNullable(procesadores.get(tipo))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tipo de pedido no soportado: " + tipo));
    }
}

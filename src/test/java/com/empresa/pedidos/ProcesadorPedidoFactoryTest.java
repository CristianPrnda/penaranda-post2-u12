package com.empresa.pedidos;

import com.empresa.pedidos.adaptadores.procesadores.*;
import com.empresa.pedidos.aplicacion.ProcesadorPedidoFactory;
import com.empresa.pedidos.dominio.EstadoPedido;
import com.empresa.pedidos.dominio.Pedido;
import com.empresa.pedidos.dominio.TipoPedido;
import com.empresa.pedidos.dominio.puertos.ProcesadorPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del patron Factory y Strategy.
 * Verifica que cada tipo de pedido retorna la implementacion correcta
 * y que el calculo de costo es el esperado.
 */
class ProcesadorPedidoFactoryTest {

    private ProcesadorPedidoFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ProcesadorPedidoFactory(List.of(
                new ProcesadorPedidoEstandar(),
                new ProcesadorPedidoExpress(),
                new ProcesadorPedidoInternacional()
        ));
    }

    // ---- Factory: seleccion correcta por tipo ----

    @Test
    @DisplayName("Factory debe retornar ProcesadorPedidoEstandar para ESTANDAR")
    void factory_tipoEstandar_debeRetornarProcesadorCorrecto() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.ESTANDAR);
        assertEquals(TipoPedido.ESTANDAR, procesador.getTipo());
    }

    @Test
    @DisplayName("Factory debe retornar ProcesadorPedidoExpress para EXPRESS")
    void factory_tipoExpress_debeRetornarProcesadorCorrecto() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.EXPRESS);
        assertEquals(TipoPedido.EXPRESS, procesador.getTipo());
    }

    @Test
    @DisplayName("Factory debe retornar ProcesadorPedidoInternacional para INTERNACIONAL")
    void factory_tipoInternacional_debeRetornarProcesadorCorrecto() {
        ProcesadorPedido procesador = factory.obtener(TipoPedido.INTERNACIONAL);
        assertEquals(TipoPedido.INTERNACIONAL, procesador.getTipo());
    }

    @Test
    @DisplayName("Factory con tipo nulo debe lanzar excepcion")
    void factory_tipoNulo_debeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> factory.obtener(null));
    }

    // ---- Strategy: calculo de costo correcto ----

    @Test
    @DisplayName("Strategy ESTANDAR debe aplicar incremento del 10%")
    void strategy_estandar_debeCobrarSubtotalMas10Porciento() {
        Pedido pedido = new Pedido("Test", 100.0, TipoPedido.ESTANDAR);
        factory.obtener(TipoPedido.ESTANDAR).procesar(pedido);
        assertEquals(110.0, pedido.getCosto(), 0.001);
        assertEquals(EstadoPedido.PROCESADO, pedido.getEstado());
    }

    @Test
    @DisplayName("Strategy EXPRESS debe aplicar incremento del 30%")
    void strategy_express_debeCobrarSubtotalMas30Porciento() {
        Pedido pedido = new Pedido("Test", 100.0, TipoPedido.EXPRESS);
        factory.obtener(TipoPedido.EXPRESS).procesar(pedido);
        assertEquals(130.0, pedido.getCosto(), 0.001);
        assertEquals(EstadoPedido.PROCESADO, pedido.getEstado());
    }

    @Test
    @DisplayName("Strategy INTERNACIONAL debe aplicar 50% mas cargo fijo de 25")
    void strategy_internacional_debeCobrarSubtotalMas50PorcentoMas25() {
        Pedido pedido = new Pedido("Test", 100.0, TipoPedido.INTERNACIONAL);
        factory.obtener(TipoPedido.INTERNACIONAL).procesar(pedido);
        assertEquals(175.0, pedido.getCosto(), 0.001);
        assertEquals(EstadoPedido.PROCESADO, pedido.getEstado());
    }
}

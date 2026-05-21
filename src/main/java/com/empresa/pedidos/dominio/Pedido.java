package com.empresa.pedidos.dominio;

import jakarta.persistence.*;

/**
 * Entidad de dominio que representa un pedido en el sistema.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private double subtotal;

    private double costo;

    @Enumerated(EnumType.STRING)
    private TipoPedido tipo;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    public Pedido() {
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Pedido(String descripcion, double subtotal, TipoPedido tipo) {
        this.descripcion = descripcion;
        this.subtotal = subtotal;
        this.tipo = tipo;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public TipoPedido getTipo() { return tipo; }
    public void setTipo(TipoPedido tipo) { this.tipo = tipo; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
}

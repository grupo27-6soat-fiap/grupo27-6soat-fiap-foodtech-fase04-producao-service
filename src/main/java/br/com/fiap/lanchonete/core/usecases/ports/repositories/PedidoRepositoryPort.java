package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;

public interface PedidoRepositoryPort {
	Pedido save(Pedido pedido);
	Pedido get(Long id);
}

package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;

public interface HistoricoPedidoRepositoryPort {
	HistoricoPedido save(HistoricoPedido historicoPedido);
}

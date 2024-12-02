package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoPedidoRepositoryPort;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReceivePedidoUsecase {

	private final PedidoRepositoryPort pedidoRepositoryPort;
	private final HistoricoPedidoRepositoryPort historicoPedidoRepositoryPort;

	public Pedido receive(Long id) {
		Pedido pedido = pedidoRepositoryPort.get(id);
		if (Objects.nonNull(pedido)) {
			throw new EntityExistsException("Pedido ja foi recebido. Id: " + id);
		}
		pedido = pedidoRepositoryPort.save(Pedido.builder()
				.idPedido(id)
				.status(StatusEnum.EM_PRODUCAO)
				.build());
		HistoricoPedido historicoPedido = HistoricoPedido.builder()
				.idPedido(pedido.getIdPedido())
				.status(pedido.getStatus())
				.build();
		historicoPedidoRepositoryPort.save(historicoPedido);
		return pedido;
	}

}

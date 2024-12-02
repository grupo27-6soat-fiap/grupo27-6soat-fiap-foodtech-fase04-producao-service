/*package br.com.fiap.lanchonete.infra.db.mappers;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.infra.db.entities.HistoricoPedidoEntity;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HistoricoPedidoDataMapper {

	private final ModelMapper modelMapper;

	public HistoricoPedidoDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public HistoricoPedido toDomain(HistoricoPedidoEntity data) {
		return modelMapper.map(data, HistoricoPedido.class);
	}

	public HistoricoPedidoEntity toData(HistoricoPedido pedido) {
		return modelMapper.map(pedido, HistoricoPedidoEntity.class);
	}
	
}

*/
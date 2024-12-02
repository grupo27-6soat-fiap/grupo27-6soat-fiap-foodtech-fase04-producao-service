/* package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoPedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.HistoricoPedidoEntity;
import br.com.fiap.lanchonete.infra.db.mappers.HistoricoPedidoDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.HistoricoPedidoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HistoricoPedidoPostgresqlRepository implements HistoricoPedidoRepositoryPort {

	@Autowired
	private HistoricoPedidoRepository historicoPedidoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public HistoricoPedidoDataMapper historicoPedidoDataMapper;

	@Override
	public HistoricoPedido save(HistoricoPedido historicoPedido) {
		HistoricoPedidoEntity historicoPedidoSchema = historicoPedidoDataMapper.toData(historicoPedido);
		return modelMapper.map(historicoPedidoRepository.save(historicoPedidoSchema), HistoricoPedido.class);
	}
	
}
*/
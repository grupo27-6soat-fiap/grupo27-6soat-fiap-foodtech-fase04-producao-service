/* package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.PedidoEntity;
import br.com.fiap.lanchonete.infra.db.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.PedidoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PedidoPostgresqlRepository implements PedidoRepositoryPort {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public PedidoDataMapper pedidoDataMapper;

	@Override
	public Pedido get(Long id) {
		return pedidoRepository.findById(id).map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
	}

	@Override
	public Pedido save(Pedido pedido) {
		PedidoEntity pedidoSchema = pedidoDataMapper.toData(pedido);
		return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
	}
	
}

*/

package br.com.fiap.lanchonete.infra.db.mongodb;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoMongoRepository;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoPedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.HistoricoMongoEntity;

@Component
public class HistoricoPedidoMongdobAdapter implements HistoricoPedidoRepositoryPort {


    @Autowired
	private HistoricoMongoRepository historicoMongoRepository;

	@Autowired
	public ModelMapper modelMapper;



    @Override
    public HistoricoPedido save(HistoricoPedido historicoPedido) {

        HistoricoMongoEntity mongoEntity = modelMapper.map(historicoPedido, HistoricoMongoEntity.class);
         mongoEntity = historicoMongoRepository.save(mongoEntity);

        return modelMapper.map(mongoEntity, HistoricoPedido.class);
     
       
    }

}

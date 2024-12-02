package br.com.fiap.lanchonete.infra.db.mongodb;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoMongoRepository;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.PedidoMongoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMongoRepositoryAdapter implements PedidoRepositoryPort {

    @Autowired
    private PedidoMongoRepository pedidoMongoRepository;

    @Autowired
    private ModelMapper modelMapper;





    @Override
    public Pedido save(Pedido pedido) {

        PedidoMongoEntity mongoEntity = modelMapper.map(pedido, PedidoMongoEntity.class);
        if (mongoEntity.getTimestampCriacao() != null) {
            mongoEntity.markAsPersisted();  
        }


         mongoEntity = pedidoMongoRepository.save(mongoEntity);

        return modelMapper.map(mongoEntity, Pedido.class);
    }

    @Override
    public Pedido get(Long id) {
        PedidoMongoEntity mongoEntity = pedidoMongoRepository.findById(id).orElse(null);
        if (mongoEntity == null) {
            return null;
        }

        return modelMapper.map(mongoEntity, Pedido.class);
        
    }

}

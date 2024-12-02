package br.com.fiap.lanchonete.core.usecases.ports.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.lanchonete.infra.db.entities.PedidoMongoEntity;


public interface PedidoMongoRepository extends MongoRepository<PedidoMongoEntity, Long> {

}

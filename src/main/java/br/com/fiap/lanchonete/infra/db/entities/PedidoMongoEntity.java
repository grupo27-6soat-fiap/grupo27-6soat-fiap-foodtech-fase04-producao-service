package br.com.fiap.lanchonete.infra.db.entities;

import java.time.Instant;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Auditable;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "pedidos")
@NoArgsConstructor
@Getter
@Setter
public class PedidoMongoEntity implements Persistable<Long> {

    @Id
    private Long idPedido;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreatedDate
    private Instant timestampCriacao;

    @LastModifiedDate
    private Instant timestampAlteracao;

    private transient boolean isNew = true;


    @Override
    public Long getId() {
       
        return idPedido;
    }

    @Override
    public boolean isNew() {
       
        return  isNew;
    }

    public void markAsPersisted() {
        this.isNew = false;
    }


    // Getters and Setters
}
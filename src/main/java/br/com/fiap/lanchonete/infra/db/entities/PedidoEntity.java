/* package br.com.fiap.lanchonete.infra.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;

import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="pedido")
public class PedidoEntity {

    @Id
    private Long idPedido;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    private LocalDateTime timestampCriacao;

    @UpdateTimestamp
    private LocalDateTime timestampAlteracao;

}

*/
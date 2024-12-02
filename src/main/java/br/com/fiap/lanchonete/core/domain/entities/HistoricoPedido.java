package br.com.fiap.lanchonete.core.domain.entities;


import java.time.Instant;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HistoricoPedido {
    private String id;
    private Long idPedido;
    private StatusEnum status;
    private Instant timestampCriacao;
}

package br.com.fiap.lanchonete.core.event;


import org.joda.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PedidoCanceladoEvent {

    private String orderId;
    private String reason;
    private LocalDateTime timestamp;

}
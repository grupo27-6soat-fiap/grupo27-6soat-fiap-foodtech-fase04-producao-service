package br.com.fiap.lanchonete.infra.messaging;

import br.com.fiap.lanchonete.core.event.PedidoPagoEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.awspring.cloud.sqs.annotation.SqsListener;

import br.com.fiap.lanchonete.core.event.PedidoCanceladoEvent;
import br.com.fiap.lanchonete.core.usecases.pedido.CanceledPedidoUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.ReceivePedidoUsecase;
import org.springframework.beans.factory.annotation.Autowired;

public class SQSMessageListener {

    //private static final Logger logger = LoggerFactory.getLogger(SQSMessageListener.class);


    @Autowired
    private  ReceivePedidoUsecase receivePedidoUsecase ;
    @Autowired
    private  CanceledPedidoUsecase canceledPedidoUsecase;

    @Autowired
    private ObjectMapper objectMapper;




    @SqsListener("${cloud.aws.sqs.queue.producao-pedido-pago}")

    public void processarPedido(String json) throws JsonProcessingException {

        System.out.println("Received message from producao-pedido-pago-queue: {}" + json.toString());

        PedidoPagoEvent deserializedEvent = objectMapper.readValue(json, PedidoPagoEvent.class);


        receivePedidoUsecase.receive(Long.parseLong(deserializedEvent.getOrderId()));
        
    }

    @SqsListener("${cloud.aws.sqs.queue.producao-cancelamento}")
    public void cancelarPedido(String json) throws JsonProcessingException {

        //System.out.println("Pedido cancelado recebido: " + event.getOrderId());

        PedidoCanceladoEvent deserializedEvent = objectMapper.readValue(json, PedidoCanceladoEvent.class);
            
        canceledPedidoUsecase.cancel(Long.parseLong(deserializedEvent.getOrderId()));
    }
}

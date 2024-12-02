package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.PedidoDto;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.pedido.CompletePedidoUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.ReceivePedidoUsecase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTests {

    @Mock
    private ReceivePedidoUsecase receivePedidoUsecase;

    @Mock
    private CompletePedidoUsecase completePedidoUsecase;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PedidoController pedidoController;

    @Test
    void should_return_pedido_dto_on_receive_success() {
        Long pedidoId = 1L;
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoId);
        PedidoDto pedidoDto = new PedidoDto();

        when(receivePedidoUsecase.receive(anyLong())).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoDto.class)).thenReturn(pedidoDto);

        ResponseEntity<PedidoDto> response = pedidoController.receivePedido(pedidoId);

        assertEquals(ResponseEntity.ok(pedidoDto), response);
        verify(receivePedidoUsecase, times(1)).receive(pedidoId);
        verify(modelMapper, times(1)).map(pedido, PedidoDto.class);
    }

    @Test
    void should_return_pedido_dto_on_complete_success() {
        Long pedidoId = 1L;
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoId);
        PedidoDto pedidoDto = new PedidoDto();

        when(completePedidoUsecase.complete(anyLong())).thenReturn(pedido);
        when(modelMapper.map(pedido, PedidoDto.class)).thenReturn(pedidoDto);

        ResponseEntity<PedidoDto> response = pedidoController.processarPedido(pedidoId);

        assertEquals(ResponseEntity.ok(pedidoDto), response);
        verify(completePedidoUsecase, times(1)).complete(pedidoId);
        verify(modelMapper, times(1)).map(pedido, PedidoDto.class);
    }
}

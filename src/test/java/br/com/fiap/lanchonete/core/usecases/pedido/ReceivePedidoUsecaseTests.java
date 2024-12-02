package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoPedidoRepositoryPort;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReceivePedidoUsecaseTests {

    @Mock
    private PedidoRepositoryPort pedidoRepositoryPort;

    @Mock
    private HistoricoPedidoRepositoryPort historicoPedidoRepositoryPort;

    @InjectMocks
    private ReceivePedidoUsecase receivePedidoUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_receive_pedido_successfully() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.EM_PRODUCAO)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null);
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(pedido);

        Pedido result = receivePedidoUsecase.receive(pedidoId);

        assertEquals(StatusEnum.EM_PRODUCAO, result.getStatus());
        verify(pedidoRepositoryPort, times(1)).save(any(Pedido.class));

        ArgumentCaptor<HistoricoPedido> historicoCaptor = ArgumentCaptor.forClass(HistoricoPedido.class);
        verify(historicoPedidoRepositoryPort, times(1)).save(historicoCaptor.capture());
        HistoricoPedido historicoPedido = historicoCaptor.getValue();
        assertEquals(pedidoId, historicoPedido.getIdPedido());
        assertEquals(StatusEnum.EM_PRODUCAO, historicoPedido.getStatus());
    }

    @Test
    void should_throw_entity_exists_exception_when_pedido_already_received() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.EM_PRODUCAO)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(pedido);

        assertThrows(EntityExistsException.class, () -> {
            receivePedidoUsecase.receive(pedidoId);
        });

        verify(pedidoRepositoryPort, never()).save(any(Pedido.class));
        verify(historicoPedidoRepositoryPort, never()).save(any(HistoricoPedido.class));
    }
}

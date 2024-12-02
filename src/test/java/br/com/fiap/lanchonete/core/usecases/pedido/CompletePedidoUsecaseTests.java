package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.HistoricoPedidoRepositoryPort;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompletePedidoUsecaseTests {

    @Mock
    private PedidoRepositoryPort pedidoRepositoryPort;

    @Mock
    private HistoricoPedidoRepositoryPort historicoPedidoRepositoryPort;

    @InjectMocks
    private CompletePedidoUsecase completePedidoUsecase;

    @Test
    void should_complete_pedido_successfully() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.EM_PRODUCAO)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(pedido);
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(pedido);

        Pedido result = completePedidoUsecase.complete(pedidoId);

        assertEquals(StatusEnum.FINALIZADO, result.getStatus());
        verify(pedidoRepositoryPort, times(1)).save(any(Pedido.class));

        ArgumentCaptor<HistoricoPedido> historicoCaptor = ArgumentCaptor.forClass(HistoricoPedido.class);
        verify(historicoPedidoRepositoryPort, times(1)).save(historicoCaptor.capture());
        HistoricoPedido historicoPedido = historicoCaptor.getValue();
        assertEquals(pedidoId, historicoPedido.getIdPedido());
        assertEquals(StatusEnum.FINALIZADO, historicoPedido.getStatus());
    }

    @Test
    void should_throw_entity_not_found_exception_when_pedido_not_found() {
        Long pedidoId = 1L;

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            completePedidoUsecase.complete(pedidoId);
        });

        verify(pedidoRepositoryPort, never()).save(any(Pedido.class));
        verify(historicoPedidoRepositoryPort, never()).save(any(HistoricoPedido.class));
    }

    @Test
    void should_throw_entity_exists_exception_when_pedido_already_finalized() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.FINALIZADO)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(pedido);

        assertThrows(EntityExistsException.class, () -> {
            completePedidoUsecase.complete(pedidoId);
        });

        verify(pedidoRepositoryPort, never()).save(any(Pedido.class));
        verify(historicoPedidoRepositoryPort, never()).save(any(HistoricoPedido.class));
    }
}

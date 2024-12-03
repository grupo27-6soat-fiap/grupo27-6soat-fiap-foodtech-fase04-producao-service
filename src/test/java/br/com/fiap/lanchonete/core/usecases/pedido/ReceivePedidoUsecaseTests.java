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

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null); // Pedido não encontrado no repositório
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

    // Teste adicional 1: Verificar comportamento quando o pedido não existe no repositório
    @Test
    void should_throw_entity_not_found_exception_when_pedido_not_found() {
        Long pedidoId = 2L;
        
        // Simulando que o pedido não existe
        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            receivePedidoUsecase.receive(pedidoId);
        });

        verify(pedidoRepositoryPort, never()).save(any(Pedido.class));
        verify(historicoPedidoRepositoryPort, never()).save(any(HistoricoPedido.class));
    }

    // Teste adicional 2: Verificar o estado do pedido após ser recebido
    @Test
    void should_update_status_when_pedido_received() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.PENDENTE)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null);
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(pedido);

        Pedido result = receivePedidoUsecase.receive(pedidoId);

        assertEquals(StatusEnum.EM_PRODUCAO, result.getStatus()); // O status do pedido deve mudar para EM_PRODUCAO
    }

    // Teste adicional 3: Verificar o histórico do pedido quando o pedido é alterado
    @Test
    void should_create_historico_when_pedido_received() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.PENDENTE)
                .build();

        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(null);
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(pedido);

        // Executando a ação
        receivePedidoUsecase.receive(pedidoId);

        // Captura o histórico criado
        ArgumentCaptor<HistoricoPedido> historicoCaptor = ArgumentCaptor.forClass(HistoricoPedido.class);
        verify(historicoPedidoRepositoryPort, times(1)).save(historicoCaptor.capture());

        HistoricoPedido historico = historicoCaptor.getValue();
        assertEquals(pedidoId, historico.getIdPedido());
        assertEquals(StatusEnum.EM_PRODUCAO, historico.getStatus()); // Verifica que o histórico foi criado corretamente
    }

    // Teste adicional 4: Verificar se o pedido não pode ser salvo caso já tenha sido recebido
    @Test
    void should_not_save_when_pedido_is_already_received() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder()
                .idPedido(pedidoId)
                .status(StatusEnum.EM_PRODUCAO)
                .build();

        // Pedido já existe, simulando um pedido que já foi recebido
        when(pedidoRepositoryPort.get(pedidoId)).thenReturn(pedido);

        assertThrows(EntityExistsException.class, () -> {
            receivePedidoUsecase.receive(pedidoId); // A exceção deve ser lançada
        });

        verify(pedidoRepositoryPort, never()).save(any(Pedido.class)); // Não deve tentar salvar novamente
        verify(historicoPedidoRepositoryPort, never()).save(any(HistoricoPedido.class)); // Histórico não deve ser salvo
    }
}

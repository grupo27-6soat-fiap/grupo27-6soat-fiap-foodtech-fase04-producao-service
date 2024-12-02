/*package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.infra.db.entities.PedidoEntity;
import br.com.fiap.lanchonete.infra.db.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PedidoPostgresqlRepositoryTests {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PedidoDataMapper pedidoDataMapper;

    @InjectMocks
    private PedidoPostgresqlRepository pedidoPostgresqlRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_return_pedido_if_exists() {
        Long pedidoId = 1L;
        PedidoEntity pedidoEntity = new PedidoEntity();
        Pedido pedido = new Pedido();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoEntity));
        when(modelMapper.map(pedidoEntity, Pedido.class)).thenReturn(pedido);

        Pedido result = pedidoPostgresqlRepository.get(pedidoId);

        assertEquals(pedido, result);
        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(modelMapper, times(1)).map(pedidoEntity, Pedido.class);
    }

    @Test
    void should_return_null_if_pedido_doesnt_exists() {
        Long pedidoId = 1L;

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        Pedido result = pedidoPostgresqlRepository.get(pedidoId);

        assertEquals(null, result);
        verify(pedidoRepository, times(1)).findById(pedidoId);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void should_save_pedido_entiy_on_success() {
        Pedido pedido = new Pedido();
        PedidoEntity pedidoEntity = new PedidoEntity();

        when(pedidoDataMapper.toData(pedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);
        when(modelMapper.map(pedidoEntity, Pedido.class)).thenReturn(pedido);

        Pedido result = pedidoPostgresqlRepository.save(pedido);

        assertEquals(pedido, result);
        verify(pedidoDataMapper, times(1)).toData(pedido);
        verify(pedidoRepository, times(1)).save(pedidoEntity);
        verify(modelMapper, times(1)).map(pedidoEntity, Pedido.class);
    }
}

*/
/*package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.HistoricoPedido;
import br.com.fiap.lanchonete.infra.db.entities.HistoricoPedidoEntity;
import br.com.fiap.lanchonete.infra.db.mappers.HistoricoPedidoDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.HistoricoPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

public class HistoricoPedidoPostgresqlRepositoryTests {

    @Mock
    private HistoricoPedidoRepository historicoPedidoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HistoricoPedidoDataMapper historicoPedidoDataMapper;

    @InjectMocks
    private HistoricoPedidoPostgresqlRepository historicoPedidoPostgresqlRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_save_historico_pedido_entity_on_success() {
        HistoricoPedido historicoPedido = new HistoricoPedido();
        HistoricoPedidoEntity historicoPedidoEntity = new HistoricoPedidoEntity();

        when(historicoPedidoDataMapper.toData(historicoPedido)).thenReturn(historicoPedidoEntity);
        when(historicoPedidoRepository.save(historicoPedidoEntity)).thenReturn(historicoPedidoEntity);

        historicoPedidoPostgresqlRepository.save(historicoPedido);

        verify(historicoPedidoDataMapper, times(1)).toData(historicoPedido);
        verify(historicoPedidoRepository, times(1)).save(historicoPedidoEntity);
    }
    
}

*/
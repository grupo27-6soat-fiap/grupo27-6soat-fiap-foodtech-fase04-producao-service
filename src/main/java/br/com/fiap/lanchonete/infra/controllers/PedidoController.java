package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.PedidoDto;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.pedido.CompletePedidoUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.ReceivePedidoUsecase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producao")
@RequiredArgsConstructor
public class PedidoController {

	private final ReceivePedidoUsecase receivePedidoUsecase;
	private final CompletePedidoUsecase completePedidoUsecase;
	private final ModelMapper modelMapper;

	@PostMapping("/pedidos/{id}")
	public ResponseEntity<PedidoDto> receivePedido(@PathVariable(name = "id") Long id) {
		Pedido pedido = receivePedidoUsecase.receive(id);
		return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
	}

	@PatchMapping("/pedidos/{id}")
	public ResponseEntity<PedidoDto> processarPedido(@PathVariable(name = "id") Long id) {
		Pedido pedido = completePedidoUsecase.complete(id);
		return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
	}

}

package me.dio.carrinho.service.impl;

import lombok.RequiredArgsConstructor;
import me.dio.carrinho.enumeration.FormaPagamento;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.model.Sacola;
import me.dio.carrinho.repository.SacolaRepository;
import me.dio.carrinho.resource.dto.ItemDto;
import me.dio.carrinho.service.SacolaService;

@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
	
	private final SacolaRepository sacolaRepository;
	
	@Override
	public Item adicionarItemNaSacola(ItemDto itemDto) {
		return null;
	}

	@Override
	public Sacola verSacola(Long id) {
		return sacolaRepository.findById(id).orElseThrow(
			() -> {
				throw new RuntimeException("Essa sacola não existe!");
			}
		);
	}

	@Override
	public Sacola fecharSacola(Long id, int numeroFormaPagemento) {
		Sacola sacola = verSacola(id);
		
		if(sacola.getItens().isEmpty()) {
			throw new RuntimeException("Adiciona ítens na sacola!");
		}
		
		FormaPagamento formaPagamento =
				numeroFormaPagemento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
		
		sacola.setFormaPagamento(formaPagamento);
		sacola.setFechada(true);

		return sacolaRepository.save(sacola);
	}

}

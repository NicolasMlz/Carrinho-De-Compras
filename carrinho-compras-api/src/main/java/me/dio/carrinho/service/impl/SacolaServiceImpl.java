package me.dio.carrinho.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dio.carrinho.enumeration.FormaPagamento;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.model.Restaurante;
import me.dio.carrinho.model.Sacola;
import me.dio.carrinho.repository.ProdutoRepository;
import me.dio.carrinho.repository.SacolaRepository;
import me.dio.carrinho.resource.dto.ItemDto;
import me.dio.carrinho.service.SacolaService;

@RequiredArgsConstructor
@Service
public class SacolaServiceImpl implements SacolaService {
	
	private final SacolaRepository sacolaRepository;
	private final ProdutoRepository produtoRepository;
	
	@Override
	public Item adicionarItemNaSacola(ItemDto itemDto) {
		
		//Encontrar a sacola
		Sacola sacola = verSacola(itemDto.getSacolaId());
		
		//Verificar se a sacola está fechada
		if(sacola.isFechada()) {
			throw new RuntimeException("A sacola está fechada!");
		}
		
		//Acessar item a ser adicionado
		Item itemASerInserido = Item.builder()
			.quantidade(itemDto.getQuantidade())
			.sacola(sacola)
			.produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
				() -> {
					throw new RuntimeException("Esse produto não existe!");
				}
			))
			.build();
		
		//Verificar se a sacola está vazia (SÓ PODE TER UM RESTAURANTE POR SACOLA)
		List<Item> itensDaSacola = sacola.getItens();
		if(itensDaSacola.isEmpty()) {
			itensDaSacola.add(itemASerInserido);
		} else {
			
			//Verificar se o item presente é do mesmo restaurante
			Restaurante restauranteDaSacola = itensDaSacola.get(0).getProduto().getRestaurante();
			Restaurante restauranteDoItem = itemASerInserido.getProduto().getRestaurante();
			if(restauranteDaSacola.equals(restauranteDoItem)) {
				itensDaSacola.add(itemASerInserido);
			} else {
				throw new RuntimeException("O restaurante desse ítem é diferente dos ítens da sacola! Esvazie a sacola ou adicione outro ítem");
			}
		}
		
        List<Double> valorDosItem = new ArrayList<>();

        for(Item itemDaSacola: itensDaSacola) {
            double valorTotal = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItem.add(valorTotal);
        }

        Double valorTotalSacola = 0.0;

        for(Double valorItem: valorDosItem) {
            valorTotalSacola +=  valorItem;
        }

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemASerInserido;
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
			throw new RuntimeException("Adicione ítens na sacola!");
		}
		
		FormaPagamento formaPagamento =
				numeroFormaPagemento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;
		
		sacola.setFormaPagamento(formaPagamento);
		sacola.setFechada(true);

		return sacolaRepository.save(sacola);
	}

}

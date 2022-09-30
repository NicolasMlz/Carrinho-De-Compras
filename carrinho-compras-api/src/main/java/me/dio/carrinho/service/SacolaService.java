package me.dio.carrinho.service;

import me.dio.carrinho.model.Item;
import me.dio.carrinho.model.Sacola;
import me.dio.carrinho.resource.dto.ItemDto;

public interface SacolaService {
	Item adicionarItemNaSacola(ItemDto itemDto);
	Sacola verSacola(Long id);
	Sacola fecharSacola(Long id, int formaPagemento);
}

package me.dio.carrinho.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.carrinho.model.Item;
import me.dio.carrinho.model.Sacola;
import me.dio.carrinho.resource.dto.ItemDto;
import me.dio.carrinho.service.SacolaService;

@Api(value = "/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
	
	private final SacolaService sacolaService;
	
	@PostMapping
	public Item incluirItemNaSacola(@RequestBody ItemDto itemDto) {
		return sacolaService.adicionarItemNaSacola(itemDto);
	}
	
	@GetMapping("/{id}")
	public Sacola verSacola(@PathVariable("id") Long id) {
		return sacolaService.verSacola(id);
	}
	
	@PatchMapping("/fecharSacola/{sacolaId}")
	public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamento") int formaPagamento) {
		return sacolaService.fecharSacola(sacolaId, formaPagamento);
	}
}

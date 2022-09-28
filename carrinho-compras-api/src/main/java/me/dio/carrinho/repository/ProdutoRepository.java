package me.dio.carrinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.dio.carrinho.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}

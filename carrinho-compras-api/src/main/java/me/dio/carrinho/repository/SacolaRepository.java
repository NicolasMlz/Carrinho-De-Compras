package me.dio.carrinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.carrinho.model.Sacola;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {

}

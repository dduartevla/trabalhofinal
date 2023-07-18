package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Conta findBynomeConta(String nomeConta);
}

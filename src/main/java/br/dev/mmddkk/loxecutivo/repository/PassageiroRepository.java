package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Passageiro;
import br.dev.mmddkk.loxecutivo.model.Viagens;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassageiroRepository extends CrudRepository<Passageiro, Integer> {
    // O Spring cria o SELECT automaticamente para nós!
    List<Passageiro> findByIdViagem(Viagens viagem);
}
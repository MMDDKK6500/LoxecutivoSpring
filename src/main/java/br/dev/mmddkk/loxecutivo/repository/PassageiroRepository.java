package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Passageiro;
import org.springframework.data.repository.CrudRepository;

public interface PassageiroRepository extends CrudRepository<Passageiro, Integer> {
}
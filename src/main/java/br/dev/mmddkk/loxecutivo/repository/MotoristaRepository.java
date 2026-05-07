package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Motorista;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface MotoristaRepository extends CrudRepository<Motorista, Integer> {
}
package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, Integer> {
}
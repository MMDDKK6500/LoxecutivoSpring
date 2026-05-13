package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Evento;
import br.dev.mmddkk.loxecutivo.model.Viagens;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViagensRepository extends CrudRepository<Viagens, Integer> {

    List<Viagens> findByIdEvento(Evento evento);

}
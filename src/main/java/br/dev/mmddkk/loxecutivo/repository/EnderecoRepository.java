package br.dev.mmddkk.loxecutivo.repository;

import br.dev.mmddkk.loxecutivo.model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "enderecos", path = "enderecos")
public interface EnderecoRepository extends CrudRepository<Endereco, Integer> {
}
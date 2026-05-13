package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.*;
import br.dev.mmddkk.loxecutivo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ViagensRepository viagensRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;
    @Autowired
    private PassageiroRepository passageiroRepository;

    @RequestMapping(value = {"/", "/{idEvento:\\d+}", "/{idEvento:\\d+}/{idViagem:\\d+}"})
    public ModelAndView index(
            @PathVariable("idEvento") Optional<Integer> idEvento,
            @PathVariable("idViagem") Optional<Integer> idViagem) {

        ModelAndView mv = new ModelAndView("index");

        // 1. Pega o Evento atual
        Evento eventoAtivo = idEvento.flatMap(eventoRepository::findById).orElse(new Evento());
        if (eventoAtivo.getIdEndereco() == null) eventoAtivo.setIdEndereco(new Endereco());

        // 2. LÓGICA NOVA: Se o evento já existe no banco, busca as viagens dele
        if (eventoAtivo.getId() != null) {
            List<Viagens> viagensDoEvento = viagensRepository.findByIdEvento(eventoAtivo);
            mv.addObject("viagensDoEvento", viagensDoEvento); // Envia para o HTML
        }

        // 3. Pega a Viagem Selecionada (se houver)
        if (idViagem.isPresent()) {
                viagensRepository.findById(idViagem.get()).ifPresent(v -> {
                    mv.addObject("viagemSelecionada", v);

                    // NOVA LINHA: Busca os passageiros da viagem no banco de forma segura
                    mv.addObject("passageirosDaViagem", passageiroRepository.findByIdViagem(v));

                    // Busca o veículo completo baseado na placa (que já tínhamos feito)
                    if (v.getIdVeiculo() != null && !v.getIdVeiculo().isEmpty()) {
                        veiculoRepository.findById(v.getIdVeiculo()).ifPresent(veiculo ->
                                mv.addObject("veiculoSelecionado", veiculo)
                        );
                    }
                });
        }

        mv.addObject("novoVeiculo", new Veiculo());
        mv.addObject("novaViagem", new Viagens());

        Passageiro pass = new Passageiro();
        pass.setEmpresa("Nenhuma");
        mv.addObject("novoPassageiro", pass);

        mv.addObject("novoMotorista", new Motorista());
        mv.addObject("novoEndereco", new Endereco());

        // 5. Listas necessárias para os selects do formulário de Viagem
        mv.addObject("listaVeiculos", veiculoRepository.findAll());
        mv.addObject("listaMotoristas", motoristaRepository.findAll()); // Assumindo que você injetou o motoristaRepository
        mv.addObject("listaEnderecos", enderecoRepository.findAll());

        mv.addObject("evento", eventoAtivo);
        mv.addObject("listaEventos", eventoRepository.findAll());

        return mv;
    }

}

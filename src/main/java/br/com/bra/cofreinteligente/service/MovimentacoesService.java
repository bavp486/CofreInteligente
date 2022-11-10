package br.com.bra.cofreinteligente.service;

import br.com.bra.cofreinteligente.dto.MovimentacoesDto;
import br.com.bra.cofreinteligente.entity.Movimentacoes;
import br.com.bra.cofreinteligente.repository.MovimentacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimentacoesService {

    @Autowired
    public MovimentacoesRepository movimentacoesRepository;

    @Autowired
    public CofreService cofreService;


    public MovimentacoesDto addMovimentacoes(MovimentacoesDto movimentacoesDto){
        Movimentacoes movimentacao = Movimentacoes.builder()
                .numeroCofre(movimentacoesDto.getNumeroCofre())
                .data(LocalDateTime.now())
                .valorRecolhido(movimentacoesDto.getValorRecolhido())
                .build();
        movimentacoesRepository.save(movimentacao);
        return new MovimentacoesDto(movimentacao);
    }

    public List<MovimentacoesDto> getAllMovimentacoes() {
        return movimentacoesRepository.findAll().stream()
                .map(MovimentacoesDto::new)
                .toList();
    }

    public MovimentacoesDto getMovimentacoes(Long id) throws Exception {
        var movimentacao = movimentacoesRepository.findById(id);
        if (movimentacao.isEmpty()){
            throw new Exception("Movimentação não encontrada");
        }
        return new MovimentacoesDto(movimentacao.get());
    }

    public List<MovimentacoesDto> getAllByPeriod(LocalDateTime inicio, LocalDateTime fim){
        return movimentacoesRepository.findByDateBetween(inicio, fim)
                .stream()
                .map(MovimentacoesDto::new)
                .toList();
    }

    public List<MovimentacoesDto> getByPeriodAndCofre(LocalDateTime inicio, LocalDateTime fim, Long numeroCofre){
        return movimentacoesRepository.findByDateBetweenAndNumeroCofre(inicio, fim, numeroCofre)
                .stream()
                .map(MovimentacoesDto::new)
                .toList();
    }

}
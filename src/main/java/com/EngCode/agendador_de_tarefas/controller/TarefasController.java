package com.EngCode.agendador_de_tarefas.controller;

import com.EngCode.agendador_de_tarefas.business.TarefasService;
import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping ("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO>  gravarTerefas (@RequestBody TarefasDTO tarefasDTO, @RequestHeader ("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO));
    }

    @GetMapping ("/eventos")
    public  ResponseEntity<List <TarefasDTO> > buscarListaTarefasPorPeriodo (
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal
    ) {
        return  ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity <List <TarefasDTO>> buscarListaTarefasPorEmail (
            @RequestHeader ("Authorization") String token) {

        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));

    }


}

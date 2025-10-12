package com.EngCode.agendador_de_tarefas.controller;

import com.EngCode.agendador_de_tarefas.business.TarefasService;
import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.infrastucture.enums.StatusNotificacaoEnum;
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

    @DeleteMapping
    public ResponseEntity <Void> deletaTarefaPorId (@RequestParam ("id") String id) {
        tarefasService.deletaTarefaPorId(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusDeNotificacao
            (@RequestParam ("status")StatusNotificacaoEnum statusNotificacaoEnum,
             @RequestParam ("id") String id) {
        return ResponseEntity.ok(tarefasService.alteraStatusDaTarefa(statusNotificacaoEnum, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> updateDeTarefas (@RequestBody TarefasDTO tarefasDTO,
                                                       @RequestParam ("id") String id) {
        return ResponseEntity.ok(tarefasService.updateDeTarefas(tarefasDTO, id));
    }
}

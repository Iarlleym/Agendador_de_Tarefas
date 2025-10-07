package com.EngCode.agendador_de_tarefas.controller;

import com.EngCode.agendador_de_tarefas.business.TarefasService;
import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO>  gravarTerefas (@RequestBody TarefasDTO tarefasDTO, @RequestHeader ("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO));
    }

}

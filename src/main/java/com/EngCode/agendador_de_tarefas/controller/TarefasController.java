// Controlador responsável por expor os endpoints relacionados às tarefas.
// Aqui são recebidas as requisições HTTP e encaminhadas ao serviço (TarefasService),
// que contém as regras de negócio. Essa classe é o ponto de entrada da API de tarefas.

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
@RequestMapping("/tarefas") // Define o endpoint base para todos os métodos
@RequiredArgsConstructor // Cria o construtor com as dependências obrigatórias (final)
public class TarefasController {

    private final TarefasService tarefasService; // Injeta o serviço que contém as regras de negócio

    // Endpoint para cadastrar uma nova tarefa.
    // Recebe os dados da tarefa (DTO) e o token JWT para associar a tarefa ao usuário logado.
    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, tarefasDTO)); // Chama o service e retorna o DTO salvo
    }

    // Endpoint para buscar tarefas agendadas dentro de um intervalo de tempo.
    // Recebe duas datas no formato ISO e retorna a lista de tarefas entre elas.
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscarListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial, // Data inicial do período
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal   // Data final do período
    ) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal)); // Retorna lista filtrada
    }

    // Endpoint para buscar todas as tarefas de um usuário específico.
    // O e-mail do usuário é extraído automaticamente do token JWT.
    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscarListaTarefasPorEmail(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token)); // Retorna lista de tarefas do usuário
    }

    // Endpoint para excluir uma tarefa pelo ID.
    // Caso o ID não exista, uma exceção é lançada pelo service.
    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id) {
        tarefasService.deletaTarefaPorId(id); // Chama o método de exclusão
        return ResponseEntity.ok().build();   // Retorna resposta vazia (200 OK)
    }

    // Endpoint para atualizar apenas o status da tarefa (ex: PENDENTE → ENVIADA).
    // Usado para mudar o estado da notificação sem alterar outras informações.
    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusDeNotificacao(
            @RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum, // Novo status
            @RequestParam("id") String id) { // ID da tarefa a ser atualizada
        return ResponseEntity.ok(tarefasService.alteraStatusDaTarefa(statusNotificacaoEnum, id)); // Retorna tarefa atualizada
    }

    // Endpoint para atualizar os dados completos de uma tarefa.
    // Caso apenas alguns campos sejam enviados, o MapStruct no service trata o merge dos dados.
    @PutMapping
    public ResponseEntity<TarefasDTO> updateDeTarefas(@RequestBody TarefasDTO tarefasDTO,
                                                      @RequestParam("id") String id) {
        return ResponseEntity.ok(tarefasService.updateDeTarefas(tarefasDTO, id)); // Retorna DTO atualizado após o save
    }
}

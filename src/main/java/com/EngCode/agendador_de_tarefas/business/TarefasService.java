// Classe de serviço responsável por conter as regras de negócio relacionadas às tarefas.
// Aqui ficam os métodos que tratam o cadastro, consulta, atualização e exclusão de tarefas.
// Essa classe se comunica com o repositório, faz uso de converters (MapStruct) e utiliza JWT para associar o usuário às tarefas.

package com.EngCode.agendador_de_tarefas.business;

import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.business.mapper.TarefaUpdateConverter;
import com.EngCode.agendador_de_tarefas.business.mapper.TarefasConverter;
import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import com.EngCode.agendador_de_tarefas.infrastucture.enums.StatusNotificacaoEnum;
import com.EngCode.agendador_de_tarefas.infrastucture.exceptions.ResourceNotFoundException;
import com.EngCode.agendador_de_tarefas.infrastucture.repository.TarefasRepository;
import com.EngCode.agendador_de_tarefas.infrastucture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor // Cria automaticamente o construtor com todos os campos "final"
public class TarefasService {

    private final TarefasRepository tarefasRepository; // Repositório de acesso ao banco
    private final TarefasConverter tarefasConverter;   // Responsável por converter Entity ↔ DTO
    private final JwtUtil jwtUtil;                     // Classe para manipulação e validação do token JWT
    private final TarefaUpdateConverter tarefaUpdateConverter; // Converter específico para atualizações

    // Método responsável por cadastrar uma nova tarefa associando-a ao usuário do token JWT.
    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefasDTO) {

        String email = jwtUtil.extrairEmailToken(token.substring(7)); // Extrai o e-mail do usuário pelo token JWT
        tarefasDTO.setEmailUsuario(email); // Associa o e-mail ao DTO
        tarefasDTO.setDataCriacao(LocalDateTime.now()); // Define data/hora de criação
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE); // Define status inicial como "pendente"

        TarefasEntity tarefasEntity = tarefasConverter.paraListaTarefasEntity(tarefasDTO); // Converte DTO em Entity
        return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity)); // Salva no banco e retorna DTO atualizado
    }

    // Busca todas as tarefas dentro de um intervalo de tempo específico.
    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum(dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE)); // Busca entre duas datas
    }

    // Busca todas as tarefas cadastradas de um usuário específico (com base no token JWT).
    public List<TarefasDTO> buscaTarefasPorEmail(String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7)); // Extrai o e-mail do usuário autenticado
        List<TarefasEntity> listaTarefasEntity = tarefasRepository.findByEmailUsuario(email); // Busca tarefas do usuário

        return tarefasConverter.paraListaTarefasDTO(listaTarefasEntity); // Retorna lista convertida em DTOs
    }

    // Deleta uma tarefa pelo ID. Caso o ID não exista, lança exceção personalizada.
    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id); // Tenta deletar a tarefa
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar Tarefa por ID, ID inexistente: " + id, e.getCause());
        }
    }

    // Altera o status da tarefa (ex: de "pendente" para "enviada" ou "falha").
    public TarefasDTO alteraStatusDaTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id) {
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não Encontrada: " + id)); // Verifica se existe

            tarefasEntity.setStatusNotificacaoEnum(statusNotificacaoEnum); // Atualiza o status
            return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity)); // Salva e retorna DTO atualizado

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar Status da Tarefa: " + e.getCause());
        }
    }

    // Atualiza as informações da tarefa já existente com base no ID.
    public TarefasDTO updateDeTarefas(TarefasDTO tarefasDTO, String id) {

        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não Encontrada: " + id)); // Verifica se existe

            tarefaUpdateConverter.updateDeTarefas(tarefasDTO, tarefasEntity); // Aplica as atualizações via MapStruct
            return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity)); // Salva e retorna a nova versão

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar Tarefa: " + e.getCause());
        }
    }
}

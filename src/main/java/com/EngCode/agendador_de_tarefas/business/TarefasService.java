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
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO tarefasDTO) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefasDTO.setEmailUsuario(email);
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefasEntity tarefasEntity = tarefasConverter.paraListaTarefasEntity(tarefasDTO);

        return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefasEntity = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefasEntity);

    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar Tarefa por ID, ID inexistente: " + id, e.getCause());
        }
    }

    public TarefasDTO alteraStatusDaTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id) {
        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não Encontrada: " + id));

            tarefasEntity.setStatusNotificacaoEnum(statusNotificacaoEnum);

            return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar Status da Tarefa: " + e.getCause());
        }

    }

    public TarefasDTO updateDeTarefas(TarefasDTO tarefasDTO, String id) {

        try {
            TarefasEntity tarefasEntity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não Encontrada: " + id));

            tarefaUpdateConverter.updateDeTarefas(tarefasDTO, tarefasEntity);

            return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar Status da Tarefa: " + e.getCause());
        }

    }


}

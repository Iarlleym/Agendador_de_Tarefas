package com.EngCode.agendador_de_tarefas.business;

import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.business.mapper.TarefasConverter;
import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import com.EngCode.agendador_de_tarefas.infrastucture.enums.StatusNotificacaoEnum;
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

    public TarefasDTO gravarTarefa (String token, TarefasDTO tarefasDTO) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefasDTO.setEmailUsuario(email);
        tarefasDTO.setDataCriacao(LocalDateTime.now());
        tarefasDTO.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);

        TarefasEntity tarefasEntity = tarefasConverter.paraListaTarefasEntity(tarefasDTO);

        return tarefasConverter.paraListaTarefaDTO(tarefasRepository.save(tarefasEntity));
    }

    public List <TarefasDTO> buscaTarefasAgendadasPorPeriodo (LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List <TarefasDTO> buscaTarefasPorEmail (String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        List<TarefasEntity> listaTarefasEntity = tarefasRepository.findByEmailUsuario(email);

        return  tarefasConverter.paraListaTarefasDTO(listaTarefasEntity);

    }


}

package com.EngCode.agendador_de_tarefas.business.mapper;


import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraListaTarefasEntity(TarefasDTO tarefasDTO);

    TarefasDTO paraListaTarefaDTO(TarefasEntity tarefasEntity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO> tarefasDTOS);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> tarefasEntity);

}

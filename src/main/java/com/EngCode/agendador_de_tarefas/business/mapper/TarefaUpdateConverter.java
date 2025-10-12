package com.EngCode.agendador_de_tarefas.business.mapper;

import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateDeTarefas (TarefasDTO tarefasDTO, @MappingTarget TarefasEntity tarefasEntity);

}

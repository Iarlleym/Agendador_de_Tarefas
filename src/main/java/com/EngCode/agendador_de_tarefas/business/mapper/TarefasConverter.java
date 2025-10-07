package com.EngCode.agendador_de_tarefas.business.mapper;


import com.EngCode.agendador_de_tarefas.business.dto.TarefasDTO;
import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefaEntity (TarefasDTO tarefasDTO);

    TarefasDTO paraTarefaDTO (TarefasEntity tarefasEntity);

}

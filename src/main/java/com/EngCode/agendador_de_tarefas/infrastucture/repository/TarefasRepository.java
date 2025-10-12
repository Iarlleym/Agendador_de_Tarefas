package com.EngCode.agendador_de_tarefas.infrastucture.repository;

import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    List<TarefasEntity> findByDataEventoBetween (LocalDateTime dataInicial, LocalDateTime dataFinal);

    List <TarefasEntity> findByEmailUsuario (String email);

}

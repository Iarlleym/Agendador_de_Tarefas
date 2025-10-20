package com.EngCode.agendador_de_tarefas.infrastucture.repository;

import com.EngCode.agendador_de_tarefas.infrastucture.entity.TarefasEntity;
import com.EngCode.agendador_de_tarefas.infrastucture.enums.StatusNotificacaoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 🔹 Interface de repositório para a entidade TarefasEntity.
 *
 * O MongoRepository já oferece métodos básicos de CRUD (Create, Read, Update, Delete)
 * sem precisar escrever implementação, como save(), findById(), deleteById(), etc.
 *
 * Aqui, adicionamos métodos específicos para consultas customizadas.
 */
@Repository
public interface TarefasRepository extends MongoRepository<TarefasEntity, String> {

    /**
     * Busca todas as tarefas cujo campo 'dataEvento' esteja dentro do intervalo especificado.
     *
     * @param dataInicial início do período
     * @param dataFinal fim do período
     * @return lista de tarefas dentro do intervalo
     */
    List<TarefasEntity> findByDataEventoBetweenAndStatusNotificacaoEnum(LocalDateTime dataInicial,
                                                                        LocalDateTime dataFinal,
                                                                        StatusNotificacaoEnum statusNotificacaoEnum);

    /**
     * Busca todas as tarefas associadas a um e-mail de usuário específico.
     *
     * @param email e-mail do usuário
     * @return lista de tarefas do usuário
     */
    List<TarefasEntity> findByEmailUsuario(String email);

}

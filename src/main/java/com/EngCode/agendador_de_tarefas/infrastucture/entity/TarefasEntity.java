package com.EngCode.agendador_de_tarefas.infrastucture.entity;

// Importa o enum que representa os status de notificação de uma tarefa (ex: PENDENTE, ENVIADA, etc.)
import com.EngCode.agendador_de_tarefas.infrastucture.enums.StatusNotificacaoEnum;

// Importa o LocalDateTime e as anotações do Lombok, que ajudam a reduzir código repetitivo.
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 🔹 Classe que representa o modelo (entidade) de uma tarefa no banco de dados MongoDB.
 * Cada objeto dessa classe é salvo como um "documento" dentro da coleção "tarefa".
 *
 * Essa classe mapeia os dados de uma tarefa, como nome, descrição, datas, e status de notificação.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("tarefa")
// Indica que essa classe representa uma coleção do MongoDB chamada "tarefa".
public class TarefasEntity {

    @Id
    private String id;
    // Identificador único da tarefa no banco de dados (gerado automaticamente pelo MongoDB).

    private String nomeTarefa;
    // Nome ou título da tarefa (ex: "Reunião com cliente").

    private String descricao;
    // Descrição detalhada da tarefa.

    private LocalDateTime dataCriacao;
    // Data e hora em que a tarefa foi criada.

    private LocalDateTime dataEvento;
    // Data e hora do evento ou prazo da tarefa.

    private String emailUsuario;
    // E-mail do usuário que criou ou está associado à tarefa.

    private LocalDateTime dataAlteracao;
    // Data e hora da última modificação feita na tarefa.

    private StatusNotificacaoEnum statusNotificacaoEnum;
    // Indica o status da notificação da tarefa (ex: NÃO_ENVIADA, ENVIADA, ATRASADA).
}

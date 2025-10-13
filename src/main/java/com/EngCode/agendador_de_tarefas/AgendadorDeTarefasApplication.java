// ================================================
// 🔹 Classe principal do microserviço Agendador de Tarefas
// ================================================
//
// Essa classe é o ponto de entrada da aplicação Spring Boot.
// Ela inicializa todo o contexto da aplicação e habilita o uso
// de FeignClients para comunicação com outros microserviços (ex: Cadastro de Usuários).
//

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// Anotação que marca essa classe como aplicação Spring Boot.
// Faz a configuração automática de todos os componentes do Spring.
@EnableFeignClients
// Habilita o uso de FeignClient no projeto, permitindo que a aplicação
// faça requisições HTTP para outros microserviços de forma declarativa.

public class AgendadorDeTarefasApplication {

    public static void main(String[] args) {
        // Método principal que inicializa o Spring Boot.
        // Cria o ApplicationContext, registra beans, configura o servidor e inicia a aplicação.
        SpringApplication.run(AgendadorDeTarefasApplication.class, args);
    }

}

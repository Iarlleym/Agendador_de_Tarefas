package com.EngCode.agendador_de_tarefas.infrastucture.client;

// Importa a classe UsuarioDTO, que representa os dados do usuário.
import com.EngCode.agendador_de_tarefas.business.dto.UsuarioDTO;

// Importa anotações do Spring Cloud e Spring Web.
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 🔹 Interface responsável por fazer a comunicação com outro serviço (microserviço de usuários)
 * usando o Feign Client — uma ferramenta que facilita chamadas HTTP entre APIs no Spring Boot.
 *
 * Em resumo: essa interface permite buscar dados de um usuário em outro sistema,
 * apenas chamando um método Java (sem precisar escrever código HTTP manualmente).
 */
@FeignClient(name = "usuario", url = "${usuario.url}")
// A anotação @FeignClient transforma essa interface em um cliente HTTP.
// O parâmetro "url" é definido no application.properties ou application.yml (ex: usuario.url=http://localhost:8081).
public interface UsuarioClient {

    /**
     * 🔹 Método responsável por buscar um usuário a partir do e-mail informado.
     * Esse método faz uma requisição GET para o endpoint "/usuario" do outro serviço.
     *
     * Exemplo de chamada:
     * GET http://servidor/usuario?email=teste@mail.com
     * Header: Authorization: Bearer tokenAqui
     *
     * @param email - e-mail do usuário a ser buscado
     * @param token - token de autenticação enviado no cabeçalho Authorization
     * @return objeto UsuarioDTO com as informações do usuário encontrado
     */
    @GetMapping("/usuario")
    // Mapeia o método para uma requisição HTTP GET no caminho "/usuario"
    UsuarioDTO buscaUsuarioPorEmail(
            @RequestParam("email") String email,          // Pega o e-mail do usuário via parâmetro da URL
            @RequestHeader("Authorization") String token  // Envia o token de autenticação no cabeçalho
    );
}

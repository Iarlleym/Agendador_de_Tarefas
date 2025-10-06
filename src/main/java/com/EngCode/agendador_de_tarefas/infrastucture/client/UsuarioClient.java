package com.EngCode.agendador_de_tarefas.infrastucture.client;


import com.EngCode.agendador_de_tarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "buscar_usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping ("/usuario")
    // Mapeia requisições HTTP GET para o caminho base (/usuario). Usado para buscar recursos.
    UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email, @RequestHeader ("Authorization") String token);
    // @RequestParam("email"): Pega o valor da URL (ex: /usuario?email=teste@mail.com).

}

package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicosDto;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicosController {

    @RequestMapping("/topicos")
    public List<TopicosDto> lista(){
        Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Strping", "Programação"));
        return TopicosDto.converter(Arrays.asList(topico,topico,topico));
    }

}

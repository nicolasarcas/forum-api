package br.com.alura.forum.controller;
import br.com.alura.forum.controller.dto.TopicosDto;
import br.com.alura.forum.controller.form.TopicosForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicosDto> lista(String nomeCurso){
        List<Topico> topicos = nomeCurso== null ? topicoRepository.findAll() : topicoRepository.findByCursoNome(nomeCurso) ;
        return TopicosDto.converter(topicos);
    }
    @PostMapping
    public ResponseEntity<TopicosDto> cadastrar(@RequestBody @Valid TopicosForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicosDto(topico));
    }

}

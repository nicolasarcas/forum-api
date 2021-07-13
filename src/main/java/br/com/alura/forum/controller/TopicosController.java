package br.com.alura.forum.controller;
import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicosDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicosForm;
import br.com.alura.forum.controller.form.TopicosForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public ResponseEntity<TopicosDto> cadastrar(@RequestBody @Valid TopicosForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicosDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id){
        Optional<Topico> topico = topicoRepository.findById(id);

        return topico.map(value -> ResponseEntity.ok(new DetalhesTopicoDto(value))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicosDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicosForm form){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicosDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Topico> optional = topicoRepository.findById(id);
        if(optional.isPresent()){
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}

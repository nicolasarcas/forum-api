package br.com.alura.forum.controller.dto;

import br.com.alura.forum.model.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicosDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime data;

    TopicosDto(Topico topico){
        this.id=topico.getId();
        this.titulo=topico.getTitulo();
        this.mensagem=topico.getMensagem();
        this.data=topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public static List<TopicosDto> converter(List<Topico> topicos){
        return topicos.stream().map(TopicosDto::new).collect(Collectors.toList());
    }
}

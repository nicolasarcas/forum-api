package br.com.alura.forum.controller.form;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicosForm {

    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String mensagem;
    @NotNull @NotEmpty
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository repository) {

        Curso curso = repository.findByNome(this.nomeCurso);

        return new Topico(
            this.titulo,
            this.mensagem,
            curso
        );
    }
}

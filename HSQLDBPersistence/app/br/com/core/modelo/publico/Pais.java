package br.com.core.modelo.publico;

import java.util.List;

public class Pais {
	
	public Pais() {
	}
	
	public Pais(Long id) {
		this.id = id;
	}

    private Long id;

    private List<Estado> estados;

    private String nome;
    
    private Boolean primeiro = false;

	public Boolean getPrimeiro() {
		return primeiro;
	}

	public void setPrimeiro(Boolean primeiro) {
		this.primeiro = primeiro;
	}

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public String getNome() {
        return this.nome;
    }

	public void setNome(String nome) {
        this.nome = nome;
    }

}

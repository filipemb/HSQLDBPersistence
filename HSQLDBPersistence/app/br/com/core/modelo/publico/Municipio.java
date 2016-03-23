package br.com.core.modelo.publico;

public class Municipio {

	public Municipio() {
	}

	public Municipio(Long id) {
		this.id = id;
	}

	private Long id;

	private String nome;

	private Estado estado;

	private Boolean capital = false;

	public Boolean getCapital() {
		return capital;
	}

	public void setCapital(Boolean capital) {
		this.capital = capital;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}


}

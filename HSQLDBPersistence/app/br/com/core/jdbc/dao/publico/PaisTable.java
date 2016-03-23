package br.com.core.jdbc.dao.publico;

public class PaisTable
{

	private Long id;

	private String nome;

	private Boolean primeiro;


	public Long getId ()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setNome (String nome)
	{
		this.nome = nome;
	}

	public String getNome ()
	{
		return this.nome;
	}

	public void setPrimeiro (Boolean primeiro)
	{
		this.primeiro = primeiro;
	}

	public Boolean getPrimeiro ()
	{
		return this.primeiro;
	}



}

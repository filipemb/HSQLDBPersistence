package br.com.core.jdbc.dao.publico;


public class MunicipioTable 
{

	private Long id;

	private String nome;

	private Long estado;

	private Boolean capital;


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

	public void setEstado (Long estado)
	{
		this.estado = estado;
	}

	public Long getEstado ()
	{
		return this.estado;
	}

	public void setCapital (Boolean capital)
	{
		this.capital = capital;
	}

	public Boolean getCapital ()
	{
		return this.capital;
	}


}

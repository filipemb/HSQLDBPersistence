package br.com.core.jdbc.dao.publico;


public class EstadoTable 
{

	private Long id;

	private Long pais;

	private String nome;

	private String uf;

	public Long getId ()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setPais (Long pais)
	{
		this.pais = pais;
	}

	public Long getPais ()
	{
		return this.pais;
	}

	public void setNome (String nome)
	{
		this.nome = nome;
	}

	public String getNome ()
	{
		return this.nome;
	}

	public void setUf (String uf)
	{
		this.uf = uf;
	}

	public String getUf ()
	{
		return this.uf;
	}


}

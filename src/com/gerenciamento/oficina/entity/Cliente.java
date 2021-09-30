package com.gerenciamento.oficina.entity;

public class Cliente {
	
	private Long codCliente;
	
	private Long cpf;
	
	private String unidadeFederativa;
	
	private String numContato;
	
	private String nomeCliente;
	
	private String enderecoCliente;

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(String unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getNumContato() {
		return numContato;
	}

	public void setNumContato(String numContato) {
		this.numContato = numContato;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEnderecoCliente() {
		return enderecoCliente;
	}

	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}
	
}

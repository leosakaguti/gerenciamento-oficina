package com.gerenciamento.oficina.entity;

public class Servico {
	
	private Long codServico;
	
	private String descServico;
	
	private Double vlrServico;

	public Long getCodServico() {
		return codServico;
	}

	public void setCodServico(Long codServico) {
		this.codServico = codServico;
	}

	public String getDescServico() {
		return descServico;
	}

	public void setDescServico(String descServico) {
		this.descServico = descServico;
	}

	public Double getVlrServico() {
		return vlrServico;
	}

	public void setVlrServico(Double vlrServico) {
		this.vlrServico = vlrServico;
	}
}

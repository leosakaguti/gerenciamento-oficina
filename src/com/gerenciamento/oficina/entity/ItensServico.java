package com.gerenciamento.oficina.entity;

public class ItensServico {
	
	private Servico servico;
	
	private OrdemServico ordemServico;
	
	private Long qtde;
	
	private Double vlrServico;
	
	public Double getVlrServico() {
		return vlrServico;
	}

	public void setVlrServico(Double vlrServico) {
		this.vlrServico = vlrServico;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Long getQtde() {
		return qtde;
	}

	public void setQtde(Long qtde) {
		this.qtde = qtde;
	}
}

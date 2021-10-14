package com.gerenciamento.oficina.entity;

public class ItensProduto {
	
	private Produto produto;
	
	private OrdemServico ordemServico;
	
	private Long qtdeProd;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Long getQtdeProd() {
		return qtdeProd;
	}

	public void setQtdeProd(Long qtdeProd) {
		this.qtdeProd = qtdeProd;
	}
}

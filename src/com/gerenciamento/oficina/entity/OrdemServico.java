package com.gerenciamento.oficina.entity;

import java.sql.Date;

public class OrdemServico {
	
	private Long codOrdem;
	
	private Long codUsuario;
	
	private Double valorTotal;
	
	private Veiculo veiculo;
	
	private Date dataEmissao;
	
	private Long statusOrdem;

	public Long getStatusOrdem() {
		return statusOrdem;
	}

	public void setStatusOrdem(Long statusOrdem) {
		this.statusOrdem = statusOrdem;
	}

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public Long getCodOrdem() {
		return codOrdem;
	}

	public void setCodOrdem(Long codOrdem) {
		this.codOrdem = codOrdem;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	
}

package com.gerenciamento.oficina.entity;

import java.sql.Date;

public class OrdemServico {
	
	private Long codOrdem;
	
	private Long codUsuario;
	
	private Usuario usuario;
	
	private Long codVeiculo;
	
	private Double valorTotal;
	
	private Veiculo veiculo;
	
	private Date dataEmissao;
	
	private Long statusOrdem;
	
	private String placaVeiculo;
	
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public Long getStatusOrdem() {
		return statusOrdem;
	}

	public void setStatusOrdem(Long statusOrdem) {
		this.statusOrdem = statusOrdem;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public Long getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(Long codVeiculo) {
		this.codVeiculo = codVeiculo;
	}
	
}

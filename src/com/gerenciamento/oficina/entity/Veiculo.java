package com.gerenciamento.oficina.entity;

import java.sql.Date;

public class Veiculo {
	
	private long codVeiculo;
	
	private Long codCliente;
	
	private String placaVeiculo;
	
	private String corVeiculo;
	
	private String modeloVeiculo;
	
	private Date anoVeiculo;
	
	private String marcaVeiculo;

	public long getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(long codVeiculo) {
		this.codVeiculo = codVeiculo;
	}

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getCorVeiculo() {
		return corVeiculo;
	}

	public void setCorVeiculo(String corVeiculo) {
		this.corVeiculo = corVeiculo;
	}

	public String getModeloVeiculo() {
		return modeloVeiculo;
	}

	public void setModeloVeiculo(String modeloVeiculo) {
		this.modeloVeiculo = modeloVeiculo;
	}

	public Date getAnoVeiculo() {
		return anoVeiculo;
	}

	public void setAnoVeiculo(Date anoVeiculo) {
		this.anoVeiculo = anoVeiculo;
	}

	public String getMarcaVeiculo() {
		return marcaVeiculo;
	}

	public void setMarcaVeiculo(String marcaVeiculo) {
		this.marcaVeiculo = marcaVeiculo;
	}
	
}

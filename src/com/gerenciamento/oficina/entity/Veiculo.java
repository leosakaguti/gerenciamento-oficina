package com.gerenciamento.oficina.entity;

public class Veiculo {
	
	private Long codVeiculo;
	
	private Long codCliente;
	
	private Cliente cliente;
	
	private String placaVeiculo;
	
	private String corVeiculo;
	
	private String marcaModeloVeiculo;
	
	private String nomeCliente;
	
	private Long anoVeiculo;
	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getCodVeiculo() {
		return codVeiculo;
	}

	public void setCodVeiculo(Long codVeiculo) {
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

	public String getMarcaModeloVeiculo() {
		return marcaModeloVeiculo;
	}

	public void setMarcaModeloVeiculo(String marcaModeloVeiculo) {
		this.marcaModeloVeiculo = marcaModeloVeiculo;
	}

	public Long getAnoVeiculo() {
		return anoVeiculo;
	}

	public void setAnoVeiculo(Long anoVeiculo) {
		this.anoVeiculo = anoVeiculo;
	}
	
}

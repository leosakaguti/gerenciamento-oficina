package com.gerenciamento.oficina.entity;

public class Usuario {
	
	private Long codUsuario;
	
	private String usuario;
	
	private String nomeUsuario;
	
	private String senhaUsuario;
	
	private Long isAdmin;
	
	private Long isLogado;

	public Long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public Long getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Long isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Long getIsLogado() {
		return isLogado;
	}

	public void setIsLogado(Long isLogado) {
		this.isLogado = isLogado;
	}
	
}

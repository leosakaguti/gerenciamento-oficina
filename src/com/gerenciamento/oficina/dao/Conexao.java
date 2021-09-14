package com.gerenciamento.oficina.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private static final String LOGIN_BANCO = "root";
	
	private static final String SENHA_BANCO = "root";
	
	private static final String URL_BANCO = "jdbc:mysql://localhost:3306/gerenciamento-oficina?autoReconnect=true&useSSL=false";
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Conexao.URL_BANCO, Conexao.LOGIN_BANCO, Conexao.SENHA_BANCO);
		}catch (SQLException e){
			System.out.println("Erro ao conectar ao banco de dados. Erro: "+e);
		}catch (ClassNotFoundException e) {
			System.out.println("Não foi possível carregar a classe JDBC MySQL. Erro: "+e);
		}
		
		return conn;
	}
}

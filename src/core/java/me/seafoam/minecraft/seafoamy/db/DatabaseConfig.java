package me.seafoam.minecraft.seafoamy.db;

import lombok.Getter;

@Getter
public class DatabaseConfig {

	private String host;
	private int port;
	private String database;
	private String username;
	private String password;

	public DatabaseConfig setHost(String host) {
		this.host = host;
		return this;
	}

	public DatabaseConfig setPort(int port) {
		this.port = port;
		return this;
	}

	public DatabaseConfig setDatabase(String database) {
		this.database = database;
		return this;
	}

	public DatabaseConfig setUsername(String username) {
		this.username = username;
		return this;
	}

	public DatabaseConfig setPassword(String password) {
		this.password = password;
		return this;
	}
}

package me.seafoam.minecraft.seafoamy.db;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.Getter;

public class Database {

	@Getter private HikariDataSource hikari;
	private final DatabaseConfig config;

	public Database(DatabaseConfig config) {
		this.config = config;

		init();
	}

	private void init() {
		hikari = new HikariDataSource();
		hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
		hikari.addDataSourceProperty("serverName", config.getHost());
		hikari.addDataSourceProperty("port", config.getPort());
		hikari.addDataSourceProperty("databaseName", config.getDatabase());
		hikari.addDataSourceProperty("user", config.getUsername());
		hikari.addDataSourceProperty("password", config.getPassword());
		hikari.addDataSourceProperty("cachePrepStmts", "true");
		hikari.addDataSourceProperty("prepStmtCacheSize", "250");
		hikari.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	}

	public Connection getConnection() throws SQLException {
		try {
			return hikari.getConnection();
		} catch (SQLException e) {
			hikari.close();
			init();
			return hikari.getConnection();
		}
	}
}

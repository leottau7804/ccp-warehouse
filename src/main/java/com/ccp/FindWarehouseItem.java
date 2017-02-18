package com.ccp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Warehouse function
 * 
 * @author sergioleottau
 *
 */
public class FindWarehouseItem implements RequestHandler<Integer, WarehouseItem> {

	/**
	 * The query to search a warehouse item
	 */
	private static final String FIND_WAREHOUSE_ITEM = "SELECT producto_id, cantidad FROM ccp.inventario WHERE producto_id = ?";

	/**
	 * Handle the request
	 * 
	 * @param productId
	 *            product identification
	 * @param context
	 *            context
	 * 
	 * @return WarehouseItem the warehouse found or null otherwise
	 */
	public WarehouseItem handleRequest(Integer productId, Context context) {

		WarehouseItem warehouseItem = null;
		ResultSet resultSet = null;

		try (Connection connection = getConnection(context);
				PreparedStatement preparedStatement = connection.prepareStatement(FIND_WAREHOUSE_ITEM)) {

			preparedStatement.setInt(1, productId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				warehouseItem = WarehouseItem.buildWarehouseItem(resultSet);
			}

		} catch (Exception e) {
			context.getLogger().log("Unexpected error trying to search a warehouse item: " + e.getMessage());
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// Ignore sql exception during the result set closing
				}
			}
		}

		return warehouseItem;
	}

	/**
	 * Creates a new database connection
	 * 
	 * @param context
	 *            context to log error messages
	 * @return the connection built
	 */
	private static Connection getConnection(Context context) {

		context.getLogger().log("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			Class.forName("org.postgresql.Driver");

			String host = System.getenv("database_host");
			String port = System.getenv("database_port");
			String database = System.getenv("database");
			String databaseUser = System.getenv("database_user");
			String databasePassword = System.getenv("database_password");

			StringBuilder connectionString = new StringBuilder("jdbc:postgresql://");
			connectionString.append(host);
			connectionString.append(":");
			connectionString.append(port);
			connectionString.append("/");
			connectionString.append(database);

			context.getLogger().log("Connection string: " + connectionString.toString());
			
			connection = DriverManager.getConnection(connectionString.toString(), databaseUser, databasePassword);
		} catch (ClassNotFoundException e) {
			context.getLogger().log("Connection Failed! Check output console");
		} catch (SQLException e) {
			context.getLogger().log("Connection Failed! Check output console");
		}

		return connection;

	}

}

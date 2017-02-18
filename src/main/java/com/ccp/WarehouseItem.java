package com.ccp;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Current information about a item in the warehouse
 * 
 * @author sergioleottau
 *
 */
public class WarehouseItem {

	/**
	 * Product identification
	 */
	private Integer productId;
	/**
	 * Product quantity currently in the all warehouses
	 */
	private Integer quantity;

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Builds a warehouse item with the result set given
	 * 
	 * @param resultSet
	 *            result set information
	 * @return the warehouse item built
	 * @throws SQLException
	 */
	public static WarehouseItem buildWarehouseItem(ResultSet resultSet) throws SQLException {
		WarehouseItem warehouseItem = new WarehouseItem();

		warehouseItem.setProductId(resultSet.getInt(1));
		warehouseItem.setQuantity(resultSet.getInt(2));

		return warehouseItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WarehouseItem [productId=" + productId + ", quantity=" + quantity + "]";
	}

}

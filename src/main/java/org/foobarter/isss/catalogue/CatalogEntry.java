package org.foobarter.isss.catalogue;

import java.math.BigDecimal;

public class CatalogEntry {
	private long id;
	private Long storeId;
	private String name;
	private BigDecimal price;

	private boolean isDir;

	public CatalogEntry() {}

	public CatalogEntry(long id, Long storeId, String name, BigDecimal price, boolean isDir) {
		this.id = id;
		this.storeId = storeId;
		this.name = name;
		this.price = price;
		this.isDir = isDir;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		if (price == null) {
			return null;
		}
		return price.toPlainString();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean dir) {
		isDir = dir;
	}
}

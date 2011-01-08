package de.db12.common.db.data;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.Sql;
import com.avaje.ebean.annotation.SqlSelect;
import com.avaje.ebean.annotation.Transactional;
import com.avaje.ebean.validation.Length;

@Entity
@Table(name = "o_product")
@Sql(select = { @SqlSelect(name = "test", query = "select id, name from o_product") })
public class Product {

	@Id
	Integer id;

	@Length(max = 20)
	String sku;

	String name;

	@CreatedTimestamp
	Timestamp cretime;

	Timestamp updtime;

	@Version
	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCretime() {
		return cretime;
	}

	public void setCretime(Timestamp cretime) {
		this.cretime = cretime;
	}

	public Timestamp getUpdtime() {
		return updtime;
	}

	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}

	// needed by GWT RequestFactory
	public static Product findProduct(Integer id) {
		return Ebean.find(Product.class, id);
	}

	@Transactional
	public static List<Product> findAllProducts() {
		return Ebean.find(Product.class).findList();

	}

	public void persist() {
		Ebean.save(this);
	}

	public void remove() {
		Ebean.delete(this);
	}
}

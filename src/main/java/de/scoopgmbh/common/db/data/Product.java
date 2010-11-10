package de.scoopgmbh.common.db.data;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.Sql;
import com.avaje.ebean.annotation.SqlSelect;
import com.avaje.ebean.validation.Length;

/**
 * ProductDto entity bean.
 */
@Entity
@Table(name="o_product")
@Sql(select={
		@SqlSelect(name="test",query="select id, name from o_product")
	}
)
public class Product {

    @Id
    Integer id;

    @Length(max=20)
    String sku;

    String name;

    @CreatedTimestamp
    Timestamp cretime;

    @Version
    Timestamp updtime;

    /**
     * Return id.
     */    
    public Integer getId() {
  	    return id;
    }

    /**
     * Set id.
     */    
    public void setId(Integer id) {
  	    this.id = id;
    }

    /**
     * Return sku.
     */    
    public String getSku() {
  	    return sku;
    }

    /**
     * Set sku.
     */    
    public void setSku(String sku) {
  	    this.sku = sku;
    }

    /**
     * Return name.
     */    
    public String getName() {
  	    return name;
    }

    /**
     * Set name.
     */    
    public void setName(String name) {
  	    this.name = name;
    }

    /**
     * Return cretime.
     */    
    public Timestamp getCretime() {
  	    return cretime;
    }

    /**
     * Set cretime.
     */    
    public void setCretime(Timestamp cretime) {
  	    this.cretime = cretime;
    }

    /**
     * Return updtime.
     */    
    public Timestamp getUpdtime() {
  	    return updtime;
    }

    /**
     * Set updtime.
     */    
    public void setUpdtime(Timestamp updtime) {
  	    this.updtime = updtime;
    }
    
}

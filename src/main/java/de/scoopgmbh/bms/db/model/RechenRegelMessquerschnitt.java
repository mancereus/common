package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("MESSQUERSCHNITT")
public class RechenRegelMessquerschnitt extends RechenRegelNode implements Serializable {

	private static final long serialVersionUID = 7903869027904758055L;

	@Column(name="VALUE")
	private String mqName;
	@Transient
	private List<Integer> values;

	public void setMqName(String mqName) {
		this.mqName = mqName;
	}

	public String getMqName() {
		return mqName;
	}
	
	@Override
	public List<String> getMQNames() {
		List<String> ret = new LinkedList<String>();
		ret.add(mqName);
		return ret;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public List<Integer> getValues() {
		return values;
	}
	
	@Override
	public boolean setValues(String mqName, List<Integer> values) throws Exception {
		if (this.mqName!=null && this.mqName.equals(mqName)) {
			this.setValues(values);
			return true;
		} else
			return false;
	}
	
	public List<Integer> getResult() {
		return this.values;
	}
	
}

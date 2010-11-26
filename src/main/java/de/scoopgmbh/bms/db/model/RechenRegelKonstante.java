package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("KONSTANTE")
public class RechenRegelKonstante extends RechenRegelNode implements Serializable {

	private static final long serialVersionUID = -2321369580110499639L;

	@Column(name="CONST")
	private double value;

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public boolean setValues(String mqName, List<Integer> values) throws Exception {
		return false;
	}
}

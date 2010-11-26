package de.scoopgmbh.bms.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author hirtech
 * @version 1.0
 * @created 25-Okt-2010 09:28:29
 */
@Entity
@DiscriminatorValue("POLIZEIAUTOBAHNSTATION")
public class AutobahnPolizeiStation extends Autoritaet {

	private static final long serialVersionUID = -4071751740155538967L;

	public AutobahnPolizeiStation() {
		super();
	}
	
}

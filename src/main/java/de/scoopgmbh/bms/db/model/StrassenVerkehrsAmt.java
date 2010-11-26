package de.scoopgmbh.bms.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author hirtech
 * @version 1.0
 * @created 25-Okt-2010 09:28:29
 */
@Entity
@DiscriminatorValue("STRASSENVERKEHRSAMT")
public class StrassenVerkehrsAmt extends Autoritaet {

	private static final long serialVersionUID = -8055116348164139569L;

	public StrassenVerkehrsAmt() {
		super();
	}
	
	
	
}

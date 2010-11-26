package de.scoopgmbh.bms.db.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * @author hirtech
 * @version 1.0
 * @created 25-Okt-2010 10:31:29
 */
@Entity
@DiscriminatorValue("AMTBAUSTOFFBODENPR")
public class BaustoffBodenPruefAmt extends Autoritaet {

	private static final long serialVersionUID = -6867283585921180530L;

}

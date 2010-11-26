package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SEQUENCE")
public class Sequence implements Serializable {

	public static final String SEQUENCE_NAME = "GLOBAL";
	public static final String SEQUENCE_NAME_TEST = "TEST_GLOBAL";
	
	private static final long serialVersionUID = 6924993872674181790L;

	@Id
	@Column(name="SEQNAME")
	private String seq;
	@Column(name="NEXTIDINSEQ")
	private long nextId;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public long getNextId() {
		return nextId;
	}
	public void setNextId(long nextId) {
		this.nextId = nextId;
	}
	
}

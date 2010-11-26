package de.scoopgmbh.bms.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 30-Sep-2010 12:05:00
 * 
 */
@Entity
@Table(name = "RECHENREGEL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISKREMINATOR", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "INNERKNOTEN")
public class RechenRegelNode implements Serializable {

	private static final long serialVersionUID = 5212498495846668556L;

	public static final char PLUS = '+';
	public static final char MINUS = '-';
	public static final char DIV = '/';
	public static final char MUL = '*';

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GLOBAL_SEQUENCE")
	private long id;
	/**
	 * Id des Benutzer der den Datensatz angelegt hat.
	 */
	@Column(name = "LOGINS_ID")
	private long editorId;
	/**
	 * Für optimistic locking.
	 */
	@Version
	@Column(name = "VERSION")
	private long version;
	@Column(name = "DELETED")
	private char deleted = DB.NO;
	@Column(name = "ABSCHNITT_ID")
	private Abschnitt idAbschnitt;
	private RechenRegelNode idLeftNode;
	private RechenRegelNode idRightNode;
	@Column(name = "OPERATOR")
	private char operator = PLUS;

	@Column(name = "LEFTCHILD_ID")
	@ManyToOne
	private RechenRegelNode leftNode;

	@Column(name = "RIGHTCHILD_ID")
	@ManyToOne
	private RechenRegelNode rightNode;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setEditorId(long editorId) {
		this.editorId = editorId;
	}

	public long getEditorId() {
		return editorId;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

	public RechenRegelNode getIdChildLeft() {
		return idLeftNode;
	}

	public void setIdChildLeft(RechenRegelNode idChildLeft) {
		this.idLeftNode = idChildLeft;
	}

	public RechenRegelNode getIdChildRigth() {
		return idRightNode;
	}

	public void setIdChildRigth(RechenRegelNode idChildRigth) {
		this.idRightNode = idChildRigth;
	}

	public void setIdAbschnitt(Abschnitt idAbschnitt) {
		this.idAbschnitt = idAbschnitt;
	}

	public Abschnitt getIdAbschnitt() {
		return idAbschnitt;
	}

	public void setLeftNode(RechenRegelNode leftNode) {
		this.leftNode = leftNode;
	}

	public RechenRegelNode getLeftNode() {
		return leftNode;
	}

	public void setRightNode(RechenRegelNode rightNode) {
		this.rightNode = rightNode;
	}

	public RechenRegelNode getRightNode() {
		return rightNode;
	}

	public void setOperator(char operator) {
		this.operator = operator;
	}

	public char getOperator() {
		return operator;
	}

	public List<String> getMQNames() {
		List<String> ret = new LinkedList<String>();
		if (leftNode != null)
			ret.addAll(leftNode.getMQNames());
		if (rightNode != null)
			ret.addAll(rightNode.getMQNames());
		return ret;
	}

	public boolean setValues(String mqName, List<Integer> values)
			throws Exception {
		if (this.leftNode != null && !this.leftNode.setValues(mqName, values))
			if (this.rightNode != null
					&& !this.rightNode.setValues(mqName, values))
				throw new Exception(
						"Messquerschnitt nicht gefunden; in Rechenregel");
		return true;
	}

	public List<Integer> getResult() {

		if (this.leftNode instanceof RechenRegelKonstante) {
			if (this.rightNode instanceof RechenRegelKonstante) {
				throw new RuntimeException(
						"Dumm! Arithmetrische Ausdrücke mit zwei Konstanten sind nicht zugelassen.");
			} else {
				return this.handel(this.rightNode.getResult(),
						((RechenRegelKonstante) this.rightNode).getValue());
			}
		} else {
			if (this.rightNode instanceof RechenRegelKonstante) {
				return this.handel(this.leftNode.getResult(),
						((RechenRegelKonstante) this.rightNode).getValue());
			} else {
				return this.handel(this.leftNode.getResult(),
						this.rightNode.getResult());
			}
		}
	}

	private List<Integer> handel(List<Integer> vals, Double d) {
		List<Integer> ret;
		switch (this.operator) {
		case RechenRegelNode.DIV:
			ret = this.div(vals, d);
			break;
		case RechenRegelNode.MUL:
			ret = this.mul(vals, d);
			break;
		case RechenRegelNode.MINUS:
			ret = this.sub(vals, d);
			break;
		case RechenRegelNode.PLUS:
			ret = this.add(vals, d);
			break;
		default:
			ret = null;
			break;
		}
		return ret;
	}

	private List<Integer> handel(List<Integer> vals1, List<Integer> vals2) {
		List<Integer> ret;
		switch (this.operator) {
		case RechenRegelNode.DIV:
			ret = this.div(vals1, vals2);
			break;
		case RechenRegelNode.MUL:
			ret = this.mul(vals1, vals2);
			break;
		case RechenRegelNode.MINUS:
			ret = this.sub(vals1, vals2);
			break;
		case RechenRegelNode.PLUS:
			ret = this.add(vals1, vals2);
			break;
		default:
			ret = null;
			break;
		}
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RechenRegelNode other = (RechenRegelNode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private List<Integer> mul(List<Integer> vals, Double d) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		for (Integer i : vals)
			if (i != null)
				ret.add((int) (i * d));
			else
				ret.add(i);
		return ret;
	}

	private List<Integer> div(List<Integer> vals, Double d) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		for (Integer i : vals)
			if (i != null)
				ret.add((int) (i / d));
			else
				ret.add(i);
		return ret;
	}

	private List<Integer> add(List<Integer> vals, Double d) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		for (Integer i : vals)
			if (i != null)
				ret.add((int) (i + d));
			else
				ret.add(i);
		return ret;
	}

	private List<Integer> sub(List<Integer> vals, Double d) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		for (Integer i : vals)
			if (i != null)
				ret.add((int) (i - d));
			else
				ret.add(i);
		return ret;
	}

	private List<Integer> mul(List<Integer> vals, List<Integer> vals2) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		if (vals.size() != vals2.size())
			throw new RuntimeException(
					"Referenzganglinie verfügen nicht über die gleiche Anzahl Spalten");
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) != null && vals2.get(i) != null)
				ret.add((vals.get(i) * vals2.get(i)));
			else if (vals.get(i) == null)
				ret.add(vals2.get(i));
			else
				ret.add(vals.get(i));
		}
		return ret;
	}

	private List<Integer> div(List<Integer> vals, List<Integer> vals2) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		if (vals.size() != vals2.size())
			throw new RuntimeException(
					"Referenzganglinie verfügen nicht über die gleiche Anzahl Spalten");
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) != null && vals2.get(i) != null)
				ret.add((vals.get(i) / vals2.get(i)));
			else if (vals.get(i) == null)
				ret.add(vals2.get(i));
			else
				ret.add(vals.get(i));
		}
		return ret;
	}

	private List<Integer> add(List<Integer> vals, List<Integer> vals2) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		if (vals.size() != vals2.size())
			throw new RuntimeException(
					"Referenzganglinie verfügen nicht über die gleiche Anzahl Spalten");
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) != null && vals2.get(i) != null)
				ret.add((vals.get(i) + vals2.get(i)));
			else if (vals.get(i) == null)
				ret.add(vals2.get(i));
			else
				ret.add(vals.get(i));
		}
		return ret;
	}

	private List<Integer> sub(List<Integer> vals, List<Integer> vals2) {
		List<Integer> ret = new ArrayList<Integer>(vals.size());
		if (vals.size() != vals2.size())
			throw new RuntimeException(
					"Referenzganglinie verfügen nicht über die gleiche Anzahl Spalten");
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i) != null && vals2.get(i) != null)
				ret.add((vals.get(i) - vals2.get(i)));
			else if (vals.get(i) == null)
				ret.add(vals2.get(i));
			else
				ret.add(vals.get(i));
		}
		return ret;
	}
}

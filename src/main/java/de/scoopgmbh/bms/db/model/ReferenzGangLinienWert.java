package de.scoopgmbh.bms.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author hirtech
 * @version 1.0
 * @created 06-Sep-2010 10:47:32
 */
@Entity
@Table(name = "REFWERTE")
public class ReferenzGangLinienWert implements Serializable {

	private static final long serialVersionUID = 1495678710996935376L;

	public static final String TYP_KFZ = "qKfz";
	public static final String TYP_LKW = "qLkw";
	public static final int numWerte = 96;

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
	@Column(name = "REFGANGLINIEN_ID")
	@ManyToOne
	private ReferenzGangLinie idReferenzGangLinie;
	@Column(name = "KFZTYP")
	private String typ;

	@Override
	public String toString() {
		return "ReferenzGangLinienWert [id=" + id + ", idReferenzGangLinie="
				+ idReferenzGangLinie + "]";
	}

	private Integer val0;
	private Integer val1;
	private Integer val2;
	private Integer val3;
	private Integer val4;
	private Integer val5;
	private Integer val6;
	private Integer val7;
	private Integer val8;
	private Integer val9;

	private Integer val10;
	private Integer val11;
	private Integer val12;
	private Integer val13;
	private Integer val14;
	private Integer val15;
	private Integer val16;
	private Integer val17;
	private Integer val18;
	private Integer val19;

	private Integer val20;
	private Integer val21;
	private Integer val22;
	private Integer val23;
	private Integer val24;
	private Integer val25;
	private Integer val26;
	private Integer val27;
	private Integer val28;
	private Integer val29;

	private Integer val30;
	private Integer val31;
	private Integer val32;
	private Integer val33;
	private Integer val34;
	private Integer val35;
	private Integer val36;
	private Integer val37;
	private Integer val38;
	private Integer val39;

	private Integer val40;
	private Integer val41;
	private Integer val42;
	private Integer val43;
	private Integer val44;
	private Integer val45;
	private Integer val46;
	private Integer val47;
	private Integer val48;
	private Integer val49;

	private Integer val50;
	private Integer val51;
	private Integer val52;
	private Integer val53;
	private Integer val54;
	private Integer val55;
	private Integer val56;
	private Integer val57;
	private Integer val58;
	private Integer val59;

	private Integer val60;
	private Integer val61;
	private Integer val62;
	private Integer val63;
	private Integer val64;
	private Integer val65;
	private Integer val66;
	private Integer val67;
	private Integer val68;
	private Integer val69;

	private Integer val70;
	private Integer val71;
	private Integer val72;
	private Integer val73;
	private Integer val74;
	private Integer val75;
	private Integer val76;
	private Integer val77;
	private Integer val78;
	private Integer val79;

	private Integer val80;
	private Integer val81;
	private Integer val82;
	private Integer val83;
	private Integer val84;
	private Integer val85;
	private Integer val86;
	private Integer val87;
	private Integer val88;
	private Integer val89;

	private Integer val90;
	private Integer val91;
	private Integer val92;
	private Integer val93;
	private Integer val94;
	private Integer val95;

	public ReferenzGangLinienWert() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReferenzGangLinie getIdReferenzGangLinie() {
		return idReferenzGangLinie;
	}

	public void setIdReferenzGangLinie(ReferenzGangLinie idReferenzGangLinie) {
		this.idReferenzGangLinie = idReferenzGangLinie;
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

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
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
		ReferenzGangLinienWert other = (ReferenzGangLinienWert) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public char getDeleted() {
		return deleted;
	}

	public Integer getVal0() {
		return val0;
	}

	public void setVal0(Integer val0) {
		this.val0 = val0;
	}

	public Integer getVal1() {
		return val1;
	}

	public void setVal1(Integer val1) {
		this.val1 = val1;
	}

	public Integer getVal2() {
		return val2;
	}

	public void setVal2(Integer val2) {
		this.val2 = val2;
	}

	public Integer getVal3() {
		return val3;
	}

	public void setVal3(Integer val3) {
		this.val3 = val3;
	}

	public Integer getVal4() {
		return val4;
	}

	public void setVal4(Integer val4) {
		this.val4 = val4;
	}

	public Integer getVal5() {
		return val5;
	}

	public void setVal5(Integer val5) {
		this.val5 = val5;
	}

	public Integer getVal6() {
		return val6;
	}

	public void setVal6(Integer val6) {
		this.val6 = val6;
	}

	public Integer getVal7() {
		return val7;
	}

	public void setVal7(Integer val7) {
		this.val7 = val7;
	}

	public Integer getVal8() {
		return val8;
	}

	public void setVal8(Integer val8) {
		this.val8 = val8;
	}

	public Integer getVal9() {
		return val9;
	}

	public void setVal9(Integer val9) {
		this.val9 = val9;
	}

	public Integer getVal10() {
		return val10;
	}

	public void setVal10(Integer val10) {
		this.val10 = val10;
	}

	public Integer getVal11() {
		return val11;
	}

	public void setVal11(Integer val11) {
		this.val11 = val11;
	}

	public Integer getVal12() {
		return val12;
	}

	public void setVal12(Integer val12) {
		this.val12 = val12;
	}

	public Integer getVal13() {
		return val13;
	}

	public void setVal13(Integer val13) {
		this.val13 = val13;
	}

	public Integer getVal14() {
		return val14;
	}

	public void setVal14(Integer val14) {
		this.val14 = val14;
	}

	public Integer getVal15() {
		return val15;
	}

	public void setVal15(Integer val15) {
		this.val15 = val15;
	}

	public Integer getVal16() {
		return val16;
	}

	public void setVal16(Integer val16) {
		this.val16 = val16;
	}

	public Integer getVal17() {
		return val17;
	}

	public void setVal17(Integer val17) {
		this.val17 = val17;
	}

	public Integer getVal18() {
		return val18;
	}

	public void setVal18(Integer val18) {
		this.val18 = val18;
	}

	public Integer getVal19() {
		return val19;
	}

	public void setVal19(Integer val19) {
		this.val19 = val19;
	}

	public Integer getVal20() {
		return val20;
	}

	public void setVal20(Integer val20) {
		this.val20 = val20;
	}

	public Integer getVal21() {
		return val21;
	}

	public void setVal21(Integer val21) {
		this.val21 = val21;
	}

	public Integer getVal22() {
		return val22;
	}

	public void setVal22(Integer val22) {
		this.val22 = val22;
	}

	public Integer getVal23() {
		return val23;
	}

	public void setVal23(Integer val23) {
		this.val23 = val23;
	}

	public Integer getVal24() {
		return val24;
	}

	public void setVal24(Integer val24) {
		this.val24 = val24;
	}

	public Integer getVal25() {
		return val25;
	}

	public void setVal25(Integer val25) {
		this.val25 = val25;
	}

	public Integer getVal26() {
		return val26;
	}

	public void setVal26(Integer val26) {
		this.val26 = val26;
	}

	public Integer getVal27() {
		return val27;
	}

	public void setVal27(Integer val27) {
		this.val27 = val27;
	}

	public Integer getVal28() {
		return val28;
	}

	public void setVal28(Integer val28) {
		this.val28 = val28;
	}

	public Integer getVal29() {
		return val29;
	}

	public void setVal29(Integer val29) {
		this.val29 = val29;
	}

	public Integer getVal30() {
		return val30;
	}

	public void setVal30(Integer val30) {
		this.val30 = val30;
	}

	public Integer getVal31() {
		return val31;
	}

	public void setVal31(Integer val31) {
		this.val31 = val31;
	}

	public Integer getVal32() {
		return val32;
	}

	public void setVal32(Integer val32) {
		this.val32 = val32;
	}

	public Integer getVal33() {
		return val33;
	}

	public void setVal33(Integer val33) {
		this.val33 = val33;
	}

	public Integer getVal34() {
		return val34;
	}

	public void setVal34(Integer val34) {
		this.val34 = val34;
	}

	public Integer getVal35() {
		return val35;
	}

	public void setVal35(Integer val35) {
		this.val35 = val35;
	}

	public Integer getVal36() {
		return val36;
	}

	public void setVal36(Integer val36) {
		this.val36 = val36;
	}

	public Integer getVal37() {
		return val37;
	}

	public void setVal37(Integer val37) {
		this.val37 = val37;
	}

	public Integer getVal38() {
		return val38;
	}

	public void setVal38(Integer val38) {
		this.val38 = val38;
	}

	public Integer getVal39() {
		return val39;
	}

	public void setVal39(Integer val39) {
		this.val39 = val39;
	}

	public Integer getVal40() {
		return val40;
	}

	public void setVal40(Integer val40) {
		this.val40 = val40;
	}

	public Integer getVal41() {
		return val41;
	}

	public void setVal41(Integer val41) {
		this.val41 = val41;
	}

	public Integer getVal42() {
		return val42;
	}

	public void setVal42(Integer val42) {
		this.val42 = val42;
	}

	public Integer getVal43() {
		return val43;
	}

	public void setVal43(Integer val43) {
		this.val43 = val43;
	}

	public Integer getVal44() {
		return val44;
	}

	public void setVal44(Integer val44) {
		this.val44 = val44;
	}

	public Integer getVal45() {
		return val45;
	}

	public void setVal45(Integer val45) {
		this.val45 = val45;
	}

	public Integer getVal46() {
		return val46;
	}

	public void setVal46(Integer val46) {
		this.val46 = val46;
	}

	public Integer getVal47() {
		return val47;
	}

	public void setVal47(Integer val47) {
		this.val47 = val47;
	}

	public Integer getVal48() {
		return val48;
	}

	public void setVal48(Integer val48) {
		this.val48 = val48;
	}

	public Integer getVal49() {
		return val49;
	}

	public void setVal49(Integer val49) {
		this.val49 = val49;
	}

	public Integer getVal50() {
		return val50;
	}

	public void setVal50(Integer val50) {
		this.val50 = val50;
	}

	public Integer getVal51() {
		return val51;
	}

	public void setVal51(Integer val51) {
		this.val51 = val51;
	}

	public Integer getVal52() {
		return val52;
	}

	public void setVal52(Integer val52) {
		this.val52 = val52;
	}

	public Integer getVal53() {
		return val53;
	}

	public void setVal53(Integer val53) {
		this.val53 = val53;
	}

	public Integer getVal54() {
		return val54;
	}

	public void setVal54(Integer val54) {
		this.val54 = val54;
	}

	public Integer getVal55() {
		return val55;
	}

	public void setVal55(Integer val55) {
		this.val55 = val55;
	}

	public Integer getVal56() {
		return val56;
	}

	public void setVal56(Integer val56) {
		this.val56 = val56;
	}

	public Integer getVal57() {
		return val57;
	}

	public void setVal57(Integer val57) {
		this.val57 = val57;
	}

	public Integer getVal58() {
		return val58;
	}

	public void setVal58(Integer val58) {
		this.val58 = val58;
	}

	public Integer getVal59() {
		return val59;
	}

	public void setVal59(Integer val59) {
		this.val59 = val59;
	}

	public Integer getVal60() {
		return val60;
	}

	public void setVal60(Integer val60) {
		this.val60 = val60;
	}

	public Integer getVal61() {
		return val61;
	}

	public void setVal61(Integer val61) {
		this.val61 = val61;
	}

	public Integer getVal62() {
		return val62;
	}

	public void setVal62(Integer val62) {
		this.val62 = val62;
	}

	public Integer getVal63() {
		return val63;
	}

	public void setVal63(Integer val63) {
		this.val63 = val63;
	}

	public Integer getVal64() {
		return val64;
	}

	public void setVal64(Integer val64) {
		this.val64 = val64;
	}

	public Integer getVal65() {
		return val65;
	}

	public void setVal65(Integer val65) {
		this.val65 = val65;
	}

	public Integer getVal66() {
		return val66;
	}

	public void setVal66(Integer val66) {
		this.val66 = val66;
	}

	public Integer getVal67() {
		return val67;
	}

	public void setVal67(Integer val67) {
		this.val67 = val67;
	}

	public Integer getVal68() {
		return val68;
	}

	public void setVal68(Integer val68) {
		this.val68 = val68;
	}

	public Integer getVal69() {
		return val69;
	}

	public void setVal69(Integer val69) {
		this.val69 = val69;
	}

	public Integer getVal70() {
		return val70;
	}

	public void setVal70(Integer val70) {
		this.val70 = val70;
	}

	public Integer getVal71() {
		return val71;
	}

	public void setVal71(Integer val71) {
		this.val71 = val71;
	}

	public Integer getVal72() {
		return val72;
	}

	public void setVal72(Integer val72) {
		this.val72 = val72;
	}

	public Integer getVal73() {
		return val73;
	}

	public void setVal73(Integer val73) {
		this.val73 = val73;
	}

	public Integer getVal74() {
		return val74;
	}

	public void setVal74(Integer val74) {
		this.val74 = val74;
	}

	public Integer getVal75() {
		return val75;
	}

	public void setVal75(Integer val75) {
		this.val75 = val75;
	}

	public Integer getVal76() {
		return val76;
	}

	public void setVal76(Integer val76) {
		this.val76 = val76;
	}

	public Integer getVal77() {
		return val77;
	}

	public void setVal77(Integer val77) {
		this.val77 = val77;
	}

	public Integer getVal78() {
		return val78;
	}

	public void setVal78(Integer val78) {
		this.val78 = val78;
	}

	public Integer getVal79() {
		return val79;
	}

	public void setVal79(Integer val79) {
		this.val79 = val79;
	}

	public Integer getVal80() {
		return val80;
	}

	public void setVal80(Integer val80) {
		this.val80 = val80;
	}

	public Integer getVal81() {
		return val81;
	}

	public void setVal81(Integer val81) {
		this.val81 = val81;
	}

	public Integer getVal82() {
		return val82;
	}

	public void setVal82(Integer val82) {
		this.val82 = val82;
	}

	public Integer getVal83() {
		return val83;
	}

	public void setVal83(Integer val83) {
		this.val83 = val83;
	}

	public Integer getVal84() {
		return val84;
	}

	public void setVal84(Integer val84) {
		this.val84 = val84;
	}

	public Integer getVal85() {
		return val85;
	}

	public void setVal85(Integer val85) {
		this.val85 = val85;
	}

	public Integer getVal86() {
		return val86;
	}

	public void setVal86(Integer val86) {
		this.val86 = val86;
	}

	public Integer getVal87() {
		return val87;
	}

	public void setVal87(Integer val87) {
		this.val87 = val87;
	}

	public Integer getVal88() {
		return val88;
	}

	public void setVal88(Integer val88) {
		this.val88 = val88;
	}

	public Integer getVal89() {
		return val89;
	}

	public void setVal89(Integer val89) {
		this.val89 = val89;
	}

	public Integer getVal90() {
		return val90;
	}

	public void setVal90(Integer val90) {
		this.val90 = val90;
	}

	public Integer getVal91() {
		return val91;
	}

	public void setVal91(Integer val91) {
		this.val91 = val91;
	}

	public Integer getVal92() {
		return val92;
	}

	public void setVal92(Integer val92) {
		this.val92 = val92;
	}

	public Integer getVal93() {
		return val93;
	}

	public void setVal93(Integer val93) {
		this.val93 = val93;
	}

	public Integer getVal94() {
		return val94;
	}

	public void setVal94(Integer val94) {
		this.val94 = val94;
	}

	public Integer getVal95() {
		return val95;
	}

	public void setVal95(Integer val95) {
		this.val95 = val95;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getTyp() {
		return typ;
	}
}
package de.scoopgmbh.bms.core.importing.impl.ganglinien;

import java.util.HashMap;
import java.util.LinkedHashSet;

import de.scoopgmbh.bms.core.importing.impl.AbstractCSVLoader;
import de.scoopgmbh.bms.core.importing.impl.CSVColumnExpectation;
import de.scoopgmbh.bms.core.importing.impl.SubstitutionableCSVColumnExpectation;

class GangLinienLoader extends AbstractCSVLoader {

	protected static final String KLASSE = "Klasse";
	protected static final String EREIGNIS = "Ereignisname";
	protected static final String KLASSENGROESSE = "Klassengroesse";
	protected static final String WOCHENTAG = "Wochentag";
	protected static final String MQNAME = "MQ-Name";
	protected static final String DATENART = "Datenart";
	protected static final String[] $r = 
		{
		"00:00", "00:15", "00:30", "00:45",
		"01:00", "01:15", "01:30", "01:45",
		"02:00", "02:15", "02:30", "02:45",
		"03:00", "03:15", "03:30", "03:45",
		"04:00", "04:15", "04:30", "04:45",
		"05:00", "05:15", "05:30", "05:45",
		"06:00", "06:15", "06:30", "06:45",
		"07:00", "07:15", "07:30", "07:45",
		"08:00", "08:15", "08:30", "08:45",
		"09:00", "09:15", "09:30", "09:45",
		"10:00", "10:15", "10:30", "10:45",
		"11:00", "11:15", "11:30", "11:45",
		"12:00", "12:15", "12:30", "12:45",
		"13:00", "13:15", "13:30", "13:45",
		"14:00", "14:15", "14:30", "14:45",
		"15:00", "15:15", "15:30", "15:45",
		"16:00", "16:15", "16:30", "16:45",
		"17:00", "17:15", "17:30", "17:45",
		"18:00", "18:15", "18:30", "18:45",
		"19:00", "19:15", "19:30", "19:45",
		"20:00", "20:15", "20:30", "20:45",
		"21:00", "21:15", "21:30", "21:45",
		"22:00", "22:15", "22:30", "22:45",
		"23:00", "23:15", "23:30", "23:45"
		};
	
	// DO NOT CHANGE DEFAULT VALUE - mmr is unsing this in his timeline analysis
	private static Integer DEFAULT_VALUE = null;// Default value der verwendet wird, wenn der Wert in der CSV-Datei null ist
	private static int CAR_COUNT_MAX = 10000;	// Maximalanzahl Autos die pro Intervall den Messquerschnitt überfahren können; darüber wird verworfen
	private static int CAR_COUNT_MIN = -1;		// Mindestanzahl Autos, die pro Intervall den Messquerschnitt überfahren müssen; darunter wird verworfen
	
	GangLinienLoader() {
		LinkedHashSet<CSVColumnExpectation<?>> cols = new LinkedHashSet<CSVColumnExpectation<?>>();
			
		cols.add(new CSVColumnExpectation<String>(KLASSE, String.class, false, CAR_COUNT_MIN, 24)); // FIXME wieso 24? - geraten
		cols.add(new CSVColumnExpectation<String>(EREIGNIS, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<Integer>(KLASSENGROESSE, Integer.class, false, 0, CAR_COUNT_MAX));
		cols.add(new CSVColumnExpectation<Integer>(WOCHENTAG, Integer.class, true, 0, 6, -1));	
		cols.add(new CSVColumnExpectation<String>(MQNAME, String.class, false, 1, 255));
		cols.add(new CSVColumnExpectation<String>(DATENART, String.class, false, 1, 255));
	
		// Substitutionen für die Werte in der CSV Datei
		HashMap<String, String> subs = new HashMap<String, String>();
		subs.put("-1", null);		// -1 ist als null zubehandeln und wird später im Programm interpoliert
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[0], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[1], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[2], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[3], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[4], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[5], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[6], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[7], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[8], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[9], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[10], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[11], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[12], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[13], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[14], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[15], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[16], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[17], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[18], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[19], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[20], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[21], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[22], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[23], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[24], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[25], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[26], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[27], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[28], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[29], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[30], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[31], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[32], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[33], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[34], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[35], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[36], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[37], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[38], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[39], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[40], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[41], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[42], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[43], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[44], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[45], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[46], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[47], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[48], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[49], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[50], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[51], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[52], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[53], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[54], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[55], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[56], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[57], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[58], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[59], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[60], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[61], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[62], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[63], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[64], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[65], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[66], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[67], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[68], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[69], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[70], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[71], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[72], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[73], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[74], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[75], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[76], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[77], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[78], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[79], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[80], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[81], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[82], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[83], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[84], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[85], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[86], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[87], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[88], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[89], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[90], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[91], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[92], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[93], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[94], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		cols.add(new SubstitutionableCSVColumnExpectation<Integer>($r[95], Integer.class, true, CAR_COUNT_MIN, CAR_COUNT_MAX, DEFAULT_VALUE, subs));
		
		setExpectedCsvColumns(cols);
	}
}

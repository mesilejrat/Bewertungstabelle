package com.hcm.TabelleBewertungsvergleich;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TabellenGenerator {


    public static void main(String[] args) {

        String pfadInputCSV = "..\\bewerberaufgabetabelle-master\\bewerberaufgabetabelle-master\\Bewertungstabelle\\resources\\LieferantenDaten.csv";
        File csvFileLieferanten = new File(pfadInputCSV);

        ArrayList<Lieferant> alleLieferanten = CSVImporter.getLieferantenFromCSV(csvFileLieferanten);
        String warenGruppenCSV =  "..\\bewerberaufgabetabelle-master\\bewerberaufgabetabelle-master\\Bewertungstabelle\\resources\\Warengruppen.csv";
        File csvFileWarenGruppen = new File(warenGruppenCSV);
        List<Warengruppen> alleWarenGruppen = CSVImporter.getWarenGruppo(csvFileWarenGruppen);

        try {
            generateTable(alleWarenGruppen, alleLieferanten, "..\\bewerberaufgabetabelle-master\\bewerberaufgabetabelle-master\\Bewertungstabelle\\resources\\Output.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private static void generateTable(List<Warengruppen> alleWarenGruppen, ArrayList<Lieferant> alleLieferanten, String outputPfad) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter csvFileOutputTabelle = new PrintWriter(outputPfad, "UTF-8");
        for (Lieferant lieferant : alleLieferanten) {
            if (lieferant.getStatus().equals("zugelassen")
                    || lieferant.getStatus().equals("archiviert")) {
                csvFileOutputTabelle.print(lieferant.getLieferantenBezeichnung() + ";");
            }
        }
        csvFileOutputTabelle.println();
        csvFileOutputTabelle.println("LieferantenBezeichnung; Warengruppen ; LfNR; Bew Kat 1; Bew Kat 2; Bew Kat 3;  Bew Kat 4; Bew Kat 5; Gesamtbewertung");


        for (Lieferant lieferant : alleLieferanten) {
            if(!lieferant.getStatus().equals("zugelassen")
                    && !lieferant.getStatus().equals("archiviert")){
                continue;
            }

            HashMap<String, String> outputValues = new HashMap<>();

            outputValues.put("LieferantenBezeichnung", lieferant.getLieferantenBezeichnung());
            for (Warengruppen warengruppe : alleWarenGruppen) {
                if (lieferant.getWGID().equals(warengruppe.getWGID())) {
                    outputValues.put("Warengruppen", warengruppe.getWarengruppenTitel());
                }
            }
            outputValues.put("LfNR", lieferant.getLfNR());
            outputValues.put("Bew Kat 1", lieferant.getBewKat1().toString());
            outputValues.put("Bew Kat 2", lieferant.getBewKat2().toString());
            outputValues.put("Bew Kat 3", lieferant.getBewKat3().toString());
            outputValues.put("Bew Kat 4", lieferant.getBewKat4().toString());
            outputValues.put("Bew Kat 5", lieferant.getBewKat5().toString());
            outputValues.put("Gesamtbewertung", calcGesamtBewertung(lieferant));

            csvFileOutputTabelle.printf("%s; %s; %s; %s; %s; %s; %s; %s; %s\n",outputValues.get("LieferantenBezeichnung"),outputValues.get("Warengruppen"),outputValues.get("LfNR"), outputValues.get("Bew Kat 1"),outputValues.get("Bew Kat 2"),outputValues.get("Bew Kat 3"),outputValues.get("Bew Kat 4"),outputValues.get("Bew Kat 5"),outputValues.get("Gesamtbewertung"));
        }


        csvFileOutputTabelle.close();

    }

    private static String calcGesamtBewertung(Lieferant lieferant) {

        Integer gesamtPunkte = lieferant.getBewKat1() + lieferant.getBewKat2() + lieferant.getBewKat3();

        if (gesamtPunkte >= 61) {
            return "sehr gut";
        } else if (gesamtPunkte >= 51) {
            return "gut";
        } else if (gesamtPunkte >= 41) {
            return "OK";
        } else {
            return "schlecht";
        }


    }


}

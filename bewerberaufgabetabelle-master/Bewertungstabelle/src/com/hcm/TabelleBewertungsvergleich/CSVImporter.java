package com.hcm.TabelleBewertungsvergleich;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter {


    public static ArrayList<Lieferant> getLieferantenFromCSV(File csvImportFileLieferanten) {

        System.out.println("Import Datei: " + csvImportFileLieferanten.getName() + " exists: " + csvImportFileLieferanten.exists());

        String line = "";

        BufferedReader reader = null;

        ArrayList<Lieferant> lieferanten = new ArrayList<>();

        boolean firstLine = true;

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvImportFileLieferanten), "UTF-8"));

            while ((line = reader.readLine()) != null) {


                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] lieferantenInformationenSplittedLine = line.split(";", -1);
                lieferanten.add(new Lieferant(lieferantenInformationenSplittedLine[0].trim(), lieferantenInformationenSplittedLine[1].trim(), lieferantenInformationenSplittedLine[2].trim(), Integer.parseInt(lieferantenInformationenSplittedLine[3].trim()), Integer.parseInt(lieferantenInformationenSplittedLine[4].trim()), Integer.parseInt(lieferantenInformationenSplittedLine[5].trim()), Integer.parseInt(lieferantenInformationenSplittedLine[6].trim()), Integer.parseInt(lieferantenInformationenSplittedLine[7].trim()), lieferantenInformationenSplittedLine[8].trim(), lieferantenInformationenSplittedLine[9].trim(), lieferantenInformationenSplittedLine[10].trim()));


            }


        } catch (Exception e) {
            System.err.println("Fehler beim CSV-Import!");
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                System.err.println("Fehler beim CSV-Import!");
            }
        }
        return lieferanten;

    }

    public static List<Warengruppen> getWarenGruppo(File csvImportFileWarengruppo) {

        System.out.println("Import Datei: " + csvImportFileWarengruppo.getName() + " exists: " + csvImportFileWarengruppo.exists());
        String line = "";
        BufferedReader reader = null;
        List<Warengruppen> warenGruppoList = new ArrayList<>();

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvImportFileWarengruppo), "UTF-8"));
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] warenGruppoSplittedLine = line.split(";", -1);
                warenGruppoList.add(new Warengruppen(warenGruppoSplittedLine[0].trim(), warenGruppoSplittedLine[1].trim()));
            }

        } catch (Exception e) {
            System.err.println("Fehler beim CSV-Import!");
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                System.err.println("Fehler beim CSV-Import!");
            }
        }
        return warenGruppoList;

    }



}

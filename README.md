# BewertungstabelleTask
1. Only approved and archived suppliers
I added to  existing code this :
```
for (Lieferant lieferant : alleLieferanten) {
            if(!lieferant.getStatus().equals("zugelassen")
                    && !lieferant.getStatus().equals("archiviert")){
                continue;
            }
            ......
```
2. Take into account the evaluation categories 4 + 5 as well
There already is a function for the overall calculation, calcGesamtBewertung() so I would add the categories 4 and 5 to it:
``` 
csvFileOutputTabelle.println("LieferantenBezeichnung; Warengruppen ; LfNR; Bew Kat 1; Bew Kat 2; Bew Kat 3;  Bew Kat 4; Bew Kat 5; Gesamtbewertung");
Integer gesamtPunkte = lieferant.getBewKat1() + lieferant.getBewKat2() + lieferant.getBewKat3()
        + lieferant.getBewKat4() + lieferant.getBewKat5();
   ```
3. Add commodity groups of the suppliers in plain names
Created a Java object to save the information that is on the Warengruppen.CSV (WGID and Title).
```
public class Warengruppen {
  public Warengruppen(String WGID, String warenGruppenTitel) {
    WarenGruppenTitel = warenGruppenTitel;
    this.WGID = WGID;
  }

  private String WarenGruppenTitel;
  private String WGID;

  public String getWarenGruppenTitel() {
    return WarenGruppenTitel;
  }

  public void setWarenGruppenTitel(String warenGruppenTitel) {
    WarenGruppenTitel = warenGruppenTitel;
  }

  public String getWGID() {
    return WGID;
  }

  public void setWGID(String WGID) {
    this.WGID = WGID;
  }
}
```
Created a method to read the WarenGruppo.csv getWarenGruppo().
```        
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
   ```
The last thing that needed to be done was to add the warengruppo title inside the output.csv if the WID id of warengruppen and lieferant are equal:
Added the condition to check if the WGID of lieferant and werengruppe was matching:
``` 
for (Warengruppen warengruppe : alleWarengruppen) {
    if (lieferant.getWGID().equals(warengruppe.getWGID())) {
        outputValues.put("Warengruppen", warengruppe.getWarenGruppenTitel());
    }
}
```
I added in the map that represent the values that will be printed in the file even the group title
```
outputValues.get("Warengruppen")
```
Added also the supplier name in the first line of the file :
```

        for (Lieferant lieferant : alleLieferanten) {
            if (lieferant.getStatus().equals("zugelassen")
                    || lieferant.getStatus().equals("archiviert")) {
                csvFileOutputTabelle.print(lieferant.getLieferantenBezeichnung() + ";");
            }
        }
csvFileOutputTabelle.println();

```
The output file after runnign the application:
```
Buerobedarf Test AG;Musterholz GmbH;Metalltester GmbH;Kunst & Stoff AG;Klein Holz AG;Holzvertrieb AG;
LieferantenBezeichnung; Warengruppen ; LfNR; Bew Kat 1; Bew Kat 2; Bew Kat 3;  Bew Kat 4; Bew Kat 5; Gesamtbewertung
Buerobedarf Test AG; Bürobedarf; 01; 10; 20; 30; 5; 30; gut
Musterholz GmbH; Holz; 02; 20; 20; 30; 20; 30; sehr gut
Metalltester GmbH; Metall; 03; 0; 20; 30; 15; 20; OK
Kunst & Stoff AG; Kunststoff; 04; 0; 5; 30; 11; 20; schlecht
Klein Holz AG; Holz; 05; 20; 20; 30; 15; 12; sehr gut
Holzvertrieb AG; Holz; 09; 0; 20; 30; 8; 7; OK


```
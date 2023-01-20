package com.hcm.TabelleBewertungsvergleich;

public class Warengruppen {
    private String WGID;
    private String warengruppenTitel;

    public Warengruppen(String WGID, String warengruppenTitel) {
        this.WGID = WGID;
        this.warengruppenTitel = warengruppenTitel;
    }

    public String getWGID() {
        return WGID;
    }
    public String getWarengruppenTitel() {
        return warengruppenTitel;
    }

}

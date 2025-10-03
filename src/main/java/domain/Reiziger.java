package domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @Column(name = "reiziger_id", columnDefinition = "NUMERIC")
    private int reiziger_id;
    @Column(name = "voorletters")
    private String voorletters;
    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;
    @Column(name = "achternaam")
    private String achternaam;
    @Column(name = "geboortedatum")
    private Date geboortedatum;
    @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Adres adres;
    @OneToMany(mappedBy = "reiziger", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();


    public Reiziger(int Reiziger_id, String Voorletters, String Tussenvoegsel, String Achternaam, Date Gebortedatum) {
        this.reiziger_id = Reiziger_id;
        this.voorletters = Voorletters;
        this.tussenvoegsel = Tussenvoegsel;
        this.achternaam = Achternaam;
        this.geboortedatum = Gebortedatum;
    }

    public Reiziger() {

    }

    public int getReiziger_id() {
        return reiziger_id;
    }
    public void setReiziger_id(int Reiziger_id) {
        this.reiziger_id = Reiziger_id;
    }
    public String getVoorletters() {
        return voorletters;
    }
    public void setVoorletters(String Voorletters) {
        this.voorletters = Voorletters;
    }
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }
    public void setTussenvoegsel(String Tussenvoegsel) {
        this.tussenvoegsel = Tussenvoegsel;
    }
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String Achternaam) {
        this.achternaam = Achternaam;
    }
    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(Date Gebortedatum) {
        this.geboortedatum = Gebortedatum;
    }
    public String getnaam(){
        if (tussenvoegsel != null){
            return(voorletters + ". " + tussenvoegsel + ". " + achternaam);
        }
        else {
            return(voorletters + ". " + achternaam);
        }
    }
    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public boolean voegToeOVChipkaart(OVChipkaart kaart) {
        if (kaart == null) return false;
        kaart.setReiziger(this);
        return ovChipkaarten.add(kaart);
    }

    public boolean verwijderOVChipkaart(OVChipkaart kaart) {
        if (kaart == null) return false;
        kaart.setReiziger(null);
        return ovChipkaarten.remove(kaart);
    }

    @Override
    public String toString() {
        StringBuilder reizigerString = new StringBuilder();
        reizigerString.append(voorletters + ", " + tussenvoegsel + ", " + achternaam + " heeft een reiziger id van: " + reiziger_id + " en een geboorte datum van: " + geboortedatum + ".");
        if (adres != null) {
            reizigerString.append(" adres: " + adres.toString());
        } else {
            reizigerString.append(" adres: Geen adres geregistreerd");
        }
        if (ovChipkaarten != null && !ovChipkaarten.isEmpty()) {
            reizigerString.append(" OVChipkaarten: ");
            for (OVChipkaart kaart : ovChipkaarten) {
                reizigerString.append(kaart.toString()).append(" ");
            }
        } else {
            reizigerString.append(" OVChipkaarten: Geen OVChipkaarten geregistreerd");
        }
        return reizigerString.toString();
    }
}
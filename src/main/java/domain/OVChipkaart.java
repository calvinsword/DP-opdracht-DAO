package domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer", columnDefinition = "NUMERIC")
    private int id;
    @Column(name = "geldig_tot")
    private Date geldig_tot;
    @Column(name = "klasse")
    private int klasse;
    @Column(name = "saldo")
    private long saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "kaart_nummer"),
            inverseJoinColumns = @JoinColumn(name = "product_nummer")
    )
    private List<Product> alleProducten = new ArrayList<>();

    public OVChipkaart(int id, Date geldig_tot, int klasse, long saldo, Reiziger reiziger) {
        this.id = id;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getGeldig_tot() {
        return geldig_tot;
    }
    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }
    public int getKlasse() {
        return klasse;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }
    public long getSaldo() {
        return saldo;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
    public Reiziger getReiziger() {
        return reiziger;
    }
    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }
    public List<Product> getAlleProducten() {
        return alleProducten;
    }
    public boolean addProduct(Product p) {
        if (!alleProducten.contains(p)) {
            alleProducten.add(p);
            p.addOVChipkaart(this);
            return true;
        }
        return false;
    }
    public boolean removeProduct(Product p) {
        if (alleProducten.contains(p)) {
            alleProducten.remove(p);
            p.getAlleOVChipkaarten().remove(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder ovString = new StringBuilder();
        ovString.append("OVKaart " + id + " is geldig tot " + geldig_tot + " en met klasse " + klasse + " heeft een saldo van €" + saldo + " en in bezit van " + reiziger.getnaam());
        if (alleProducten != null && !alleProducten.isEmpty()) {
            ovString.append(" met producten: ");
            for (Product p : alleProducten) {
                ovString.append(p.getNaam()).append(" ");
            }
        } else {
            ovString.append(" met geen producten");
        }
        return ovString.toString();
    }

}

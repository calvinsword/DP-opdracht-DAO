package domain;

import jakarta.persistence.*;

import java.sql.Date;
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
    @ManyToMany
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "kaart_nummer"),
            inverseJoinColumns = @JoinColumn(name = "product_nummer")
    )
    private List<Product> alleProdcuten;

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
    public List<Product> getAlleProdcuten() {
        return alleProdcuten;
    }
    public void addProdcut(Product p) {
        alleProdcuten.add(p);
    }

    @Override
    public String toString() {
        return ("OVKaart " + id + " is geldig tot " + geldig_tot + " en met klasse " + klasse + " heeft een saldo van €" + saldo + " en in bezit van " + reiziger.getnaam());
    }

}

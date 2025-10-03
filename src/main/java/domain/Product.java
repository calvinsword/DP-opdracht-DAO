package domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int product_nummer;
    @Column(name = "naam")
    private String naam;
    @Column(name = "beschrijving")
    private String beschrijving;
    @Column(name = "prijs")
    private int prijs;
    @ManyToMany(mappedBy = "alleProducten", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OVChipkaart> alleOVChipkaarten = new ArrayList<>();

    public Product(int Product_nummer, String Naam, String Beschrijving, int Prijs) {
        this.product_nummer = Product_nummer;
        this.naam = Naam;
        this.beschrijving = Beschrijving;
        this.prijs = Prijs;
    }

    public Product() {

    }

    public int getProduct_nummer() {
        return product_nummer;
    }
    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }
    public String getNaam() {
        return naam;
    }
    public void setNaam(String naam) {
        this.naam = naam;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public int getPrijs() {
        return prijs;
    }
    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
    public List<OVChipkaart> getAlleOVChipkaarten() {
        return alleOVChipkaarten;
    }
    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        if (!alleOVChipkaarten.contains(ovChipkaart)) {
            alleOVChipkaarten.add(ovChipkaart);
        }
    }
    @Override
    public String toString() {
        StringBuilder productString = new StringBuilder();
        productString.append("Product " + product_nummer + " met naam " + naam + " heeft beschrijving " + beschrijving + " en een prijs van " + prijs);
        if (alleOVChipkaarten != null && !alleOVChipkaarten.isEmpty()) {
            productString.append(" en is gekoppeld aan OVChipkaarten: ");
            for (OVChipkaart ovChipkaart : alleOVChipkaarten) {
                productString.append(ovChipkaart.getId()).append(" ");
            }
        }
        return productString.toString();

    }
}

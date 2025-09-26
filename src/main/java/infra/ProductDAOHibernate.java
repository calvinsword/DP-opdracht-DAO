package infra;

import data.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product pr) throws SQLException {
        try {
            session.save(pr);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij opslaan van Product: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public boolean update(Product pr) throws SQLException {
        try {
            Calendar calendar = Calendar.getInstance();
            String query = "UPDATE ov_chipkaart_product SET last_update = :last_update WHERE product_nummer = :product_nummer";
            Query<?> query1 = session.createNativeQuery(query);
            query1.setParameter("last_update", calendar.getTime());
            query1.setParameter("product_nummer", pr.getProduct_nummer());
            query1.executeUpdate();

            session.update(pr);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij updaten van Product: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public boolean delete(Product pr) throws SQLException {
        try {
            Product buffer = (Product) session.merge(pr);
            session.delete(buffer);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij verwijderen van Product: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        try {
            return session.createQuery(
                            "select p from Product p join fetch p.alleOVChipkaarten o where o.id = :kaartNummer", Product.class)
                    .setParameter("kaartNummer", ovChipkaart.getId())
                    .getResultList();
        } catch (HibernateException e) {
            System.err.println("Fout bij zoeken van Producten voor OVChipkaart " + ovChipkaart.getId() + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.getResultList();
        } catch (HibernateException e) {
            System.err.println("Fout bij ophalen van alle Producten: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

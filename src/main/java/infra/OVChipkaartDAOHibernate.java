package infra;

import data.OVChipkaartDAO;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            session.save(ovChipkaart);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            session.update(ovChipkaart);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            List<Product> ovProducten = List.copyOf(ovChipkaart.getAlleProducten());
            for (Product p : ovProducten) {
                ovChipkaart.removeProduct(p);
            }
            session.update(ovChipkaart);

            session.delete(ovChipkaart);
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            return session.createQuery(
                            "FROM OVChipkaart a WHERE a.reiziger.reiziger_id = :id", OVChipkaart.class)
                    .setParameter("id", reiziger.getReiziger_id())
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            return session.createQuery("FROM OVChipkaart", OVChipkaart.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

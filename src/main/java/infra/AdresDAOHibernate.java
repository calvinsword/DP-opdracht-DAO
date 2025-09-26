package infra;

import data.AdresDAO;
import domain.Adres;
import domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        try {
            session.save(adres);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij opslaan van Adres: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        try {
            session.update(adres);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij updaten van Adres: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        try {
            Adres buffer = (Adres) session.merge(adres);
            session.delete(buffer);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij verwijderen van Adres: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            return session.createQuery(
                            "FROM Adres a WHERE a.reiziger.reiziger_id = :reizigerId", Adres.class)
                    .setParameter("reizigerId", reiziger.getReiziger_id())
                    .uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Fout bij zoeken van Adres via Reiziger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        try {
            return session.createQuery("FROM Adres", Adres.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Fout bij ophalen van alle Adressen: " + e.getMessage());
            throw e;
        }
    }
}

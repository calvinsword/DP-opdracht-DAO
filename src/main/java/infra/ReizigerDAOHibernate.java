package infra;

import data.ReizigerDAO;
import domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) throws Exception {
        try {
            session.save(reiziger);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij opslaan van Reiziger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) throws Exception {
        try {
            session.update(reiziger);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij updaten van Reiziger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) throws Exception {
        try {
            Reiziger buffer = (Reiziger) session.merge(reiziger);
            session.delete(buffer);
            return true;
        } catch (HibernateException e) {
            System.err.println("Fout bij verwijderen van Reiziger: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Reiziger findById(int id) throws Exception {
        try {
            return session.get(Reiziger.class, id);
        } catch (HibernateException e) {
            System.err.println("Fout bij zoeken van Reiziger met id " + id + ": " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) throws Exception {
        try {
            String hql = "FROM Reiziger WHERE geboortedatum = :geboortedatum";
            return session.createQuery(hql, Reiziger.class)
                    .setParameter("geboortedatum", date)
                    .list();
        } catch (HibernateException e) {
            System.err.println("Fout bij zoeken van Reizigers met geboortedatum " + date + ": " + e.getMessage());
            return Collections.emptyList(); // veilige fallback
        }
    }

    @Override
    public List<Reiziger> findAll() throws Exception {
        try {
            String hql = "FROM Reiziger";
            return session.createQuery(hql, Reiziger.class).list();
        } catch (HibernateException e) {
            System.err.println("Fout bij ophalen van alle Reizigers: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

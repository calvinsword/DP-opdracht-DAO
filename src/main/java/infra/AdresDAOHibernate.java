package infra;

import data.AdresDAO;
import domain.Adres;
import domain.Reiziger;
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
        session.save(adres);
        return true;
    }
    @Override
    public boolean update(Adres adres) throws SQLException {
        session.update(adres);
        return true;
    }
    @Override
    public boolean delete(Adres adres) throws SQLException {
        Adres buffer = (Adres) session.merge(adres);
        session.delete(buffer);
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres a = session.createQuery("FROM Adres a WHERE a.reiziger.reiziger_id = ?1", Adres.class)
                .setParameter(1,reiziger.getReiziger_id())
                .uniqueResult();
        return a;
    }

    public List<Adres> findAll() throws SQLException {
        List<Adres> adresList = session.createQuery("FROM Adres", Adres.class).getResultList();
        return adresList;
    }
}

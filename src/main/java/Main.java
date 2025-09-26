import data.AdresDAO;
import data.ReizigerDAO;
import domain.Adres;
import domain.Reiziger;
import infra.AdresDAOHibernate;
import infra.ReizigerDAOHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        AdresDAOHibernate adao = new AdresDAOHibernate(session);
        try {
            testReizigerDAO(rdao);
            testAdresDAO(rdao, adao);
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws Exception {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //UPDATE
        System.out.println("UPDATE + GETBYID");
        System.out.println("Reiziger");
        System.out.println("Oude achternaam: " + sietske.getAchternaam());
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        //GET
        Reiziger sietske1 = rdao.findById(77);
        System.out.println("Nieuwe achternaam: " + sietske1.getAchternaam());



        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

    private static void testAdresDAO(ReizigerDAO rdao, AdresDAO adao) throws Exception {
        System.out.println("\n---------- Test AdresDAO -------------");
        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();
        //New adres
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres placeholder = new Adres(6,"3432CD","9","beringerschans","nieuwegein",sietske);
        rdao.save(sietske);
        adao.save(placeholder);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        //UPDATE
        System.out.println("UPDATE + GETBYReiziger");
        System.out.println("Adres");
        System.out.println("Oude straat: " + placeholder.getStraat());
        placeholder.setStraat("Janssen");
        adao.update(placeholder);
        //GET
        Adres placeholder1 = adao.findByReiziger(sietske);
        System.out.println("Nieuwe straat: " + placeholder1.getStraat());
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");

        System.out.println("*A2* reizigen koppelen aan adres");
        sietske.setAdres(placeholder);
        rdao.update(sietske);
        System.out.println(sietske);

        //DELETE
        System.out.println("[Test] AdresDAO.delete()");
        sietske.setAdres(null);
        rdao.update(sietske);
        adao.delete(placeholder);
        rdao.delete(sietske);
        System.out.println(adressen.size() + " adressen\n");
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
    }

}

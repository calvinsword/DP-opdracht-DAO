import data.AdresDAO;
import data.OVChipkaartDAO;
import data.ProductDAO;
import data.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import infra.AdresDAOHibernate;
import infra.OVChipkaartDAOHibernate;
import infra.ProductDAOHibernate;
import infra.ReizigerDAOHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        AdresDAOHibernate adao = new AdresDAOHibernate(session);
        OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);
        ProductDAOHibernate pdao = new ProductDAOHibernate(session);
        try {
            testReizigerDAO(rdao);
            testAdresDAO(rdao, adao);
            testOVChipkaartDAO(rdao, ovdao);
            testProductDAO(rdao, ovdao, pdao);
//            testdelete(rdao, adao, ovdao, pdao);
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
    private static void testOVChipkaartDAO(ReizigerDAO rdao, OVChipkaartDAO odao) throws Exception {
        System.out.println("\n---------- Test OVChipkaart -------------");
        // Haal alle reizigers op uit de database
        List<OVChipkaart> ovChipkaarten = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart ov1 = new OVChipkaart(1, java.sql.Date.valueOf("2028-03-14"), 1, 1000, sietske);
        OVChipkaart ov2 = new OVChipkaart(2, java.sql.Date.valueOf("2028-03-14"), 2, 500, sietske);
        rdao.save(sietske);
        odao.save(ov1);
        odao.save(ov2);

        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");

        //UPDATE
        System.out.println("UPDATE + GETBYREIZIGER");
        System.out.println("OVChipkaart");
        System.out.println("Oude klasse: " + ov1.getKlasse());
        ov1.setKlasse(2);
        odao.update(ov1);

        //GET
        List<OVChipkaart> ov1get = odao.findByReiziger(sietske);
        System.out.println("Nieuwe klasse: " + ov1get.get(0).getKlasse());

        //DELETE
        System.out.print("\n[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.delete() ");
        odao.delete(ov1);
        odao.delete(ov2);
        rdao.delete(sietske);
        System.out.println("na delete ");
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");
    }

    public static void testProductDAO(ReizigerDAO rdao,OVChipkaartDAO odao, ProductDAO pdao) throws Exception {
        System.out.println("\n---------- Test ProductDAO -------------");
        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende Producten:");
        for (Product p : producten) {
            System.out.println(p);
        }

        //New product
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart producttestov = new OVChipkaart(1, java.sql.Date.valueOf("2018-05-31"),2,26,sietske);
        Product testproduct = new Product(22, "test","voor test gelegenheden", 50);
        rdao.save(sietske);
        odao.save(producttestov);
        pdao.save(testproduct);
        testproduct.addOVChipkaart(producttestov);
        pdao.update(testproduct);
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");

        //UPDATE
        System.out.println("UPDATE + GETBYOVCHIPKAART");
        System.out.println("Oude naam: " + testproduct.getNaam());
        testproduct.setNaam("test2");
        pdao.update(testproduct);
        //GET
        List<Product> testproduct1 = pdao.findByOVChipkaart(producttestov);
        System.out.println("Nieuwe naam: " + testproduct1.get(0).getNaam());
        //DELETE
        System.out.print("\n[Test] Eerst " + producten.size() + " producten, na ProductDAO.delete() ");
        pdao.delete(testproduct);
        producten = pdao.findAll();
        System.out.println("na delete : " + producten.size() + " producten\n");
        odao.delete(producttestov);
        rdao.delete(sietske);
    }

    public static void testdelete(ReizigerDAO rdao,AdresDAO adao,OVChipkaartDAO odao,ProductDAO pdao) throws Exception {
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres placeholder = new Adres(6,"3432CD","9","beringerschans","nieuwegein",sietske);
        OVChipkaart ov1 = new OVChipkaart(1, java.sql.Date.valueOf("2028-03-14"), 1, 1000, sietske);
        OVChipkaart ov2 = new OVChipkaart(2, java.sql.Date.valueOf("2028-03-14"), 2, 500, sietske);
        Product testproduct = new Product(22, "test","voor test gelegenheden", 50);
        rdao.update(sietske);
        adao.delete(placeholder);
        odao.delete(ov1);
        odao.delete(ov2);
        rdao.delete(sietske);
        pdao.delete(testproduct);

    }

}

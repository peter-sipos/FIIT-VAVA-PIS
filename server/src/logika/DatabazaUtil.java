package logika;

import entity.Akcia;
import entity.Clen;
import entity.UcastNaAkcii;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabazaUtil {

//    private static final LogManager logManager = LogManager.getLogManager();
//    private static final Logger dbErrorLogger = Logger.getLogger("dbErrorLogger");
//    static{
//
//        try {
//            String cesta = System.getProperty("jboss.server.config.dir") + "/logger.properties";
//            logManager.readConfiguration(new FileInputStream(cesta));
//        } catch (IOException exception) {
//            dbErrorLogger.log(Level.SEVERE, "Error in loading configuration",exception);
//        }
//    }
    private static final Logger dbErrorLogger = KonfiguraciaLoggera.vratErrorLogger(DatabazaUtil.class.getName(), Level.SEVERE);
    private static final Logger pridanieAkcieLogger = KonfiguraciaLoggera.vratPridanieAkcieLogger("pridanieAkcie", Level.FINE);

    public void DBpridajAkciu(Akcia akcia){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
           session.saveOrUpdate(akcia);
            session.merge(akcia.getZodpovednaOsoba());
            transaction.commit();

            pridanieAkcieLogger.log(Level.FINE, "Pridana akcia a osoba za nu zodpovedna: " + akcia.getNazov() + " - "
                    + akcia.getZodpovednaOsoba().getMeno() + " " + akcia.getZodpovednaOsoba().getPriezvisko());

        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
    }


    public void DBpridajUcastNaAkciu(UcastNaAkcii ucastNaAkcii){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ucastNaAkcii.getClen().getUcastiNaAkciach().add(ucastNaAkcii);
            session.saveOrUpdate(ucastNaAkcii);
            session.merge(ucastNaAkcii.getClen());
            session.merge(ucastNaAkcii.getAkcia());
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    public List<Clen> DBvratPrihlasenychNaAkciu(Akcia akcia){
        List<Clen> prihlaseni = new ArrayList<>();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT clen FROM Clen clen JOIN clen.ucastiNaAkciach UcastNaAkcii WHERE UcastNaAkcii.akcia = :akcia";
            Query query = session.createQuery(hql);
            query.setParameter("akcia", akcia);
            prihlaseni = query.list();
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return prihlaseni;
    }

    public List<Akcia> DBvratVsetkyAkcie(){
        List<Akcia> akcie = new ArrayList<>();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT a FROM Akcia a";
            Query query = session.createQuery(hql);
            akcie = query.list();
            transaction.commit();
            }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return akcie;
    }

    public Akcia DBvratAkciuPodlaId(int id){
        Akcia akcia = new Akcia();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT a FROM Akcia a WHERE a.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            akcia = (Akcia) query.getSingleResult();
            Hibernate.initialize(akcia.getZodpovednaOsoba());
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return akcia;
    }


    public Clen DBvratClenaPodlaId(int id){
        Clen clen = new Clen();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT c FROM Clen c WHERE c.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            clen = (Clen) query.getSingleResult();
            Hibernate.initialize(clen.getUcastiNaAkciach());
           transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return clen;
    }

    public int DBvratIdClenaPodlaMena(Clen clen){
        int idClena = -1;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT c.id FROM Clen c WHERE c.meno = :meno AND c.priezvisko = :priezvisko";
            Query query = session.createQuery(hql);
            query.setParameter("meno", clen.getMeno());
            query.setParameter("priezvisko", clen.getPriezvisko());
            idClena = (int) query.getSingleResult();
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return idClena;
    }

    public Clen DBvratClenaPodlaMena(Clen clen){
        Clen hladany = new Clen();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT c FROM Clen c WHERE c.meno = :meno AND c.priezvisko = :priezvisko";
            Query query = session.createQuery(hql);
            query.setParameter("meno", clen.getMeno());
            query.setParameter("priezvisko", clen.getPriezvisko());
            hladany = (Clen) query.getSingleResult();
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return hladany;
    }

    public List<Clen> DBvratClenovPodlaOddielu(String oddiel){
        List<Clen> clenovia = new ArrayList<>();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT c FROM Clen c WHERE c.oddiel = :oddiel";
            Query query = session.createQuery(hql);
            query.setParameter("oddiel", oddiel);
            clenovia = query.list();
            transaction.commit();
        }catch (Exception e){
            dbErrorLogger.log(Level.SEVERE, e.toString(), e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return clenovia;
    }

}

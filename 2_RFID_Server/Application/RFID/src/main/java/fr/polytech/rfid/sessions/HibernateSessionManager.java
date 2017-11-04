package fr.polytech.rfid.sessions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionManager {

    public static final String HIBERNATE_CONFIGURATION_FILE = "hibernate.cfg.xml";

    private static SessionFactory sessionFactory = null;

    private static final Object lock = new Object();

    private HibernateSessionManager() {
    }

    public static Session getSession() {
        synchronized (lock) {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure(HIBERNATE_CONFIGURATION_FILE).buildSessionFactory();
            }

            return sessionFactory.openSession();
        }
    }
}
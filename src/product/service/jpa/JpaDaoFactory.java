package product.service.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import product.service.DaoFactory;
import product.service.ProductDao;


public class JpaDaoFactory extends DaoFactory {
	private static final String PERSISTENCE_UNIT = "products";
	private static JpaDaoFactory factory;
	private ProductDao productDao;
	private final EntityManagerFactory emf;
	private EntityManager em;
	private static Logger logger;
	
	static {
		logger = Logger.getLogger(JpaDaoFactory.class.getName());
	}
	
	public JpaDaoFactory() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		em = emf.createEntityManager();
		productDao = new JpaProductDao( em );
	}
	
	/**
	 * Get the instance of DaoFactory.
	 * @return instance of DaoFactory.
	 */
	public static JpaDaoFactory getInstance() {
		if ( factory == null ) {
			factory = new JpaDaoFactory();
		}
		return factory;
	}
	
	@Override
	public void shutdown() {
		try {
			if (em != null && em.isOpen()) em.close();
			if (emf != null && emf.isOpen()) emf.close();
		} catch (IllegalStateException ex) {
			// SEVERE - highest
			logger.log( Level.SEVERE, ex.toString() );
		}
	}

	@Override
	public ProductDao getProductDao() {
		return productDao;
	}
}

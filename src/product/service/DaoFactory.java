package product.service;

import product.service.jpa.JpaDaoFactory;


public abstract class DaoFactory {
	
private static DaoFactory factory;
	
	/** this class shouldn't be instantiated, but constructor must be visible to subclasses. */
	protected DaoFactory() {
		// nothing to do
	}
	
	/**
	 * Get a singleton instance of the DaoFactory.
	 * @return instance of a concrete DaoFactory
	 */
	public static DaoFactory getInstance() {
		if (factory == null) {
			String factoryclass = System.getProperty("product.daofactory");
			if(factoryclass != null){
				ClassLoader loader = DaoFactory.class.getClassLoader();
				try {
					factory = (DaoFactory)loader.loadClass(factoryclass).newInstance();
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			if(factory == null){
				setFactory( JpaDaoFactory.getInstance() );
			}
		}
		return factory;
	}
	
	public static void setFactory(DaoFactory daoFactory){
		factory = daoFactory;
	}
	
	/**
	 * Get an instance of a data access object for Contact objects.
	 * Subclasses of the base DaoFactory class must provide a concrete
	 * instance of this method that returns a ContactDao suitable
	 * for their persistence framework.
	 * @return instance of Contact's DAO
	 */
	public abstract ProductDao getProductDao();
	
	/**
	 * Shutdown all persistence services.
	 * This method gives the persistence framework a chance to
	 * gracefully save data and close databases before the
	 * application terminates.
	 */
	public abstract void shutdown();
}

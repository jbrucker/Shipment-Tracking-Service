package product.service.jpa;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import product.entity.Product;
import product.service.ProductDao;

public class JpaProductDao implements ProductDao{
	private final EntityManager em;

	/**
	 * constructor with injected EntityManager to use.
	 * 
	 * @param em
	 *            an EntityManager for accessing JPA services.
	 */
	public JpaProductDao(EntityManager em) {
		this.em = em;
	}


	/**
	 * @see contact.service.ContactDao#find(long)
	 */
	@Override
	public Product find(long id) {
		return em.find(Product.class, id);
	}

	/**
	 * @see contact.service.ContactDao#findAll()
	 */
	@Override
	public List<Product> findAll() {
		Query query = em.createQuery("SELECT c FROM Product c");
		List<Product> products = query.getResultList();
		return Collections.unmodifiableList(products);
	}
	
}

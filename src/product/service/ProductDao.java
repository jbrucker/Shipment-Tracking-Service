package product.service;

import java.util.List;

import product.entity.Product;

public interface ProductDao {
	/** Find a contact by ID in contacts.
	 * @param the id of contact to find
	 * @return the matching contact or null if the id is not found
	 */
	public abstract Product find(long id);

	/**
	 * Return all the persisted contacts as a List.
	 * There is no guarantee what implementation of
	 * List is returned, so caller should use only
	 * List methods (not, say ArrayList).
	 * @return list of all contacts in persistent storage.
	 *   If no contacts, returns an empty list.
	 */
	public abstract List<Product> findAll();
	
}

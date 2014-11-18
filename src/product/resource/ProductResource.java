package product.resource;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import product.entity.Product;
import product.service.DaoFactory;
import product.service.ProductDao;

@Path("/products")
@Singleton
public class ProductResource {
	private ProductDao dao;
	private CacheControl cc;
	@Context
	private UriInfo uriInfo;

	/**
	 * Construct ContactDao from DaoFactory.
	 */
	public ProductResource() {
		cc = new CacheControl();
		cc.setMaxAge(46800);
		dao = DaoFactory.getInstance().getProductDao();
		System.out.println("Initial ContactDao.");
	}

	/**
	 * Get a list of all contacts or Get contact(s) whose title contains the
	 * query string (substring match).
	 * 
	 * @param query
	 *            is query string (title)
	 * @return response 200 OK if result not null that show list of result
	 *         contacts. If result is null response 404 NOT FOUND
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getProduct(@Context Request request) {
		GenericEntity<List<Product>> ge = null;
		ge = convertListToGE(dao.findAll());
		if (!ge.getEntity().isEmpty()) {
			return Response.ok(ge).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();

	}

	/**
	 * Get a contact by id.
	 * 
	 * @param id
	 *            identifier of contact
	 * @return response 200 OK if result not null that show contact. If result
	 *         is null response 404 NOT FOUND
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getProductById(@PathParam("id") long id,
			@Context Request request) {
		Product product = dao.find(id);
		if (product == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		EntityTag etag = attachEtag(product);
		ResponseBuilder builder = request.evaluatePreconditions(etag);
		if (builder == null) {
			builder = Response.ok(product);
			builder.tag(etag);
		}
		builder.cacheControl(cc);
		return builder.build();
	}
	/**
	 * Create an instance directly by supplying the generic type information
	 * with the entity.
	 * 
	 * @param contacts
	 *            list of contacts
	 * @return generic entity
	 */
	public GenericEntity<List<Product>> convertListToGE(List<Product> products) {
		GenericEntity<List<Product>> ge = new GenericEntity<List<Product>>(products) {
		};
		return ge;
	}

	/**
	 * Construct Etag from contact
	 * 
	 * @param contact
	 * @return etag Entity tag of contact
	 */
	public EntityTag attachEtag(Product product) {
		EntityTag etag = new EntityTag(product.sha1());
		return etag;
	}
}

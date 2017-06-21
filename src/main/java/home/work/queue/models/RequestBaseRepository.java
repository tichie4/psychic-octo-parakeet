package home.work.queue.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository for the entity User and its subclasses, extending the 
 * CrudRepository interface provided by spring data jpa.
 * The following methods are some of the ones available from CrudRepository: 
 * save, delete, deleteAll, findOne and findAll.
 * 
 * All methods in this repository will be available in the Request repositories.
 * 
 * @author Richard
 */
@NoRepositoryBean
public interface RequestBaseRepository<T extends Request> 
extends CrudRepository<T, Long> {

  /**
   * Method findByCustomerId
   * 
   * @param customerId The id of the request owner.
   * @return A request with the customerId.
   */
  public T findByCustomerId(Long customerId);
  
  public List<T> findAll();
  
  public void delete(Request request);
  
}
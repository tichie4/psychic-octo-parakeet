package home.work.queue.models;

import javax.transaction.Transactional;

/**
 * Repository for the entity Company.
 * 
 * @see netgloo.models.UserBaseRepository
 */
@Transactional
public interface PriorityRequestRepository extends RequestBaseRepository<PriorityRequest> { }

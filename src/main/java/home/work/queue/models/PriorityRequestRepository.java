package home.work.queue.models;

import javax.transaction.Transactional;


@Transactional
public interface PriorityRequestRepository extends RequestBaseRepository<PriorityRequest> { }

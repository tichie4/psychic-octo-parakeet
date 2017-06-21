package home.work.queue.models;

import javax.transaction.Transactional;


@Transactional
public interface ManagerRequestRepository extends RequestBaseRepository<ManagerRequest> { }
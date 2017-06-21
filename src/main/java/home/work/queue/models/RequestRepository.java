package home.work.queue.models;

import javax.transaction.Transactional;

@Transactional
public interface RequestRepository extends RequestBaseRepository<Request> { }
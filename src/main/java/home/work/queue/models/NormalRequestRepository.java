package home.work.queue.models;

import javax.transaction.Transactional;


@Transactional
public interface NormalRequestRepository extends RequestBaseRepository<NormalRequest> { }
package home.work.queue.models;

import javax.transaction.Transactional;


@Transactional
public interface VipRequestRepository extends RequestBaseRepository<VipRequest> { }

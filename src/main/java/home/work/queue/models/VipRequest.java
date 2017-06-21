package home.work.queue.models;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.lang.Math;
import java.util.Date;

@Entity
@JsonDeserialize(as = VipRequest.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VipRequest extends Request {
  
  public VipRequest() {}
  
  public VipRequest(Long customerId, Date date) {
	  super(customerId, date);
  }
  
  @Override
  public double getPriority(Date now) {
	//max(4, 2n log n)
	long secondsInQueue = this.getSecondsInQueue(now);
	double priority = Math.max(4L, (2*secondsInQueue) * Math.log(secondsInQueue));
	return priority;
  }

}
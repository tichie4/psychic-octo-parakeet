package home.work.queue.models;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.lang.Math;
import java.util.Date;

@Entity
@JsonDeserialize(as = PriorityRequest.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriorityRequest extends Request {

  public PriorityRequest() {}
  
  public PriorityRequest(Long customerId, Date date) {
	  super(customerId, date);
  }
 
  @Override
  public double getPriority(Date now) {
	//max(3, n log n)
	long secondsInQueue = this.getSecondsInQueue(now);
	double priority = Math.max(3L, secondsInQueue * Math.log(secondsInQueue));
	return priority;
  }

} // class Company
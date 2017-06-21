package home.work.queue.models;


import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Inheritance
@DiscriminatorColumn(name = "request_type")
@Table(name = "requests")
@JsonDeserialize(using = RequestDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Request implements Comparable<Request> {
	
	public Request() {}
	
	public Request(Long customerId, Date date) {
		this.customerId = customerId;
		this.date = date;
	}

    @Id
    private Long customerId;
    
    private Date date;

	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDate() {
		return date;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setDate(Date date) {
		this.date = date;
	}
	
	public long getSecondsInQueue(Date now){
		long seconds = (now.getTime() - this.date.getTime()) /1000;
		return seconds;
	}
	
	public abstract double getPriority(Date date);
	
	@Override
	public int compareTo(Request request) {
		Date now = new Date();
		if (this.getPriority(now) == 0 && request.getPriority(now) == 0) {
			if (this.getSecondsInQueue(now) > request.getSecondsInQueue(now)){
				return -1;
			} else if (this.getSecondsInQueue(now) == request.getSecondsInQueue(now)){
				return 0;
			} else {
				return 1;
			}
		} else if (this.getPriority(now) == 0) {
			return -1;
		} else if (request.getPriority(now) == 0) {
			return 1;
		} else if (this.getPriority(now) > request.getPriority(now)) {
			return -1;
		} else if (this.getPriority(now) == request.getPriority(now)) {
			return 0;
		} else {
			return 1;
		}
    }
}
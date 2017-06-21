package home.work.queue.models;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@JsonDeserialize(as = NormalRequest.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NormalRequest extends Request {

	public NormalRequest() { }

	public NormalRequest(Long customerId, Date date) {
		super(customerId, date);
	}

	@Override
	public double getPriority(Date now) {
		return this.getSecondsInQueue(now);
	}

}
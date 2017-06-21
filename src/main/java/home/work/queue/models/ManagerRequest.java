package home.work.queue.models;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@JsonDeserialize(as = ManagerRequest.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerRequest extends Request {

	public ManagerRequest() { }

	public ManagerRequest(Long customerId, Date date) {
		super(customerId, date);
	}

	@Override
	public double getPriority(Date now) {
		// special case
		return 0;
	}

}

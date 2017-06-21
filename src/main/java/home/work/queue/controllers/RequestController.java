package home.work.queue.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import home.work.queue.models.*;

/**
 * Controller class providing REST access Request objects.
 *
 * @author Richard
 */
@RestController
public class RequestController {

	// ==============
	// PRIVATE FIELDS
	// ==============

	@Autowired
	private RequestRepository requestRepository;

	// ==============
	// PUBLIC METHODS
	// ==============

	/**
	 * /requests/enqueue -> add a new request to the queue
	 * 
	 * @body { "customerId": 3, "date": "2017-06-19 09:00:00" }
	 * 
	 * @param customerId
	 *            The id of the Request to be enqueued
	 * @param date
	 *            The date that the Request is added in format "yyyy-MM-dd
	 *            hh:mm:ss"
	 * @return No body is returned. Will return status code 201 or 409.
	 */
	@RequestMapping(value = "requests/enqueue", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Void> enqueueRequest(@RequestBody Request request, UriComponentsBuilder ucBuilder) {

		if (requestRepository.exists(request.getCustomerId())) {
			System.out.println("A Request with id " + request.getCustomerId() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		requestRepository.save(request);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/requests/{id}").buildAndExpand(request.getCustomerId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	/**
	 * /requests/dequeue -> Retrieve highest priority request from queue
	 * and delete from database
	 * 
	 * @return No body is returned. Will return status code 200 or 404.
	 */
	@RequestMapping(value = "requests/dequeue", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> dequeueRequest(UriComponentsBuilder ucBuilder) {

		List<Request> requests = requestRepository.findAll();
		if (requests.size() == 0) {
			return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
		}
		Collections.sort(requests);
		Request request = requests.get(0);
		String requestJson = "{\"customerId\": " + request.getCustomerId().toString() + ",\"date\": \""
				+ request.getDate().toString() + "\"}";
		requestRepository.delete(request);
		return new ResponseEntity<String>(requestJson, HttpStatus.OK);
	}

	/**
	 * /requests -> returns the contents of the queue sorted by
	 * priority (Highest -> Lowest)
	 * 
	 * @return Returns sorted contents of queue. 
	 * 			Will return status code 200.
	 */
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Request>> getSortedRequests() {
		List<Request> requests = requestRepository.findAll();
		Collections.sort(requests);
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
	}

	/**
	 * /requests/index/{id} -> Return position in queue of Request with 
	 * 							customerId of {id}
	 * 
	 * @param customerId
	 *            The id of the Request
	 * @return Returns json with structure: "{"index": "2"}". 
	 * 			Will return status code 200 or 404.
	 */
	@RequestMapping(value = "requests/index/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getIndexOfRequest(@PathVariable("id") Long customerId) {
		List<Request> requests = requestRepository.findAll();
		Collections.sort(requests);
		Request request = requestRepository.findByCustomerId(customerId);
		int index = requests.indexOf(request);
		if (index == -1) {
			return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("{\"index\": " + index + "}", HttpStatus.OK);
	}

	/**
	 * /requests/meanwait -> get mean time that Requests have been in 
	 * 						the queue in seconds
	 * 
	 * @param date
	 *            The date in format "yyyy-MM-dd hh:mm:ss"
	 * @return Returns json with structure: "{"queueWaitTime": "30000"}". 
	 * 			Will return status code 200 or 404.
	 */
	@RequestMapping(value = "requests/meanwait", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getMeanWaitTimeInQueue(@RequestParam(value = "date") String dateStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
		}
		List<Request> requests = requestRepository.findAll();
		if (requests.size() == 0) {
			return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
		}
		Long totalTimeInQueue = 0L;
		for (Request request : requests) {
			totalTimeInQueue += (date.getTime() - request.getDate().getTime()) / 1000;
		}
		long meanTimeInQueue = totalTimeInQueue / requests.size();
		return new ResponseEntity<String>("{\"queueWaitTime\": " + meanTimeInQueue + "}", HttpStatus.OK);
	}

}
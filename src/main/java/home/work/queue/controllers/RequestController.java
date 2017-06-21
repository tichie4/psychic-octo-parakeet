package home.work.queue.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
 * Controller class for testing user's repositories classes.
 *
 * @author netgloo
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
   * /user/create/person?email=[email]&firstName=[firstName] -> create a new 
   * person user and save it in the database.
   * 
   * @param email The person's email
   * @param firstName The person's first name
   * @return a string describing if the person is succesfully created or not.
   */
  @RequestMapping(value="requests/enqueue", method=RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<Void> enqueueRequest(@RequestBody Request request, UriComponentsBuilder ucBuilder) {

   	if(requestRepository.exists(request.getCustomerId())){
        System.out.println("A Request with id "+request.getCustomerId()+" already exist");
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
   	requestRepository.save(request);
        
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/requests/{id}").buildAndExpand(request.getCustomerId()).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }
  
  @RequestMapping(value="requests/dequeue", method=RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> dequeueRequest(UriComponentsBuilder ucBuilder) {

   	List<Request> requests = requestRepository.findAll();
   	Collections.sort(requests);
   	Request request = requests.get(0);
   	String requestJson = "{\"customerId\": "+request.getCustomerId().toString()+",\"date\": \""+request.getDate().toString()+"\"}";
    requestRepository.delete(request);
    return new ResponseEntity<String>(requestJson, HttpStatus.OK);
  }
  
  /**
   * /user/get?email=[email] -> return the user having the passed email.
   * 
   * @param email The email to search in the database.
   * @return The user id or a message error if the user is not found.
   */
  @RequestMapping(value = "/requests", method=RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Request>> getSortedRequests() {
    List<Request> requests = requestRepository.findAll();
    Collections.sort(requests);
    return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
  }
  
  @RequestMapping(value="requests/index/{id}", method=RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> getIndexOfRequest(@PathVariable("id") Long customerId) {
	  List<Request> requests = requestRepository.findAll();
	  Collections.sort(requests);
	  Request request = requestRepository.findByCustomerId(customerId);
	  int index = requests.indexOf(request);
	  if (index == -1) {
		  return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
	  }
	  return new ResponseEntity<String>("{\"index\": "+index+"}", HttpStatus.OK);
  }
  
  @RequestMapping(value="requests/meanwait", method=RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<String> getMeanWaitTimeInQueue(@RequestParam(value="date") String dateStr) {
	  
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	  Date date;
	  try {
		  date = format.parse( dateStr );
	  } catch (ParseException e) {
		  return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
	  }
	  List<Request> requests = requestRepository.findAll();
	  if(requests.size() == 0){
		  return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
	  }
	  Long totalTimeInQueue = 0L;
	  for (Request request : requests) {
		  totalTimeInQueue += (date.getTime() - request.getDate().getTime()) /1000;
	  }
	  long meanTimeInQueue = totalTimeInQueue/requests.size();
	  return new ResponseEntity<String>("{\"queueWaitTime\": "+meanTimeInQueue+"}", HttpStatus.OK);
  }
  
} // class UserController
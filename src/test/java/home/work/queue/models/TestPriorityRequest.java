package home.work.queue.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPriorityRequest {
	Request request;
	Date date;
	Date date2;
	SimpleDateFormat format;
	
    @Before
    public void setUp() throws Exception {
    	format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  	  	date = format.parse("2012-06-18 09:00:00");
  	  	date2 = format.parse("2012-06-20 09:00:00");
    	request = new PriorityRequest(2L, date);
    }
    @Test
    public void testGetCustomerId() {
        Assert.assertEquals(Long.valueOf(2L), request.getCustomerId());
    }
    
    @Test
    public void testSetCustomerId() {
    	request.setCustomerId(5L);
    	Assert.assertEquals(Long.valueOf(5L), request.getCustomerId());
    }
    
    @Test
    public void testGetDate() {
    	Assert.assertEquals(date, request.getDate());
    }
    
    @Test
    public void testSetDate() {
    	request.setDate(date2);
    	Assert.assertEquals(date2, request.getDate());
    }
    
    @Test
    public void testGetPriority() {
    	Assert.assertTrue(2083949.0153888415 == request.getPriority(date2));
    }
    
    @Test
    public void testGetSecondsInQueue() {
    	Assert.assertEquals(172800L, request.getSecondsInQueue(date2));
    }
}
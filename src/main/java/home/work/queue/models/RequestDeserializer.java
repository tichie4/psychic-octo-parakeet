package home.work.queue.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RequestDeserializer extends JsonDeserializer<Request> {
    @Override
    public Request deserialize(JsonParser jp,  DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        Class<? extends Request> requestClass = null;
        String type = getRequestType(root);
        if(type.contains("priority")) {
            requestClass = PriorityRequest.class;
        } else if (type.contains("vip")) {
        	requestClass = VipRequest.class;
        } else if (type.contains("management")) {
        	requestClass = ManagerRequest.class;
        } else {
        	requestClass = NormalRequest.class;
        }
        if (requestClass == null){
            return null;
        }
        return mapper.convertValue(root, requestClass);
    }
    
    private String getRequestType(ObjectNode root) {
    	String type = "";
    	JsonNode customerIdNode = root.get("customerId");
    	Long customerId = customerIdNode.longValue();
    	if (customerId % 3 == 0 && customerId % 5 == 0) {
    		type = "management";
    	} 
    	else if (customerId % 5 == 0) {
    		type = "vip";
    	}
    	else if (customerId % 3 == 0) {
    		type = "priority";
    	}else{
    		type = "normal";
    	}
    	return type;
    }
}
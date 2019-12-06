package com.web.json;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Example Usage:
 * 
 * return new JsonResponse()
 *     .with("status", "ok")
 *     .with("shouldRefresh", true)
 *     .done();
 * 
 */
public class JsonResponse {

    protected Map<String, Object> map = new LinkedHashMap<String, Object>();
 
    public JsonResponse() {}
    
    public JsonResponse with(String key, Object value) {
    	map.put(key, value);
        return this;
    }
    
    public JsonResponse done() {
        try {
            new ObjectMapper().writeValueAsString(map);
            return this;
        } catch (Exception e) {
        	return new JsonResponse().with("Error", "Well, this is embarrassing, we are having trouble generating the response for you !");
        }
    }

    public Map<String, Object> getResponse() {
        return map;
    }

}

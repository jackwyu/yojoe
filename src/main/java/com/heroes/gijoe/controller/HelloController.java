package com.heroes.gijoe.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.heroes.gijoe.dto.ApiRequest;

@RestController
public class HelloController {
//    @Autowired
//    private FilterChainProxy filterChainProxy;

//    @RequestMapping("/filterChain")
//    public @ResponseBody Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy(){
//        return this.getSecurityFilterChainProxy();
//    }

//    @RequestMapping("/filterChain")
//    public Map<Integer, Map<Integer, String>> getSecurityFilterChainProxy(){
//        Map<Integer, Map<Integer, String>> filterChains= new HashMap<Integer, Map<Integer, String>>();
//        int i = 1;
//        for(SecurityFilterChain secfc :  this.filterChainProxy.getFilterChains()){
//            //filters.put(i++, secfc.getClass().getName());
//            Map<Integer, String> filters = new HashMap<Integer, String>();
//            int j = 1;
//            for(Filter filter : secfc.getFilters()){
//                filters.put(j++, filter.getClass().getName());
//            }
//            filterChains.put(i++, filters);
//        }
//        return filterChains;
//    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/gijoes")
    public String getTeamRoster(Principal principle) throws InvalidNameException {
    	
    	System.out.println("Controller principle: " + principle.getName());
    	LdapName ldap = new LdapName(principle.getName());
    	
    	for(Rdn rdn : ldap.getRdns()) {
//    	    if(rdn.getType().equalsIgnoreCase("CN")) {
    	        System.err.println(rdn.getType() + ";" + rdn.getValue());
//    	    }
    	}
    	
    	Map<String, Object> myMap = ldap.getRdns().stream().collect(Collectors.toMap(Rdn::getType, Rdn::getValue));
    	for(Map.Entry<String, Object> e : myMap.entrySet()) {
    		System.out.println(e.getKey() + ", " + (String)e.getValue());
    	}
        return "The whole Joe team";
    }
    
    @RequestMapping("/gijoes/joe")
    public String getOneJoe(Principal principal, ApiRequest apiRequest) {
    	System.out.println(apiRequest.getCodeName());
    	System.out.println(apiRequest.getName());
//    	System.out.println(apiRequest.getDateOfBirthStr()());
    	
    	return "name: " + apiRequest.getCodeName();
    }

}

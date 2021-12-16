package com.wanderlust.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wanderlust.model.Booking;
import com.wanderlust.model.Destination;
import com.wanderlust.model.HotDeals;
import com.wanderlust.service.DealsService;

@CrossOrigin
@RestController
@RequestMapping("DealsAPI")
public class DealsAPI {
	
	@Autowired
	private DealsService dealsService;
	
	@Autowired
	private Environment environment;
	
	@PostMapping(value = "hotDeals")
	public ResponseEntity<List<HotDeals>> hotDeals(@RequestBody String[] destinationId)throws Exception{
		try {
			List<HotDeals> hot=dealsService.getDealsForHotDeals(destinationId);
			return new ResponseEntity<List<HotDeals>>(hot, HttpStatus.OK);
			
		}catch(Exception e) {
			throw new Exception(e);
		}
		
	}
	
	@GetMapping(value = "allDeals")
	public ResponseEntity<List<Destination>> allDeals()throws Exception{
		List<Destination> allDeals=dealsService.getAllDeals();
		return new ResponseEntity<List<Destination>>(allDeals,HttpStatus.OK);
	}
	
	@PostMapping(value = "booking",params = "userId")
	public ResponseEntity<Integer> booking(@RequestBody Booking booking, @RequestParam(name = "userId") String userId)throws Exception{
		try {
			return new ResponseEntity<Integer>(dealsService.setBooking(booking,userId), HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Sorry");
		}
	}
	
	@GetMapping(value = "getBooking",params = "userId")
	public ResponseEntity<List<Booking>> getBooking(@RequestParam(name="userId") String userId)throws Exception{
		try {
			return new ResponseEntity<List<Booking>>(dealsService.getBooking(userId),HttpStatus.OK);
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Sorry");
			
		}
	}
	
	@PostMapping(value = "cancelBooking")
	public ResponseEntity<String> cancelBooking(@RequestBody Booking booking)throws Exception{
		try {
			dealsService.deleteBooking(booking);
			return new ResponseEntity<String>(environment.getProperty("DealsAPI.Success"), HttpStatus.OK);
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,environment.getProperty(e.getMessage()));
			
		}
		
	}
	
	
	
	

}

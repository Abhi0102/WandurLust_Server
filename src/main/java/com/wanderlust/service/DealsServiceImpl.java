package com.wanderlust.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanderlust.dao.DealsDAO;
import com.wanderlust.model.Booking;
import com.wanderlust.model.Destination;
import com.wanderlust.model.HotDeals;
import com.wanderlust.validator.BookingValidator;

@Transactional
@Service(value = "dealsService")
public class DealsServiceImpl implements DealsService {
	
	@Autowired
	private DealsDAO dealsDAO;
	
	
	@Override
	public List<HotDeals> getDealsForHotDeals(String[] destinationId) {
		List<HotDeals> hotDealsList=new ArrayList<HotDeals>();
		for(String i: destinationId) {
			hotDealsList.add(dealsDAO.getDealsByDestinationIdForHotDeals(i));
		}
		return hotDealsList;
	}
	
	@Override
	public List<Destination> getAllDeals(){
		List<Destination> destination =dealsDAO.getAllDeals();
		return destination;
	}
	
	@Override
	public Integer setBooking(Booking booking, String userId)throws Exception {
		
		
		
		return dealsDAO.setBooking(booking,userId);
	}
	
	@Override
	public List<Booking> getBooking(String userId)throws Exception{
		Integer userIdInt=Integer.parseInt(userId);
		return dealsDAO.getBooking(userIdInt);
		
	}
	
	@Override
	public void deleteBooking(Booking booking)throws Exception{
		try {
			BookingValidator.deleteBookingValidate(booking);
			Integer deletedBooking=dealsDAO.deleteBooking(booking.getBookingId());
			if(deletedBooking==0) {
				throw new Exception("BookingValidator.Booking_Not_Available");
			}
		}catch(Exception e) {
			throw e;
		}
	}
}

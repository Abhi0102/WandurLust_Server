package com.wanderlust.service;

import java.util.List;

import com.wanderlust.model.Booking;
import com.wanderlust.model.Destination;
import com.wanderlust.model.HotDeals;

public interface DealsService {
	public List<HotDeals> getDealsForHotDeals(String[] destinationId);
	public List<Destination> getAllDeals();
	public Integer setBooking(Booking booking, String userId)throws Exception;
	public List<Booking> getBooking(String userId)throws Exception;
	public void deleteBooking(Booking booking)throws Exception;
}

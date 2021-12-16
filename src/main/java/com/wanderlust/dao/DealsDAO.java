package com.wanderlust.dao;

import java.util.List;

import com.wanderlust.model.Booking;
import com.wanderlust.model.Destination;
import com.wanderlust.model.Details;
import com.wanderlust.model.HotDeals;
import com.wanderlust.model.Itinerary;

public interface DealsDAO {
	public HotDeals getDealsByDestinationIdForHotDeals(String destinationId);
	public List<Destination> getAllDeals();
	public Integer setBooking(Booking booking,String userId);
	public List<Booking> getBooking(Integer userId);
	public Destination getDestinationById(String destinationId);
	public Details getDetail(String detailsId);
	public Itinerary getItinerary(String itineraryId);
	public Integer deleteBooking(Integer bookingId);
}

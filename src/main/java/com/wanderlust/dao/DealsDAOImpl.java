package com.wanderlust.dao;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wanderlust.entity.BookingEntity;
import com.wanderlust.entity.DestinationEntity;
import com.wanderlust.entity.DetailsEntity;
import com.wanderlust.entity.ItineraryEntity;
import com.wanderlust.entity.UserEntity;
import com.wanderlust.model.Booking;
import com.wanderlust.model.Destination;
import com.wanderlust.model.Details;
import com.wanderlust.model.HotDeals;
import com.wanderlust.model.Itinerary;

@Repository(value = "dealsDAO")
public class DealsDAOImpl implements DealsDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public HotDeals getDealsByDestinationIdForHotDeals(String destinationId) {
		DestinationEntity destinationEntity=entityManager.find(DestinationEntity.class, destinationId);
		if(destinationEntity!=null) {
			HotDeals hotDeals=new HotDeals();
			hotDeals.setDestinationId(destinationEntity.getDestinationId());
			hotDeals.setDestinationName(destinationEntity.getDestinationName());
			hotDeals.setContinent(destinationEntity.getContinent());
			hotDeals.setImageUrl(destinationEntity.getImageUrl());
			return hotDeals;
		}
		return null;
		
	}
	
	@Override
	public List<Destination> getAllDeals() {
		Query query= entityManager.createQuery("select c from DestinationEntity c");
		List<DestinationEntity> destinationEntity=query.getResultList();
		List<Destination> destination=new ArrayList<Destination>();
		if(!destinationEntity.isEmpty()) {
			for(DestinationEntity i: destinationEntity) {
				Destination d=new Destination();
				d.setDestinationId(i.getDestinationId());
				d.setAvailability(i.getAvailability());
				d.setChargePerPerson(i.getChargePerPerson());
				d.setContinent(i.getContinent());
				d.setDestinationName(i.getDestinationName());
				d.setDiscount(i.getDiscount());
				d.setFlightCharge(i.getFlightCharge());
				d.setImageUrl(i.getImageUrl());
				d.setNoOfNights(i.getNoOfNights());
				if(i.getDetailsEntity()!=null) {
					Details details=new Details();
					details.setAbout(i.getDetailsEntity().getAbout());
					details.setDetailsId(i.getDetailsEntity().getDetailsId());
					details.setHighlights(i.getDetailsEntity().getHighlights());
					if(i.getDetailsEntity().getItineraryEntity()!=null) {
						Itinerary itinerary=new Itinerary();
						itinerary.setFirstDay(i.getDetailsEntity().getItineraryEntity().getFirstDay());
						itinerary.setItineraryId(i.getDetailsEntity().getItineraryEntity().getItineraryId());
						itinerary.setRestOfDays(i.getDetailsEntity().getItineraryEntity().getRestOfDays());
						itinerary.setLastDay(i.getDetailsEntity().getItineraryEntity().getLastDay());
						details.setItinerary(itinerary);
					}
					details.setPace(i.getDetailsEntity().getPace());
					details.setPackageInclusion(i.getDetailsEntity().getPackageInclusion());
					d.setDetails(details);
				}
				destination.add(d);
			}
		}
		return destination;
	}
	
	@Override
	public Integer setBooking(Booking booking, String userId) {
		
		BookingEntity bookingEntity=new BookingEntity();
		bookingEntity.setCheckIn(booking.getCheckIn());
		bookingEntity.setCheckOut(booking.getCheckIn().plusDays(booking.getDestination().getNoOfNights()));
		
		DestinationEntity destinationEntity=entityManager.find(DestinationEntity.class, booking.getDestination().getDestinationId());
		bookingEntity.setDestinationEntity(destinationEntity);
		
		bookingEntity.setNoOfPeople(booking.getNoOfPeople());
		bookingEntity.setTimeOfBooking(LocalDateTime.now());
		bookingEntity.setTotalCost(booking.getTotalCost());
		
		UserEntity user=entityManager.find(UserEntity.class, Integer.parseInt(userId));
		bookingEntity.setUserEntity(user);
		entityManager.persist(bookingEntity);
		
		return bookingEntity.getBookingId();
	}
	
	@Override
	public List<Booking> getBooking(Integer userId) {
		
		Query query=entityManager.createQuery("select be from BookingEntity be where be.userEntity.userId=:userId");
		query.setParameter("userId", userId);
		List<BookingEntity> be =query.getResultList();
		List<Booking> booking=new ArrayList<Booking>();
		if(!be.isEmpty()) {
			for(BookingEntity i: be) {
				Booking b=new Booking();
				b.setBookingId(i.getBookingId());
				b.setCheckIn(i.getCheckIn());
				b.setCheckOut(i.getCheckOut());
				b.setNoOfPeople(i.getNoOfPeople());
				b.setTimeOfBooking(i.getTimeOfBooking());
				b.setTotalCost(i.getTotalCost());
				b.setDestination(getDestinationById(i.getDestinationEntity().getDestinationId()));
				booking.add(b);
			}
		}
		
		return booking;
	}
	
	@Override
	public Destination getDestinationById(String destinationId) {
		DestinationEntity de=entityManager.find(DestinationEntity.class, destinationId);
		Destination d=new Destination();
		d.setAvailability(de.getAvailability());
		d.setChargePerPerson(de.getChargePerPerson());
		d.setContinent(de.getContinent());
		d.setDestinationId(de.getDestinationId());
		d.setDestinationName(de.getDestinationName());
		d.setDiscount(de.getDiscount());
		d.setFlightCharge(de.getFlightCharge());
		d.setImageUrl(de.getImageUrl());
		d.setNoOfNights(de.getNoOfNights());
		d.setDetails(getDetail(de.getDetailsEntity().getDetailsId()));
		return d;
	}
	
	@Override
	public Details getDetail(String detailsId) {
		DetailsEntity de=entityManager.find(DetailsEntity.class,detailsId);
		Details d=new Details();
		d.setAbout(de.getAbout());
		d.setDetailsId(de.getDetailsId());
		d.setHighlights(de.getHighlights());
		d.setPace(de.getPace());
		d.setPackageInclusion(de.getPackageInclusion());
		d.setItinerary(getItinerary(de.getItineraryEntity().getItineraryId()));
		return d;
	}
	
	@Override
	public Itinerary getItinerary(String itineraryId) {
		ItineraryEntity ie=entityManager.find(ItineraryEntity.class, itineraryId);
		Itinerary i=new Itinerary();
		i.setFirstDay(ie.getFirstDay());
		i.setItineraryId(ie.getItineraryId());
		i.setLastDay(ie.getLastDay());
		i.setRestOfDays(ie.getRestOfDays());
		return i;
	}
	
	@Override
	public Integer deleteBooking(Integer bookingId) {
		Query query= entityManager.createQuery("DELETE FROM BookingEntity be where be.bookingId=:bookingId");
		query.setParameter("bookingId", bookingId);
		Integer updateEntity=query.executeUpdate();
		return updateEntity;
	}

}

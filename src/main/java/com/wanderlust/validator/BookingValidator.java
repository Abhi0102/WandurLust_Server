package com.wanderlust.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.wanderlust.model.Booking;

public class BookingValidator {
	
	public static void deleteBookingValidate(Booking booking)throws Exception {
			if(!validateCheckDate(booking.getCheckIn())) {
				throw new Exception("BookingValidator.Date_Error");
			}
		
	}
	
	public static Boolean validateCheckDate(LocalDate checkIn) {
		LocalDate today= LocalDate.now();
		long days =ChronoUnit.DAYS.between(today, checkIn);
		if(days>2) {
			return true;
		}
		return false;
	}

}

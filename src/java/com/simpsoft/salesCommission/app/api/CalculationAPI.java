package com.simpsoft.salesCommission.app.api;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.model.Frequency;

@Component
public class CalculationAPI {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(CalculationAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	public long getFullWeeks(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) {
		Date startDate;
		Date endDate;
		if(planStartDate.equals(rosterStartDate)) {
			startDate=rosterStartDate; // or startDate = planStartDate
		}else {
			if(planStartDate.after(rosterStartDate)) {
				startDate= planStartDate;
			}else {
				startDate = rosterStartDate;
			}
		}
		if(planEndDate.equals(rosterEndDate)) {
			endDate= rosterEndDate; // or endDate=planEndDate
		}else {
			if(planEndDate.before(rosterEndDate)) {
				endDate= planEndDate;
			}else {
				endDate = rosterEndDate;
			}
		}
		
		logger.debug("start date to calculate from= "+startDate);
		logger.debug("end date to calculate till= "+endDate);
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		 
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		
		  // make the starting date relative to the Monday we need to calculate from
	    int dayOfStartWeek = d1.get(Calendar.DAY_OF_WEEK);
	    int dayOfEndWeek = d2.get(Calendar.DAY_OF_WEEK);
	    
	 // IF we have a partial week we should add an offset that moves the start
	    // date UP TO the next Monday to simulate a week starting on Monday
	    // eliminating partial weeks from the calculation
	    // NOTE THIS METHOD WILL RETURN NEGATIVES IF D1 < D2 after adjusting for 
	    // offset
	    if (dayOfStartWeek == Calendar.SUNDAY) {
	        // add an offset of 1 day because this is a partial week
	        d1.add(Calendar.DAY_OF_WEEK, 1);
	    } else if (dayOfStartWeek != Calendar.MONDAY){
	        // add an offset for the partial week
	        // Hence subtract from 9 accounting for shift by 1
	        // and start at 1
	        // ex if WEDNESDAY we need to add 9-4 (WED Int Val) = 5 days
	        d1.add(Calendar.DAY_OF_WEEK, 9 - dayOfStartWeek);
	    }
	    
	    if(dayOfEndWeek == Calendar.SUNDAY) {
	    	d2.add(Calendar.DAY_OF_WEEK, 1);
	    }

	    Instant d1i = Instant.ofEpochMilli(d1.getTimeInMillis());
	    Instant d2i = Instant.ofEpochMilli(d2.getTimeInMillis());

	    LocalDateTime startDate1 = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
	    LocalDateTime endDate1 = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

	    return ChronoUnit.WEEKS.between(startDate1, endDate1);
	}
	
	
	public long getFullMonths(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) {
		Date startDate;
		Date endDate;
		if(planStartDate.equals(rosterStartDate)) {
			startDate=rosterStartDate; // or startDate = planStartDate
		}else {
			if(planStartDate.after(rosterStartDate)) {
				startDate= planStartDate;
			}else {
				startDate = rosterStartDate;
			}
		}
		if(planEndDate.equals(rosterEndDate)) {
			endDate= rosterEndDate; // or endDate=planEndDate
		}else {
			if(planEndDate.before(rosterEndDate)) {
				endDate= planEndDate;
			}else {
				endDate = rosterEndDate;
			}
		}
		
		logger.debug("start date to calculate from= "+startDate);
		logger.debug("end date to calculate till= "+endDate);
		Calendar d1 = Calendar.getInstance();
		d1.setTime(startDate);
		 
		Calendar d2 = Calendar.getInstance();
		d2.setTime(endDate);
		
		int dateOfStartMonth = d1.get(Calendar.DATE);
		logger.debug("dateOfStartMonth = "+dateOfStartMonth);
		
		if(dateOfStartMonth != 1) {
			d1.add(Calendar.MONTH, 1);
		}
		
		int dateOfEndMonth = d2.get(Calendar.DATE);
		int maxDate = d2.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		if(dateOfEndMonth == maxDate ) {
			d2.add(Calendar.MONTH, 1);
		}
		
		Instant d1i = Instant.ofEpochMilli(d1.getTimeInMillis());
	    Instant d2i = Instant.ofEpochMilli(d2.getTimeInMillis());

	    LocalDateTime startDate1 = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
	    LocalDateTime endDate1 = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());
	    
		return ChronoUnit.MONTHS.between(startDate1, endDate1);
		
		  
	}
	
}

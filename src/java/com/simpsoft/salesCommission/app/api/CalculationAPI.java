package com.simpsoft.salesCommission.app.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	
	@SuppressWarnings("deprecation")
	public long getFullQuarters(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		
		long total_quarts=0;
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		int year = d1.get(Calendar.YEAR);
		logger.debug("year= "+year);
		int end_year = d2.get(Calendar.YEAR);
		logger.debug("end_year= "+end_year);
		
		while(year <= end_year) {
			logger.debug("startDate= "+startDate);
			Date quart1_start_date= new Date("01/01/"+year);
			String sd = format1.format(quart1_start_date);
			quart1_start_date = format1.parse(sd);
			logger.debug("quart1_start_date= "+quart1_start_date);
			
			Date quart1_end_date = new Date("03/31/"+year);
			String ed = format1.format(quart1_end_date);
			quart1_end_date = format1.parse(ed);
			logger.debug("quart1_end_date= "+quart1_end_date);
			
			Date quart2_start_date= new Date("04/01/"+year);
			String sd2 = format1.format(quart2_start_date);
			quart2_start_date = format1.parse(sd2);
			logger.debug("quart2_start_date= "+quart2_start_date);
			
			Date quart2_end_date = new Date("06/30/"+year);
			String ed2 = format1.format(quart2_end_date);
			quart2_end_date = format1.parse(ed2);
			logger.debug("quart2_end_date= "+quart2_end_date);
			
			Date quart3_start_date= new Date("07/01/"+year);
			String sd3 = format1.format(quart3_start_date);
			quart3_start_date = format1.parse(sd3);
			logger.debug("quart3_start_date= "+quart3_start_date);
			
			Date quart3_end_date = new Date("09/30/"+year);
			String ed3 = format1.format(quart3_end_date);
			quart3_end_date = format1.parse(ed3);
			logger.debug("quart3_end_date= "+quart3_end_date);
			
			Date quart4_start_date= new Date("10/01/"+year);
			String sd4 = format1.format(quart4_start_date);
			quart4_start_date = format1.parse(sd4);
			logger.debug("quart4_start_date= "+quart4_start_date);
			
			Date quart4_end_date = new Date("12/31/"+year);
			String ed4 = format1.format(quart4_end_date);
			quart4_end_date = format1.parse(ed4);
			logger.debug("quart4_end_date= "+quart4_end_date);
			
			
			if(startDate.equals(quart1_start_date)) {
				if(endDate.equals(quart1_end_date)  || endDate.after(quart1_end_date)) {
					total_quarts=total_quarts+1;
				}
				if(endDate.equals(quart2_end_date)  || endDate.after(quart2_end_date)) {
					total_quarts=total_quarts+1;
				}
				if(endDate.equals(quart3_end_date)  || endDate.after(quart3_end_date)) {
					total_quarts=total_quarts+1;
				}
				if(endDate.equals(quart4_end_date)  || endDate.after(quart4_end_date)) {
					total_quarts=total_quarts+1;
				}
			}
			else if(startDate.after(quart1_start_date)) {
				if(startDate.before(quart2_start_date) || startDate.equals(quart2_start_date)) {
					if(endDate.equals(quart2_end_date)  || endDate.after(quart2_end_date)) {
						total_quarts=total_quarts+1;
					}
					if(endDate.equals(quart3_end_date)  || endDate.after(quart3_end_date)) {
						total_quarts=total_quarts+1;
					}
					if(endDate.equals(quart4_end_date)  || endDate.after(quart4_end_date)) {
						total_quarts=total_quarts+1;
					}
				}
				
				else if( (startDate.after(quart2_start_date)) &&   
						( startDate.before(quart3_start_date) || startDate.equals(quart3_start_date) ) ){
					if(endDate.equals(quart3_end_date)  || endDate.after(quart3_end_date)) {
						total_quarts=total_quarts+1;
					}
					if(endDate.equals(quart4_end_date)  || endDate.after(quart4_end_date)) {
						total_quarts=total_quarts+1;
					}
				}
				
				else if((startDate.after(quart3_start_date)) &&   
						( startDate.before(quart4_start_date) || startDate.equals(quart4_start_date) ) ) {
					if(endDate.equals(quart4_end_date)  || endDate.after(quart4_end_date)) {
						total_quarts=total_quarts+1;
					}
				}
				
				
			}
			
			year++;
			startDate= new Date("01/01/"+year);
		}
		
		return total_quarts;		
		  
	}
	
	
	@SuppressWarnings("deprecation")
	public long getFullHalves(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		
		long total_halves=0;
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		int year = d1.get(Calendar.YEAR);
		logger.debug("year= "+year);
		int end_year = d2.get(Calendar.YEAR);
		logger.debug("end_year= "+end_year);
		
		while(year <= end_year) {
			logger.debug("startDate= "+startDate);
			Date half1_start_date= new Date("01/01/"+year);
			String sd = format1.format(half1_start_date);
			half1_start_date = format1.parse(sd);
			logger.debug("half1_start_date= "+half1_start_date);
			
			Date half1_end_date = new Date("06/30/"+year);
			String ed = format1.format(half1_end_date);
			half1_end_date = format1.parse(ed);
			logger.debug("half1_end_date= "+half1_end_date);
			
			Date half2_start_date= new Date("07/01/"+year);
			String sd2 = format1.format(half2_start_date);
			half2_start_date = format1.parse(sd2);
			logger.debug("half2_start_date= "+half2_start_date);
			
			Date half2_end_date = new Date("12/31/"+year);
			String ed2 = format1.format(half2_end_date);
			half2_end_date = format1.parse(ed2);
			logger.debug("half2_end_date= "+half2_end_date);
			
					
			
			if(startDate.equals(half1_start_date)) {
				if(endDate.equals(half1_end_date)  || endDate.after(half1_end_date)) {
					total_halves=total_halves+1;
				}
				if(endDate.equals(half2_end_date)  || endDate.after(half2_end_date)) {
					total_halves=total_halves+1;
				}
				
			}
			else if(startDate.after(half1_start_date)) {
				if(startDate.before(half2_start_date) || startDate.equals(half2_start_date)) {
					if(endDate.equals(half2_end_date)  || endDate.after(half2_end_date)) {
						total_halves=total_halves+1;
					}
					
				}
			}
			
			year++;
			startDate= new Date("01/01/"+year);
		}
		
		return total_halves;		
		  
	}
	
	@SuppressWarnings("deprecation")
	public long getFullYears(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		
		long total_years=0;
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		
		int year = d1.get(Calendar.YEAR);
		logger.debug("year= "+year);
		int end_year = d2.get(Calendar.YEAR);
		logger.debug("end_year= "+end_year);
		
		while(year <= end_year) {
			logger.debug("startDate= "+startDate);
			Date year_start_date= new Date("01/01/"+year);
			String sd = format1.format(year_start_date);
			year_start_date = format1.parse(sd);
			logger.debug("year_start_date= "+year_start_date);
			
			Date year_end_date = new Date("12/31/"+year);
			String ed = format1.format(year_end_date);
			year_end_date = format1.parse(ed);
			logger.debug("year_end_date= "+year_end_date);
			
					
					
			
			if(startDate.equals(year_start_date)) {
				if(endDate.equals(year_end_date)  || endDate.after(year_end_date)) {
					total_years=total_years+1;
				}		
				
			}
			
			
			year++;
			startDate= new Date("01/01/"+year);
		}
		
		return total_years;		
		  
	}
	
}

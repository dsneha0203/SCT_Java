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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.model.CalcDetailsOrderLineItems;
import com.simpsoft.salesCommission.app.model.CalculationRoster;
import com.simpsoft.salesCommission.app.model.CalculationSimple;
import com.simpsoft.salesCommission.app.model.Employee;
import com.simpsoft.salesCommission.app.model.Frequency;
import com.simpsoft.salesCommission.app.model.OrderDetail;
import com.simpsoft.salesCommission.app.model.OrderLineItems;
import com.simpsoft.salesCommission.app.model.OrderLineItemsSplit;
import com.simpsoft.salesCommission.app.model.QualifyingClause;
import com.simpsoft.salesCommission.app.model.Rule;
import com.simpsoft.salesCommission.app.model.RuleAssignment;
import com.simpsoft.salesCommission.app.model.RuleAssignmentDetails;
import com.simpsoft.salesCommission.app.model.RuleAssignmentParameter;
import com.simpsoft.salesCommission.app.model.RuleComposite;
import com.simpsoft.salesCommission.app.model.RuleSimple;
import com.simpsoft.salesCommission.app.model.Target;
import com.simpsoft.salesCommission.app.model.TargetDefinition;

import java.util.Iterator;

@Component
public class CalculationAPI {

	@Autowired
	private SessionFactory sessionFactory;
	
	
		
	
	private static final Logger logger = Logger.getLogger(CalculationAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}
	
	public Map<Date,Date> getDatesFixedRule(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate){
		Date startDate;
		Date endDate;
		Map<Date,Date> fixedRuleDatesMap = new HashMap<>();
		
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
		
		logger.debug("fixed rule start date to calculate from= "+startDate);
		logger.debug("fixed rule end date to calculate till= "+endDate);
		
		fixedRuleDatesMap.put(startDate,endDate);
		return fixedRuleDatesMap;
		
	}
	

	public Map<Date,Date> getFullWeeks(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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

	    long num= ChronoUnit.WEEKS.between(startDate1, endDate1);
	    Map<Date, Date> weekDates = getWeekStartEndDates(d1, num);
	    for(Map.Entry<Date, Date> dates : weekDates.entrySet()) {
	    	logger.debug("WEEK START DATE= "+dates.getKey()+" WEEK END DATE= "+dates.getValue());
	    }
	    return weekDates;
	}
	
	public Map<Date,Date> getWeekStartEndDates(Calendar d1, long num) throws ParseException{
		Map<Date,Date> dates = new HashMap<>();
		//generate the start and end dates of each week
		for(int i=0; i<num ; i++) {
			Date startDate = d1.getTime();
			 d1.add(Calendar.DAY_OF_WEEK, 6);
			 Date endDate = d1.getTime();
			 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 String sd = format1.format(startDate);
			 startDate = format1.parse(sd);
			 String ed = format1.format(endDate);
			 endDate = format1.parse(ed);
			 dates.put(startDate, endDate);
			 d1.add(Calendar.DAY_OF_WEEK, 1);
			 
			
		}
		return dates;
	}
	
	public Map<Date,Date> getFullMonths(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
			d1.set(Calendar.DATE, 1);
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
	    
	    long num= ChronoUnit.MONTHS.between(startDate1, endDate1);
	    Map<Date, Date> monthDates = getMonthStartEndDates(d1, num);
	    for(Map.Entry<Date, Date> dates : monthDates.entrySet()) {
	    	logger.debug("MONTH START DATE= "+dates.getKey()+" MONTH END DATE= "+dates.getValue());
	    }
	    
		return monthDates;
		
		  
	}
	
	public Map<Date,Date> getMonthStartEndDates(Calendar d1, long num) throws ParseException{
		Map<Date,Date> dates = new HashMap<>();
		//generate the start and end dates of each week
		for(int i=0; i<num ; i++) {
			 Date startDate = d1.getTime();
			 logger.debug("START DATE OF MONTH= "+startDate);
			 Calendar cal = Calendar.getInstance();
		     cal.setTime(startDate);
		     cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		     Date endDate = cal.getTime();
		     SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 String sd = format1.format(startDate);
			 startDate = format1.parse(sd);
			 String ed = format1.format(endDate);
			 endDate = format1.parse(ed);
			 dates.put(startDate, endDate);
		     d1.add(Calendar.DAY_OF_WEEK,  cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			 
			
		}
		return dates;
	}
	
	@SuppressWarnings("deprecation")
	public Map<Date,Date> getFullQuarters(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		Map<Date, Date> quarterDates = getQuarterStartEndDates(d1, total_quarts);
	    for(Map.Entry<Date, Date> dates : quarterDates.entrySet()) {
	    	logger.debug("QUARTER START DATE= "+dates.getKey()+" QUARTER END DATE= "+dates.getValue());
	    }
	    
		return quarterDates;		
		  
	}
	
	public Map<Date,Date> getQuarterStartEndDates(Calendar d1, long num) throws ParseException{
		Map<Date,Date> dates = new HashMap<>();
		//generate the start and end dates of each week
		for(int i=0; i<num ; i++) {
			int startCalDate = d1.get(Calendar.DATE);
			logger.debug("startCalDate= "+startCalDate);
			int startMonth= d1.get(Calendar.MONTH);
			logger.debug("startMonth= "+startMonth);
			if(startCalDate != 1 ) {
				
				if(startMonth == 0) {
					d1.set(Calendar.MONTH, 3);
				}
				else if(startMonth>0 && startMonth<3) {
					d1.set(Calendar.MONTH, 3);
				}
				else if(startMonth==3) {
					d1.set(Calendar.MONTH, 6);
				}
				else if(startMonth >3 && startMonth <6) {
					d1.set(Calendar.MONTH, 6);
				}
				else if(startMonth == 6) {
					d1.set(Calendar.MONTH, 9);
				}
				else if(startMonth>6 && startMonth<9) {
					d1.set(Calendar.MONTH, 9);
				}
				else if(startMonth >= 9) {
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);					
				}
				
				d1.set(Calendar.DAY_OF_MONTH,1);
			}else {
				if(startMonth>0 && startMonth<3) {
					d1.set(Calendar.MONTH, 3);
				}
				else if(startMonth >3 && startMonth <6) {
					d1.set(Calendar.MONTH, 6);
				}
				else if(startMonth>6 && startMonth<9) {
					d1.set(Calendar.MONTH, 9);
				}
				else if(startMonth > 9) {
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);					
				}
			}
			 Date startDate = d1.getTime();			 
			 logger.debug("START DATE OF QUARTER= "+startDate);			 		 
			 Calendar cal = d1;
			 cal.add(Calendar.MONTH, 2);
		     cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		     Date endDate = cal.getTime();
		     SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 String sd = format1.format(startDate);
			 startDate = format1.parse(sd);
			 String ed = format1.format(endDate);
			 endDate = format1.parse(ed);
			 dates.put(startDate, endDate);
		     d1.add(Calendar.DAY_OF_WEEK,  1);
			 
			
		}
		return dates;
	}
	
	@SuppressWarnings("deprecation")
	public Map<Date,Date> getFullHalves(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		Map<Date, Date> halfDates = getHalfStartEndDates(d1, total_halves);
	    for(Map.Entry<Date, Date> dates : halfDates.entrySet()) {
	    	logger.debug("HALF START DATE= "+dates.getKey()+" HALF END DATE= "+dates.getValue());
	    }
	    
		return halfDates;		
		  
	}
	
	public Map<Date,Date> getHalfStartEndDates(Calendar d1, long num) throws ParseException{
		Map<Date,Date> dates = new HashMap<>();
		//generate the start and end dates of each week
		for(int i=0; i<num ; i++) {
			int startCalDate = d1.get(Calendar.DATE);
			logger.debug("startCalDate= "+startCalDate);
			int startMonth= d1.get(Calendar.MONTH);
			logger.debug("startMonth= "+startMonth);
			if(startCalDate != 1 ) {
				
				if(startMonth == 0) {
					d1.set(Calendar.MONTH, 6);
				}
				else if(startMonth>0 && startMonth<6) {
					d1.set(Calendar.MONTH, 6);
				}
				else if(startMonth == 6 || startMonth > 6 ) {
				
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);					
				}
				
				d1.set(Calendar.DAY_OF_MONTH,1);
			}else {
				if(startMonth>0 && startMonth<6) {
					d1.set(Calendar.MONTH, 6);
				}
				
				else if(startMonth > 6) {
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);					
				}
			}
			 Date startDate = d1.getTime();			 
			 logger.debug("START DATE OF HALF= "+startDate);			 		 
			 Calendar cal = d1;
			 cal.add(Calendar.MONTH, 5);
		     cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		     Date endDate = cal.getTime();
		     SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 String sd = format1.format(startDate);
			 startDate = format1.parse(sd);
			 String ed = format1.format(endDate);
			 endDate = format1.parse(ed);
			 dates.put(startDate, endDate);
		     d1.add(Calendar.DAY_OF_WEEK,  1);
			 
			
		}
		return dates;
	}
	
	@SuppressWarnings("deprecation")
	public Map<Date,Date> getFullYears(Date planStartDate, Date planEndDate, Date rosterStartDate, Date rosterEndDate) throws ParseException {
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
		
		Map<Date, Date> yearDates = getYearStartEndDates(d1, total_years);
	    for(Map.Entry<Date, Date> dates : yearDates.entrySet()) {
	    	logger.debug("ANNUAL START DATE= "+dates.getKey()+" ANNUAL END DATE= "+dates.getValue());
	    }
		
		return yearDates;		
		  
	}
	
	public Map<Date,Date> getYearStartEndDates(Calendar d1, long num) throws ParseException{
		Map<Date,Date> dates = new HashMap<>();
		//generate the start and end dates of each week
		for(int i=0; i<num ; i++) {
			int startCalDate = d1.get(Calendar.DATE);
			logger.debug("startCalDate= "+startCalDate);
			int startMonth= d1.get(Calendar.MONTH);
			logger.debug("startMonth= "+startMonth);
			if(startCalDate != 1 ) {
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);				
					d1.set(Calendar.DAY_OF_MONTH,1);
			}else {			
				
				if(startMonth != 0) {
					d1.set(Calendar.MONTH, 0);
					d1.add(Calendar.YEAR, 1);					
				}
			}
			 Date startDate = d1.getTime();			 
			 logger.debug("START DATE OF YEAR= "+startDate);			 		 
			 Calendar cal = d1;
			 cal.add(Calendar.MONTH, 11);
		     cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		     Date endDate = cal.getTime();
		     SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 String sd = format1.format(startDate);
			 startDate = format1.parse(sd);
			 String ed = format1.format(endDate);
			 endDate = format1.parse(ed);
			 dates.put(startDate, endDate);
		     d1.add(Calendar.DAY_OF_WEEK,  1);
			 
			
		}
		return dates;
	}
	
	public List<Rule> getCompRuleDetails(RuleAssignmentAPI ruleAssAPI, RuleAPI ruleAPI,
			Rule compRule) {
		logger.debug("RULE TYPE = COMPOSITE");
		RuleComposite composite = ruleAPI.findCompRule(compRule.getId());
		logger.debug("COMPOSITE RULE ID= "+ composite.getId());
		List<Rule> simpleRules = ruleAssAPI.getSimpleRuleList(composite.getId());
		for(Rule simpRule : simpleRules) {								
			RuleSimple ruleSimple = ruleAPI.findSimpleRule(simpRule.getId());
			logger.debug("SIMPLE RULE NAME= "+ruleAPI.getRule(simpRule.getId()).getRuleName());
			logger.debug("SIMPLE RULE ID= "+ruleSimple.getId());
			List<QualifyingClause> clauses = ruleSimple.getQualifyingClause();
			if(clauses != null) {
				logger.debug("---QUALIFYING CLAUSE LIST---");
				printQualClause(clauses);
			}
			
		}
		return simpleRules;
	}
	
	public static void printQualClause(List<QualifyingClause> clauses) {
		for(QualifyingClause clause : clauses) {
			logger.debug("QUAL CLAUSE ID= "+clause.getId());
			logger.debug("Field name= "+clause.getFieldList().getDisplayName());
			logger.debug("Condition value= "+clause.getConditionList().getConditionValue());
			if(clause.getAggregateFunctions() != null) {
				logger.debug("Agg Func= "+clause.getAggregateFunctions().getFunctionName());
			}
			logger.debug("Not= "+clause.isNotFlag());
			logger.debug("Value= "+clause.getValue());
		}
	}
	
	
	
	public boolean checkValidity(RuleAssignmentAPI ruleAssAPI, long ruleAssDetailId, Date startDate2, Date endDate2) throws ParseException {
		RuleAssignmentDetails assignmentDetails = ruleAssAPI.getRuleAssignmentDetail(ruleAssDetailId);
		String validityType = assignmentDetails.getValidityType();
		Date ruleAssDetStartDate = assignmentDetails.getStartDate();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String sd = format1.format(ruleAssDetStartDate);
		ruleAssDetStartDate = format1.parse(sd);
		logger.debug("ruleAssDetStartDate= "+ruleAssDetStartDate);
		logger.debug("roster start date="+ startDate2);
		Date ruleAssDetEndDate = assignmentDetails.getEndDate();
		String ed = format1.format(ruleAssDetEndDate);
		ruleAssDetEndDate = format1.parse(ed);
		logger.debug("ruleAssDetEndDate= "+ruleAssDetEndDate);
		logger.debug("roster end date= "+endDate2);
		
		if(validityType.equals("repeats")) {
			if(ruleAssDetStartDate.equals(startDate2)){
				logger.debug("plan start date = roster start date");
				return true;
			}
			if(ruleAssDetEndDate.equals(endDate2)) {
				logger.debug("plan end date = roster end date");
				return true;
			}
			if(ruleAssDetStartDate.before(startDate2) && ruleAssDetEndDate.after(endDate2)) {
				logger.debug("plan start date is before roster start date and plan end date is after roster end date");
				return true;
			}
			if(ruleAssDetStartDate.before(startDate2) && ruleAssDetEndDate.after(startDate2)) {
				logger.debug("plan start date is before roster start date and plan end date is after roster start date");
				return true;
			}
			if(ruleAssDetStartDate.after(startDate2) && ruleAssDetEndDate.before(endDate2)) {
				logger.debug("plan start date is after roster start date and plan end date is before roster end date");
				return true;
			}
			if(ruleAssDetStartDate.after(startDate2)&& ruleAssDetStartDate.before(endDate2) && ruleAssDetEndDate.after(endDate2)) {
				logger.debug("plan start date is after roster start date but before roster end date and plan end date is after roster end date");
				return true;
			}
						
		}else {
			// for fixed
			if(ruleAssDetStartDate.equals(startDate2) && ruleAssDetEndDate.equals(endDate2)) {
				logger.debug("plan start date = roster start date and plan end date = roster end date");
				return true;
			}
			if(ruleAssDetStartDate.after(startDate2) && ruleAssDetEndDate.equals(endDate2)) {
				logger.debug("plan start date is after roster start date and plan end date= roster end date");
				return true;
			}
			if(ruleAssDetStartDate.equals(startDate2) && ruleAssDetEndDate.before(endDate2)) {
				logger.debug("plan start date = roster start date and plan end date is before roster end date");
				return true;
			}
			if(ruleAssDetStartDate.after(startDate2) && ruleAssDetEndDate.before(endDate2)) {
				logger.debug("plan start date is after roster start date and plan end date is before roster end date");
				return true;
			}
		}
		

		return false;
	}
	
	
	public void saveDatesSimpList(Date startdate, Date enddate, List<CalculationSimple> calcSimpList, Map<Employee, Map<OrderLineItemsSplit, Boolean>> empSplitQualMap, boolean dummyCalcInt) {
		logger.debug("---IN SAVE DATE METHOD---");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		CalculationRoster calculationRoster = new CalculationRoster();
		List<CalculationSimple> calSimp = new ArrayList<CalculationSimple>();
		try {
			tx = session.beginTransaction();
			logger.debug("ROSTER START DATE= "+startdate);
			calculationRoster.setStartDate(startdate);
			logger.debug("ROSTER END DATE= "+enddate);
			calculationRoster.setEndDate(enddate);
			logger.debug("ROSTER CALC SIMP LIST");
			for(CalculationSimple calculationSimple : calcSimpList) {
				logger.debug("ROSTER CALC START DATE= "+calculationSimple.getCalStartDate());
				logger.debug("ROSTER CALC END DATE= "+calculationSimple.getCalEndDate());
				logger.debug("ROSTER COMP AMT= "+calculationSimple.getCompensationAmount());
				logger.debug("ROSTER EMP ID= "+calculationSimple.getEmployee().getId());
				logger.debug("ROSTER RULE ID= "+calculationSimple.getRule().getId());
				CalculationSimple simple = new CalculationSimple();
				simple.setCalStartDate(calculationSimple.getCalStartDate());
				simple.setCalEndDate(calculationSimple.getCalEndDate());
				simple.setCompensationAmount(calculationSimple.getCompensationAmount());
				simple.setEmployee(calculationSimple.getEmployee());
				simple.setRule(calculationSimple.getRule());
				simple.setDummyCalcInternal(dummyCalcInt);
				List<CalcDetailsOrderLineItems> calcDetailsOrderLineItems = new ArrayList<>();
				for(Map.Entry<Employee, Map<OrderLineItemsSplit, Boolean>> entry_main : empSplitQualMap.entrySet()) {
					if(entry_main.getKey().getId() == calculationSimple.getEmployee().getId()) {
						logger.debug("FOR EMP ID= "+entry_main.getKey().getId());
						Map<OrderLineItemsSplit, Boolean> map = entry_main.getValue();
						for(Map.Entry<OrderLineItemsSplit, Boolean> entry : map.entrySet()) {
							CalcDetailsOrderLineItems calcDetailsOrderLineItem = new CalcDetailsOrderLineItems();
							calcDetailsOrderLineItem.setItemsSplit(entry.getKey());
							logger.debug("ORDER LINE ITEM SPLIT ID= "+entry.getKey().getId());
							calcDetailsOrderLineItem.setQualificationFlag(entry.getValue());
							logger.debug("QUALIFICATION FLAG= "+entry.getValue());
							if(entry.getValue() == true) {
								calcDetailsOrderLineItem.setCompensationAmount( ((entry.getKey().getSplitQuantity()/100)*(calculationSimple.getCompensationAmount())) );
								logger.debug("COMP AMOUNT IN CALC_DETAILS_ORDER_LINE_ITEMS= "+((entry.getKey().getSplitQuantity()/100)*(calculationSimple.getCompensationAmount())));
							}else {
								calcDetailsOrderLineItem.setCompensationAmount(0);
								logger.debug("COMP AMOUNT IN CALC_DETAILS_ORDER_LINE_ITEMS= 0");
							}
							
							calcDetailsOrderLineItems.add(calcDetailsOrderLineItem);
						}
						simple.setCalcDetailsOrderLineItemsList(calcDetailsOrderLineItems);
						break;
					}
					
				}
				
				calSimp.add(simple);
				
				
			}
			calculationRoster.setCalcSimpleList(calSimp);
			
			session.merge(calculationRoster);
			tx.commit();
			logger.debug("---SAVED---");
		}catch(HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
	
		}
	}
	
	/**
	 * @param ruleAPI
	 * @param details
	 */
	public  List<QualifyingClause> getSimpRuleDetails( RuleAPI ruleAPI, Rule simpRule) {
		logger.debug("RULE TYPE = SIMPLE");
		RuleSimple ruleSimple = ruleAPI.findSimpleRule(simpRule.getId());
		List<QualifyingClause> clauses = ruleSimple.getQualifyingClause();
		if(clauses != null) {
			logger.debug("---QUALIFYING CLAUSE LIST---");
			printQualClause(clauses);
		}
		return clauses;
	}

	

	public int getParameterValue(String param, long detailsId, long empId, Date ruleCalcStartDate, Date ruleCalcEndDate) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignmentDetails assignmentDetails = (RuleAssignmentDetails)session.get(RuleAssignmentDetails.class, detailsId);
		List<RuleAssignmentParameter> assignmentParameters = assignmentDetails.getRuleAssignmentParameter();
		
		for(RuleAssignmentParameter assignmentParameter :assignmentParameters) {
			if(assignmentParameter.getParameterName().equals(param)) {
				if(assignmentParameter.getValueType().equals("default")) {
					return Integer.parseInt(assignmentParameter.getDefaultValue());
				}else if(assignmentParameter.getValueType().equals("overwrite")) {
					return Integer.parseInt(assignmentParameter.getOverwriteValue());
				}else {
					logger.debug("VALUE TYPE = OVERLAY");
					// get the target value for this parameter based on frequency and dates
					TargetDefinition definition = assignmentParameter.getTargetDefinition();
					List employees = session.createQuery("FROM Employee").list();
					List<Target> empTargetList = new ArrayList<>();
					for(Iterator itr = employees.iterator(); itr.hasNext();) {
						Employee emp = (Employee)itr.next();
						if(emp.getId() == empId) {
							empTargetList = emp.getTarget();
							break;
						}
					}
					if(empTargetList.size() > 0) {
						List<Target> modifiedEmpTargetList = new ArrayList<>();
						for(Target target : empTargetList) {
							TargetDefinition targetDefinition = target.getTargetDefinition();
							if(targetDefinition == definition) {
								modifiedEmpTargetList.add(target);
							}
						}
						if(modifiedEmpTargetList.size() > 0) {
							Frequency assgFreq = assignmentDetails.getFrequency();
							for(Target target : modifiedEmpTargetList) {
								Frequency targetFreq = target.getFrequency();
								Date targetStartDate = target.getStartDate();
								Date targetEndDate = target.getTerminationDate();
								boolean qualified = checkValidityForTarget(assgFreq, targetFreq, targetStartDate, targetEndDate, 
										ruleCalcStartDate, ruleCalcEndDate);
								if(qualified == true) {
									logger.debug("OVERLAY VALUE FOR PARAM= "+target.getValue());
									return target.getValue();
								}
							}
							logger.debug("OVERLAY VALUE FOR PARAM=0 ");
							return 0;
						}else {
							logger.debug("OVERLAY VALUE FOR PARAM= 0");
							return 0;
						}
					}
				}
			}
		}
		return 0;
	}

	private boolean checkValidityForTarget(Frequency assgFreq, Frequency targetFreq, Date targetStartDate,
			Date targetEndDate, Date ruleCalcStartDate, Date ruleCalcEndDate) {
		if(assgFreq == targetFreq) {
			Date TS = targetStartDate;
			logger.debug("TARGET START DATE= "+TS);
			Date TE = targetEndDate;
			logger.debug("TARGET END DATE= "+TE);
			Date RS = ruleCalcStartDate;
			logger.debug("RULE CALC START DATE= "+RS);
			Date RE = ruleCalcEndDate;
			logger.debug("RULE CALC END DATE= "+RE);
			
			if(TS.before(RS) && TE.after(RE)) {
				logger.debug("TS IS BEFORE RS AND TE IS AFTER RE");
				return true;
			}
			else if(TS.after(RS) && TE.before(RE)) {
				logger.debug("TS IS AFTER RS AND TE IS BEFORE RE");
				return true;
			}
			else if(TS.equals(RS) && TE.before(RE)) {
				logger.debug("TS IS EQUAL TO RS AND TE IS BEFORE RE");
				return true;
			}
			else if(TS.after(RS) && TE.equals(RE)) {
				logger.debug("TS IS AFTER RS AND TE IS EQUAL TO RE");
				return true;
			}
			else if(TS.equals(RS) && TE.equals(RE)) {
				logger.debug("TS IS EQUAL TO RS AND TE IS EQUAL TO RE");
				return true;
			}
			else {
				return false;
			}
		}else {
			return false;
		}
		
	}

	public long getAssignmentDetailId(long assgId, Rule satisfiedRule) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		RuleAssignment assignment = (RuleAssignment)session.get(RuleAssignment.class, assgId);
		List<RuleAssignmentDetails> assignmentDetails = assignment.getRuleAssignmentDetails();
		for(RuleAssignmentDetails assignmentDetails2 :assignmentDetails) {
			if(assignmentDetails2.getRule().getRuleName().equals(satisfiedRule.getRuleName())) {
				return assignmentDetails2.getId();
			}
		}
		return 0;
	}
	
	
	public boolean checkLineItemDate(OrderLineItems lineItem, Date ruleCalcStartDate, Date ruleCalcEndDate) {
		logger.debug("CHECK LINE ITEM ID = "+ lineItem.getId());
		OrderDetail orderDetail = new OrderAPI().getOrderDetailFromLineItem(lineItem.getId());
		logger.debug("CHECK ORDER DETAIL ID = "+orderDetail.getId());
		Date lineItemOrderDate = orderDetail.getOrderDate();
		
		
		return ruleCalcStartDate.compareTo(lineItemOrderDate) * lineItemOrderDate.compareTo(ruleCalcEndDate) >= 0;
	}
	
	
	public Object replaceAndCalcCompAmt(ArrayList<Double> paramValues, String formula) throws ScriptException {
		// print paramValues
		logger.debug("---PARAM VALUES----");
		for(int i=0; i<paramValues.size(); i++) {
			logger.debug("paramVal= "+paramValues.get(i));
		}
		
		
		
		// replace parameter and rule output values in formula
		for(int i=0; i< paramValues.size(); i++) {
			String val = String.valueOf(paramValues.get(i));
			 formula= formula.replace("$"+(i+1), val);
		}
		
		//evaluate the formula
			logger.debug("NEW FORMULA= "+formula);
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			Object result = engine.eval(formula);
			logger.debug("COMPENSATION AMOUNT= "+result);
			
			return result;
		
		
	}
	
}

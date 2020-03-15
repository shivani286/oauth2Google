package com.syncgoogle.springoauth2project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDate {

	public static void main(String[] args) throws ParseException {
		{
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate = format.parse("01-03-2020");
			Date endDate = format.parse("31-03-2020");
			List<Date> datesss = new ArrayList<Date>();
			datesss.add(format.parse("01-02-2020"));
			datesss.add(format.parse("01-03-2020"));
			datesss.add(format.parse("04-03-2020"));
			datesss.add(format.parse("06-03-2020"));
			datesss.add(format.parse("16-03-2020"));
			datesss.add(format.parse("17-03-2020"));
			datesss.add(format.parse("18-03-2020"));
			datesss.add(format.parse("19-03-2020"));
			datesss.add(format.parse("31-03-2020"));
			datesss.add(format.parse("19-04-2020"));
			datesss.add(format.parse("20-04-2020"));

			getDatesBetweenUsingJava7(datesss, startDate, endDate);
		}
	}

	private static void getDatesBetweenUsingJava7(List<Date> datesss, Date startDate, Date endDate) {
		datesss.forEach(datess -> {
			if (datess.after(startDate) && datess.before(endDate) || datess.equals(startDate) || datess.equals(endDate) ) {
				System.out.println(datess);
			}
		});
	}
}

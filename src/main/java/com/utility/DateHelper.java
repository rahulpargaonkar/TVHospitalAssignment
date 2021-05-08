package com.utility;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class DateHelper implements JsonDeserializer<Date> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public Date deserialize(JsonElement dateStr, Type typeOfSrc, JsonDeserializationContext context) {
		try {
			return dateFormat.parse(dateStr.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Date> getFormatedDatesForRange(int dateRange) throws ParseException {

		Instant now = Instant.now();
		Instant before = now.minus(Duration.ofDays(dateRange));
		Date fromDate = dateFormat.parse(dateFormat.format(Date.from(before)));
		Date toDate = dateFormat.parse(dateFormat.format(Date.from(now)));
		List<Date> dateList = new ArrayList<>();
		dateList.add(fromDate);
		dateList.add(toDate);
		return dateList;

	}
}
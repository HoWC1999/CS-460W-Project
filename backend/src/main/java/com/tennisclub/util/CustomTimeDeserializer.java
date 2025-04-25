// src/main/java/com/tennisclub/util/CustomTimeDeserializer.java
package com.tennisclub.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTimeDeserializer extends JsonDeserializer<Time> {
  // Define multiple date/time patterns to try
  private static final SimpleDateFormat[] formatters = new SimpleDateFormat[] {
    new SimpleDateFormat("hh:mm a"),  // e.g., "11:11 AM"
    new SimpleDateFormat("HH:mm"),    // e.g., "11:11" in 24-hour format
    new SimpleDateFormat("hh:mm:ss a"), // e.g., "11:11:30 AM"
    new SimpleDateFormat("HH:mm:ss")    // e.g., "11:11:30" in 24-hour format
  };

  @Override
  public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String timeStr = p.getText().trim();
    for (SimpleDateFormat formatter : formatters) {
      try {
        Date parsedDate = formatter.parse(timeStr);
        return new Time(parsedDate.getTime());
      } catch (ParseException e) {
        // Try the next pattern
      }
    }
    throw new RuntimeException("Failed to parse time: " + timeStr);
  }
}

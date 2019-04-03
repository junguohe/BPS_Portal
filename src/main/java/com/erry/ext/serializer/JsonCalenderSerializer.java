package com.erry.ext.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonCalenderSerializer  extends JsonSerializer<java.util.Calendar>{ 

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 

    @Override 
    public void serialize(Calendar calendar, JsonGenerator gen, SerializerProvider provider) 
            throws IOException, JsonProcessingException { 

        String formattedDate = dateFormat.format(calendar.getTime()); 

        gen.writeString(formattedDate); 
    } 

} 

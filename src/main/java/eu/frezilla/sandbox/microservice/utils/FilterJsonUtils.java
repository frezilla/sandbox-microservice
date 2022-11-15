package eu.frezilla.sandbox.microservice.utils;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public final class FilterJsonUtils {
    
    private static class Holder {
        private static final FilterJsonUtils INSTANCE= new FilterJsonUtils(); 
    }
    
    private final FilterProvider filterProvider;
    
    private FilterJsonUtils() {
        this.filterProvider = 
                new SimpleFilterProvider()
                        .addFilter(
                                "productFilter", 
                                SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice")
                        );
    }
    
    public MappingJacksonValue filter(Object object) {
        MappingJacksonValue mjv = new MappingJacksonValue(object);
        mjv.setFilters(filterProvider);
        return mjv;
    }
    
    public static FilterJsonUtils getInstance() {
        return Holder.INSTANCE;
    }
    
}

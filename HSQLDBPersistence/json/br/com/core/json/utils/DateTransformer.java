package br.com.core.json.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import flexjson.JSONException;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class DateTransformer extends AbstractTransformer implements ObjectFactory {

    SimpleDateFormat simpleDateFormatter;

    public DateTransformer(String dateFormat) {
        simpleDateFormatter = new SimpleDateFormat(dateFormat);
    }

    public void transform(Object value) {
        getContext().writeQuoted(simpleDateFormatter.format(value));
    }

    public Object instantiate(ObjectBinder context, Object value, Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {
        try {
	        	if(value==null || value.toString().trim().isEmpty()){
	        		return null;
	    		}else{
	            return simpleDateFormatter.parse( value.toString() );
	    		}
        } catch (ParseException e) {
            throw new JSONException(String.format( "Failed to parse %s with %s pattern.", value, simpleDateFormatter.toPattern() ), e );
        }
    }
}

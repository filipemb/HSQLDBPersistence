package br.com.core.json.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import flexjson.BasicType;
import flexjson.JSONException;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class CalendarDateTransformer extends AbstractTransformer implements ObjectFactory{

	SimpleDateFormat sdf;

	public CalendarDateTransformer(String dateFormat) { 
		sdf=new SimpleDateFormat(dateFormat);
	}

	public void transform(Object o) { 
		
		
//        getContext().writeQuoted(simpleDateFormatter.format(value));

		boolean setContext = false;
		
		TypeContext typeContext = getContext().peekTypeContext(); 
		String propertyName = typeContext != null ? typeContext.getPropertyName() : ""; 
		
		if (typeContext == null || typeContext.getBasicType() != BasicType.OBJECT) { 
			typeContext = getContext().writeOpenObject(); setContext = true;
		}
		
		Calendar c =  (Calendar) o; 
		
		if (!typeContext.isFirst()) 
			getContext().writeComma();
//		typeContext.setFirst(false);
		
		getContext().writeName(propertyName); 
		getContext().writeQuoted(sdf.format(c.getTime()));
		if (setContext) { 
			getContext().writeCloseObject(); 
		} 
	} 

	@Override 
	public Boolean isInline() { 
		return Boolean.TRUE; 
	}

	public Object instantiate(ObjectBinder context, Object value, Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {
        try {
        	String data = value.toString();
        	if (data == null || data.length() == 0) return null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(data));
            return cal;
        } catch (ParseException e) {
            throw new JSONException(String.format( "Failed to parse %s with %s pattern.", value, sdf.toPattern() ), e );
        }
    }
	

	   
}


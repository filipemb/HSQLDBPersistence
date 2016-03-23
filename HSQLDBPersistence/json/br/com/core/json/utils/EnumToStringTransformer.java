package br.com.core.json.utils;

import java.lang.reflect.Type;
import java.util.Arrays;

import flexjson.JSONException;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class EnumToStringTransformer extends AbstractTransformer  implements ObjectFactory {

	@SuppressWarnings("rawtypes")
	public void transform(Object object) {
		getContext().writeQuoted(((Enum) object).toString());
	}


	@Override
	public Object instantiate(final ObjectBinder context, final Object value, final Type targetType,  @SuppressWarnings("rawtypes") final Class targetClass) {
		try {
			
			if(value.toString().isEmpty()){
				return null;
			}
			
			@SuppressWarnings("rawtypes")
			Class c = targetClass;

			for( Object o : Arrays.asList(c.getEnumConstants()) ) {
				if( value.toString().equals(o.toString())){
					return o;
				}
			}
			
			try{
				@SuppressWarnings("unchecked")
				Object object = Enum.valueOf(c, value.toString());
				return object;
			}catch(Exception ex){}
			
			throw new Exception();

		}catch(Exception e){
			
			
			throw new JSONException(String.format("%s:  Don't know how to convert %s to enumerated constant of %s",
					context.getCurrentPath(), value, targetType));
		}


	}

}

package br.com.core.json.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class ConversorUtils {

	@SuppressWarnings("rawtypes")
	public static JSONDeserializer getJsonDeserializer() {
		JSONDeserializer deserializer = new JSONDeserializer();
		deserializer.use(Date.class,new DateTransformer("dd/MM/yyyy"));
		deserializer.use(Calendar.class,new CalendarDateTransformer("dd/MM/yyyy HH:mm:ss"));
		deserializer.use(Integer.class,new NumberJsonToObjectTransformer());
		deserializer.use(int.class,new NumberJsonToObjectTransformer());
		deserializer.use(Integer.TYPE,new NumberJsonToObjectTransformer());
		deserializer.use(Float.class,new NumberJsonToObjectTransformer());
		deserializer.use(float.class,new NumberJsonToObjectTransformer());
		deserializer.use(Float.TYPE,new NumberJsonToObjectTransformer());
		deserializer.use( Double.class, new NumberJsonToObjectTransformer() );
		deserializer.use( double.class, new NumberJsonToObjectTransformer() );
		deserializer.use( Double.TYPE, new NumberJsonToObjectTransformer() );
		deserializer.use( Long.class, new NumberJsonToObjectTransformer() );
		deserializer.use( long.class, new NumberJsonToObjectTransformer() );
		deserializer.use( Long.TYPE, new NumberJsonToObjectTransformer() );
		deserializer.use( BigDecimal.class, new NumberJsonToObjectTransformer() );
		deserializer.use( BigInteger.class, new NumberJsonToObjectTransformer() );
		deserializer.use(Enum.class,new EnumToStringTransformer());
		return deserializer;
	}
	
	public static JSONSerializer getJsonSerializer() {
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude("*.class");
		serializer.transform(new DateTransformer("dd/MM/yyyy"), Date.class);
		serializer.transform(new EnumToStringTransformer(), Enum.class);
		serializer.transform(new CalendarDateTransformer("dd/MM/yyyy HH:mm:ss"), Calendar.class);
		serializer.transform(new NumberJsonToObjectTransformer(),Integer.class);
		serializer.transform(new NumberJsonToObjectTransformer(),int.class);
		serializer.transform(new NumberJsonToObjectTransformer(),Integer.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(), Float.class);
		serializer.transform(new NumberJsonToObjectTransformer(), float.class);
		serializer.transform(new NumberJsonToObjectTransformer(), Float.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(),  Double.class);
		serializer.transform(new NumberJsonToObjectTransformer(), double.class );
		serializer.transform(new NumberJsonToObjectTransformer(), Double.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(), Long.class);
		serializer.transform(new NumberJsonToObjectTransformer(), long.class);
		serializer.transform(new NumberJsonToObjectTransformer(), Long.TYPE );
		serializer.transform(new NumberJsonToObjectTransformer(), BigDecimal.class );
		serializer.transform( new NumberJsonToObjectTransformer(),BigInteger.class );
		return serializer;
	}

	public static JSONSerializer getJsonSerializerForAclClass() {
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("dd/MM/yyyy"), Date.class);
		serializer.transform(new EnumToStringTransformer(), Enum.class);
		serializer.transform(new CalendarDateTransformer("dd/MM/yyyy HH:mm:ss"), Calendar.class);
		serializer.transform(new NumberJsonToObjectTransformer(),Integer.class);
		serializer.transform(new NumberJsonToObjectTransformer(),int.class);
		serializer.transform(new NumberJsonToObjectTransformer(),Integer.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(), Float.class);
		serializer.transform(new NumberJsonToObjectTransformer(), float.class);
		serializer.transform(new NumberJsonToObjectTransformer(), Float.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(),  Double.class);
		serializer.transform(new NumberJsonToObjectTransformer(), double.class );
		serializer.transform(new NumberJsonToObjectTransformer(), Double.TYPE);
		serializer.transform(new NumberJsonToObjectTransformer(), Long.class);
		serializer.transform(new NumberJsonToObjectTransformer(), long.class);
		serializer.transform(new NumberJsonToObjectTransformer(), Long.TYPE );
		serializer.transform(new NumberJsonToObjectTransformer(), BigDecimal.class );
		serializer.transform( new NumberJsonToObjectTransformer(),BigInteger.class );
		return serializer;
	}

}

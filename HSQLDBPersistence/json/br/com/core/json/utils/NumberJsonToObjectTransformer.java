package br.com.core.json.utils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class NumberJsonToObjectTransformer extends AbstractTransformer implements ObjectFactory{

	public Object instantiate(ObjectBinder context, Object value, Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {

		//    	System.out.println(value.toString());

		if(value.toString().equals("")) {
			return null;
		}else {

			String classType = obterTipoDeClasse(targetClass.toString());
			if(classType.equals("integer")) {
				return  Integer.valueOf(value.toString());

			}else if(classType.equals("int")) {
				return  Integer.valueOf(value.toString());

			}else if(classType.equals("float")) {
				value = value.toString().replaceAll("\\.", "");
				value = value.toString().replace(",",".");
				return  Float.valueOf(value.toString());

			}else if(classType.equals("double")) {
				value = value.toString().replaceAll("\\.", "");
				value = value.toString().replace(",",".");
				return  Double.valueOf(value.toString());

			}else if(classType.equals("long")) {
				return  Long.valueOf(value.toString());

			}else if(classType.equals("bigdecimal")) {
//				value = value.toString().replaceAll("\\.", "");
//				value = value.toString().replace(",",".");
//				return  BigDecimal.valueOf( Double.valueOf(value.toString()));
				return StringUtils.stringToBigDecimal(value.toString());

			}else if(classType.equals("biginteger")) {
				value = value.toString().replaceAll("\\.", "");
				value = value.toString().replace(",",".");
				return  BigInteger.valueOf(Long.valueOf(value.toString()));

			}else if(classType.equals("short")) {
				return  Short.valueOf(value.toString());

			}else if(classType.equals("byte")) {
				return  Byte.valueOf(value.toString());

			}else {
//				System.out.println("Nao foi possivel identificar o tipo de numero para deserializar o objeto Json");
				value.toString().replaceAll("\\.", "");
				value.toString().replace(",",".");
				return   Double.valueOf(value.toString());
			}

		}
	}

	@Override
	public void transform(Object object) {

		String classType = obterTipoDeClasse(object.getClass().getCanonicalName());
		if(classType.equals("float")) {
			getContext().writeQuoted(object.toString().replace(".", ","));
		}else if(classType.equals("double")) {
			getContext().writeQuoted(object.toString().replace(".", ","));
		}else if(classType.equals("bigdecimal")) {
			getContext().writeQuoted(StringUtils.bigDecimalToString((BigDecimal)object));
		}else if(classType.equals("biginteger")) {
			getContext().writeQuoted(object.toString().replace(".", ","));
		}else {
			getContext().writeQuoted(object.toString().replace(".", ","));
		}
		
	}

	public static String obterTipoDeClasse(String classe) {

		String[] partes = classe.split("\\.");
		String ultimoCampo = partes[partes.length-1];

		return ultimoCampo.toLowerCase();

	}

}


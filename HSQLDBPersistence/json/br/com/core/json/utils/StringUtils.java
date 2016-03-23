package br.com.core.json.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class StringUtils {


	public static BigDecimal reaisToBigDecimal(String reais){
		
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(new Locale ("pt", "BR"));
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		
//		DecimalFormat decimalFormat=(DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); <--- erra
		DecimalFormat decimalFormat= new DecimalFormat("#,##0.0#", symbols);
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setMinimumFractionDigits(2);
		
		try {
			return (BigDecimal) decimalFormat.parse(reais.replace("R$", "").trim());
		} catch (ParseException e) {e.printStackTrace(); return null;}
		
	}

	public static String stringToReais(String unformatted, boolean comSimbolo){
		
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(new Locale ("pt", "BR"));
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');

		if(!comSimbolo){
			symbols.setCurrencySymbol("");
		}
		
		DecimalFormat decimalFormat=(DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); 
//		DecimalFormat decimalFormat= new DecimalFormat("#,##0.0#", symbols); <-- erra
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setMinimumFractionDigits(2);
		
		return decimalFormat.format(unformatted).trim();

	}

	public static String bigDecimalToReais(BigDecimal number, boolean comSimbolo){
		
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(new Locale ("pt", "BR"));
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');

		if(!comSimbolo){
			symbols.setCurrencySymbol("");
		}
		
		DecimalFormat decimalFormat=(DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); 
//		DecimalFormat decimalFormat= new DecimalFormat("#,##0.0#", symbols); <-- erra
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setMinimumFractionDigits(2);
		
		return decimalFormat.format(number).trim();

	}

	public static String bigDecimalToString(BigDecimal number){
		
		DecimalFormatSymbols symbols=new DecimalFormatSymbols(new Locale ("pt", "BR"));
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("");
		
		DecimalFormat decimalFormat=(DecimalFormat) NumberFormat.getNumberInstance(new Locale("pt", "BR")); 
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(10);
		
		return decimalFormat.format(number).trim();
	}
	
	public static BigDecimal stringToBigDecimal(String numeroStr){

		DecimalFormatSymbols symbols=new DecimalFormatSymbols(new Locale ("pt", "BR"));
		symbols.setDecimalSeparator(',');
		symbols.setCurrencySymbol("");

		DecimalFormat decimalFormat= new DecimalFormat("#,##0.0#", symbols);
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setGroupingUsed(false);
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.setMinimumFractionDigits(2);
		try {
			return (BigDecimal) decimalFormat.parse(numeroStr.replace("R$", "").trim());
		} catch (ParseException e) {e.printStackTrace(); return null;}

	}

}

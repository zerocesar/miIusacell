package mx.com.iusacell.services.miiusacell.masivo.security;

import java.util.Calendar;


public abstract class GeneraToken {

	
	
	public static String generaToken(final String palabra){
		String cadenaRegresa = "";
		
		Calendar calendario = Calendar.getInstance();
		int ano=calendario.get(Calendar.YEAR);
		int mes=calendario.get(Calendar.MONTH);
		int dia = calendario.get(Calendar.DATE);
		int hora =calendario.get(Calendar.HOUR_OF_DAY);
		int minuto = calendario.get(Calendar.MINUTE);
		
		
		mes = mes + 1;
		String anoS = Integer.toString(ano);
		String mesS = Integer.toString(mes);
		String diaS = Integer.toString(dia);
		String horaS = Integer.toString(hora);
		String minutoS = Integer.toString(minuto);
		if(diaS.length() == 1){
			diaS = "0"+diaS;
		}
		if(mesS.length() == 1){
			mesS = "0"+mesS;
		}
		if(minutoS.length() == 1){
			minutoS = "0"+minutoS;
		}
		if(horaS.length() == 1){
			horaS = "0"+horaS;
		}
		
		
		
		cadenaRegresa = diaS + mesS + anoS + horaS + minutoS + palabra;
		
		
		
		return cadenaRegresa;
	}
	
}

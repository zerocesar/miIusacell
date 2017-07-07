package mx.com.iusacell.services.miiusacell.arquitectura.autorizador;

import mx.com.iusacell.services.miiusacell.vo.ReferenciaBancariaVO;

import org.apache.commons.lang3.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenciaBancaria {
	
	public static void main(String[] args)
    {
		String cta = "1.7401420";
		System.out.println(ReferenciaBancaria.bancoAzteca(cta));
        System.out.println(ReferenciaBancaria.banamex(cta));
        System.out.println(ReferenciaBancaria.bancomer(cta));
        System.out.println(ReferenciaBancaria.hsbc(cta));
        System.out.println(ReferenciaBancaria.banorte(cta));
        System.out.println(ReferenciaBancaria.scotiabank(cta));
        System.out.println(ReferenciaBancaria.santander(cta));
    }
	
	public static String getDigitoVerificador(final String referencia) {        
		String digitoVerificador = "";
		if(StringUtils.isNotEmpty(referencia) && StringUtils.isNumeric(referencia)){
			char[] charArray = referencia.toCharArray();
			int j = 1;
			int suma = 0;
			while (j <= charArray.length) {
				int valor = new Integer(charArray[j - 1] + "").intValue();
				if (j % 2 == 0) {
					if (valor * 2 >= 10) {
						char[] tmp = new Integer(valor * 2 + "").toString().toCharArray();
						suma += new Integer(tmp[0] + "").intValue();
						suma += new Integer(tmp[1] + "").intValue();
					} else {
						suma += valor * 2;
					}
				} else {
					suma += valor;
				}
				j++;
			}
			charArray = new Integer(suma).toString().toCharArray();
			if (charArray.length > 0) {
				if (new Integer(charArray[charArray.length - 1] + "").intValue() == 0) {
					digitoVerificador = "0";
				} else {
					digitoVerificador = 10 - new Integer(charArray[charArray.length - 1] + "").intValue() + "";
				}
			}
		}
		return digitoVerificador;
	}
	
	public static String getReferencia(final String cuenta){
		final StringBuffer referencia = new StringBuffer();
		
		if(StringUtils.isNotEmpty(cuenta)){
			final int longReferencia = 16;
			final String refCta = cuenta.trim().replace(".", "");
			final int longCta = refCta.length();
			final int totalCeros = longReferencia - longCta;
			if(totalCeros > 0){		
				for(int pos = 0; pos < totalCeros; pos++){
					referencia.append("0");
				}
			}
			referencia.append(refCta);
		}
		
		return referencia.toString();
	}	
	
	 public static String bancoAzteca(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "bancoazteca");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String banamex(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "banamex");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String bancomer(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "bbva");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String hsbc(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "hsbc");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String banorte(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "banorte");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String scotiabank(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "scotiabank");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    public static String santander(final String numeroDeCuenta){
	        ReferenciaBancariaVO vo = generaReferenciaBancaria(numeroDeCuenta, "santander");
	        return vo!=null?vo.getReferenciaBancaria():"";
	    }
	    
	    public static ReferenciaBancariaVO bancoAztecaVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "bancoazteca");
	    }
	    public static ReferenciaBancariaVO banamexVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "banamex");
	    }
	    public static ReferenciaBancariaVO bancomerVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "bbva");
	    }
	    public static ReferenciaBancariaVO hsbcVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "hsbc");
	    }
	    public static ReferenciaBancariaVO banorteVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "banorte");
	    }
	    public static ReferenciaBancariaVO scotiabankVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "scotiabank");
	    }
	    public static ReferenciaBancariaVO santanderVO(final String numeroDeCuenta){
	        return generaReferenciaBancaria(numeroDeCuenta, "santander");
	    }
	    
	    private static ReferenciaBancariaVO generaReferenciaBancaria(String numeroDeCuenta, final String banco)
	    {
	        final ReferenciaBancariaVO referencia = new ReferenciaBancariaVO();
	        
	        final Integer  _digitoVeficador = Genera_Digito(numeroDeCuenta);
	        final Integer  _tipoDeCuenta    = tipoDeCuenta(numeroDeCuenta);
	        
	        if(esCadenaValida(_tipoDeCuenta, numeroDeCuenta))
	        {
	            numeroDeCuenta = numeroDeCuenta.replace(".", "");
	            
	            if(banco.equalsIgnoreCase("bancoazteca"))
	            {
	                referencia.setConvenio("0000");
	                referencia.setReferenciaBancaria("0000" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) + _digitoVeficador);
	                referencia.setBanco("Banamex");
	            }
	            else if(banco.equalsIgnoreCase("banamex"))
	            {
	                referencia.setConvenio("131001");
	                referencia.setReferenciaBancaria(calculaReferenciaBanamex(numeroDeCuenta,Long.parseLong("131001")));
	                referencia.setBanco("Banamex");
	            }
	            else if(banco.equalsIgnoreCase("bbva"))
	            {
	                referencia.setConvenio("878944");
	                referencia.setReferenciaBancaria("878944" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) + _digitoVeficador);
	                referencia.setBanco("BBVA Bancomer");
	            }
	            else if(banco.equalsIgnoreCase("hsbc"))
	            {
	                referencia.setConvenio("55035632");
	                referencia.setReferenciaBancaria("55035632" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) + _digitoVeficador);
	                referencia.setBanco("HSBC");
	            }
	            else if(banco.equalsIgnoreCase("banorte"))
	            {
	                referencia.setConvenio("12833");
	                referencia.setReferenciaBancaria("12833" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) +" " + _digitoVeficador);
	                referencia.setBanco("Banorte");
	            }
	            else if(banco.equalsIgnoreCase("scotiabank"))
	            {
	                referencia.setConvenio("3364");
	                referencia.setReferenciaBancaria("3364" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) + _digitoVeficador);
	                referencia.setBanco("Scotiabank");
	            }
	            else if(banco.equalsIgnoreCase("santander"))
	            {
	                referencia.setConvenio("1563");
	                referencia.setReferenciaBancaria("1563" + " " + String.format("%016d",Long.parseLong(numeroDeCuenta)) + " " +_digitoVeficador);
	                referencia.setBanco("Santander");
	            }
	        }
	        
	        return referencia;
	    }
	    
	    public static String calculaReferenciaBanamex(String pv_refbanamex,long conv2){
	    	String  refi="";
	    	if(StringUtils.isNotEmpty(pv_refbanamex)){
	    		pv_refbanamex=pv_refbanamex.replace(".","");
	    		long ref=conv2;
	    		String lv_referencia;
	    		String lv_cadena_uno;
	    		String lv_custcode_sp;
	    		int ln_len=0;
	    		int ln_suma=0;
	    		int ln_modulo=0;
	    		int ln_numero=0;
	    		String lv_ponderadores="11,13,17,19,23,11,13,17,19,23,11,13,17,19,23,11,13,17,19,23,11,13,17,19,23,";
	    		int ln_resultado=0;
	    		int ln_ponderador=0;
	    		int ln_digito=0;
	    		String lv_digito;
	    		String lv_referencia_sal;
	    		if(pv_refbanamex.length()>16){pv_refbanamex=pv_refbanamex.substring(0,16);}
	    		lv_referencia=String.format("%06d", ref);
	    		lv_custcode_sp=String.format("%016d", Long.parseLong(pv_refbanamex));
	    		lv_custcode_sp=lv_custcode_sp+"0";
	    		lv_cadena_uno  =  lv_referencia + lv_custcode_sp;
	    		ln_len=lv_cadena_uno.length();


	    		for(int i = ln_len; i > 0; i--)
	    		{
	    			ln_numero =Integer.parseInt(lv_cadena_uno.substring(i-1,i));
	    			ln_resultado=0; 
	    			ln_ponderador=0;
	    			String var;
	    			var=",";
	    			ln_ponderador=Integer.parseInt(lv_ponderadores.substring(0,lv_ponderadores.indexOf(var))); 
	    			ln_resultado= ln_numero * ln_ponderador;
	    			lv_ponderadores=lv_ponderadores.substring(lv_ponderadores.indexOf(var) + var.length(),lv_ponderadores.length());
	    			ln_suma = ln_suma + ln_resultado;

	    		}
	    		ln_modulo = ln_suma%97;
	    		ln_digito =  ln_modulo + 1;
	    		lv_digito =String.format("%02d",ln_digito);
	    		lv_referencia_sal= lv_cadena_uno + lv_digito;


	    		String numeros = "4,2,4,4,4,4,1,2";
	    		String[] numerosComoArray = numeros.split(",");	    		

	    		int ban=0;
	    		for (int x = 0; x < numerosComoArray.length; x++) 
	    		{
	    			String lv_referencia_sal2=lv_referencia_sal.substring(ban,Integer.parseInt(numerosComoArray[x])+ ban);
	    			ban=ban+Integer.parseInt(numerosComoArray[x]);
	    			refi= refi + lv_referencia_sal2 + " ";
	    		}
	    		refi= refi.substring(0, refi.length()-1); 
	    		refi="B: " + refi;
	    	}
	        return refi;
	      }

	    private static Integer tipoDeCuenta(final String numeroDeCuenta)
	    {
	        Integer tipocta = 0;
	        if(numeroDeCuenta.indexOf(".") == 1)
	        {
	            tipocta = Integer.parseInt(numeroDeCuenta.substring(0, numeroDeCuenta.indexOf(".")));
	        }
	        else
	        {
	            tipocta = Integer.parseInt(numeroDeCuenta.substring(0, 1));
	        }

	        return tipocta;
	    }

	    private static boolean esCadenaValida(final Integer tipoDeCuenta, String numeroDeCuenta)
	    {
	        boolean esCadenaValida = false;
	        int longitudCuenta;

	        if(tipoDeCuenta == 1)
	        {
	            Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{1,18}");
	            Matcher mat = pat.matcher(numeroDeCuenta);
	            if(mat.matches())
	            {
	                if(numeroDeCuenta.length() > 16)
	                {
	                    numeroDeCuenta = numeroDeCuenta.replace(".", "");
	                    numeroDeCuenta = numeroDeCuenta.substring(0, 16);
	                }
	                esCadenaValida = true;
	            }
	        }
	        else if(tipoDeCuenta == 2)
	        {
	            longitudCuenta = numeroDeCuenta.length();
	            if(longitudCuenta == 4)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 7)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{2,2}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 10)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{2,2}\\.\\d{2,2}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 17)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{2,2}\\.\\d{2,2}\\.\\d{2,2}.\\d{6,6}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	        }
	        else if(tipoDeCuenta == 3)
	        {
	            longitudCuenta = numeroDeCuenta.length();
	            if(longitudCuenta == 5)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{3,3}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 8)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{3,3}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 11)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{3,3}\\.\\d{2,2}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 18)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{3,3}\\.\\d{2,2}\\.\\d{2,2}.\\d{6,6}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	        }
	        else if(tipoDeCuenta == 4)
	        {
	            longitudCuenta = numeroDeCuenta.length();
	            if(longitudCuenta == 6)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{4,4}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 9)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{4,4}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 12)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{4,4}\\.\\d{2,2}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 19)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{4,4}\\.\\d{2,2}\\.\\d{2,2}.\\d{6,6}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	        }
	        else if(tipoDeCuenta == 5)
	        {
	            longitudCuenta = numeroDeCuenta.length();
	            if(longitudCuenta == 7)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{5,5}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 10)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{5,5}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 13)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{5,5}\\.\\d{2,2}\\.\\d{2,2}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	            else if(longitudCuenta == 20)
	            {
	                Pattern pat = Pattern.compile("\\d{1,1}\\.\\d{5,5}\\.\\d{2,2}\\.\\d{2,2}.\\d{6,6}");
	                Matcher mat = pat.matcher(numeroDeCuenta);
	                if(mat.matches())
	                {
	                    esCadenaValida = true;
	                }
	            }
	        }

	        return esCadenaValida;
	    }

	    private static Integer Genera_Digito(String pv_referencia)
	    {
	        int ln_digito = 0;
	        int ln_len = 0;
	        String lv_referencia;
	        int ln_pivote = 1;
	        int ln_num1;
	        int ln_num2;
	        int ln_acumula = 0;

	        pv_referencia = pv_referencia.replace(".", "");
	        ln_len = pv_referencia.length();
	        for(int i = ln_len; i > 0; i--)
	        {
	            pv_referencia = String.valueOf(pv_referencia);
	            lv_referencia = pv_referencia.substring(i - 1, i);
	            ln_num1 = Integer.parseInt(lv_referencia);
	            if(ln_pivote == 1)
	            {
	                ln_pivote = 2;
	            }
	            else
	            {
	                ln_pivote = 1;
	            }
	            
	            ln_num2 = ln_pivote * ln_num1;
	            if(ln_num2 == 18)
	            {
	                ln_num2 = 9;
	            }
	            else if(ln_num2 == 16)
	            {
	                ln_num2 = 7;
	            }
	            else if(ln_num2 == 14)
	            {
	                ln_num2 = 5;
	            }
	            else if(ln_num2 == 12)
	            {
	                ln_num2 = 3;
	            }
	            else if(ln_num2 == 10)
	            {
	                ln_num2 = 1;
	            }

	            ln_acumula = ln_acumula + ln_num2;
	        }
	        if(ln_acumula > 0)
	        {
	            ln_digito = 10 - (ln_acumula % 10);
	        }
	        
	        if(ln_digito == 10)
	        {
	            ln_digito = 0;
	        }
	        
	        return ln_digito;
	    }
}

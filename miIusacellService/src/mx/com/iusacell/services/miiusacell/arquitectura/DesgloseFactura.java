package mx.com.iusacell.services.miiusacell.arquitectura;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.SOAPManager.SOAPManagerQA;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoParserVO;
import mx.com.iusacell.services.miiusacell.vo.DetalleSaldoVO;
import mx.com.iusacell.services.miiusacell.vo.EstadoCuentaVO;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.Formatter;
import mx.com.iusacell.services.miusacell.util.ParseHtmlFile;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class DesgloseFactura {
	
	public static EstadoCuentaVO flujo(final String dn, final String facturaUrl) throws ServiceException
	{
		String sResponse = "";
		EstadoCuentaVO response = new EstadoCuentaVO();
		SOAPManagerQA soapManager = new SOAPManagerQA();
		CallServiceServiciosContratos serviciosContratados =  new CallServiceServiciosContratos();
		String noContrato ="";
		
		try {
			if(facturaUrl == null || facturaUrl.trim().equals(""))
				throw new ServiceException("la factura no puede ir vacio");
			

			
			/*Produccion*/
			soapManager.createSOAPManager(facturaUrl, new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML("", new MensajeLogBean());

			/* DESARROLLO */
            //sResponse = getResponseFromFile(dn);			
			
			
			response = ParseHtmlFile.parseFactura(sResponse);
			
//			if(response.getNoCuenta() != null && !response.getNoCuenta().equals("")){
//	            sResponse = serviciosContratados.getResultadoBusqueda(response.getNoCuenta());
//	            noContrato= ParseXMLFile.parseResultadoBusqueda(sResponse);
//	            
//	            if(noContrato != null &&  !noContrato.equals(""))
//			    	response.setNoContrato(noContrato);
//			}
            
		}
		catch (Exception ex)
		{
			throw new ServiceException(ex.getMessage());
		}
		return response;
	}
	
	public static DetalleSaldoVO flujoSaldo(final String dn, final String facturaUrl) throws ServiceException
	{
		String sResponse = "";
		DetalleSaldoParserVO respuestaParser = new DetalleSaldoParserVO();
		DetalleSaldoVO respuesta = new DetalleSaldoVO();
		DetalleSaldoVO response = new DetalleSaldoVO();
		SOAPManagerQA soapManager = new SOAPManagerQA();
		
		double saldoAbonado = 0.00;
		String responseTotalaPagar = "";
		String responseSaldoaPagar = "";
		String responseRedondeo = "";
		String pendientePago = "0.00";
		double totalaPagar = 0;
		double saldoaPagar = 0;
		double ajusteRedondeo = 0;
		double totalPendientePago = 0;
		
		try {
			if(facturaUrl == null || facturaUrl.trim().equals(""))
				throw new ServiceException("la factura no puede ir vacio");
			
			/* PRODUCCION */
			soapManager.createSOAPManager(facturaUrl, new MensajeLogBean());
			sResponse = soapManager.sendRequestWithXML("", new MensajeLogBean());

			/* DESARROLLO */
            //sResponse = getResponseFromFile(dn);			
			
			respuestaParser = ParseHtmlFile.parseFacturaSaldos(sResponse);
			
			respuesta.setO1saldoAnterior(respuestaParser.getO1saldoAnterior());
			respuesta.setO2suPagoGracias(respuestaParser.getO2suPagoGracias());
			respuesta.setO3ajustesCuenta(respuestaParser.getO3ajustesCuenta());
			respuesta.setO4totalFacturaActual(respuestaParser.getO4totalFacturaActual());
			respuesta.setO5ajusteRedondeo(respuestaParser.getO5ajusteRedondeo());
			respuesta.setO6totalPagar(respuestaParser.getO6totalPagar());
						
			responseTotalaPagar = respuesta.getO6totalPagar();
			responseRedondeo = respuesta.getO5ajusteRedondeo();
			
			//Saldo a pagar
			responseSaldoaPagar = ObtieneSaldoAPagar.flujo(dn);
			try
			{
				responseTotalaPagar = responseTotalaPagar.trim().replace("$", "").replace(",", "");
				totalaPagar = Double.parseDouble(responseTotalaPagar);

			}
			catch (NumberFormatException e) {
				throw new ServiceException(e.getLocalizedMessage());
			}
			catch (Exception e) {
				throw new ServiceException(e.getLocalizedMessage());
			}
			
			try
			{
				responseSaldoaPagar = responseSaldoaPagar.trim().replace("$", "").replace(",", "");
				saldoaPagar = Double.parseDouble(responseSaldoaPagar);
			}
			catch (NumberFormatException e) {
				throw new ServiceException(e.getLocalizedMessage());
			}
			catch (Exception e) {
				throw new ServiceException(e.getLocalizedMessage());
			}
			
			try
			{
				if(responseRedondeo != null && !responseRedondeo.equals(""))
				{
					responseRedondeo = responseRedondeo.trim().replace("$", "").replace(",", "");
					responseRedondeo = eliminaCaracteresEspeciales(responseRedondeo);
					ajusteRedondeo = Double.parseDouble(responseRedondeo);
				}				
			}
			catch (NumberFormatException e) {
				ajusteRedondeo = 0;
			}
			
			if(totalaPagar > saldoaPagar)
			{
				
				saldoAbonado = totalaPagar - (saldoaPagar + ajusteRedondeo);
				totalPendientePago = totalaPagar - saldoAbonado;
				
				pendientePago = String.valueOf(totalPendientePago);
//				double diferencia= 0.00;
//				boolean aplicaRedondeoUp = false;
//				diferencia = (totalaPagar - ajusteRedondeo);
//				saldoAbonado = diferencia - saldoaPagar;
//				
//				aplicaRedondeoUp = DesgloseFactura.redondearUp(saldoAbonado);
//				
//				if(aplicaRedondeoUp)
//					saldoAbonado = redondeaArriba(saldoAbonado, 2);
//				else					
//					saldoAbonado = redondeaAbajo(saldoAbonado, 2);
//				
//				totalPendientePago = totalaPagar - saldoAbonado;
//				aplicaRedondeoUp = DesgloseFactura.redondearUp(totalPendientePago);
//				if(aplicaRedondeoUp)
//					totalPendientePago = redondeaArriba(totalPendientePago, 2);
//				else					
//					totalPendientePago = redondeaAbajo(totalPendientePago, 2);
//				
			}else pendientePago = String.valueOf(saldoaPagar); 
			
			String pagado = String.valueOf(saldoAbonado);
			pagado = formateadorMoneda(pagado);
						
			response.setO1saldoAnterior(eliminaCaracteresEspeciales(respuesta.getO1saldoAnterior()));
			response.setO2suPagoGracias(eliminaCaracteresEspeciales(respuesta.getO2suPagoGracias()));
			response.setO3ajustesCuenta(eliminaCaracteresEspeciales(respuesta.getO3ajustesCuenta()));
			response.setO4totalFacturaActual(eliminaCaracteresEspeciales(respuesta.getO4totalFacturaActual()));
			response.setO5ajusteRedondeo(eliminaCaracteresEspeciales(respuesta.getO5ajusteRedondeo()));
			response.setO6totalPagar(eliminaCaracteresEspeciales(respuesta.getO6totalPagar()));
			
			response.setO7pagadoSupuesto(pagado);
			
			pendientePago = formateadorMoneda(pendientePago);
			response.setO8pendientePago(pendientePago);
            
		}
		catch (Exception ex)
		{
			throw new ServiceException(ex.getMessage());
		}
		return response;
	}
	
	public static String getResponseFromFile(final String dn)
	{		
		StringBuilder xmlFile = new StringBuilder();
		FileReader f = null;
		String archivo = "C:\\facturadetalle.txt";
		try {
			f = new FileReader(archivo);
			BufferedReader read = new BufferedReader(f);
			String linea;
			while((linea=read.readLine())!=null)
			{
				xmlFile.append(linea.trim());
			}
		} catch (FileNotFoundException e) {
		    Logger.write("DesgloseFactura.getResponseFromFile :: (FileNotFoundException) :: " + e.getMessage());
//			e.printStackTrace();
		} catch (IOException e) {
		    Logger.write("DesgloseFactura.getResponseFromFile :: (IOException) :: " + e.getMessage());
//			e.printStackTrace();
		}
		finally
		{
			if(f != null)
			{
				try {
					f.close();
				} catch (IOException e) {
				    Logger.write("DesgloseFactura.getResponseFromFile :: (IOException 2) :: " + e.getMessage());
//					e.printStackTrace();
				}
			}
		}
		
		return xmlFile.toString();
	}
	
	public static double redondeaAbajo(final double numero, final int decimales) 
	{ 
	  double resultado;
	  BigDecimal res;

	  res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_DOWN);
	  resultado = res.doubleValue();
	  return resultado; 
	}  
	
	public static double redondeaArriba(final double numero, final int decimales) 
	{ 
	  double resultado;
	  BigDecimal res;

	  res = new BigDecimal(numero).setScale(decimales, BigDecimal.ROUND_UP);
	  resultado = res.doubleValue();
	  return resultado; 
	}  
	
	public static String eliminaCaracteresEspeciales(String cadena)
	{				
		try
		{
			String proc = java.text.Normalizer.normalize(cadena,java.text.Normalizer.Form.NFD);
			StringBuilder sb = new StringBuilder();
			for (char c : proc.toCharArray()) {
			  if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {
			    sb.append(c);
			  }
			}
			cadena = sb.toString();
		}
		catch (Exception e) {		
			Logger.write(e.getLocalizedMessage());
		}				
		
		return cadena;
	}

	public static String formateadorMoneda(final String formato)
	{
		String respuesta = "";
		try
		{
			Double formatoDoble = Double.parseDouble(formato);
			DecimalFormat formatDecimal = new DecimalFormat("$###,###,###.00");
			respuesta = formatDecimal.format(formatoDoble);
		}
		catch (Exception e) {
			respuesta = formato;
		}
		return respuesta.toString();
	}
	
	public static boolean redondearUp(final double numero)
	{
		boolean redondeaUp = false;
		String numeroC = Double.toString(numero);
		numeroC = numeroC.substring(numeroC.indexOf(".")+1);
		if(numeroC.startsWith("000"))
			redondeaUp = false;
		else
			redondeaUp = true;		
		
		return redondeaUp;
		
	}

}

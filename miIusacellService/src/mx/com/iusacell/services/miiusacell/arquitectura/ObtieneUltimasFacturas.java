package mx.com.iusacell.services.miiusacell.arquitectura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.masivo.model.OracleProcedures;
import mx.com.iusacell.services.miiusacell.masivo.security.DESAlgorithm;
import mx.com.iusacell.services.miiusacell.vo.DatosSQLDispatcherVO;
import mx.com.iusacell.services.miiusacell.vo.DatosUltimasFacturasVO;
import mx.com.iusacell.services.miusacell.call.CallServiceServiciosContratos;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ObtieneUltimasFacturas {
	
	public static List<DatosUltimasFacturasVO> flujo(final String dn,final String numeroFacturas) throws ServiceException
	{
		List<DatosUltimasFacturasVO> response = new ArrayList<DatosUltimasFacturasVO>();
		CallServiceServiciosContratos service = new CallServiceServiciosContratos();
		OracleProcedures oracle = new OracleProcedures();
		DatosSQLDispatcherVO data = new DatosSQLDispatcherVO();
		SimpleDateFormat month= new SimpleDateFormat("MM");
		SimpleDateFormat year= new SimpleDateFormat("yyyy");
		String sResponse = "";
		
		String originalData = "";
		String datosFactura = "";
		String datosFacturaPart[] = null;
		String url = "";
		String usr = "";
		String pwd = "";
		String strURL = "";
		Date datecon = null;
		
		if(dn == null || dn.equals("")){
			throw new ServiceException("DN no puede ir vacio");
		}
		
		try
		{
			
			try{
				datosFactura = oracle.getValorParametro(115);
				datosFacturaPart = datosFactura.split("\\|");
				url = datosFacturaPart[0];
				usr = datosFacturaPart[1];
				pwd = datosFacturaPart[2];
			}catch(Exception ex){
				url = "https://iusacellfactura.interfactura.com/?";
				usr = "consulta";
				pwd = "asdfg123";
			}
			
			sResponse = service.getStatusLineaPospago(dn);
			if(sResponse != null && !sResponse.equals(""))
				data = ParseXMLFile.parseCustomerIdCustCode(sResponse);
			
			if(data != null && data.getCustCode() != null){
				String newCustCode = "";
				String[] puntos = data.getCustCode().split("\\.");
				if(puntos.length >= 3){
					if(puntos[2] != null && puntos[2].trim().equals("00"))
						newCustCode = puntos[0] + "." + puntos[1];					
					else					
						newCustCode = puntos[0] + "." + puntos[1] + "." + puntos[2] ;
					
					data.setCustCode(newCustCode);
				}
			}
				
			if(data.getCustCode() != null && data.getCustomerId() != null){
				sResponse = service.getUltimasFacturas(data.getCustomerId(), numeroFacturas);
				if(sResponse != null && !sResponse.equals("")){
					response = ParseXMLFile.parseUltimasFacturas(sResponse);
				}

				if(response.size() == 0){
					sResponse = service.getDatosCliente(data.getCustomerId());
					if(sResponse != null && !sResponse.equals("")){
						data.setCustomerId(ParseXMLFile.parseGetDatosCliente(sResponse));
					}
					if(data.getCustomerId() != null && !data.getCustomerId().equals("")){
						sResponse = service.getUltimasFacturas(data.getCustomerId(), numeroFacturas);
						if(sResponse != null && !sResponse.equals("")){
							response = ParseXMLFile.parseUltimasFacturas(sResponse);
						}
					}
				}

				if(response != null && response.size()>0){
					for(int i=0; i<response.size(); i++){
						if(!response.get(i).getTotalPagar().equals("0")){
							datecon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH).parse(response.get(i).getFechaCorte());
							originalData = "user="+usr+"&password="+pwd+"&key2="+data.getCustCode()+"&key4="+month.format(datecon)+"&key5="+year.format(datecon);
							strURL = url + DESAlgorithm.getInstance().encrypt64(originalData);
							response.get(i).setLink(strURL);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		return response;
	}
}

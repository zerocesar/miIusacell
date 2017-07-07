package mx.com.iusacell.services.miiusacell.arquitectura;

import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.CatalogoCaeGeneralVO;
import mx.com.iusacell.services.miusacell.call.CallObtieneCaes;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ObtieneCaes {
		
	public static List<CatalogoCaeGeneralVO> flujo(final int canal,final int idAplicacion ) throws ServiceException{
			
		CallObtieneCaes obtieneCaes =  new CallObtieneCaes();
		List<CatalogoCaeGeneralVO> respuesta = new ArrayList<CatalogoCaeGeneralVO>();
			String obtieneListaCaes = "";
			Logger.write("ObtieneCaes");
			
			try{
			if(canal < 0)
			{				 
				throw new ServiceException("El canal no puede ir vacio");
			}
			if(idAplicacion < 0)
			{				 
				throw new ServiceException("El idAplicacion no puede ir vacio");
			}

			obtieneListaCaes = obtieneCaes.callObtieneListaVersionCaeS(canal,idAplicacion);
			if(obtieneListaCaes != "" || obtieneListaCaes != null){
				respuesta = ParseXMLFile.parseObtieneCaes(obtieneListaCaes);
			}
			}catch (Exception e) {
				throw new ServiceException(e.getMessage());
			}
			
			return respuesta;
		}
}

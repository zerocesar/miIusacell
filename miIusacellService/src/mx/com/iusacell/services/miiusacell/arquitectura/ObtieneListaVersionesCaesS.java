package mx.com.iusacell.services.miiusacell.arquitectura;

import javax.xml.rpc.ServiceException;

import mx.com.iusacell.services.miiusacell.vo.CatalogoMovilCaeVO;
import mx.com.iusacell.services.miusacell.call.CallObtieneListaVersionCaeS;
import mx.com.iusacell.services.miusacell.util.ParseXMLFile;

public abstract class ObtieneListaVersionesCaesS {
	
public static CatalogoMovilCaeVO flujo(final int canal,final int idAplicacion,final int version ) throws ServiceException{
		
	CallObtieneListaVersionCaeS obtieneListaV =  new CallObtieneListaVersionCaeS();
		CatalogoMovilCaeVO respuesta = new CatalogoMovilCaeVO();
		String obtieneLista = "";
		Logger.write("ObtieneListaVersionesCaesS");
		
		//folio vacio y nulo, llamar call
		try{
		if(canal < 0)
		{				 
			throw new ServiceException("El canal no puede ir vacio");
		}
		if(idAplicacion < 0)
		{				 
			throw new ServiceException("El idAplicacion no puede ir vacio");
		}
		if(version < 0)
		{				 
			throw new ServiceException("El version no puede ir vacio");
		}

		obtieneLista = obtieneListaV.callObtieneListaVersionCaeS(canal,idAplicacion,version);
		if(obtieneLista != "" || obtieneLista != null){
			respuesta = ParseXMLFile.parseObtieneListaVersionCaeS(obtieneLista);
		}
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return respuesta;
	}
}

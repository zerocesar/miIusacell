package mx.com.iusacell.services.miiusacell.vo.autorizador;

import java.io.Serializable;
import java.util.List;

public class TarjetasItemVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<CardsVO> itemM;

	public List<CardsVO> getItemM() {
		return itemM;
	}
	
	public  void setItemM(List<CardsVO> itemM) {
		this.itemM=itemM;
	}
	
}

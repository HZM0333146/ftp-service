package idv.hzm.simpleftp.common.molde;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFileMolde implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3306279689767731904L;
	
	public List<FileMolde> fileMoldeList = null;
	
	public AllFileMolde(List<FileMolde> fileMoldeList){
		this.fileMoldeList = new ArrayList<>();
		this.fileMoldeList.addAll(fileMoldeList);
	}

	public List<FileMolde> getFileMoldeList() {
		return fileMoldeList;
	}

	public void setFileMoldeList(List<FileMolde> fileMoldeList) {
		this.fileMoldeList = fileMoldeList;
	}
	
	public void showList() {
		for( FileMolde fileMolde : fileMoldeList) {
			System.out.println(fileMolde);
		}
	}

	@Override
	public String toString() {
		return "AllFileMolde [fileMoldeList=" + fileMoldeList + "]";
	}
	
}

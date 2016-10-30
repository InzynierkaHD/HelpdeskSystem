package pl.helpdesk.filefinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Klasa służąca do szukania plików w katalogach
 * @author Krzysztof Krocz
 *
 */
public class FileFinder {

	private String folderPath;
	private File folder;
	

	
	public FileFinder(String folderPath){
		this.folderPath = folderPath;
		this.folder = new File(folderPath);
	}
	
	public List<File> getAllFilesFromFolder(){
		List<File> fileList = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  fileList.add(listOfFiles[i]);
	      }
	    }
		return fileList;
	}
	
	/**
	 * Wyszukuje znaki występujące przed separatorem i porownuje z wzorcem
	 * 
	 * @param separator separator
	 * @param pattern wzorzec
	 * @return wszytkie pliki pasujace do wzorca
	 */
	public List<File> getAllFilesFromFolderBeforeSeparator(String separator,String pattern){
		List<File> fileList = new ArrayList<File>();
		String[] split;
		File[] listOfFiles = folder.listFiles();
		if( listOfFiles != null){
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  split = listOfFiles[i].getName().split(separator);
	    	  if(pattern.equals(split[0]))
	    	  fileList.add(listOfFiles[i]);
	      }
	    }
		}
		return fileList;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	
	
	
}

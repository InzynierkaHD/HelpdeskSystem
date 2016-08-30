package pl.helpdesk.components;

import java.io.Serializable;
import java.util.HashMap;

import pl.helpdesk.api.INavbarComponent;

/**
 * Dropdown dla navbara
 * 
 * @author Krzysztof Krocz
 *
 */
public class Dropdown implements INavbarComponent, Serializable{

	private String name;
	private HashMap<String, String> subOptions = new HashMap<String, String>();
	private StringBuilder finalHtml = new StringBuilder();

	/**
	 * 
	 * @param name
	 *            nagłówek dropdowna
	 * @param subNames
	 *            lista opcji
	 */
	public Dropdown(String name, HashMap<String, String> subOptions) {
		this.name = name;
		this.subOptions = subOptions;
		finalHtml.append("<li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">"
				+ this.name + "<span class=\"caret\"></span></a>");
		finalHtml.append("<ul class=\"dropdown-menu\">");
		for (HashMap.Entry<String, String> entry : subOptions.entrySet())
		{
			finalHtml.append(" <li><a href=\""+""+entry.getValue()+"\">" + entry.getKey() + "</a></li>");
	
		}
		finalHtml.append(" </ul></li>");
	}

	@Override
	public String getHtml() {
		return finalHtml.toString();
	}

}

package pl.helpdesk.components;

import java.util.LinkedList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import pl.helpdesk.api.INavbarComponent;

/**
 * Klasa reprezentujaca pasek nawigacyjny u góry strony
 * 
 * @author Krzysztof Krocz
 *
 */
public class Navbar extends Panel{

	
	private static final long serialVersionUID = 1L;
	
	private String brandName = "";
	private String optionsHtml = "";
	private LinkedList<INavbarComponent> navComponent;
	 
	public Navbar(String id) {
		super(id);
		addComponents();
	}
	
	/**
	 * 
	 * @param id wicket id Komponentu
	 * @param brandName nagłówek navbara
	 * @param navComp lista opcji które mogą znajdować się w navbarze
	 */
	public Navbar(String id,String brandName,LinkedList<INavbarComponent> navComp) {
		super(id);
		this.brandName = brandName;
		this.navComponent = navComp;
		if(navComponent != null)
		for(INavbarComponent component : navComponent){
			optionsHtml+=component.getHtml();
		}
		addComponents();
		
	}
	
	private void addComponents(){
		add(new Label("brand", brandName).setEscapeModelStrings(false));
		add(new Label("options", optionsHtml).setEscapeModelStrings(false));
	}

}

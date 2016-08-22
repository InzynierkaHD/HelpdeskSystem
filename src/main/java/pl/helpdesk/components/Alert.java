package pl.helpdesk.components;

import java.util.ArrayList;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;


public class Alert extends Panel implements IMarkupResourceStreamProvider{
	  
	ArrayList<Link> buttonList;
	StringBuilder html = new StringBuilder();
	private static final long serialVersionUID = 1L;

	public Alert(String id,ArrayList<Link> buttonList) {
		super(id);
		this.buttonList = buttonList;
		setVisible(false);
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		
		html.append("<div id=\"myModal\" class=\"modal fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" >");
		html.append("<div class=\"modal-dialog\">");
		html.append("<div class=\"modal-content\">");
		html.append("<div class=\"modal-header alert-error\">");
		html.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		html.append("<h3 id=\"myModalLabel\" class=\"modal-title\">Modal Heading</h3></div>");
		html.append("<div class=\"modal-body \">");
		html.append("<h4>Text in a modal</h4></div>");
		html.append("<div class=\"modal-footer\">");
		html.append("<button type=\"button\" class=\"btn\" data-dismiss=\"modal\">Close</button>");
		if(buttonList != null)
		for(Link link : buttonList){
			add(link);
			html.append("<button type=\"button\" class=\"btn\" data-dismiss=\"modal\" wicket:id=\""+link.getId()+"\">"+link.getId()+"</button>");
		}
		html.append("</div></div></div></div><script type=\"text/javascript\">$(window).load(function(){$('#myModal').modal('show');});</script>");
		return new StringResourceStream(html.toString());
		
		
	}
	
	

	
}

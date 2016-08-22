package pl.helpdesk.components;

import java.util.ArrayList;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;


public class Alert extends Panel implements IMarkupResourceStreamProvider{
	   
	private ArrayList<Link> buttonList;
	private StringBuilder html = new StringBuilder();
	private String alertHead;
	private Label alertBody;
	
	public enum typeAlert {
	    info,error,warning
	}
	private typeAlert type;
	
	private static final long serialVersionUID = 1L;

	public Alert(String id,ArrayList<Link> buttonList,typeAlert type,String alertHead,Label alertBody) {
		super(id);
		this.buttonList = buttonList;
		this.type = type;
		this.alertHead = alertHead;
		this.alertBody = alertBody;
		this.alertBody.setEscapeModelStrings(false);
		setVisible(false);
	}
	
	public Alert(String id,typeAlert type,String alertHead,Label alertBody) {
		super(id);
		this.alertHead = alertHead;
		this.type = type;
		this.alertBody = alertBody;
		this.alertBody.setEscapeModelStrings(false);
		setVisible(false);
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		
		html.append("<wicket:panel><div id=\"myModal\" class=\"modal fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" >");
		html.append("<div class=\"modal-dialog\">");
		html.append("<div class=\"modal-content\">");
		html.append("<div class=\"modal-header alert-"+type+"\">");
		html.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		html.append("<h3 id=\"myModalLabel\" class=\"modal-title ");
		if(type == typeAlert.info)
		html.append("glyphicon glyphicon-info-sign");
		if(type == typeAlert.warning)
		html.append("glyphicon glyphicon-alert");
		if(type == typeAlert.error)
			html.append("glyphicon glyphicon-exclamation-sign");
		html.append("\"> "+alertHead+"</h3></div>");
		if(alertBody != null){
		add(alertBody);
		html.append("<div class=\"modal-body \" wicket:id=\""+alertBody.getId()+"\">");
		}
		html.append("</div>");
		html.append("<div class=\"modal-footer\">");
		if(buttonList != null)
		for(Link link : buttonList){
			add(link);
			html.append("<button type=\"button\" class=\"btn\" data-dismiss=\"modal\" wicket:id=\""+link.getId()+"\">"+link.getId()+"</button>");
		}
		html.append("</div></div></div></div><script type=\"text/javascript\">$(window).load(function(){$('#myModal').modal('show');});</script></wicket:panel>");
		return new StringResourceStream(html.toString());
		
		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
	    super.renderHead(response);
	    response.render(CssHeaderItem.forReference(new CssResourceReference(Alert.class, "Alert.css")));
	
	}

	
}

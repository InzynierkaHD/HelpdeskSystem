package pl.helpdesk.panels.issue;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import pl.helpdesk.api.IInfoSlider;

public class InfoSlider extends Panel implements IInfoSlider{
	
	private List<String> listOfDisplayData;
	private String header;
	private boolean down = false;

	public InfoSlider(String id,String header,List<String> listOfDisplayData) {
		super(id);
		this.header = header;
		this.listOfDisplayData = listOfDisplayData;
		prepareSlider();
	}
	
	private void prepareSlider(){
		Label headerLabel = new Label("header",new PropertyModel<String>(this,"header"));
		headerLabel.add(new AjaxEventBehavior("onclick") {
			
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				if(down == false){
				target.appendJavaScript(" $(\"#infoBody\").slideDown();");
				down = true;
				return;
				}
				if(down == true){
					target.appendJavaScript(" $(\"#infoBody\").slideUp();");
					down = false;
					return;
				}
			}
		});
		this.add(headerLabel);
		RepeatingView content = new RepeatingView("content");
		for(String value : listOfDisplayData){
		content.add(new Label(content.newChildId(),Model.of(value)));
		}
		add(content);
		}

	@Override
	public void slideUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void slideDown() {
		// TODO Auto-generated method stub
		
	}

}

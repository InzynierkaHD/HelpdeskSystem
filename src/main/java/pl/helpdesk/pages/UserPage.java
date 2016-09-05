package pl.helpdesk.pages;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.userSession.ApplicationSession;


public class UserPage extends ClientSuccessPage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserPage(PageParameters parameters){
		super(parameters);
		String result = ""; 
		String result2= "";
		result = String.valueOf(ApplicationSession.getInstance().getUser().getLogin());
		
		result2=String.valueOf(ApplicationSession.getInstance().getAttribute("id"));
		add(new Label("SendedParam",result)); 
		add(new Label("SendedParam2",result2)); 
		Form<?> form = new Form<Void>("form") ;
		Button logOut=new Button("logOut"){
			
			private static final long serialVersionUID = -1800911970905016411L;
			@Override
			public void onSubmit() {
				super.onSubmit();
				ApplicationSession.getInstance().invalidate();
				setResponsePage(LoginPage.class); 
			}
		};
		
		add(form);

		
		form.add(logOut);
	}


}

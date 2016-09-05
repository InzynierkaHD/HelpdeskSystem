package pl.helpdesk.pages;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.AlertModal.typeAlert;
import pl.helpdesk.forms.AddIssueForm;

public class MyIssue extends ClientSuccessPage{

	private static final long serialVersionUID = 1L;
	private AjaxLink<String> addIssue;
	private AlertModal alert;
	private AddIssueForm addIssueForm;
	
	public MyIssue(PageParameters parameters){
		super(parameters);
		addIssueForm = new AddIssueForm("form");
		alert = new AlertModal("alert",new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie", new StringBuilder("<div wicket:id=\"form\"></div>"));
		alert.add(addIssueForm);
		//alert.show(new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie", new StringBuilder("dziala"));
		add(alert);
		alert.setVisible(true);
		add(addIssue = new AjaxLink<String>("addIssue"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript("$('#myModal').modal('show');");
				
			}

			
			
		});
	}
	
}

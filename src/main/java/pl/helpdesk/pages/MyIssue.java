package pl.helpdesk.pages;

import java.util.ArrayList;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.AlertModal.typeAlert;
import pl.helpdesk.forms.AddIssueForm;

public class MyIssue extends SuccessPage{

	private static final long serialVersionUID = 1L;
	private Link addIssue;
	private AlertModal alert;
	private AddIssueForm addIssueForm;
	
	public MyIssue(PageParameters parameters){
		super(parameters);
		addIssueForm = new AddIssueForm("form");
		alert = new AlertModal("alert",new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie", new StringBuilder("<div wicket:id=\"form\"></div>"));
		alert.add(addIssueForm);
		//alert.show(new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie", new StringBuilder("dziala"));
		add(alert);
		add(addIssue = new Link("addIssue"){

			@Override
			public void onClick() {
				alert.setVisible(true);
				//alert.show(new ArrayList<AjaxLink>(), typeAlert.info, "Dodaj zgłoszenie", new StringBuilder("dziala"));
				//target.appendJavaScript("$(\"#myModal\").modal(\"toggle\");");
				//alert.add(new AddIssueForm("form"));
			}
			
		});
	}
	
}

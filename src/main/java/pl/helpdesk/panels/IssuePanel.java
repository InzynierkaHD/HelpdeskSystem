package pl.helpdesk.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

public class IssuePanel extends Panel{

	private static final long serialVersionUID = 1L;
	
	private RepeatingView clientComments;
	
	public IssuePanel(String id) {
		super(id);
		clientComments = new RepeatingView("clientComments");
		clientComments.add(new ClientComment(clientComments.newChildId(),"head","content"));
		add(clientComments);
		add(new AjaxLink("backToIssues"){

			@Override
			public void onClick(AjaxRequestTarget target) {
				target.appendJavaScript(" $(\"#issuePanel\").slideUp();");
				target.appendJavaScript(" $(\"#addIssueButton\").slideDown();");	
				target.appendJavaScript(" $(\"#myIssuesTable\").slideDown();");	
			}
			
		});
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(IssuePanel.class, "issuePanel.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}
	
	@Override
	protected void onBeforeRender() {
		System.out.println("before ");
		super.onBeforeRender();
	}

}

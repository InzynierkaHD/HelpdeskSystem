package pl.helpdesk.components;

import java.util.ArrayList;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

/**
 * Klasa dla alertów i dialogów
 * 
 * @author Krzysztof Krocz
 *
 */
public class AlertModal extends Panel implements IMarkupCacheKeyProvider,IMarkupResourceStreamProvider {

	private static final long serialVersionUID = 1L;
	private StringBuilder html = new StringBuilder();
	private ArrayList<AjaxLink> buttonList = new ArrayList<AjaxLink>();
	private String alertHead = "";
	private StringBuilder htmlBody;
	private typeAlert type;

	public enum typeAlert {
		info, error, warning, success
	}

	public AlertModal(String id) {
		super(id);
		setVisible(false);
	}

	/**
	 * 
	 * @param id
	 * @param buttonList
	 *            lista buttonów dołączonych do modala
	 * @param type
	 *            typ okna (warning,info,error)
	 * @param alertHead
	 *            nagłówek okna
	 * @param alertBody
	 *            treść okna (wyświetlana w postaci html)
	 */
	public AlertModal(String id, ArrayList<AjaxLink> buttonList, typeAlert type, String alertHead,
			StringBuilder stringBody) {
		super(id);
		this.buttonList = buttonList;
		this.type = type;
		this.alertHead = alertHead;
		this.htmlBody = stringBody;
		for (AjaxLink link : buttonList) {
			add(link);
		}
		setVisible(false);
	}

	/**
	 * 
	 * @param id
	 * @param type
	 *            typ okna (warning,info,error)
	 * @param alertHead
	 *            nagłówek okna
	 * @param alertBody
	 *            treść okna (wyświetlana w postaci html)
	 */
	public AlertModal(String id, typeAlert type, String alertHead, StringBuilder stringBody) {
		super(id);
		this.type = type;
		this.alertHead = alertHead;
		this.htmlBody = stringBody;
		buttonList.add(new AjaxLink("Close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
			}
		});
		add(buttonList.get(0));
		setVisible(false);
	}

	public void show(ArrayList<AjaxLink> buttonList, typeAlert type, String alertHead, StringBuilder stringBody) {
		this.setAlertHead(alertHead);
		this.setHtmlBody(stringBody);
		this.setButtonList(buttonList);
		this.setType(type);
		setVisible(true);
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		html.append(
				"<wicket:panel><div id=\"myModal\" class=\"modal fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" >");
		html.append("<div class=\"modal-dialog\">");
		html.append("<div class=\"modal-content\">");
		html.append("<div id=\"" + type + "\" class=\"modal-header alert-" + type + "\">");
		html.append(
				"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>");
		html.append("<h3 id=\"myModalLabel\" class=\"modal-title ");
		if (type == typeAlert.info)
			html.append("glyphicon glyphicon-info-sign");
		if (type == typeAlert.warning)
			html.append("glyphicon glyphicon-alert");
		if (type == typeAlert.error)
			html.append("glyphicon glyphicon-exclamation-sign");
		if (type == typeAlert.success)
			html.append("glyphicon glyphicon-ok");
		html.append("\"> " + alertHead + "</h3></div>");
		html.append("<div class=\"modal-body \">");
		html.append(htmlBody);
		html.append("</div>");
		if (!buttonList.isEmpty()) {
			html.append("<div class=\"modal-footer\">");
			for (AjaxLink link : buttonList) {
				html.append(
						"<button onclick=\"closeAlert()\" type=\"button\" class=\"btn btn-default navbar-inverse\" data-dismiss=\"modal\" wicket:id=\""
								+ link.getId() + "\">" + link.getId() + "</button>");
			}
			html.append("</div>");
		}
		html.append(
				"</div></div></div><script type=\"text/javascript\">$(window).load(function(){$('#myModal').modal('hide');});"
						+ " function closeAlert() {$('#myModal').modal('hide');}</script></wicket:panel>");
		return new StringResourceStream(html.toString());
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		PackageResourceReference cssFile = new PackageResourceReference(this.getClass(), "AlertModal.css");
		CssHeaderItem cssItem = CssHeaderItem.forReference(cssFile);

		response.render(cssItem);
	}

	@Override
	protected void onAfterRender() {
		getApplication().getResourceSettings().getPropertiesFactory().clearCache();
		super.onAfterRender();
	}

	@Override
	protected void onBeforeRender() {
		getApplication().getResourceSettings().getPropertiesFactory().clearCache();
		super.onBeforeRender();
	}

	public ArrayList<AjaxLink> getButtonList() {
		return buttonList;
	}

	public void setButtonList(ArrayList<AjaxLink> buttonList) {
		this.buttonList = buttonList;
		for (AjaxLink link : buttonList) {
			this.add(link);
		}
	}

	public String getAlertHead() {
		return alertHead;
	}

	public StringBuilder getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(StringBuilder htmlBody) {
		this.htmlBody = htmlBody;
	}

	public void setAlertHead(String alertHead) {
		this.alertHead = alertHead;
	}

	public typeAlert getType() {
		return type;
	}

	public void setType(typeAlert type) {
		this.type = type;
	}

	@Override
	public String getCacheKey(MarkupContainer container, Class<?> containerClass) {
		// TODO Auto-generated method stub
		return null;
	}

}

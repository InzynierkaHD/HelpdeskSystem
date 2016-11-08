package pl.helpdesk.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public class AdminEmployeeList extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	private User userDataModel;

	private static final List<String> SORTUJ = Arrays
			.asList(new String[] { "Nazwisko", "E-mail", "Ostatnie logowanie", "Blokowany" });

	private String selected= "Nazwisko";
	
	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IUserDao userDao;

	public AdminEmployeeList(PageParameters parameters) {
		super(parameters);

		if (!(ApplicationSession.getInstance().getUser() == null)
				&& adminDao.isAdmin(ApplicationSession.getInstance().getUser())) {

			userDataModel = new User();
			final SelectForm sortBy = new SelectForm("sortowanie", new PropertyModel<String>(this, "selected"), SORTUJ);

			final TextField<String> serachingSurname = new TextField<String>("serachingSurname",
					new PropertyModel<String>(userDataModel, "nazwisko"));
			Form<?> form = new Form<Void>("form") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit() {

					PageParameters parameterss = new PageParameters();
					String sortByOK = sortBy.getValue();
					parameterss.set("sortBy", sortByOK);
					String serachingSurnameOK = serachingSurname.getInput();
					parameterss.set("serachingSurname", serachingSurnameOK);
					setResponsePage(AdminEmployeeList.class, parameterss);
				}
			};

			add(form);
			form.add(sortBy);
			form.add(serachingSurname);

			WebMarkupContainer datacontainer = new WebMarkupContainer("data");
			datacontainer.setOutputMarkupId(true);
			add(datacontainer);

			String sorting = "";
			if (parameters.get("sortBy").isEmpty()) {
				sorting = "Nazwisko";
			} else {
				sorting = String.valueOf(parameters.get("sortBy"));
			}

			String surnamee = "";
			if (parameters.get("serachingSurname").isEmpty()) {
				surnamee = "";
			} else {
				surnamee = String.valueOf(parameters.get("serachingSurname"));
			}
			
			datacontainer.add(new Label("liczbaPracownikow", employeeDao.numOfEmpl(surnamee)));
			PageableListView<?> pageableListView = new PageableListView<Employee>("lista",
					employeeDao.getSortedEmployees(sorting, surnamee), 8) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<Employee> item) {
					final Employee employee = (Employee) item.getModelObject();

					item.add(new Label("imie", employee.getUserDataModel().getImie()));
					item.add(new Label("nazwisko", employee.getUserDataModel().getNazwisko()));
					item.add(new Label("email", employee.getUserDataModel().getEmail()));
					item.add(new Label("ostatnie_logowanie", employee.getUserDataModel().getOst_logowanie()));

					final Label blokujWyswietl = new Label("blokujWyswietl", new AbstractReadOnlyModel<String>() {

						private static final long serialVersionUID = 1L;

						@Override
						public String getObject() {
							if (employee.getUserDataModel().getCzy_blokowany()) {
								return "Odblokuj";
							} else {
								return "Zablokuj";
							}
						}
					});

					final Link zablokujUsera = new Link<Object>("zablokujUsera") {

						private static final long serialVersionUID = 1L;

						@Override
						public void onClick() {
							if (employee.getUserDataModel().getCzy_blokowany()) {
								employee.getUserDataModel().setCzy_blokowany(false);
								userDao.update(employee.getUserDataModel());
							} else {
								employee.getUserDataModel().setCzy_blokowany(true);
								userDao.update(employee.getUserDataModel());
							}
						}
					};
					item.add(zablokujUsera.add(blokujWyswietl));

					Label usunWyswietl = new Label("usunWyswietl", "Usuń");
					Label Usuniety = new Label("czyUsuniety", "Usunięty");
					Usuniety.setVisible(false);

					final Link usunUsera = new Link<Object>("usunUsera") {

						private static final long serialVersionUID = 1L;

						@Override
						public void onClick() {
							employee.getUserDataModel().setCzy_usuniety(true);
							userDao.update(employee.getUserDataModel());
						}
					};
					usunUsera.setVisible(false);
					item.add(usunUsera.add(usunWyswietl));
					item.add(Usuniety);
					if (employee.getUserDataModel().getCzy_usuniety()) {
						zablokujUsera.setVisible(false);
						Usuniety.setVisible(true);
					} else {
						usunUsera.setVisible(true);
						zablokujUsera.setVisible(true);
					}
				}

			};
			datacontainer.add(pageableListView);
			datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
			datacontainer.setVersioned(false);
		} else {
			setResponsePage(LoginPage.class);
		}
	}

}

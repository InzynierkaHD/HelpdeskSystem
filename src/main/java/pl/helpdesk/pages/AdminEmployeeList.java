package pl.helpdesk.pages;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.Employee;

public class AdminEmployeeList extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	private IUserDao userDao;
	
	public AdminEmployeeList(PageParameters parameters) {
		super(parameters);

		WebMarkupContainer datacontainer = new WebMarkupContainer("data");
        datacontainer.setOutputMarkupId(true);
        add(datacontainer);
        
        PageableListView<?> pageableListView=new PageableListView<Employee>("lista", employeeDao.getAll(), 8) {
			
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Employee> item) {
		        final Employee employee = (Employee) item.getModelObject();
        
		        	item.add(new Label("imie", employee.getUserDataModel().getImie()));
		        	item.add(new Label("nazwisko", employee.getUserDataModel().getNazwisko()));
		        	item.add(new Label("email", employee.getUserDataModel().getEmail()));
		        	item.add(new Label("ostatnie_logowanie", employee.getUserDataModel().getOst_logowanie()));
		        	
		        	
		        	final Label blokujWyswietl = new Label("blokujWyswietl", new AbstractReadOnlyModel<String>() {
						/**
						 * 
						 */
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
}
}


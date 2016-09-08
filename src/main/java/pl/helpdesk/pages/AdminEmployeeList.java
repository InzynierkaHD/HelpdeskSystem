package pl.helpdesk.pages;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.entity.Employee;

public class AdminEmployeeList extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private IEmployeeDao employeeDao;


	
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
		        Employee employee = (Employee) item.getModelObject();
		        	item.add(new Label("imie", employee.getUserDataModel().getImie()));
		        	item.add(new Label("nazwisko", employee.getUserDataModel().getNazwisko()));
		        	item.add(new Label("email", employee.getUserDataModel().getEmail()));
		        	item.add(new Label("login", employee.getUserDataModel().getLogin()));
		        	item.add(new Label("ostatnie_logowanie", employee.getUserDataModel().getOst_logowanie()));
		    }
		};
        datacontainer.add(pageableListView);
        datacontainer.add(new AjaxPagingNavigator("nav", pageableListView));
        datacontainer.setVersioned(false);
}
}


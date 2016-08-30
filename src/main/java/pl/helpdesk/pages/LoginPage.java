package pl.helpdesk.pages;

import java.util.ArrayList;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.components.AlertModal;
import pl.helpdesk.components.AlertModal.typeAlert;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 1L;


	
	@SpringBean
	private IUserDao userSpring;
	// private CompanyDao companyDao;

	private User userDataModel = new User();

	public LoginPage() {
		//System.out.println(userSpring.getUser("Login").getNazwisko());
		final TextField<String> login = new TextField<String>("login",
				new PropertyModel<String>(userDataModel, "login"));
		
		PasswordTextField haslo = new PasswordTextField("haslo", new PropertyModel<String>(userDataModel, "haslo"));

		final Label badInfo = new Label("message", "Złe dane!");
		badInfo.setVisible(Boolean.FALSE);
		
		final Label userBlocked = new Label("blocked", "Konto zablokowane!");
		userBlocked.setVisible(Boolean.FALSE);
		
		

		Form<?> form = new Form<Void>("form") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {

				super.onSubmit();
				User findedUser = userSpring.findUserByLoginAndPassword(userDataModel.getLogin(), userDataModel.getHaslo());
				if(findedUser != null)
				{
					ApplicationSession.getInstance().setUser(findedUser);
					setResponsePage(UserFinalPage.class);
				}
				else System.out.println("Bledne dane");
				
				/*userDao = new UserDao();
				badInfo.setVisible(Boolean.FALSE);
				userBlocked.setVisible(Boolean.FALSE);
				try {
					if ((userDao.findUserByLoginAndPassword(userDataModel.getLogin(), userDataModel.getHaslo())
							.equals("none"))) {
						badInfo.setVisible(Boolean.TRUE);
					} else if(userDao.czyBlokowany(userDataModel.getLogin())==true){						
						userBlocked.setVisible(Boolean.TRUE);
					} else {
						UserSession.getInstance().setuSerDataModel(userDataModel);
						UserSession.getInstance().setAttribute("id",
								userDao.findUserId(UserSession.getInstance().getuSerDataModel().getLogin()));
						UserSession.getInstance().setAttribute("blok",
								userDao.czyBlokowany(UserSession.getInstance().getuSerDataModel().getLogin()));
						if (userDao.userType(UserSession.getInstance().getuSerDataModel().getLogin()) == 4) {
							setResponsePage(AdminPage.class);
						} else if (userDao.userType(UserSession.getInstance().getuSerDataModel().getLogin()) == 1){
							setResponsePage(EmployeePage.class);
						} else {
							setResponsePage(UserPage.class);
						}
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}*/

			}

		};
		form.add(login);
		form.add(haslo);
		add(form);
		add(badInfo);
		add(userBlocked);
	}
}

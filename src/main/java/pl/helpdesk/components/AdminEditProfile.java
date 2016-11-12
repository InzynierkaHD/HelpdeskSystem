package pl.helpdesk.components;

import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;
import pl.helpdesk.validation.Validation;


public class AdminEditProfile extends Panel{


	@SpringBean
	private IUserDao userDao;

	private User user;
	private String userLogin;
	private String userEmail;

	final Label badEmail;
	final Label loginLength;
	final Label passLength;
	final Label userExist;
	final Label emailExist;
	final Label emaillOK;
	final Label loginOK;
	
	final Form<AdminEditProfile> form;
	
	private static final long serialVersionUID = 1L;
	

	public AdminEditProfile(String id, int userId) {
		super(id);
		
		user = userDao.getById(userId);
		userLogin = user.getLogin();
		userEmail = user.getEmail();
		
		badEmail = new Label("bademail", "Wpisz poprawnie email!");
		loginLength = new Label("loginlength", "Login musi zawierac od 4 do 15 znaków!");
		passLength = new Label("passlength", "Hasło musi zawierac od 4 do 15 znaków!");
		userExist = new Label("userExist", "Użytkownik o podanym loginie istnieje w systemie!");
		emailExist = new Label("emailExist", "Użytkownik o podanym adresie istnieje w systemie!");
		emaillOK = new Label("emaillOK", "Zmieniono e-mail!");
		loginOK = new Label("loginOK", "Zmieniono login!");

		badEmail.setVisible(false);
		emaillOK.setVisible(false);
		loginLength.setVisible(false);
		loginOK.setVisible(false);
		passLength.setVisible(false);
		userExist.setVisible(false);
		emailExist.setVisible(false);

		form = new Form<AdminEditProfile>("form", new CompoundPropertyModel<>(this));
		addComponents();
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		badEmail.setVisible(false);
		emaillOK.setVisible(false);
		loginLength.setVisible(false);
		loginOK.setVisible(false);
		userExist.setVisible(false);
		emailExist.setVisible(false);
		try {
			if (userLogin.length() < 4 || userLogin.length() > 15 || userLogin.isEmpty()) {
				loginLength.setVisible(true);
			} else if (userDao.loginExist(userLogin)) {
				userExist.setVisible(true);
			} else {
				user.setLogin(userLogin);
				userDao.update(user);
				this.userLogin = userLogin;
				loginOK.setVisible(true);
			}
		} catch (RuntimeException e) {
			loginLength.setVisible(true);
		}
		setResponsePage(getPage());
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		badEmail.setVisible(false);
		emaillOK.setVisible(false);
		loginLength.setVisible(false);
		loginOK.setVisible(false);
		passLength.setVisible(false);
		userExist.setVisible(false);
		emailExist.setVisible(false);
		try {
			if (Validation.emailValidate(userEmail) == false) {
				badEmail.setVisible(true);
			} else if (userDao.emailExist(userEmail)) {
				emailExist.setVisible(true);
			} else if (userEmail.equals("")) {
				badEmail.setVisible(true);
			} else {
				user.setEmail(userEmail);
				userDao.update(user);
				this.userEmail = userEmail;
				emaillOK.setVisible(true);
			}
		} catch (RuntimeException e) {
			badEmail.setVisible(true);
		}
		setResponsePage(getPage());
	}
	
	
	
	private void addComponents(){
		add(form);
		form.add(new Label("userName", user.getImie()));
		form.add(new Label("userSurname", user.getNazwisko()));
		form.add(new AjaxEditableLabel<Object>("userLogin"));
		form.add(new AjaxEditableLabel<Object>("userEmail"));
		form.add(userExist);
		form.add(loginLength);
		form.add(emailExist);
		form.add(badEmail);
		form.add(emaillOK);
		form.add(loginOK);
		
	}
}

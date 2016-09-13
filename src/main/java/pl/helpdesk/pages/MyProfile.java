package pl.helpdesk.pages;

import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;
import pl.helpdesk.validation.Validation;

public class MyProfile extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserDao userDao;

	User user;
	private String userLogin;
	private String userEmail;

	final Label badEmail;
	final Label loginLength;
	final Label passLength;
	final Label userExist;
	final Label emailExist;
	final Label emaillOK;
	final Label loginOK;

	public MyProfile(PageParameters parameters) {
		super(parameters);
		
		user = userDao.getById(ApplicationSession.getInstance().getUser().getId());
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

		Form form = new Form("form", new CompoundPropertyModel<>(this));
		add(form);
		form.add(new Label("userName", ApplicationSession.getInstance().getUser().getImie()));
		form.add(new Label("userSurname", ApplicationSession.getInstance().getUser().getNazwisko()));
		form.add(new AjaxEditableLabel<Object>("userLogin"));
		form.add(new AjaxEditableLabel<Object>("userEmail"));
		form.add(userExist);
		form.add(loginLength);
		form.add(passLength);
		form.add(emailExist);
		form.add(badEmail);
		form.add(emaillOK);
		form.add(loginOK);

	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		badEmail.setVisible(false);
		emaillOK.setVisible(false);
		loginLength.setVisible(false);
		loginOK.setVisible(false);
		passLength.setVisible(false);
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
				ApplicationSession.getInstance().getUser().setLogin(userLogin);
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
				ApplicationSession.getInstance().getUser().setEmail(userEmail);
				this.userEmail = userEmail;
				emaillOK.setVisible(true);
			}
		} catch (RuntimeException e) {
			badEmail.setVisible(true);
		}
		setResponsePage(getPage());
	}

}
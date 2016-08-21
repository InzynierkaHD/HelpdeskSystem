package pl.helpdesk.pages;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pl.helpdesk.dao.UserDao;
import pl.helpdesk.entity.User;
import pl.helpdesk.userSession.ApplicationSession;

public class AddWorkerPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddWorkerPage() {
		
		String result = String.valueOf(ApplicationSession.getInstance().getUser().getLogin());
		Form<?> form = new Form<Void>("form");
		Button logOut = new Button("logOut") {

			private static final long serialVersionUID = -1800911970905016411L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				UserDao userDao= new UserDao();
				userDao.closeConection();
				ApplicationSession.getInstance().invalidate();
				setResponsePage(LoginPage.class);
			}
		};
		add(form);
		form.add(logOut);

		// String message="error";

		final Label badName = new Label("badname", "Wpisz poprawnie imię!");
		badName.setVisible(false);
		final Label badSurname = new Label("badsurname", "Wpisz poprawnie nazwisko!");
		badSurname.setVisible(false);
		final Label badEmail = new Label("bademail", "Wpisz poprawnie email!");
		badEmail.setVisible(false);
		final Label loginLength = new Label("loginlength", "Login musi zawierac od 4 do 15 znaków!");
		loginLength.setVisible(false);
		final Label passLength = new Label("passlength", "Hasło musi zawierac od 4 do 15 znaków!");
		passLength.setVisible(false);
		final Label userExist = new Label("userExist", "Użytkownik o podanym loginie istnieje w systemie!");
		userExist.setVisible(false);
		final Label emailExist = new Label("emailExist", "Użytkownik o podanym adresie istnieje w systemie!");
		emailExist.setVisible(false);
		final Label przeszlo = new Label("przeszlo", "Dodano pracownika!");
		przeszlo.setVisible(false);

		User userDataModel = new User();
		final UserDao userDao = new UserDao();

		final TextField<String> imie = new TextField<String>("imie", new PropertyModel<String>(userDataModel, "imie"));
		final TextField<String> nazwisko = new TextField<String>("nazwisko",
				new PropertyModel<String>(userDataModel, "nazwisko"));
		final TextField<String> email = new TextField<String>("email",
				new PropertyModel<String>(userDataModel, "email"));
		final TextField<String> login = new TextField<String>("login",
				new PropertyModel<String>(userDataModel, "login"));

		final PasswordTextField haslo = new PasswordTextField("haslo",
				new PropertyModel<String>(userDataModel, "haslo"));

		Form<?> creating = new Form("creating") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				badName.setVisible(false);
				badSurname.setVisible(false);
				badEmail.setVisible(false);
				loginLength.setVisible(false);
				passLength.setVisible(false);
				userExist.setVisible(false);
				emailExist.setVisible(false);
				przeszlo.setVisible(false);

				UserDao user = new UserDao();
				String imie2 = imie.getInput();
				String nazwisko2 = nazwisko.getInput();
				String email2 = email.getInput();
				String login2 = login.getInput();
				String haslo2 = haslo.getInput();

				boolean IsOk = true;
				if (userDao.nameValidate(imie2) == false || imie2.length() < 2 || imie2.length() > 20
						|| imie2.isEmpty()) {
					badName.setVisible(true);
					IsOk = false;
				}

				if (userDao.nameValidate(nazwisko2) == false || nazwisko2.length() < 2 || nazwisko2.length() > 20
						|| nazwisko2.isEmpty()) {
					badSurname.setVisible(true);
					IsOk = false;
				}

				if (userDao.emailValidate(email2) == false) {
					badEmail.setVisible(true);
					IsOk = false;
				} else if (userDao.emailExist(email2) == true) {
					emailExist.setVisible(true);
					IsOk = false;
				}

				if (login2.length() < 4 || login2.length() > 15 || login2.isEmpty()) {
					loginLength.setVisible(true);
					IsOk = false;
				} else if (user.findUserId(login2) != 0) {
					userExist.setVisible(true);
					IsOk = false;
				}

				if (haslo2.length() < 4 || haslo2.length() > 15 || haslo2.isEmpty()) {
					passLength.setVisible(true);
					IsOk = false;
				}

				if (IsOk) {
					

					 try {
					 user.addUser(login2, haslo2, email2 ,imie2, nazwisko2, 1);
					 } catch (NoSuchAlgorithmException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
					 }
					 //setResponsePage(AdminPanel.class);
					 przeszlo.setVisible(true);
				}

			}

			// error.setVisible(true);

		};

		creating.add(badName);
		creating.add(badSurname);
		creating.add(badEmail);
		creating.add(loginLength);
		creating.add(passLength);
		creating.add(userExist);
		creating.add(emailExist);
		add(przeszlo);

		add(creating);
		creating.add(login);
		creating.add(imie);
		creating.add(nazwisko);
		creating.add(email);
		creating.add(haslo);

	}

}

package pl.helpdesk.pages;

import java.security.NoSuchAlgorithmException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;
import pl.helpdesk.passwordHash.HashPassword;
import pl.helpdesk.validation.Validation;

public class AdminAddEmployee extends AdminSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserDao userSpring;


	@SpringBean
	private IEmployeeDao employeeDao;

	public AdminAddEmployee(PageParameters parameters) {
		super(parameters);

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

				String imie2 = imie.getInput();
				String nazwisko2 = nazwisko.getInput();
				String email2 = email.getInput();
				String login2 = login.getInput();
				String haslo2 = haslo.getInput();
				String hasloHash = null;
				try {
					hasloHash = HashPassword.PasswordHash(haslo2);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}

				boolean IsOk = true;
				if (Validation.nameValidate(imie2) == false || imie2.length() < 2 || imie2.length() > 20
						|| imie2.isEmpty()) {
					badName.setVisible(true);
					IsOk = false;
				}

				if (Validation.nameValidate(nazwisko2) == false || nazwisko2.length() < 2 || nazwisko2.length() > 20
						|| nazwisko2.isEmpty()) {
					badSurname.setVisible(true);
					IsOk = false;
				}

				if (Validation.emailValidate(email2) == false) {
					badEmail.setVisible(true);
					IsOk = false;
				} else if (userSpring.emailExist(email2)) {
					emailExist.setVisible(true);
					IsOk = false;
				}

				if (login2.length() < 4 || login2.length() > 15 || login2.isEmpty()) {
					loginLength.setVisible(true);
					IsOk = false;
				} else if (userSpring.loginExist(login2)) {
					userExist.setVisible(true);
					IsOk = false;
				}

				if (haslo2.length() < 4 || haslo2.length() > 15 || haslo2.isEmpty()) {
					passLength.setVisible(true);
					IsOk = false;
				}

				if (IsOk) {
					User newUser = new User(login2, hasloHash, imie2, nazwisko2, email2, false, false, 0);
					userSpring.save(newUser);
					przeszlo.setVisible(true);
					Employee employee=new Employee(newUser);
					employeeDao.save(employee);
				}

			}
		};

		creating.add(badName);
		creating.add(badSurname);
		creating.add(badEmail);
		creating.add(loginLength);
		creating.add(passLength);
		creating.add(userExist);
		creating.add(emailExist);
		creating.add(przeszlo);

		add(creating);
		creating.add(login);
		creating.add(imie);
		creating.add(nazwisko);
		creating.add(email);
		creating.add(haslo);

	}

}

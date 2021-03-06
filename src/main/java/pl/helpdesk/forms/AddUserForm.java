package pl.helpdesk.forms;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAdminDao;
import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.ICompanyDao;
import pl.helpdesk.api.IEmployeeDao;
import pl.helpdesk.api.INotificationDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.api.IUserNotificationsDao;
import pl.helpdesk.components.SelectForm;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;
import pl.helpdesk.passwordHash.HashPassword;
import pl.helpdesk.userSession.ApplicationSession;
import pl.helpdesk.validation.Validation;

public class AddUserForm extends Panel {

	@SpringBean
	private IUserDao userSpring;

	@SpringBean
	private IAdminDao adminDao;

	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IUserNotificationsDao userNotificationsDao;

	@SpringBean
	private INotificationDao notificationDao;

	@SpringBean
	private IClientDao clientDao;

	@SpringBean
	private IEmployeeDao employeeDao;

	@SpringBean
	ICompanyDao companyDao;

	private String selectedCompany;
	private Agent agent;
	private User userDataModel;

	private Label badName;
	private Label badPhone;
	private Label badSurname;
	private Label badEmail;
	private Label loginLength;
	private Label passLength;
	private Label userExist;
	private Label emailExist;
	private Label przeszlo;
	private Label tooMany;
	private Label companyFail;
	private Label company;

	private SelectForm selectCompany;
	private TextField<String> imie;
	private TextField<String> nazwisko;
	private TextField<String> email;
	private TextField<String> telefon;
	private TextField<String> login;
	private PasswordTextField haslo;
	private Form<?> creating;

	private static final long serialVersionUID = 1L;

	public AddUserForm(String id, final String userType) {
		super(id);
		CreateMessageLabels();
		setMessageLabelsInvisible(userType);

		createFormComponents(userType);

		creating = new Form("creating") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				setMessageLabelsInvisible(userType);

				String imie2 = imie.getInput();
				String nazwisko2 = nazwisko.getInput();
				String telefon2 = telefon.getInput();
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
						|| imie2.equals("")) {
					badName.setVisible(true);
					IsOk = false;
				}
				if (Validation.phoneValidate(telefon2) == false || telefon2.length() < 7 || telefon2.length() > 11
						|| telefon2.equals("")) {
					badPhone.setVisible(true);
					IsOk = false;
				}
				if (Validation.nameValidate(nazwisko2) == false || nazwisko2.length() < 2 || nazwisko2.length() > 20
						|| nazwisko2.equals("")) {
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
				if (login2.length() < 4 || login2.length() > 15 || login2.equals("")) {
					loginLength.setVisible(true);
					IsOk = false;
				} else if (userSpring.loginExist(login2)) {
					userExist.setVisible(true);
					IsOk = false;
				}
				if (haslo2.length() < 4 || haslo2.length() > 15 || haslo2.equals("")) {
					passLength.setVisible(true);
					IsOk = false;
				}
				if (userType.equals("agent") && companyDao.getCompanyByName(selectedCompany) == null) {
					companyFail.setVisible(true);
					IsOk = false;
				}
				if (userType.equals("client")) {
					agent = agentDao.findAgentByUser(ApplicationSession.getInstance().getUser());
					int numberOfActiveClients = 0;
					List<Client> clientsList = clientDao.clientsFromAgent(agent);
					for (Client clientsListt : clientsList) {
						if (clientsListt.getUserDataModel().getCzy_usuniety() == false) {
							numberOfActiveClients++;
						}
					}
					if (numberOfActiveClients > 4) {
						tooMany.setVisible(true);
						IsOk = false;
					}
					if (IsOk) {
						for (Client clientsListt : clientsList) {
							userNotificationsDao.addNotification(clientsListt.getUserDataModel(),
									notificationDao.getById(3), ApplicationSession.getInstance().getUser().getLogin());
						}
					}

				}
				if (IsOk) {
					User newUser = new User(login2, hasloHash, imie2, nazwisko2, email2, telefon2, false, false, 0);
					userSpring.save(newUser);
					if (userType.equals("employee")) {
						Employee employee = new Employee(newUser);
						employeeDao.save(employee);
						List<Admin> allAdmins = adminDao.getAll();
						for (Admin allAdminss : allAdmins) {
							userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
									notificationDao.getById(5), ApplicationSession.getInstance().getUser().getLogin());
						}
						przeszlo.setVisible(true);
					} else if (userType.equals("client")) {
						Client client = new Client(newUser, agent.getCompanyDataModel(), agent);
						clientDao.save(client);
						userNotificationsDao.addNotification(ApplicationSession.getInstance().getUser(),
								notificationDao.getById(1), ApplicationSession.getInstance().getUser().getLogin());
						List<Admin> allAdmins = adminDao.getAll();
						for (Admin allAdminss : allAdmins) {
							userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
									notificationDao.getById(2), ApplicationSession.getInstance().getUser().getLogin());
						}

						przeszlo.setVisible(true);
					} else if (userType.equals("agent")) {
						Company company = companyDao.getCompanyByName(selectedCompany);
						Agent agent = new Agent(newUser, company);
						agentDao.save(agent);
						List<Admin> allAdmins = adminDao.getAll();
						for (Admin allAdminss : allAdmins) {
							userNotificationsDao.addNotification(allAdminss.getUserDataModel(),
									notificationDao.getById(4), ApplicationSession.getInstance().getUser().getLogin());
						}
						przeszlo.setVisible(true);
					}

				}

			}
		};

		AddMessageLabels();
		AddForm();
	}

	private void CreateMessageLabels() {
		badPhone = new Label("badphone", "Wpisz poprawnie numer telefonu!");
		badName = new Label("badname", "Wpisz poprawnie imię!");
		badSurname = new Label("badsurname", "Wpisz poprawnie nazwisko!");
		badEmail = new Label("bademail", "Wpisz poprawnie email!");
		loginLength = new Label("loginlength", "Login musi zawierac od 4 do 15 znaków!");
		passLength = new Label("passlength", "Hasło musi zawierac od 4 do 15 znaków!");
		userExist = new Label("userExist", "Użytkownik o podanym loginie istnieje w systemie!");
		emailExist = new Label("emailExist", "Użytkownik o podanym adresie istnieje w systemie!");
		przeszlo = new Label("przeszlo", "Dodano użytkownika!");
		tooMany = new Label("tooMany", "W twojej firmie jest już zarejestrowane 5 kont klienckich!");
		companyFail = new Label("companyFail", "Musisz wybrać firmę!");
		company = new Label("company", "Firma:");
	}

	private void setMessageLabelsInvisible(final String userType) {
		badPhone.setVisible(false);
		badName.setVisible(false);
		badSurname.setVisible(false);
		badEmail.setVisible(false);
		loginLength.setVisible(false);
		passLength.setVisible(false);
		userExist.setVisible(false);
		emailExist.setVisible(false);
		company.setVisible(false);
		przeszlo.setVisible(false);
		tooMany.setVisible(false);
		companyFail.setVisible(false);
		if (userType.equals("agent")) {
			company.setVisible(true);
		}
	}

	private void createFormComponents(final String userType) {
		userDataModel = new User();
		selectCompany = new SelectForm("selectCompany", new PropertyModel<String>(this, "selectedCompany"),
				companyDao.getCompaniesWithoutAgent());
		selectCompany.setVisible(false);
		if (userType.equals("agent")) {
			selectCompany.setVisible(true);
		}
		imie = new TextField<String>("imie", new PropertyModel<String>(userDataModel, "imie"));
		nazwisko = new TextField<String>("nazwisko", new PropertyModel<String>(userDataModel, "nazwisko"));
		email = new TextField<String>("email", new PropertyModel<String>(userDataModel, "email"));
		telefon = new TextField<String>("telefon", new PropertyModel<String>(userDataModel, "telefon"));
		login = new TextField<String>("login", new PropertyModel<String>(userDataModel, "login"));
		haslo = new PasswordTextField("haslo", new PropertyModel<String>(userDataModel, "haslo"));
	}

	private void AddForm() {
		add(creating);
		creating.add(login);
		creating.add(imie);
		creating.add(nazwisko);
		creating.add(email);
		creating.add(haslo);
		creating.add(selectCompany);
		creating.add(company);
		creating.add(telefon);
	}

	private void AddMessageLabels() {
		creating.add(badPhone);
		creating.add(badName);
		creating.add(badSurname);
		creating.add(badEmail);
		creating.add(loginLength);
		creating.add(passLength);
		creating.add(userExist);
		creating.add(emailExist);
		creating.add(przeszlo);
		creating.add(tooMany);
		creating.add(companyFail);
	}
}

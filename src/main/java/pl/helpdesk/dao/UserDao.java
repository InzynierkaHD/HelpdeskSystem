package pl.helpdesk.dao;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.entity.Admin;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;

public class UserDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory entityMF = Persistence.createEntityManagerFactory("baza");
	private EntityManager entityM = entityMF.createEntityManager();
	private CriteriaBuilder builder = entityM.getCriteriaBuilder();
	private CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
	private Root<User> user = criteriaQuery.from(User.class);

	public UserDao() {

	}

	/**
	 * Zamyka połączenie z bazą danych. Wywyoływana przy wylogowaniu z systemu.
	 * 
	 */
	public void closeConection() {
		entityM.close();
		entityMF.close();
	}

	/**
	 * Tworzy listę wszystkich użytkowników systemu.
	 * 
	 * @return Lista wszystkich użytkowników.
	 */
	public List<User> createUserList() {

		criteriaQuery.select(user);

		TypedQuery<User> query = entityM.createQuery(criteriaQuery);

		List<User> users = query.getResultList();
		return users;
	}

	/**
	 * Znajduje użytkownika po loginie i haśle.
	 * 
	 * @param Login
	 *            Login użytkownika.
	 * @param Passw
	 *            Gasło użytkownika.
	 * @return Login, jeżeli użytkownik o podanym loginie i haśle istnieje,
	 *         "none"m jeżeli użytkownika nie ma w tabeli.
	 * @throws NoSuchAlgorithmException
	 */
	public String findUserByLoginAndPassword(String Login, String Passw) throws NoSuchAlgorithmException {

		createUserList();
		for (User userDM : createUserList()) {
			if (userDM.getLogin().equals(Login) && userDM.getHaslo().equals(PasswordHash(Passw))) {
				return Login;
			}
		}
		return "none";

	}

	/**
	 * Znajduje id użytkownika o podanym w argumencie loginie.
	 * 
	 * @param Login
	 *            Login szukanego użytkownika.
	 * @return Id użytkownika, jeżeli istnieje, "0", jeżeli nie użytkownika o
	 *         podanym loginie.
	 */
	public int findUserId(String Login) {

		createUserList();
		for (User users2 : createUserList()) {
			if (users2.getLogin().equals(Login)) {
				return users2.getId();
			}
		}
		return 0;
	}

	/**
	 * Sprawdza, czy użytkownik o podanym lloginie jest blokowany.
	 * 
	 * @param Login
	 *            Login użytkownika.
	 * @return True, jeżeli użytkownik ma status zablokowanego. False, jeżeli
	 *         użyktownik nie jest zablokowany.
	 */
	public boolean czyBlokowany(String Login) {

		createUserList();
		for (User users2 : createUserList()) {
			if (users2.getLogin().equals(Login)) {
				if (users2.getCzy_blokowany() == true) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Sprawdza, czy podany Email i stnieje w systemie.
	 * 
	 * @param Email
	 *            Szukany Email.
	 * @return True, jeżeli szukany email istnieje w bazie. False w przeciwnym
	 *         wypadku.
	 */
	public boolean emailExist(String Email) {

		createUserList();
		for (User users2 : createUserList()) {
			if (users2.getEmail().equals(Email)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Haszuje podane w parametrze hasło algorytmem "SHA-256".
	 * 
	 * @param password
	 *            Hasło do zahaszowania.
	 * @return Zahaszowane hasło.
	 * @throws NoSuchAlgorithmException
	 */
	public String PasswordHash(String password) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	/**
	 * Dodaje do systemu użytkownika o podanych danych.
	 * 
	 * @param login
	 *            Login użytkownika.
	 * @param pass
	 *            Hasło użytkownika.
	 * @param email
	 *            Email użytkownika.
	 * @param imie
	 *            Imię użytkownika.
	 * @param nazwisko
	 *            Nazwisko użytkownika.
	 * @param rodzaj
	 *            Rodzaj użytkownika(1-pracownik, 2-przedstawiciel, 3-klient,
	 *            4-administrator).
	 * @throws NoSuchAlgorithmException
	 */
	
	public void addUser(String login, String pass, String email, String imie, String nazwisko, int rodzaj)
			throws NoSuchAlgorithmException {

		User userDataModel = new User();

		userDataModel.setLogin(login);
		userDataModel.setHaslo(PasswordHash(pass));
		userDataModel.setEmail(email);
		userDataModel.setImie(imie);
		userDataModel.setNazwisko(nazwisko);
		userDataModel.setCzy_blokowany(false);
		userDataModel.setCzy_usuniety(false);
		// userDataModel.setOst_logowanie(new Date());

		// EmployeeDataModel employeeDataModel = new EmployeeDataModel();

		// employeeDataModel.setId_uzytkownika(userDataModel.getId());

		entityM.getTransaction().begin();
		entityM.persist(userDataModel);
		entityM.getTransaction().commit();

		if (rodzaj == 1) {
			
			Employee employeeDataModel = new Employee();
			employeeDataModel.setUserDataModel(userDataModel);

			entityM.getTransaction().begin();
			entityM.persist(employeeDataModel);
			entityM.getTransaction().commit();
		} else if (rodzaj == 2) {
			// tworzy przedstawiciela
		} else if (rodzaj == 3) {
			// tworzy klienta
		} else if (rodzaj == 4) {
			Admin adminDataModel = new Admin();
			adminDataModel.setUserDataModel(userDataModel);
			entityM.getTransaction().begin();
			entityM.persist(adminDataModel);
			entityM.getTransaction().commit();
		}

	}

	/**
	 * Spradza typ użytkownika o podanym loginie.
	 * 
	 * @param login
	 *            Login użytkownika.
	 * @return Liczba odpowiadająca typowi użytkownika(1-pracownik,
	 *         2-przedstawiciel, 3-klient, 4-administrator) lub "0" gdy nie ma
	 *         użytkownika o podanym loginie.
	 */
	/*public int userType(String login) {

		AdminDao adminDao = new AdminDao();
		EmployeeDao employeeDao = new EmployeeDao();

		for (Employee employeeDM : employeeDao.createEmployeeList()) {
			if (employeeDM.getUserDataModel().getId() == (findUserId(login))) {
				return 1;
			}
		}
		for (Admin adminDM : adminDao.createAdminList()) {
			if (adminDM.getUserDataModel().getId() == (findUserId(login))) {
				return 4;
			}
		}

		return 0;

	}*/

	/**
	 * Waliduje adres email.
	 * 
	 * @param email Ciąg znaków do spradzenia.
	 * @return True, jeżeli email jest poprawny. W przeciwnym wypadku false.
	 */
	public boolean emailValidate(String email) {

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);

		boolean matchFound = m.matches();

		if (matchFound) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Waliduje nazwy zawierające jedynie litery, włączając polskie znaki.
	 * 
	 * @param name Ciąg znaków do alidacji.
	 * @return True, jeżeli ciąg znaków jest poprawny. W przeciwnym wypadku false.
	 */
	public boolean nameValidate(String name) {

		String illegal_regex = "[^a-zA-ZąćęłńóśźżĄĘŁŃÓŚŹŻ]";
		Pattern p = Pattern.compile(illegal_regex);
		Matcher m = p.matcher(name);

		if (m.find()) {
			return false;
		} else {
			return true;
		}
	}

	// Wersja inna:
	// public String findUserByLoginAndPassword(String Login,String Passw){
	// //EntityManagerFactory entityMF=
	// Persistence.createEntityManagerFactory("baza");
	// //EntityManager entityM=entityMF.createEntityManager();
	// //CriteriaBuilder builder = entityM.getCriteriaBuilder();
	// //CriteriaQuery <UserDataModel> criteriaQuery =
	// builder.createQuery(UserDataModel.class);
	//
	// //Root <UserDataModel> user=criteriaQuery.from(UserDataModel.class);
	// //Wersja z dluzzym selectem:
	// ////Path <String> name=user.get("login");
	// ////criteriaQuery.select(user).where(builder.like(name, Login));
	// criteriaQuery.select(user);
	//
	// TypedQuery<UserDataModel> query=entityM.createQuery(criteriaQuery);
	//
	// List <UserDataModel> users=query.getResultList();
	//
	// //entityM.close();
	// //entityMF.close();
	//
	// for (UserDataModel userDM : users) {
	// if(userDM.getLogin().equals(Login) && userDM.getHaslo().equals(Passw)){
	// return Login;
	// }
	// }
	//
	// return "none";
	//
	// }
	
}

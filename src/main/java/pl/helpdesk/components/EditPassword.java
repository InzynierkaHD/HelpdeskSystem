package pl.helpdesk.components;

import java.security.NoSuchAlgorithmException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.User;
import pl.helpdesk.passwordHash.HashPassword;
import pl.helpdesk.userSession.ApplicationSession;

public class EditPassword extends Panel {

	@SpringBean
	private IUserDao userDao;

	private User user;
	private String userPassword;

	final Label passLength;
	final Label badPassword;
	final Label badSecoundPassword;
	final Label passwordChanged;

	private static final long serialVersionUID = 1L;

	public EditPassword(String id) {
		super(id);
		
		user = userDao.getById(ApplicationSession.getInstance().getUser().getId());
		
		userPassword = user.getHaslo();
		
		
		passLength = new Label("passLength", "Hasło musi zawierac od 4 do 15 znaków!");
		badPassword = new Label("badPassword", "Niepoprawne hasło!");
		badSecoundPassword = new Label("badSecoundPassword", "Hasła nie są zgodne!");
		passwordChanged = new Label("passwordChanged", "Hasło zostało zmienione!");
		
		badPassword.setVisible(false);
		passLength.setVisible(false);
		badSecoundPassword.setVisible(false);
		passwordChanged.setVisible(false);

		final User newUser = userDao.getById(ApplicationSession.getInstance().getUser().getId());
		
		final PasswordTextField stareHaslo = new PasswordTextField("stareHaslo", new PropertyModel<String>(user, "haslo"));
		final PasswordTextField noweHaslo = new PasswordTextField("noweHaslo", new PropertyModel<String>(newUser, "haslo"));
		final PasswordTextField potwierdzHaslo = new PasswordTextField("potwierdzHaslo", new PropertyModel<String>(newUser, "haslo"));
		
		Form<?> changePassword = new Form("changePassword") {
		private static final long serialVersionUID = 1L;

		@Override
		public void onSubmit() {
			super.onSubmit();
			badPassword.setVisible(false);
			passLength.setVisible(false);
			badSecoundPassword.setVisible(false);
			passwordChanged.setVisible(false);
			//String hasloo=stareHaslo.getInput();
			String hasloStare = null;
			String hasloNowe = null;
			String hasloPotwierdz = null;
			try {
				hasloStare = HashPassword.PasswordHash(stareHaslo.getInput());
				hasloNowe = HashPassword.PasswordHash(noweHaslo.getInput());
				hasloPotwierdz = HashPassword.PasswordHash(potwierdzHaslo.getInput());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			if (!hasloStare.equals(userPassword)) {
				System.out.println(hasloStare);
				System.out.println(userPassword);
				badPassword.setVisible(true);
			}
			else if(noweHaslo.getInput().length() < 4 ||noweHaslo.getInput().length() > 15){
				passLength.setVisible(true);
			}else if(!hasloPotwierdz.equals(hasloNowe)){
				badSecoundPassword.setVisible(true);
			}else{
				user.setHaslo(hasloNowe);
				userDao.update(user);
				passwordChanged.setVisible(true);
			}
		}
		};
		add(changePassword);
		changePassword.add(stareHaslo);
		changePassword.add(noweHaslo);
		changePassword.add(potwierdzHaslo);
		
		changePassword.add(passLength);
		changePassword.add(badPassword);
		changePassword.add(badSecoundPassword);
		changePassword.add(passwordChanged);
		
		addComponents();
	}

	

	private void addComponents() {
	
	}
}

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

public class AdminEditPassword extends Panel {

	@SpringBean
	private IUserDao userDao;

	private User user;
	private String userPassword;

	final Label passLength;
	final Label badSecoundPassword;
	final Label passwordChanged;

	final Form<?> changePassword;

	final PasswordTextField noweHaslo;
	final PasswordTextField potwierdzHaslo;
	private static final long serialVersionUID = 1L;

	public AdminEditPassword(String id, int userId) {
		super(id);
		
		user = userDao.getById(userId);
		
		userPassword = user.getHaslo();
		
		
		passLength = new Label("passLength", "Hasło musi zawierac od 4 do 15 znaków!");
		badSecoundPassword = new Label("badSecoundPassword", "Hasła nie są zgodne!");
		passwordChanged = new Label("passwordChanged", "Hasło zostało zmienione!");
		
	
		passLength.setVisible(false);
		badSecoundPassword.setVisible(false);
		passwordChanged.setVisible(false);

		final User newUser = user = userDao.getById(userId);
		
		noweHaslo = new PasswordTextField("noweHaslo", new PropertyModel<String>(newUser, "haslo"));
		potwierdzHaslo = new PasswordTextField("potwierdzHaslo", new PropertyModel<String>(newUser, "haslo"));
		
		
		changePassword = new Form<Object>("changePassword") {
		private static final long serialVersionUID = 1L;

		@Override
		public void onSubmit() {
			super.onSubmit();
			passLength.setVisible(false);
			badSecoundPassword.setVisible(false);
			passwordChanged.setVisible(false);
			String hasloNowe = null;
			String hasloPotwierdz = null;
			try {
				hasloNowe = HashPassword.PasswordHash(noweHaslo.getInput());
				hasloPotwierdz = HashPassword.PasswordHash(potwierdzHaslo.getInput());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} if(noweHaslo.getInput().length() < 4 ||noweHaslo.getInput().length() > 15){
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
		
		addComponents();
	}


	private void addComponents() {
		add(changePassword);
		
		changePassword.add(noweHaslo);
		changePassword.add(potwierdzHaslo);	
		changePassword.add(passLength);
		changePassword.add(badSecoundPassword);
		changePassword.add(passwordChanged);
		
	}
}

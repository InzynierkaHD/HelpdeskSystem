package pl.helpdesk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.Company;
import pl.helpdesk.entity.Employee;
import pl.helpdesk.entity.User;

@Transactional
public class ClientDao extends GenericDao<Client, Integer> implements IClientDao {

	private List<Client> activeClientList;

	public ClientDao() {
		super();
	}

	@Override
	public Boolean isClient(User user) {
		if (!sessionFactory.getCurrentSession().createCriteria(Client.class).add(Restrictions.eq("userDataModel", user))
				.list().isEmpty()) {
			return true;
		} else
			return false;
	}

	@Override
	public Boolean isClientFromLogin(String login) {
		if (!sessionFactory.getCurrentSession().createCriteria(Client.class).createCriteria("userDataModel", "user")
				.add(Restrictions.eq("user.login", login)).list().isEmpty()) {
			return true;
		} else
			return false;
	}

	@Override
	public Client getClientForUser(User user) {
		return (Client) sessionFactory.getCurrentSession().createCriteria(Client.class)
				.add(Restrictions.eq("userDataModel", user)).uniqueResult();
	}

	// @Override
	// public List<Client> clientsFromAgent(Agent agent) {
	//
	// List<Client> clientList =
	// sessionFactory.getCurrentSession().createCriteria(Client.class)
	// .add(Restrictions.eq("agentDataModel", agent)).list();
	// System.out.println(clientList.size());
	// activeClientList = null;
	// for(Client client : clientList){
	// if(client.getUserDataModel().getCzy_usuniety()!=true){
	// activeClientList.add(client);
	// System.out.println("Po usunieciu: " +clientList.size());
	// }
	// }
	// System.out.println("Zwrocilo, koniec funkcji");
	// return (List<Client>) activeClientList;
	// }

	@Override
	public List<Client> clientsFromAgent(Agent agent) {
		return (List<Client>) sessionFactory.getCurrentSession().createCriteria(Client.class)
				.add(Restrictions.eq("agentDataModel", agent)).list();
	}

	@Override
	public String numOfCl(String surname) {

		if (surname.equals("")) {
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Client.class)
					.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.setProjection(Projections.rowCount()).uniqueResult());
		} else
			return String.valueOf(sessionFactory.getCurrentSession().createCriteria(Client.class)
					.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
					.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
					.setProjection(Projections.rowCount()).uniqueResult());

	}

	@Override
	public List<Client> getSortedClients(String sortBy, String surname) {
		if (sortBy.equals("Nazwisko") || sortBy.equals("0")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.nazwisko")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.nazwisko")).list();
		}
		if (sortBy.equals("1")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.email")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.email")).list();
		}
		if (sortBy.equals("2")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.ost_logowanie")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.ost_logowanie")).list();
		}
		if (sortBy.equals("3")) {
			if (surname.equals("")) {
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.addOrder(Order.asc("user.czy_blokowany")).list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Client.class)
						.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.addOrder(Order.asc("user.czy_blokowany")).list();
		}
		if (sortBy.equals("4")) {
			if (surname.equals("")) {

				return sessionFactory.getCurrentSession().createCriteria(Client.class, "client")
						.createCriteria("client.userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.createCriteria("client.companyDataModel", "company").addOrder(Order.asc("company.nazwa"))
						.list();
			} else
				return sessionFactory.getCurrentSession().createCriteria(Client.class, "client")
						.createCriteria("client.userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false))
						.add(Restrictions.like("user.nazwisko", surname, MatchMode.ANYWHERE))
						.createCriteria("client.companyDataModel", "company").addOrder(Order.asc("company.nazwa"))
						.list();
		} else {
			return sessionFactory.getCurrentSession().createCriteria(Client.class)
					.createCriteria("userDataModel", "user").add(Restrictions.eq("user.czy_usuniety", false)).list();
		}

	}

}

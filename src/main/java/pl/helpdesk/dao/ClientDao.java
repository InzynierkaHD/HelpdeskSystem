package pl.helpdesk.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import pl.helpdesk.api.IClientDao;
import pl.helpdesk.entity.Client;
import pl.helpdesk.entity.User;

@Transactional
public class ClientDao  extends GenericDao<Client,Integer> implements IClientDao{
	
	public ClientDao(){
		super();
	}
	
	@Override
	public Boolean isClient(User user) {
		if(!sessionFactory.getCurrentSession().createCriteria(Client.class)
			.add(Restrictions.eq("userDataModel", user)).list().isEmpty()){
			System.out.println("Klient");
			return true;
		}
		else
			System.out.println("NIE KLIENT");
			return false;
	}
}

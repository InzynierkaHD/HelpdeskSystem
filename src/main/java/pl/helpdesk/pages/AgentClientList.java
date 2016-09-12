package pl.helpdesk.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pl.helpdesk.api.IAgentDao;
import pl.helpdesk.api.IClientDao;
import pl.helpdesk.api.IUserDao;
import pl.helpdesk.entity.Agent;
import pl.helpdesk.entity.Client;
import pl.helpdesk.userSession.ApplicationSession;

public class AgentClientList extends AgentSuccessPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private IUserDao userDao;

	@SpringBean
	private IAgentDao agentDao;

	@SpringBean
	private IClientDao clientDao;

	final Agent agent = agentDao.findAgentByUser(ApplicationSession.getInstance().getUser());

	public AgentClientList(PageParameters parameters) {
		super(parameters);

		clientDao.clientsFromAgent(agent);

		ListView<?> listView = new ListView<Client>("listview", clientDao.clientsFromAgent(agent)) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Client> item) {
				Client client = (Client) item.getModelObject();
				item.add(new Label("imie", client.getUserDataModel().getImie()));
				item.add(new Label("nazwisko", client.getUserDataModel().getNazwisko()));
				item.add(new Label("email", client.getUserDataModel().getEmail()));
				item.add(new Label("ostatnie_logowanie", client.getUserDataModel().getOst_logowanie()));
				item.add(new Label("czy_blokowany", client.getUserDataModel().getCzy_blokowany()));
				item.add(new Label("czy_usuniety", client.getUserDataModel().getCzy_usuniety()));
			}
		};

		add(listView);

	}
}
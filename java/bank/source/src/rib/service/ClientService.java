package rib.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import rib.dao.ClientDao;
import rib.entity.Address;
import rib.entity.BankAccount;
import rib.entity.Client;
import rib.entity.CustomerAdvisors;
import rib.util.HibernateUtils;

@SuppressWarnings("rawtypes")
public class ClientService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private ClientDao clientDao;
	private Query query;
	Scanner scanner = new Scanner(System.in);
	BankAccount bankAccount = new BankAccount();
	CustomerAdvisors customerAdvisors = new CustomerAdvisors();
	Address address = new Address();
	

	public ClientService() throws Exception {
		super();
		this.clientDao = new ClientDao();
	}

	public void addClient(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"INSERT INTO Client (Id, FirstName, LastName, CNP, Email, PhoneNumber) values(?1,?2,?3,?4,?5,?6)");
		System.out.print("ID: ");
		client.setId(scanner.next());
		System.out.print("Prenume: ");
		scanner.nextLine();
		client.setFirstName(scanner.nextLine());
		System.out.print("Nume: ");
		client.setLastName(scanner.next());
		System.out.print("CNP: ");
		client.setCnp(scanner.nextLong());
		System.out.println("Daca email-ul este momentan indisponibil, introduceti \"-\"");
		System.out.print("Email: ");
		client.setEmail(scanner.next());
		System.out.print("Telefon: ");
		client.setPhoneNumber(scanner.next());

		query.setParameter(1, client.getId());
		query.setParameter(2, client.getFirstName());
		query.setParameter(3, client.getLastName());
		query.setParameter(4, client.getCnp());
		query.setParameter(5, client.getEmail().equals("-") ? null : client.getEmail());
		query.setParameter(6, client.getPhoneNumber());
		System.err.println("Client adaugat cu succes!" + client.toString());
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void addClientBankingData(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"UPDATE Client SET bankAccount_ID=?1, Address_No=?2, customerAdvisors_No=?3 WHERE LastName=?4 and FirstName=?5");
		System.out.println("Daca un parametru este momentan indisponibil, introduceti \"0\"");
		System.out.print("Id-ul contului: ");
		bankAccount.setId(scanner.nextInt());
		System.out.print("Numarul adresei: ");
		address.setNo(scanner.nextInt());
		System.out.print("Id-ul bancherului: ");
		customerAdvisors.setId(scanner.nextInt());
		System.out.print("Prenume client: ");
		scanner.nextLine();
		client.setFirstName(scanner.nextLine());
		System.out.print("Nume client: ");
		client.setLastName(scanner.next());

		query.setParameter(1, bankAccount.getId() == 0 ? null : bankAccount.getId());
		query.setParameter(2, address.getNo() == 0 ? null : address.getNo());
		query.setParameter(3, customerAdvisors.getId() == 0 ? null : customerAdvisors.getId());
		query.setParameter(4, client.getLastName());
		query.setParameter(5, client.getFirstName());
		System.err.println("Date adaugate cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void clientLogin() {
		clientDao.openCurrentSession();
		clientDao.clientLogin();
		clientDao.closeCurrentSession();
	}

	public List<Address> findClientAddress() {
		clientDao.openCurrentSession();
		List<Address> list = clientDao.findClientAddress();
		clientDao.closeCurrentSession();
		return list;
	}

	public List<Client> findClientByName() {
		clientDao.openCurrentSession();
		List<Client> list = clientDao.findClientByName();
		clientDao.closeCurrentSession();
		return list;
	}

	public List<Client> findClientByCnp() {
		clientDao.openCurrentSession();
		List<Client> list = clientDao.findByCnp();
		clientDao.closeCurrentSession();
		return list;
	}

	public List<Client> findClientByAccount() {
		clientDao.openCurrentSession();
		List<Client> list = clientDao.findClientByPassAndUser();
		clientDao.closeCurrentSession();
		return list;
	}

	public List<Client> findClientById() {
		clientDao.openCurrentSession();
		List<Client> list = clientDao.findClientById();
		clientDao.closeCurrentSession();
		return list;
	}

	public void deleteClientById() throws Exception {
		clientDao.openCurrentSessionwithTransaction();
		clientDao.deleteClientById();
		clientDao.closeCurrentSessionwithTransaction();
	}

	public void deleteAllClients() {
		clientDao.openCurrentSessionwithTransaction();
		clientDao.deleteAll();
		clientDao.closeCurrentSessionwithTransaction();
	}

	public List<Client> orderClientsByIdAsc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByIdAsc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByIdDesc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByIdDesc();
		clientDao.closeCurrentSession();
		return lista;
	}
	
	public List<Client> getAllClientsUsingJDBC(Connection conn) throws Exception {
		AddressService  addressService = new AddressService();
		// This will not work !!! use just for testing
		List<Client> lista = new ArrayList<Client>();
		String query = "SELECT * FROM Clients";
	    try (Statement stmt = conn.createStatement()) {
	      ResultSet rs = stmt.executeQuery(query);
	      while (rs.next()) {
	        String id = rs.getString("ID");
	        String firstName = rs.getString("FIRST_NAME");
	        String lastName = rs.getString("LAST_NAME");
	        String email = rs.getString("EMAIL");
	        String phoneNumber = rs.getString("PHONE");
	        long cnp = rs.getInt("CNP");
	        Address address = null;
	        CustomerAdvisors customerAdvisors = null;
	        BankAccount bankAccount = null;
	        Client client = new Client(id, firstName, lastName, cnp, email, address,
	        		phoneNumber, customerAdvisors, bankAccount);
	        lista.add(client);
	        addressService.saveAddress(address);
	      }
	    
	    } catch (SQLException e) {
	      
	    }
	    
		return lista;
	}

	public List<Client> orderClientsByFirstNameAsc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByFirstNameAsc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByFirstNameDesc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByFirstNameDesc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByLastNameAsc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByLastNameAsc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByLastNameDesc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByLastNameDesc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByNoAsc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByNoAsc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public List<Client> orderClientsByNoDesc() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.orderByNoDesc();
		clientDao.closeCurrentSession();
		return lista;
	}

	public void saveClient(Client client) {
		clientDao.openCurrentSessionwithTransaction();
		clientDao.persist(client);
		clientDao.closeCurrentSessionwithTransaction();
	}

	public List<Client> showAllClients() {
		clientDao.openCurrentSession();
		List<Client> lista = clientDao.showAll();
		clientDao.closeCurrentSession();
		return lista;
	}

	public void updateClientEmail(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE CLIENT SET Email=?1 where Id=?2");
		System.out.print("Email: ");
		client.setEmail(scanner.next());
		System.out.print("ID: ");
		client.setId(scanner.next());

		query.setParameter(1, client.getEmail());
		query.setParameter(2, client.getId());
		System.err.println("Client actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void updateClientPhoneNumber(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE CLIENT SET PhoneNumber=?1 where Id=?2");
		System.out.print("Telefon: ");
		client.setPhoneNumber(scanner.next());
		System.out.print("ID: ");
		client.setId(scanner.next());

		query.setParameter(1, client.getPhoneNumber());
		query.setParameter(2, client.getId());
		System.err.println("Client actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void updateClientFirstName(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE CLIENT SET FirstName=?1 where Id=?2");
		System.out.print("Prenume: ");
		client.setFirstName(scanner.next());
		System.out.print("ID: ");
		client.setId(scanner.next());

		query.setParameter(1, client.getFirstName());
		query.setParameter(2, client.getId());
		System.err.println("Client actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void updateClientLastName(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE CLIENT SET LastName=?1 where Id=?2");
		System.out.print("Nume: ");
		client.setLastName(scanner.next());
		System.out.print("ID: ");
		client.setId(scanner.next());

		query.setParameter(1, client.getLastName());
		query.setParameter(2, client.getId());
		System.err.println("Client actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void updateClientAddress(Client client) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE CLIENT SET Address_No=?1 where Id=?2");
		System.out.print("Numarul adresei: ");
		address.setNo(scanner.nextInt());
		System.out.print("ID: ");
		client.setId(scanner.next());

		query.setParameter(1, address.getNo());
		query.setParameter(2, client.getId());
		System.err.println("Client actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void selectBirthDayAndAgeFromCNP() {
		clientDao.openCurrentSession();
		clientDao.selectBirthDayAndAgeFromCNP();
		clientDao.closeCurrentSession();

	}
}
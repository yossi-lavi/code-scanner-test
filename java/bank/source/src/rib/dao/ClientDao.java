package rib.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.query.Query;

import rib.entity.Address;
import rib.entity.BankAccount;
import rib.entity.Client;
import rib.util.HibernateUtils;
import rib.util.Warehouse;

public class ClientDao implements EntityDao<Client, Integer> {

	private Session session = HibernateUtils.getSessionFactory().getCurrentSession();
	// private Query query;
	private Transaction transaction;
	BankAccount bankAccount = new BankAccount();
	Scanner scanner = new Scanner(System.in);
	Warehouse warehouse = new Warehouse();

	public ClientDao() {
	}

	public Session openCurrentSession() {
		session = HibernateUtils.getSessionFactory().openSession();
		return session;
	}

	public Session openCurrentSessionwithTransaction() {
		session = HibernateUtils.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		return session;
	}

	public void closeCurrentSession() {
		session.close();
	}

	public void closeCurrentSessionwithTransaction() {
		transaction.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public void clientLogin() {
		int attempts = 0;
		do {
			if (attempts == 3) {
				System.err.println("\nATI INTRODUS DE PREA MULTE ORI CONTUL GRESIT!\nLA REVEDERE!");
				System.exit(0);
			} else {
				System.out.print("Nume utilizator (username): ");
				String username = scanner.next();
				System.out.print("Parola: ");
				int password = scanner.nextInt();
				List<Client> list = session
						.createQuery("Select 1 from BankAccount where username=:username and password=:password")
						.setParameter("username", username).setParameter("password", password).list();

				if (!list.isEmpty()) {
					warehouse.progressBar();
					System.out.println("terminata");
					break;
				}
			}
			attempts++;
			if ((3 - attempts) == 2)
				System.err.print(
						"\nAutentificare incorecta, va rugam să incercati din nou!\nMai aveti 2 incercari disponibile!\n");
			if ((3 - attempts) == 1)
				System.err.print(
						"\nAutentificare incorecta, va rugam sa incercati din nou!\nMai aveti o incercare disponibila!\n");
		} while (attempts <= 3);
	}

	@SuppressWarnings("unchecked")
	public List<Address> findClientAddress() {
		System.out.print("Introduceti numele clientului: ");
		String lastName = scanner.next();
		System.out.print("Introduceti prenumele clientului: ");
		String firstName = scanner.next();
		List<Address> list = session.createQuery(
				"FROM Address where No in (SELECT no from Client where LastName=:lastName and Firstname=:firstName)")
				.setParameter("lastName", lastName).setParameter("firstName", firstName).list();
		while (list.isEmpty()) {
			System.err.println(
					"Nu s-a gasit adresa clientului " + lastName.toUpperCase() + " " + firstName.toUpperCase() + "!");
			break;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Client> findByCnp() {
		System.out.print("Introduceti CNP-ul clientului: ");
		long cnp = scanner.nextLong();
		List<Client> list = session.createQuery("from Client where CNP=:cnp").setParameter("cnp", cnp).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit client cu CNP-ul " + cnp + "!");
			break;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Client> findClientById() {
		System.out.print("Introduceti id-ul clientului: ");
		String id = scanner.next();
		List<Client> list = session.createQuery("from Client where ID=:id").setParameter("id", id).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit client cu id-ul " + id + "!");
			break;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void selectBirthDayAndAgeFromCNP() {
		System.out.print("Introduceti numele: ");
		String lastName = scanner.next();
		System.out.print("Introduceti prenumele: ");
		String firstName = scanner.next();
		//birthday
		List<Client> list1 = session.createNativeQuery(
				"SELECT STR_TO_DATE(concat(MID(Client.cnp, 6,2),'-', MID(Client.cnp, 4,2),'-', MID(Client.cnp, 2,2)), '%d-%m-%Y') AS DATA_NASTERII from Client where Client.lastName=?1 and Client.firstName=?2")
				.setParameter(1, lastName).setParameter(2, firstName).list();
		//age
		List<Client> list2 = session.createNativeQuery(
				"SELECT TRUNCATE(DATEDIFF(sysdate(),str_TO_DATE(concat(substr(Client.cnp,6,2),'-', substr(Client.cnp,4,2),'-',substr(Client.cnp,2,2) ),'%d-%m-%Y'))/365,0) from Client where Client.lastName=?1 and Client.firstName=?2")
				.setParameter(1, lastName).setParameter(2, firstName).list();
		while (list1.isEmpty() || list2.isEmpty()) {
			System.err.println(
					"\nNu s-a gasit clientul " + lastName.toUpperCase() + " " + firstName.toUpperCase() + "!\n");
			break;
		}
		while (!list1.isEmpty() || !list2.isEmpty()) {
			System.out.print("\n\nData nasterii: " + list1 + ", varsta: " + list2 + "\n\n");
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Client> findClientByName() {
		System.out.print("Introduceti numele clientului (nume de familie): ");
		String lastName = scanner.next();
		System.out.print("Introduceti prenumele: ");
		String firstName = scanner.next();
		List<Client> list = session.createQuery("from Client where LastName=:lastName and Firstname=:firstName")
				.setParameter("lastName", lastName).setParameter("firstName", firstName).list();
		while (list.isEmpty()) {
			System.err.println(
					"Nu s-a gasit client cu numele " + lastName.toUpperCase() + " " + firstName.toUpperCase() + "!");
			break;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Client> findClientByPassAndUser() {
		System.out.print("Numele de utilizator (username): ");
		String username = scanner.next();
		System.out.print("Parola: ");
		int password = scanner.nextInt();
		List<Client> list = session.createQuery(
				"from Client where bankAccount_ID in (select id from BankAccount where Username=:username AND Password=:password)")
				.setParameter("username", username).setParameter("password", password).list();
		while (list.isEmpty()) {
			System.err.println(
					"Nu s-a gasit client cu numele de utilizator " + username + " si cu parola " + password + "!");
			break;
		}
		return list;
	}

	public void deleteClientById() {
		System.out.print("Introduceti id-ul clientului: ");
		String id = scanner.next();
		session.createQuery("delete from Client where id=:id").setParameter("id", id).executeUpdate();
	}

	@Override
	public void deleteAll() {
		session.createQuery("delete from Client").executeUpdate();
		System.err.println("Toti clientii au fost stersi!");
	}

	public List<Client> orderByIdAsc() {
		return session.createQuery("FROM Client order by id asc", Client.class).list();
	}

	public List<Client> orderByIdDesc() {
		return session.createQuery("FROM Client order by id desc", Client.class).list();
	}

	public List<Client> orderByFirstNameAsc() {
		return session.createQuery("FROM Client order by FirstName ASC", Client.class).list();
	}

	public List<Client> orderByFirstNameDesc() {
		return session.createQuery("FROM Client order by FirstName DESC", Client.class).list();
	}

	public List<Client> orderByLastNameAsc() {
		return session.createQuery("FROM Client order by LastName ASC", Client.class).list();
	}

	public List<Client> orderByLastNameDesc() {
		return session.createQuery("FROM Client order by LastName DESC", Client.class).list();
	}

	@Override
	public List<Client> orderByNoAsc() {
		return session.createQuery("FROM Client Order by NO asc", Client.class).list();
	}

	@Override
	public List<Client> orderByNoDesc() {
		return session.createQuery("FROM Client order by no desc", Client.class).list();
	}

	@Override
	public void persist(Client entity) {
		session.save(entity);
	}

	@Override
	public List<Client> showAll() {
		return session.createQuery("from Client", Client.class).list();
	}
}

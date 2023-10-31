package rib.service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import rib.dao.CustomerAdvisorsDao;
import rib.entity.Address;
import rib.entity.BankAgency;
import rib.entity.CustomerAdvisors;
import rib.entity.CustomerAdvisorsPassword;
import rib.util.HibernateUtils;

public class CustomerAdvisorsSevice {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private CustomerAdvisorsDao customerAdvisorsDao;
	Scanner scanner = new Scanner(System.in);
	BankAgency bankAgency = new BankAgency();
	private Query query;
	CustomerAdvisors customerAdvisors = new CustomerAdvisors();
	CustomerAdvisorsPassword customerAdvisorsPassword = new CustomerAdvisorsPassword();
	Address address = new Address();

	public CustomerAdvisorsSevice() {
		super();
		this.customerAdvisorsDao = new CustomerAdvisorsDao();
	}

	public void addCustomersAdvisors(CustomerAdvisors customerAdvisors) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"INSERT INTO Customer_Advisors (LastName, FirstName, CNP, PhoneNumber, PositionHeld) values(?1,?2,?3,?4,?5)");
		System.err.println("\nIntroduceti datele personale");
		System.out.print("Nume: ");
		customerAdvisors.setLastName(scanner.next());
		System.out.print("Prenume: ");
		customerAdvisors.setFirstName(scanner.next());
		System.out.print("CNP: ");
		customerAdvisors.setCnp(scanner.next());
		System.out.print("Telefon: ");
		customerAdvisors.setPhoneNumber(scanner.next());
		System.out.print("Functie: ");
		customerAdvisors.setPositionHeld(scanner.next());

		query.setParameter(1, customerAdvisors.getLastName());
		query.setParameter(2, customerAdvisors.getFirstName());
		query.setParameter(3, customerAdvisors.getCnp());
		query.setParameter(4, customerAdvisors.getPhoneNumber());
		query.setParameter(5, customerAdvisors.getPositionHeld());
		System.err.println("Angajat adaugat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void addCustomersAdvisors2(CustomerAdvisors customerAdvisors) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"UPDATE Customer_Advisors SET address_No=?1, bankAgency_No=?2, customerAdvisorsPassword_No=?3 WHERE LastName=?4 and FirstName=?5");
		System.out.println("Daca un parametru este momentan indisponibil, introduceti \"0\"");
		System.out.print("Nmarul adresei: ");
		address.setNo(scanner.nextInt());
		System.out.print("Numarul agentiei bancare: ");
		bankAgency.setNo(scanner.nextInt());
		System.out.print("Introduceti numarul parolei: ");
		customerAdvisorsPassword.setNo(scanner.nextInt());
		System.out.print("Nume: ");
		customerAdvisors.setLastName(scanner.next());
		System.out.print("Prenume: ");
		customerAdvisors.setFirstName(scanner.next());

		query.setParameter(1, address.getNo() == 0 ? null : address.getNo());
		query.setParameter(2, bankAgency.getNo() == 0 ? null : bankAgency.getNo());
		query.setParameter(3, customerAdvisorsPassword.getNo() == 0 ? null : customerAdvisorsPassword.getNo());
		query.setParameter(4, customerAdvisors.getLastName());
		query.setParameter(5, customerAdvisors.getFirstName());
		System.err.println("Date adaugate cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void addCustomerAdvisorsPassword(CustomerAdvisorsPassword customerAdvisorsPassword) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("INSERT INTO CustomerAdvisorsPassword(Password) values(?1)");
		System.out.print("Parola: ");
		customerAdvisorsPassword.setPassword(scanner.nextInt());

		query.setParameter(1, customerAdvisorsPassword.getPassword());
		System.err.println("Parola adaugata cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void customerAdvisorsLogin() {
		customerAdvisorsDao.openCurrentSession();
		customerAdvisorsDao.customerAdvisorsLogin();
		customerAdvisorsDao.closeCurrentSession();
	}

	public void deleteCustomerAdvisors() {
		customerAdvisorsDao.openCurrentSessionwithTransaction();
		customerAdvisorsDao.deleteCustomerAdvisorsByCnp();
		customerAdvisorsDao.closeCurrentSessionwithTransaction();
	}

	public void deleteAllCustomersAdvisors() {
		customerAdvisorsDao.openCurrentSessionwithTransaction();
		customerAdvisorsDao.deleteAll();
		customerAdvisorsDao.closeCurrentSessionwithTransaction();
	}

	public List<CustomerAdvisors> findCustomerAdvisorsByBankAgency() {
		customerAdvisorsDao.openCurrentSession();
		List<CustomerAdvisors> list = customerAdvisorsDao.findBankEmployees();
		customerAdvisorsDao.closeCurrentSession();
		return list;
	}

	public List<CustomerAdvisors> orderCustomerAdvisorsbyFirstNameAsc() {
		customerAdvisorsDao.openCurrentSession();
		List<CustomerAdvisors> list = customerAdvisorsDao.orderByFirstNameAsc();
		customerAdvisorsDao.closeCurrentSession();
		return list;
	}

	public List<CustomerAdvisors> orderCustomerAdvisorsbyFirstNameDesc() {
		customerAdvisorsDao.openCurrentSession();
		List<CustomerAdvisors> list = customerAdvisorsDao.orderByFirstNameDesc();
		customerAdvisorsDao.closeCurrentSession();
		return list;
	}

	public List<CustomerAdvisors> orderCustomerAdvisorsbyLastNameAsc() {
		customerAdvisorsDao.openCurrentSession();
		List<CustomerAdvisors> list = customerAdvisorsDao.orderByLastNameAsc();
		customerAdvisorsDao.closeCurrentSession();
		return list;
	}

	public List<CustomerAdvisors> orderCustomerAdvisorsbyLastNameDesc() {
		customerAdvisorsDao.openCurrentSession();
		List<CustomerAdvisors> list = customerAdvisorsDao.orderByLastNameDesc();
		customerAdvisorsDao.closeCurrentSession();
		return list;
	}

	public List<CustomerAdvisors> showAll() throws Exception {
		customerAdvisorsDao.openCurrentSessionwithTransaction();
		List<CustomerAdvisors> lista = customerAdvisorsDao.showAll();
		customerAdvisorsDao.closeCurrentSessionwithTransaction();
		return lista;
	}

	public void updateCustomerAdvisors(CustomerAdvisors customerAdvisors) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createNativeQuery(
				"UPDATE CustomerAdvisors set FirstName=:firstName, LastName=:lastName, PhoneNumber=:phoneNumber, PositionHeld=:positionHeld address_No=:address_No, bankAgency_No=:bankAgency_No where CNP=:cnp");
		System.out.print("Prenume: ");
		customerAdvisors.setFirstName(scanner.next());
		System.out.print("Nume: ");
		customerAdvisors.setLastName(scanner.next());
		System.out.print("Telefon: ");
		customerAdvisors.setPhoneNumber(scanner.next());
		System.out.print("Functie: ");
		customerAdvisors.setPositionHeld(scanner.next());
		System.out.print("Numarul adresei: ");
		address.setNo(scanner.nextInt());
		System.out.print("Numarul agentiei bancare: ");
		bankAgency.setNo(scanner.nextInt());
		System.out.print("CNP: ");
		customerAdvisors.setCnp(scanner.next());
		
		
		query.setParameter(1, customerAdvisors.getFirstName());
		query.setParameter(2, customerAdvisors.getLastName());
		query.setParameter(3, customerAdvisors.getPhoneNumber());
		query.setParameter(4, customerAdvisors.getPositionHeld());
		query.setParameter(5, address.getNo());
		query.setParameter(6, bankAgency.getNo());
		query.setParameter(7, customerAdvisors.getCnp());
		System.err.println("Bancher actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}
}

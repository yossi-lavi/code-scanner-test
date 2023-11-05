package rib.service;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import rib.dao.BankAccountDao;
import rib.entity.BankAccount;
import rib.entity.Deposit;
import rib.util.HibernateUtils;

@SuppressWarnings("rawtypes")
public class BankAccountService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	BankAccountDao bankAccountDao = new BankAccountDao();
	BankAccount bankAccount = new BankAccount();
	Deposit deposit = new Deposit();
	Scanner scanner = new Scanner(System.in);
	private Query query;

	public BankAccountService() throws Exception {
		super();
		this.bankAccountDao = new BankAccountDao();
	}

	public void addBankAccount(BankAccount bankAccount) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("INSERT INTO BankAccount (Username, Password) values(?1,?2)");
		System.out.print("Username: ");
		bankAccount.setUsername(scanner.next());
		System.out.print("Parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, bankAccount.getUsername());
		query.setParameter(2, bankAccount.getPassword());
		System.err.println("Contul bancar s-a creat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void updateDoposit(Deposit deposit) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery("UPDATE BankAccount SET Deposit_No=?1 where Username=?2 and Password=?3");
		System.out.print("Numarul depozitului: ");
		deposit.setNo(scanner.nextInt());
		System.out.print("Username: ");
		bankAccount.setUsername(scanner.next());
		System.out.print("Parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getNo());
		query.setParameter(2, bankAccount.getUsername());
		query.setParameter(3, bankAccount.getPassword());
		System.err.println("Date adaugate cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public List<BankAccount> findPasswordByUsername(String username) throws Exception {
		bankAccountDao.openCurrentSession();
		List<BankAccount> bankAccount = bankAccountDao.findPasswordByUser(username);
		bankAccountDao.closeCurrentSession();
		return bankAccount;
	}

	public List<BankAccount> showPersonalAccount() {
		bankAccountDao.openCurrentSession();
		List<BankAccount> list = bankAccountDao.showPersonalAccount();
		bankAccountDao.closeCurrentSession();
		return list;
	}
}

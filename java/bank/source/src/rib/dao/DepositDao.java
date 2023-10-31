package rib.dao;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import rib.entity.BankAccount;
import rib.entity.Deposit;
import rib.util.HibernateUtils;

public class DepositDao {
	private Session session = HibernateUtils.getSessionFactory().getCurrentSession();
	@SuppressWarnings("rawtypes")
	private Query query;
	private Transaction transaction;
	BankAccount bankAccount = new BankAccount();
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	Scanner scanner = new Scanner(System.in);

	public DepositDao() {
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
	public void addRon(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_RON=d.deposit_RON+?1 where No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma pe care o depuneti: ");
		deposit.setRon(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getRon());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_RON from Deposit where No in (SELECT deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold RON: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void addEur(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_EUR=d.deposit_EUR+?1 where No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma pe care o depuneti: ");
		deposit.setEur(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getEur());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_EUR from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold EUR: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void addUsd(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_USD=d.deposit_USD+?1 where No in (SELECT Deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma pe care o depuneti: ");
		deposit.setUsd(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getUsd());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_USD from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold USD: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void addGbp(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_GBP=d.deposit_GBP+?1 where No in (SELECT Deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma pe care o depuneti: ");
		deposit.setGbp(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getGbp());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_GBP from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold GBP: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void withdrawRon(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_RON=d.deposit_RON-?1 where No in (SELECT Deposit_No from BankAccount where Password=?2) and d.deposit_RON-?1>=0");
		System.out.print("Introduceti suma pe care doriti sa o retrageti: ");
		deposit.setRon(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getRon());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_RON from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold RON: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void withdrawEur(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_EUR=d.deposit_EUR-?1 where No in (SELECT Deposit_No from BankAccount where Password=?2) and d.deposit_EUR-?1>=0");
		System.out.print("Introduceti suma pe care doriti sa o retrageti: ");
		deposit.setEur(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getEur());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_EUR from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold EUR: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void withdrawUsd(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_USD=d.deposit_USD-?1 where No in (SELECT Deposit_No from BankAccount where Password=?2) and d.deposit_USD-?1>=0");
		System.out.print("Introduceti suma pe care doriti sa o retrageti: ");
		deposit.setUsd(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getUsd());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_USD from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold USD: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void withdrawGbp(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.deposit_GBP=d.deposit_GBP-?1 where No in (SELECT Deposit_No from BankAccount where Password=?2) and d.deposit_GBP-?1>=0");
		System.out.print("Introduceti suma pe care doriti sa o retrageti: ");
		deposit.setGbp(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getGbp());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();

		List<Deposit> list = session.createNativeQuery(
				"SELECT Deposit.deposit_GBP from Deposit where No in (SELECT Deposit_No from BankAccount where Password=?1)")
				.setParameter(1, bankAccount.getPassword()).list();
		System.err.print("Sold GBP: " + list + "\n");
	}

	public List<Deposit> showAll() {
		return session.createQuery("FROM Deposit", Deposit.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Deposit> selectTheLastDepositCreated() {
		List<Deposit> list = session.createQuery("FROM Deposit where no=(Select max(no) from Deposit)").list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Deposit> showPersonalDeposit() {
		System.out.print("Introduceti parola: ");
		int password = scanner.nextInt();
		List<Deposit> list = session
				.createQuery("FROM Deposit where No in (SELECT id from BankAccount where Password=:password)")
				.setParameter("password", password).list();
		if (list.isEmpty())
			System.err.println("Nu s-a gasit cont asociat acestei parole!");
		return list;
	}

	@SuppressWarnings("unchecked")
	public void convertAllMoneyToRON() {
		System.out.print("Introduceti parola: ");
		int password = scanner.nextInt();
		List<Deposit> list = session.createNativeQuery(
				"SELECT truncate(Deposit_RON+(Deposit_EUR*4.8849)+(Deposit_GBP*5.7)+(Deposit_USD*4.1036),2) as TOTAL from Deposit where No in (SELECT id from BankAccount where Password=:password)")
				.setParameter("password", password).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit cont asociat acestei parole!");
			break;
		}
		if (!list.isEmpty())
			System.err.print("Sold RON: " + list + "\n");
	}

	@SuppressWarnings("unchecked")
	public void convertAllMoneyToEUR() {
		System.out.print("Introduceti parola: ");
		int password = scanner.nextInt();
		List<Deposit> list = session.createNativeQuery(
				"SELECT truncate((Deposit_RON/4.8849)+Deposit_EUR+(Deposit_GBP/1.16551)+(Deposit_USD/0.840084),2) as TOTAL from Deposit where No in (SELECT id from BankAccount where Password=:password)")
				.setParameter("password", password).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit cont asociat acestei parole!");
			break;
		}
		if (!list.isEmpty())
			System.err.print("Sold EUR: " + list + "\n");
	}

	public void convertRonToEurInAccount(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.Deposit_EUR=truncate((?1/4.8849)+d.Deposit_EUR,2), d.Deposit_RON=d.Deposit_RON-?1 where d.Deposit_RON>=?1 and No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma: ");
		deposit.setRon(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getRon());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();
	}

	public void convertRonToUsdInAccount(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.Deposit_USD=truncate((?1/4.0908)+d.Deposit_USD,2), d.Deposit_RON=d.Deposit_RON-?1 where d.Deposit_RON>=?1 and No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma: ");
		deposit.setRon(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getRon());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();
	}

	public void convertEurToRonInAccount(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.Deposit_RON=truncate(Deposit_RON+(?1*4.8849),2), d.Deposit_EUR=d.Deposit_EUR-?1 where d.Deposit_EUR>=?1 and No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma: ");
		deposit.setEur(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getEur());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();
	}

	public void convertUsdToRonInAccount(Deposit deposit) {
		query = session.createNativeQuery(
				"UPDATE Deposit d set d.Deposit_RON=truncate(Deposit_RON+(?1*4.0908),2), d.Deposit_USD=d.Deposit_USD-?1 where d.Deposit_USD>=?1 and No in (SELECT deposit_No from BankAccount where Password=?2)");
		System.out.print("Introduceti suma: ");
		deposit.setUsd(scanner.nextInt());
		System.out.print("Introduceti parola: ");
		bankAccount.setPassword(scanner.nextInt());

		query.setParameter(1, deposit.getUsd());
		query.setParameter(2, bankAccount.getPassword());
		query.executeUpdate();
	}

	public void convertRonToEur() {
		System.out.print("Introduceti suma: ");
		int ron = scanner.nextInt();
		if (ron > 0) {
			double eur;
			eur = ron / 4.8849;
			System.out.println("Suma rezultata dupa convertire este: " + decimalFormat.format(eur) + " euro");
		} else
			System.err.println("Nu puteti sa convertiti " + ron + " ron!");
	}

	public void convertEurToRon() {
		System.out.print("Introduceti suma: ");
		int eur = scanner.nextInt();
		if (eur > 0) {
			double ron;
			ron = eur * 4.8849;
			System.out.println("Suma rezultata dupa convertire este: " + decimalFormat.format(ron) + " ron");
		} else
			System.err.println("Nu puteti sa convertiti " + eur + " euro!");
	}

	public void convertRonToUsd() {
		System.out.print("Introduceti suma: ");
		int ron = scanner.nextInt();
		if (ron > 0) {
			double usd;
			usd = ron / 4.0908;
			System.out.println("Suma rezultata dupa convertire este: " + decimalFormat.format(usd) + " dolari\n");
		} else
			System.err.println("Nu puteti sa convertiti " + ron + " ron!");
	}

	public void convertUsdToRon() {
		System.out.print("Introduceti suma: ");
		int usd = scanner.nextInt();
		if (usd > 0) {
			double ron;
			ron = usd * 4.0908;
			System.out.println("Suma rezultata dupa convertire este: " + decimalFormat.format(ron) + " ron");
		} else
			System.err.println("Nu puteti sa convertiti " + usd + " dolari!");
	}

	@SuppressWarnings("unchecked")
	public void totalSumInEUR() {
		List<Deposit> list = session.createNativeQuery(
				"SELECT sum(truncate((Deposit_RON*4.8849)+Deposit_EUR+(Deposit_GBP*1.16551)+(Deposit_USD*0.840084),2)) from Deposit")
				.list();
		System.err.print("Sold total EUR: " + list + "\n");
	}
}

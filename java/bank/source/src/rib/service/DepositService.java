package rib.service;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import rib.dao.DepositDao;
import rib.entity.Deposit;
import rib.util.HibernateUtils;

@SuppressWarnings("rawtypes")
public class DepositService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private Query query;
	DepositDao depositDao = new DepositDao();
	Scanner scanner = new Scanner(System.in);

	public DepositService() {
		super();
	}

	public void addRon(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.addRon(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void addEur(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.addEur(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void addUsd(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.addUsd(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void addGbp(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.addGbp(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void createDeposit(Deposit deposit) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"INSERT INTO Deposit (Deposit_RON, Deposit_EUR,Deposit_USD,Deposit_GBP) values(?1, ?2, ?3, ?4)");
		System.out.println("Daca un parametru este momentan indisponibil, introduceti \"0\"");
		System.out.print("Introduceti suma in RON: ");
		deposit.setRon(scanner.nextInt());
		System.out.print("Introduceti suma in EUR: ");
		deposit.setEur(scanner.nextInt());
		System.out.print("Introduceti suma in USD: ");
		deposit.setUsd(scanner.nextInt());
		System.out.print("Introduceti suma in GBP: ");
		deposit.setGbp(scanner.nextInt());

		query.setParameter(1, deposit.getRon() == 0 ? 0 : deposit.getRon());
		query.setParameter(2, deposit.getEur() == 0 ? 0 : deposit.getEur());
		query.setParameter(3, deposit.getUsd() == 0 ? 0 : deposit.getUsd());
		query.setParameter(4, deposit.getGbp() == 0 ? 0 : deposit.getGbp());
		System.err.println("Depozitul s-a creat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void withdrawalRon(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.withdrawRon(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void withdrawalEur(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.withdrawEur(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void withdrawalUsd(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.withdrawUsd(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void withdrawalGbp(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.withdrawGbp(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public List<Deposit> showAll() {
		depositDao.openCurrentSession();
		List<Deposit> list = depositDao.showAll();
		depositDao.closeCurrentSession();
		return list;
	}

	public List<Deposit> showPersonalDeposit() {
		depositDao.openCurrentSession();
		List<Deposit> list = depositDao.showPersonalDeposit();
		depositDao.closeCurrentSession();
		return list;
	}

	public List<Deposit> selectTheLastDepositCreated() {
		depositDao.openCurrentSession();
		List<Deposit> list = depositDao.selectTheLastDepositCreated();
		depositDao.closeCurrentSession();
		return list;
	}

	public void convertAllMoneyToRON() {
		depositDao.openCurrentSession();
		depositDao.convertAllMoneyToRON();
		depositDao.closeCurrentSession();
	}

	public void convertAllMoneyToEUR() {
		depositDao.openCurrentSession();
		depositDao.convertAllMoneyToEUR();
		depositDao.closeCurrentSession();
	}

	public void convertRonToEurInAccount(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.convertRonToEurInAccount(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void convertRonToEur() {
		depositDao.convertRonToEur();
	}

	public void convertEurToRon() {
		depositDao.convertEurToRon();
	}

	public void convertRonToUsd() {
		depositDao.convertRonToUsd();
	}

	public void convertUsdToRon() {
		depositDao.convertUsdToRon();
	}

	public void convertRonToUsdInAccount(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.convertRonToUsdInAccount(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void convertEurToRonInAccount(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.convertEurToRonInAccount(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void convertUsdToRonInAccount(Deposit deposit) {
		depositDao.openCurrentSessionwithTransaction();
		depositDao.convertUsdToRonInAccount(deposit);
		depositDao.closeCurrentSessionwithTransaction();
	}

	public void totalSumInEUR() {
		depositDao.openCurrentSession();
		depositDao.totalSumInEUR();
		depositDao.closeCurrentSession();
	}
}

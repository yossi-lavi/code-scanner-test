package rib.service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import rib.dao.BankAgencyDao;
import rib.entity.Address;
import rib.entity.BankAccount;
import rib.entity.BankAgency;
import rib.entity.CustomerAdvisors;
import rib.util.HibernateUtils;

public class BankAgencyService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private BankAgencyDao bankAgencyDao;
	private Query query;
	Address address = new Address();
	Scanner scanner = new Scanner(System.in);
	BankAccount bankAccounts = new BankAccount();
	CustomerAdvisors customerAdvisors = new CustomerAdvisors();

	public BankAgencyService() throws Exception {
		super();
		this.bankAgencyDao = new BankAgencyDao();
	}

	public void addBankAgency(BankAgency bankAgency) {
		Session session = sessionFactory.openSession();
		Query query = session.createNativeQuery(
				"INSERT INTO BankAgency(ID, LunchBreak, OperatingMorningHours, OperatingAfternoonHours, PhoneBankNumber, address_No) values(?1,?2,?3,?4,?5,?6)");
		System.err.println("Daca un parametru este momentan indisponibil, introduceti \"-\"");
		System.out.print("ID: ");
		bankAgency.setId(scanner.next());
		System.out.print("Pauza de masa: ");
		bankAgency.setLunchBreak(scanner.next());
		System.out.print("Programul de dimineata: ");
		bankAgency.setOperatingMorningHours(scanner.next());
		System.out.print("Programul de dupa-amiaza: ");
		bankAgency.setOperatingAfternoonHours(scanner.next());
		System.out.print("Telefon: ");
		bankAgency.setPhoneBankNumber(scanner.next());
		System.err.println("Daca adresa este momentan indisponibila introduceti \"0\"");
		System.out.print("Numarul adresei: ");
		address.setNo(scanner.nextInt());

		query.setParameter(1, bankAgency.getId());
		query.setParameter(2, bankAgency.getLunchBreak());
		query.setParameter(3,
				bankAgency.getOperatingMorningHours().equals("-") ? null : bankAgency.getOperatingMorningHours());
		query.setParameter(4,
				bankAgency.getOperatingAfternoonHours().equals("-") ? null : bankAgency.getOperatingAfternoonHours());
		query.setParameter(5, bankAgency.getPhoneBankNumber().equals("-") ? null : bankAgency.getPhoneBankNumber());
		query.setParameter(6, address.getNo() == 0 ? null : address.getNo());
		System.err.println("Agentia bancara a fost adaugata cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public List<BankAgency> findBankAgencyByCity(){
		bankAgencyDao.openCurrentSessionwithTransaction();
		List<BankAgency> list = bankAgencyDao.findBankAgencyByCity();
		bankAgencyDao.closeCurrentSessionwithTransaction();
		return list;
	}
	
	public List<BankAgency> findBankAgencyByCounty(){
		bankAgencyDao.openCurrentSessionwithTransaction();
		List<BankAgency> list = bankAgencyDao.findBankAgencyByCounty();
		bankAgencyDao.closeCurrentSessionwithTransaction();
		return list;
	}
	
	public BankAgency findBankAgencyByNo() {
		bankAgencyDao.openCurrentSession();
		BankAgency bankAgency = bankAgencyDao.findByNo();
		bankAgencyDao.closeCurrentSession();
		return bankAgency;
	}

	public void deleteBankAgency(BankAgency bankAgency) {
		bankAgencyDao.openCurrentSessionwithTransaction();
		bankAgencyDao.delete(bankAgency);
		bankAgencyDao.closeCurrentSessionwithTransaction();
	}

	public void deleteAllBankAgency() {
		bankAgencyDao.openCurrentSessionwithTransaction();
		bankAgencyDao.deleteAll();
		bankAgencyDao.closeCurrentSessionwithTransaction();
	}

	public List<BankAgency> orderBankByIdAsc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByIdAsc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public List<BankAgency> orderBankByIdDesc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByIdDesc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public List<BankAgency> orderBankByOperatingMorningHoursAsc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByOperatingMorningHoursAsc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public List<BankAgency> orderBankByOperatingMorningHoursDesc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByOperatingMorningHoursDesc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public List<BankAgency> orderBankByNoAsc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByNoAsc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public List<BankAgency> orderBankByNoDesc() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.orderByNoDesc();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public void saveBankAgency(BankAgency bankAgency) {
		bankAgencyDao.openCurrentSessionwithTransaction();
		bankAgencyDao.persist(bankAgency);
		bankAgencyDao.closeCurrentSessionwithTransaction();
	}

	public List<BankAgency> showAllBankAgency() {
		bankAgencyDao.openCurrentSession();
		List<BankAgency> list = bankAgencyDao.showAll();
		bankAgencyDao.closeCurrentSession();
		return list;
	}

	public void updateBankAgency(BankAgency bankAgency) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"UPDATE BankAgency SET PhoneBankNumber=?1, OperatingMorningHours=?2, OperatingAfternoonHours=?3, LunchBreak=?4, Address_no=?5 where ID=?6");
		System.err.println("Daca un parametru este momentan indisponibil, introduceti \"-\"");
		System.out.print("Telefon: ");
		bankAgency.setPhoneBankNumber(scanner.next());
		System.out.print("Programul de dimineata: ");
		bankAgency.setOperatingMorningHours(scanner.next());
		System.out.print("Programul de dupa-amiaza: ");
		bankAgency.setOperatingAfternoonHours(scanner.next());
		System.out.print("Pauza de masa: ");
		bankAgency.setLunchBreak(scanner.next());
		System.err.println("Daca adresa este momentan indisponibila introduceti \"0\"");
		System.out.print("Numarul adresei: ");
		address.setNo(scanner.nextInt());
		System.out.print("Introduceti ID-ul bancii pe care doriti sa o modificati: ");
		bankAgency.setId(scanner.next());

		query.setParameter(1, bankAgency.getPhoneBankNumber().equals("-") ? null : bankAgency.getPhoneBankNumber());
		query.setParameter(2,
				bankAgency.getOperatingMorningHours().equals("-") ? null : bankAgency.getOperatingMorningHours());
		query.setParameter(3,
				bankAgency.getOperatingAfternoonHours().equals("-") ? null : bankAgency.getOperatingAfternoonHours());
		query.setParameter(4, bankAgency.getLunchBreak().equals("-") ? null : bankAgency.getLunchBreak());
		query.setParameter(5, address.getNo() == 0 ? null : address.getNo());
		query.setParameter(6, bankAgency.getId());
		System.err.println("Datele agentiei bancare s-au actualizat cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}
}

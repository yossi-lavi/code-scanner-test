package rib.dao;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import rib.entity.BankAgency;
import rib.util.HibernateUtils;

public class BankAgencyDao implements EntityDao<BankAgency, Integer> {

	private Session session = HibernateUtils.getSessionFactory().getCurrentSession();

	private Transaction transaction;
	Scanner scanner = new Scanner(System.in);

	public BankAgencyDao() {
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
	public List<BankAgency> findBankAgencyByCity() {
		System.out.print("Introduceti orasul: ");
		String city = scanner.next();
		List<BankAgency> list = session
				.createQuery("from BankAgency where address_No in (SELECT no from Address where City=:city)")
				.setParameter("city", city).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit nicio agentie bancara in " + city.toUpperCase() + "!");
			break;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<BankAgency> findBankAgencyByCounty() {
		System.out.print("Introduceti judetul: ");
		String county = scanner.next();
		List<BankAgency> list = session
				.createQuery("from BankAgency where address_No in (SELECT no from Address where County=:county)")
				.setParameter("county", county).list();
		while (list.isEmpty()) {
			System.err.println("Nu s-a gasit nicio agentie bancara in " + county.toUpperCase() + "!");
			break;
		}
		return list;
	}

	public BankAgency findByNo() {
		System.out.print("Introduceti numarul agentiei pe care o cautati: ");
		int no = scanner.nextInt();
		return session.get(BankAgency.class, no);
	}

	public void delete(BankAgency entity) {
		session.delete(entity);
	}

	@Override
	public void deleteAll() {
		session.createQuery("delete from BankAgency").executeUpdate();
	}

	public List<BankAgency> orderByIdAsc() {
		return session.createQuery("FROM BankAgency order by ID ASC", BankAgency.class).list();
	}

	public List<BankAgency> orderByIdDesc() {
		return session.createQuery("FROM bankagency order by ID DESC", BankAgency.class).list();
	}

	public List<BankAgency> orderByOperatingMorningHoursAsc() {
		return session.createQuery("FROM bankAgency order by operatingMorningHours ASC", BankAgency.class).list();
	}

	public List<BankAgency> orderByOperatingMorningHoursDesc() {
		return session.createQuery("FROM BankAgency order by operatingMorningHours DESC", BankAgency.class).list();
	}

	@Override
	public List<BankAgency> orderByNoAsc() {
		return session.createQuery("FROM BankAgency Order by NO ASC", BankAgency.class).list();
	}

	@Override
	public List<BankAgency> orderByNoDesc() {
		return session.createQuery("FROM BankAgency Order by NO DESC", BankAgency.class).list();
	}

	@Override
	public void persist(BankAgency entity) {
		session.save(entity);
	}

	@Override
	public List<BankAgency> showAll() {
		return session.createQuery("FROM BankAgency", BankAgency.class).list();
	}
}

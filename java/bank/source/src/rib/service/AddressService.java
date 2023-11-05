package rib.service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import rib.dao.AddressDao;
import rib.entity.Address;
import rib.entity.Client;
import rib.util.HibernateUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AddressService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private Query query;
	AddressDao addressDao = new AddressDao();
	Scanner scanner = new Scanner(System.in);
	Client client = new Client();
	private Logger logger = LogManager.getLogger(AddressService.class.getName());
	public AddressService() throws Exception {
		super();
		this.addressDao = new AddressDao();
	}

	public void addAddress(Address address) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"INSERT INTO Address(City, County, HouseNumber, BlockOfFlatsNumber, Apartment, Street) values(?1,?2,?3,?4,?5,?6)");
		System.out.println("Daca un parametru este momentan indisponibil, introduceti \"-\"");
		System.out.print("Oras: ");
		System.out.print("address: " + address.toString());
		address.setCity(scanner.next());
		System.out.print("Judet: ");
		address.setCounty(scanner.next());
		System.out.print("Numar casa: ");
		address.setHouseNumber(scanner.next());
		System.out.print("Numar bloc: ");
		address.setBlockOfFlatsNumber(scanner.next());
		System.out.print("Apartament: ");
		address.setApartment(scanner.next());
		System.out.print("Strada: ");
		scanner.nextLine();
		address.setStreet(scanner.nextLine());

		query.setParameter(1, address.getCity());
		query.setParameter(2, address.getCounty());
		query.setParameter(3, address.getHouseNumber().equals("-") ? null : address.getHouseNumber());
		query.setParameter(4, address.getBlockOfFlatsNumber().equals("-") ? null : address.getBlockOfFlatsNumber());
		query.setParameter(5, address.getApartment().equals("-") ? null : address.getApartment());
		query.setParameter(6, address.getStreet());
		System.err.println("Adresa adaugata cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public void deleteAddress(Address address) {
		addressDao.openCurrentSessionwithTransaction();
		addressDao.delete(address);
		addressDao.closeCurrentSessionwithTransaction();
	}

	public void deleteAllAddresses() {
		addressDao.openCurrentSessionwithTransaction();
		addressDao.deleteAll();
		addressDao.closeCurrentSessionwithTransaction();
	}

	public List<Address> orderAddressesByCountyAsc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByCountyAsc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public List<Address> orderAddressesByCountyDesc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByCountyDesc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public List<Address> orderAddressesByCityAsc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByCityAsc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public List<Address> orderAddressesByCityDesc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByCityDesc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public List<Address> orderAddressesByNoAsc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByNoAsc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public List<Address> orderAddressesByNoDesc() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.orderByNoAsc();
		addressDao.closeCurrentSession();
		return lista;
	}

	public void saveAddress(Address address) {
		addressDao.openCurrentSessionwithTransaction();
		addressDao.persist(address);
		addressDao.closeCurrentSessionwithTransaction();
		logger.info("saved address" + address.toString());
	}

	public List<Address> showAllAddresses() {
		addressDao.openCurrentSession();
		List<Address> lista = addressDao.showAll();
		addressDao.closeCurrentSession();
		return lista;
	}

	public void updateAddress(Address address) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		query = session.createNativeQuery(
				"UPDATE Address SET County=:county, City=:city, Street=:street, HouseNumber=:houseNumber, BlockOfFlatsNumber=:blockOfFlatsNumber, Apartment=:apartment where No in (SELECT Address_No from Client where ID=:id)");
		System.out.println("Daca un parametru este momentan indisponibil, introduceti \"-\"");
		System.out.print("Judet: ");
		address.setCounty(scanner.next());
		System.out.print("Oras: ");
		address.setCity(scanner.next());
		System.out.print("Strada: ");
		scanner.nextLine();
		address.setStreet(scanner.nextLine());
		System.out.print("Numar casa: ");
		address.setHouseNumber(scanner.next());
		System.out.print("Numar bloc: ");
		address.setBlockOfFlatsNumber(scanner.next());
		System.out.print("Apartament: ");
		address.setApartment(scanner.next());
		System.out.print("ID-ul clientului a carui adresa o modificati: ");
		client.setId(scanner.next());

		query.setParameter("county", address.getCounty());
		query.setParameter("city", address.getCity());
		query.setParameter("street", address.getStreet().equals("-") ? null : address.getStreet());
		query.setParameter("houseNumber", address.getHouseNumber().equals("-") ? null : address.getHouseNumber());
		query.setParameter("blockOfFlatsNumber",
				address.getBlockOfFlatsNumber().equals("-") ? null : address.getBlockOfFlatsNumber());
		query.setParameter("apartment", address.getApartment().equals("-") ? null : address.getApartment());
		query.setParameter("id", client.getId());
		System.err.println("Adresa actualizata cu succes!");
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public List<Address> selectTheLastAddressCreated() {
		addressDao.openCurrentSession();
		List<Address> list = addressDao.selectTheLastAddressCreated();
		addressDao.closeCurrentSession();
		return list;
	}
}

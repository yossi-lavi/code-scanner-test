package rib.dao;

import java.util.List;

public interface EntityDao<Entity, No> {

	public void deleteAll();

	public List<Entity> orderByNoAsc();

	public List<Entity> orderByNoDesc();

	public void persist(Entity entity);

	public List<Entity> showAll();
}
package com.simpsoft.salesCommission.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsoft.salesCommission.app.model.Role;

@Component
public class RoleAPI {

	@Autowired
	private static SessionFactory sessionFactory;
	
	private static final Logger logger = Logger.getLogger(RoleAPI.class);

	public void setSessionFactory(SessionFactory factory) {
		sessionFactory = factory;
	}

	/* ........getRole...... */

	public Role getRole(Long RoleID) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		return (Role) session.get(Role.class, RoleID);
	}

	/**
	 * 
	 * @param role
	 */
	public long createRole(Role role) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Role newRole = new Role();
		try {
			tx = session.beginTransaction();
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			newRole.setReportsTo(role.getReportsTo());
			newRole.setTarget(role.getTarget());
			session.save(newRole);
			tx.commit();
			logger.debug("CREATED AN ROLE INTO DATABASE" + newRole);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return newRole.getId();
	}

	/**
	 * Method for getting list of roles
	 * 
	 * @return
	 */
	public List<Role> listOfRoles() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		List roles = session.createQuery("FROM Role").list();
		for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
			Role role = (Role) iterator.next();
			logger.debug("GET THE RULE DETAILS FROM DATABASE" + role.getRoleName() + "REPORTS TO "+role.getReportsTo());
			
		}
		return roles;
	}

	
	
	public Role searchRoleByName(String roleName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Role roleResult = new Role();
		try {
			tx = session.beginTransaction();

			/*
			 * Criteria cr = session.createCriteria(Role.class);
			 * cr.add(Restrictions.like("reportsTo", roleName)); List<Role>
			 * results = (List<Role>) cr.list(); roleResult = (Role)
			 * results.get(0);
			 */
			List roles = session.createQuery("FROM Role").list();
			for (Iterator iterator = roles.iterator(); iterator.hasNext();) {

				Role role = (Role) iterator.next();
				if (roleName.equals(role.getRoleName())) {
					roleResult.setId(role.getId());
					roleResult.setRoleName(role.getRoleName());
					roleResult.setDescription(role.getDescription());
					roleResult.setReportsTo(role.getReportsTo());
				}
			}

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return roleResult;
	}

	public Role searchRole(String roleName) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Role> roleList = new ArrayList<>();
		try {
			tx = session.beginTransaction();
			Criteria crit = session.createCriteria(Role.class);
			crit.add(Restrictions.eq("roleName", roleName));
			roleList = crit.list();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return roleList.get(0);
	}

	/* ............. delete role........ */

	public void deleteRole(Integer RoleID) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role role = (Role) session.get(Role.class, RoleID);
			session.delete(role);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void editRole(Role role) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role newRole = (Role) session.get(Role.class, role.getId());
			newRole.setRoleName(role.getRoleName());
			newRole.setDescription(role.getDescription());
			newRole.setReportsTo(role.getReportsTo());
			newRole.setTarget(role.getTarget());
			session.merge(newRole);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}
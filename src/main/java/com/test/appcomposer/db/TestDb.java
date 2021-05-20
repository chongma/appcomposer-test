package com.test.appcomposer.db;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.test.appcomposer.entities.jpa.Test;

@ApplicationScoped
public class TestDb {

	@Inject
	private EntityManager em;

	public List<Test> selectTests() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Test> cq = cb.createQuery(Test.class);
		Root<Test> root = cq.from(Test.class);
		cq.select(root);
		cq.distinct(true);
		TypedQuery<Test> query = em.createQuery(cq);
		// log.fine(query.unwrap(org.apache.openjpa.persistence.QueryImpl.class).getQueryString());
		List<Test> list = query.getResultList();
		return list;
	}

	public void create(Test item) {
		em.persist(item);
	}

	public void flush() {
		em.flush();
	}

}

package com.test.appcomposer.controllers;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.test.appcomposer.db.TestDb;
import com.test.appcomposer.entities.jpa.Test;

@ApplicationScoped
public class TestController {

	@Inject
	private TestDb testDb;

	@Transactional
	public String selectHello() {
		testDb.create(new Test("test 1"));
		testDb.create(new Test("test 2"));
		testDb.flush();
		List<Test> list = testDb.selectTests();
		return "Hello world " + list.size();
	}

}

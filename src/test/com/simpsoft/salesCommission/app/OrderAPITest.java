package com.simpsoft.salesCommission.app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simpsoft.salesCommission.app.api.OrderAPI;
import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.model.OrderRoster;
import com.simpsoft.salesCommission.app.model.Role;
import com.simpsoft.salesCommission.app.model.State;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class OrderAPITest {
	@Autowired
	private OrderAPI orderAPI;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

/*	@Test
	public void testCreateState() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateAddress() {
		fail("Not yet implemented");
	}
*/
/*	@Test
	public void testSearchState() {
		State state = orderAPI.searchState("West Bengal");
		Assert.assertEquals(800, state.getStateCode());
	} */
	
	@Test
	public void testListofOrderRoster() {
		List <OrderRoster> orderRosterList = orderAPI.listOfOrderRosters();
		Assert.assertEquals(4, orderRosterList.size());
	}

}

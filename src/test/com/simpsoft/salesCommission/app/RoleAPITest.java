package com.simpsoft.salesCommission.app;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simpsoft.salesCommission.app.api.RoleAPI;
import com.simpsoft.salesCommission.app.model.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class RoleAPITest {
	@Autowired
	private RoleAPI roleAPI;


	/*	@Test
	public void testCreateRole() {
       
        Role role1 = new Role();
        role1.setRoleName("CEO");
        role1.setDescription("Bad");
      //  role1.setReportsTo("CEO");
       Long id = roleAPI.createRole(role1);
        Role role = roleAPI.getRole(id);
		Assert.assertEquals("CEO", role.getRoleName());
	} */
	
	/*	@Test
	public void testListOfRoles() {
		List<Role> role= roleAPI.listOfRoles();
		  Assert.assertEquals(2, role.size());
	} 
			@Test
	public void testGetRoles() {
		Role role = roleAPI.searchRoleByName("VP2");
		Assert.assertEquals("Bad", role.getDescription());
	}*/
			
			@Test
			public void testSearchRoleByName() {
				Role role = roleAPI.searchRoleByName("VP Finance");
				Assert.assertEquals("Good", role.getDescription());
			}
/*
@Test
	public void testEditRole() {
		Role e = new Role();
		e.setRoleName("cc");
		e.setDescription("dd");
		// e.setReportTo("ff");
		roleAPI.createRole(e);
		Role emp = roleAPI.getRole(e.getId());
		emp.setRoleName("joydeepda");
		roleAPI.editRole(emp);
		Role emp1 = roleAPI.getRole(emp.getId());
		Assert.assertEquals("joydeepda", emp1.getRoleName());

	} */

}

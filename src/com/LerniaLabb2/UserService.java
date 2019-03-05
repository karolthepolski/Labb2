package com.LerniaLabb2;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;  
@Path("/UserService") 

public class UserService {  
	
	UserDao userDao = new UserDao();
	private static final String SUCCESS_RESULT="Result: Success";
	private static final String FAILURE_RESULT="Result: Failure";
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers(){
	return userDao.getAllUsers();
	}

	@GET
	@Path("/users/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("userid") int userid){
		return userDao.getUser(userid);
	}	

	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createUser(@FormParam("name") String name, @FormParam("profession") String profession, @Context HttpServletResponse servletResponse) throws IOException{
		System.out.println(servletResponse);
		User user = new User(0, name, profession);
		int result = userDao.createUser(user);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}

	@PUT
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateUser(@FormParam("userid") String id, @FormParam("name") String name, @FormParam("profession") String profession, @Context HttpServletResponse servletResponse) throws IOException{
		User user = new User(Integer.valueOf(id), name, profession);
		int result = userDao.updateUser(user);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}

	@DELETE
	@Path("/users/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@PathParam("userid") int userid){
		int result = userDao.deleteUser(userid);
		if(result == 1){
			return SUCCESS_RESULT;
		}
		return FAILURE_RESULT;
	}

	@OPTIONS
	@Path("/users")
	@Produces(MediaType.APPLICATION_XML)
	public String getSupportedOperations(){
		return "<operations>GET, PUT, POST, DELETE</operations>";
	}   
}

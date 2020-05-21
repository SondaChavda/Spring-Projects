package com.restapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restapi.model.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{

	private final static  String SALARY="salary";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Employee addEmployee(Employee employee) throws Exception {
		
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		try
		{
			session.save(employee);
			t.commit();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			session.close();	
		}
		
		return employee;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmployee()
	{
		List<Employee> employees=new ArrayList<Employee>();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		
		String str="from Employee";
		employees=session.createQuery(str).list();
		t.commit();
		session.close();
		return employees;
	}
	
	@Override
	public void removeEmployee(int id) throws Exception
	{
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		try
		{
			Employee employee=session.load(Employee.class, new Integer(id));
			if(employee.getId() == id)
			{
				session.delete(employee);
			}
			t.commit();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			session.close();	
		}
		
	}
	
	@Override
	public int updateEmployeeDetails(int id,Employee employee) throws Exception
	{
		int updateCount=0;
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		String str="update Employee set name= :name, gender= :gender, salary= :salary where id= :id";
		try
		{
			Query q=session.createQuery(str);
			q.setParameter("id", id);
			q.setParameter("name", employee.getName());
			q.setParameter("gender", employee.getGender());
			q.setParameter(SALARY, employee.getSalary());
			updateCount=q.executeUpdate();
			t.commit();
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		finally
		{
			session.close();	
		}
		
		return updateCount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> searchEmployee(String column,String searchValue)
	{
		List<Employee> employees=new ArrayList<Employee>();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		Criteria cr=session.createCriteria(Employee.class);
		cr.add(Restrictions.like(column, "%"+searchValue+"%"));
		employees=cr.list();
		t.commit();
		session.close();
		return employees;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> filterSalary(long min, Long max, String order, Integer startPos, Integer endPos)
	{
		List<Employee> employees=new ArrayList<Employee>();
		Session session=sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		Criteria cr=session.createCriteria(Employee.class);
		if(min>0)
			cr.add(Restrictions.ge(SALARY, min));
		if(max>0)
			cr.add(Restrictions.le(SALARY, max));
		if("Asc".equalsIgnoreCase(order))
			cr.addOrder(Order.asc(SALARY));
		else
			cr.addOrder(Order.desc(SALARY));
		if(startPos > 0)
			cr.setFirstResult(startPos);
		if(endPos > 0)
			cr.setMaxResults(endPos);
		
		employees=cr.list();
		t.commit();
		session.close();
		return employees;
	}
}

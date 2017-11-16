package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Instructor;
import com.model.InstructorDetail;

public class OneToOneDelete {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration()
												.configure("hibernate.cfg.xml")
												.addAnnotatedClass(InstructorDetail.class)
												.addAnnotatedClass(Instructor.class)
												.buildSessionFactory();
		
		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();
			
			Instructor instructor = session.get(Instructor.class,1);
			
			System.out.println("Instructor Found: " + instructor );
			
			if(instructor != null) {
				
				System.out.println("About to delete instructor");
				
				session.delete(instructor);
			}
			

			session.getTransaction().commit();
			
			System.out.println("One To One Mapping Delete Done");

		} catch (Exception e) {
			factory.close();
		}

	}

}

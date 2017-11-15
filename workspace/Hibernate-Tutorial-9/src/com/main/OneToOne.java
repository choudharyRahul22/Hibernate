package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Instructor;
import com.model.InstructorDetail;

public class OneToOne {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration()
												.configure("hibernate.cfg.xml")
												.addAnnotatedClass(InstructorDetail.class)
												.addAnnotatedClass(Instructor.class)
												.buildSessionFactory();
		
		InstructorDetail insDetail = new InstructorDetail("thecrazzyrahul@youtube", "learning new things");
		Instructor ins = new Instructor("Rahul", "Choudhary", "rahul@gmail.com", insDetail);

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();
			
			session.save(ins);

			session.getTransaction().commit();
			
			System.out.println("One To One Mapping Done");

		} catch (Exception e) {
			factory.close();
		}

	}

}
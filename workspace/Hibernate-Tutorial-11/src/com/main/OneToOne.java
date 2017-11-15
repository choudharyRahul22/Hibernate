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
		
		InstructorDetail insDetail1 = new InstructorDetail("thecrazzyrahul@youtube", "learning new things");
		Instructor ins1 = new Instructor("Rahul", "Choudhary", "rahul@gmail.com", insDetail1);
		
		InstructorDetail insDetail2 = new InstructorDetail("shalu@youtube", "cooking new things");
		Instructor ins2 = new Instructor("Shalu", "Baliyan", "shalu@gmail.com", insDetail2);

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();
			
			session.save(ins1);
			session.save(ins2);

			session.getTransaction().commit();
			
			System.out.println("One To One Mapping Done");

		} catch (Exception e) {
			factory.close();
		}

	}

}

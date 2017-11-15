package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Instructor;
import com.model.InstructorDetail;

public class OneToOneBidirectional {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Instructor.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();

			InstructorDetail instructorDetail = session.get(InstructorDetail.class, 1);

			System.out.println(instructorDetail);

			System.out.println("Instructor from Instructor Detail : " + instructorDetail.getInstructor());

			System.out.println("One To One Bidirectional Mapping Done");

			session.delete(instructorDetail);

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}

	}

}

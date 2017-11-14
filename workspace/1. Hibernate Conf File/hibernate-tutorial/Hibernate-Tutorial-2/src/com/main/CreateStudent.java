package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;

public class CreateStudent {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		try {

			Session session = factory.getCurrentSession();

			Student student = new Student("Rahul", "Choudhary", "thecrazzyrahul@gmail.com");

			session.beginTransaction();

			session.save(student);

			session.getTransaction().commit();

			System.out.println("Student is Persist To Database");

		} catch (Exception e) {
			factory.close();
		}

	}

}
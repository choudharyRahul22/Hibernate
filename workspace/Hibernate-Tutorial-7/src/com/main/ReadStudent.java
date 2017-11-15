package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;

public class ReadStudent {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		try {

			Session session = factory.getCurrentSession();

			session.beginTransaction();

			Student readStudent = session.get(Student.class, 2);

			System.out.println(readStudent);

		} catch (Exception e) {
			factory.close();
		}

	}

}

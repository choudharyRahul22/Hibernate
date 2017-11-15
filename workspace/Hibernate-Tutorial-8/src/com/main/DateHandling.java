package com.main;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;
import com.util.DateUtils;

public class DateHandling {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// create a session
		Session session = factory.getCurrentSession();

		try {
			// create a student object
			System.out.println("creating a new student object ...");

			String theDateOfBirthStr = "22/05/1990";

			Date theDateOfBirth = DateUtils.parseDate(theDateOfBirthStr);
			System.out.println(theDateOfBirthStr);

			Student temoStudent = new Student("Rahul", "Choudhary", "thecrazzy@gamil.com", theDateOfBirth);
			
			// start transaction
			session.beginTransaction();

			// save the student object
			System.out.println("Saving the student ...");
			session.save(temoStudent);

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Success!");
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			factory.close();
		}
	}

}

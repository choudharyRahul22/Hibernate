package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;

public class CreateStudent {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		Student student = new Student("Rahul", "Choudhary", "thecrazzyrahul@gmail.com");
		
		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			session.save(student);

			session.getTransaction().commit();

			System.out.println("Saved Student , Genrated Id : " + student.getId());

			System.out.println("Student is Persist To Database");

		} catch (Exception e) {
			factory.close();
		}
		

		try {

		    session = factory.getCurrentSession();

			session.beginTransaction();

			Student readStudent = session.get(Student.class, student.getId());
			
			session.getTransaction().commit();

			System.out.println(readStudent);

		} catch (Exception e) {
			factory.close();
		}

	}

}

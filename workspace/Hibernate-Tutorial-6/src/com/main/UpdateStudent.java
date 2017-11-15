package com.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;

public class UpdateStudent {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Student student1 = new Student("Rahul", "Choudhary", "thecrazzyrahul@gmail.com");

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			session.save(student1);

			session.getTransaction().commit();

			System.out.println("Saved Student , Genrated Id : " + student1.getId());

			System.out.println("Student is Persist To Database");

		} catch (Exception e) {
			factory.close();
		}

		System.out.println("********************************************************");
		try {

			session = factory.getCurrentSession();

			session.beginTransaction();

			Student student = session.get(Student.class, 1);
			System.out.println(student);

			System.out.println("Updating Student setting firstName = Ravi using setter");
			student.setFirstName("Ravi");

			session.getTransaction().commit();

		} catch (Exception e) {
			factory.close();
		}

		
		System.out.println("********************************************************");
		try {

			session = factory.getCurrentSession();

			session.beginTransaction();

			Student student = session.get(Student.class, 1);
			System.out.println(student);

			System.out.println("Updating Student setting firstName = Shalu using HQL");
			session.createQuery("update Student set firstName='Shalu' where id=1").executeUpdate();

			session.getTransaction().commit();

		} catch (Exception e) {
			factory.close();
		}

	}

}

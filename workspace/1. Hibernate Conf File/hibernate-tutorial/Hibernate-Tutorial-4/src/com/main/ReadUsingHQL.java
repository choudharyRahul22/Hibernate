package com.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;


public class ReadUsingHQL {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class)
				.buildSessionFactory();
		
		Student student1 = new Student("Rahul", "Choudhary", "thecrazzyrahul@gmail.com");
		Student student2 = new Student("Ravi", "Malik", "thecrazzyrahul@gmail.com");
		Student student3 = new Student("Deepak", "Singh", "thecrazzyrahul@gmail.com");
		
		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			session.save(student1);
			session.save(student2);
			session.save(student3);

			session.getTransaction().commit();

			System.out.println("Saved Student , Genrated Id : Student-1. " + student1.getId() + " Student-2. " + student2.getId() + " Student-3. "+ student3.getId());

			System.out.println("Student is Persist To Database");

		} catch (Exception e) {
			factory.close();
		}
		
		System.out.println("********************************************************");
		try {

		    session = factory.getCurrentSession();

			session.beginTransaction();

			List<Student> students = session.createQuery("from Student").getResultList();
			
			session.getTransaction().commit();

			System.out.println(students);

		} catch (Exception e) {
			factory.close();
		}
		
		System.out.println("********************************************************");
		try {

		    session = factory.getCurrentSession();

			session.beginTransaction();

			List<Student> students = session.createQuery("from Student s where s.firstName = 'Rahul' ").getResultList();
			
			session.getTransaction().commit();

			System.out.println(students);

		} catch (Exception e) {
			factory.close();
		}
		
		System.out.println("********************************************************");
		try {

		    session = factory.getCurrentSession();

			session.beginTransaction();

			List<Student> students = session.createQuery("from Student s where s.firstName = 'Rahul' or s.lastName = 'Singh' ").getResultList();
			
			session.getTransaction().commit();

			System.out.println(students);

		} catch (Exception e) {
			factory.close();
		}
		
		System.out.println("********************************************************");
		try {

		    session = factory.getCurrentSession();

			session.beginTransaction();

			List<Student> students = session.createQuery("from Student s where email like '%@gmail.com' ").getResultList();
			
			session.getTransaction().commit();

			System.out.println(students);

		} catch (Exception e) {
			factory.close();
		}


	}

}

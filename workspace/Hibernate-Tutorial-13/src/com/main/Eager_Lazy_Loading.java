package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Course;
import com.model.Instructor;
import com.model.InstructorDetail;

public class Eager_Lazy_Loading {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(Course.class).buildSessionFactory();

		InstructorDetail instructorDetail = new InstructorDetail("thecrazzyrahul@youtube", "learning new things");
		Instructor instructor = new Instructor("Rahul", "Choudhary", "rahul@gmail.com", instructorDetail);

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			session.save(instructor);

			Instructor instructor2 = session.get(Instructor.class, 1);
			System.out.println(instructor2);

			Course course = new Course();
			course.setTitle("Cricket Instructor");

			Course course2 = new Course();
			course2.setTitle("Football Instructor");

			instructor2.add(course);
			instructor2.add(course2);
			System.out.println(instructor2);

			session.save(course);
			session.save(course2);

			Instructor instructor3 = session.get(Instructor.class, 1);
			System.out.println("Debug Instructor: " + instructor3);
			System.out.println("Debug Courses : " + instructor3.getCourses());

			session.getTransaction().commit();

			System.out.println("One To Many Mapping Done");

		} catch (Exception e) {
			session.close();
			factory.close();
		}
		
		System.out.println("*******************************************");
		session = null;
		try {

			session = factory.getCurrentSession();
			System.out.println(session);
			session.beginTransaction();

			Instructor instructor3 = session.get(Instructor.class, 1);

			System.out.println("Debug Instructor: " + instructor3);

			System.out.println("Debug Courses : " + instructor3.getCourses());
			
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
			factory.close();
		}

	}

}

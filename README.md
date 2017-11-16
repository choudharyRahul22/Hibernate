# Hibernate
Hibernate Under The Hood.

Hibernate:
----------
Double Click on DB ifinances on Left than run the script.

ORM:
----
Java object and Database relational table Mapping.
Hibernate implements JPA.

1. Hibernate Configuration
2. Mapping Metadata
3. Create Session & Invoke Persistance Methods

MYSQL:
------
1. Create User
2. Grant All Privlages
3. Login with that user
4. Create Database
5. Create Table

Start:
------
1. Hibernate Configuration File
2. Annotate Java Class
3. Java Code to perform Database Operation.

Hibernate Configuration File:
-----------------------------
Hibernate-Tutorial-1

Use to tell how to connect to DB.
Hibernate uses JDBC in background.

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>

    <session-factory>

        <!-- JDBC Database connection settings -->
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/hb_student_tracker?useSSL=false</property>
        <property name="connection.username">hbstudent</property>
        <property name="connection.password">hbstudent</property>

        <!-- JDBC connection pool settings ... using built-in test pool -->
        <property name="connection.pool_size">1</property>

        <!-- Select our SQL dialect -->
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        <!-- Echo the SQL to stdout -->
        <property name="show_sql">true</property>

		<!-- Set the current session context -->
		<property name="current_session_context_class">thread</property>
 
    </session-factory>

</hibernate-configuration>

Annotate Java Class:
--------------------
@Entity : Java class which is mapped to database table with annotation.

@Table : To name java class same as table in database. If java class name and database table name are same no need to give this annotation.

@Column : To map java class property to database table column. If java class property name is same as db table column name than no need to use this annotation.

package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
}

Java Code to perform Database Operation:
----------------------------------------
SessionFactory:
1. Read Hibernate Config File.
2. Create connection with Database.
3. Create Session Objects.
4. Heavy weight object created once per application.

Session:
1. It wraps the jdbc connection.
2. Main object used to store and retrieve objects
3. short live object (we get session use it and throw away)
4. Retrived from sessionFactory.

package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Student;

public class RunHibernate {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
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

Primary Key:
------------
Hibernate-Tutorial-2

primary key should be unique and not null.
In above program we just pass the property of student to DB and id is maintain by the DB.
when we create the table we mention id as auto_increment which will insert the id automatically.

GenerationType.AUTO     : Pick an appropriate strategy for the pirticular database.
GenerationType.IDENTITY : Assign primary key using database identity column. like mysql
GenerationType.SEQUENCE : Assign primary key using database sequence. like oracle
GenerationType.TABLE    : Assign primary key using an underlying database table to ensure uniqueness.

We can genrate the primary key using custom genration strategy:
1. create subclass for org.hibernate.id.SequenceGenrator
2. override the method : public Serializable genrate()


Now we change student class id : Hibernate will let Database to manage the id using identity column in our case as we are using mysql.
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

Student.java

package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
}

Change Starting Index:
----------------------
alter table hb_student_tracker.student auto_increment = 1000;

truncate table hb_student_tracker.student;

Note: Once we truncate the previous alter will be gone.

Read Objects: 
-------------
Hibernate-Tutorial-3

When we save java object using session.save(student) we get the primary key back if successfuly saved.
we can retrive that key by using student.getId as we didnt set the id it was inserted by database but when we do get id what we see is the result of session.save.

session.get(Student.class, 2) will return null if there key not present and return object if key is present.

HQL:
----
Hibernate-Tutorial-5

Hibernate Query Language.
Used to query the DB.

List of students:
List<Student> students = session.createQuery("from Student").getResultList();

List of students where firstname = rahul
List<Student> students = session.createQuery("from Student s where s.firstName = 'Rahul' ").getResultList();

List of students where firstname = rahul and last name = singh
List<Student> students = session.createQuery("from Student s where s.firstName = 'Rahul' or s.lastName = 'Singh' ").getResultList();

List of students where email like %@gmail.com.
List<Student> students = session.createQuery("from Student s where email like '%@gmail.com' ").getResultList();

Note: In HQL we use java property to fetch data from db.
.list() is depricated now, we use .getResultList();

Log4j:
------
Hibernate-Tutorial-5

1. Add log4j to your project classpath

1a. Download log4j v1.2.17 from this link: – http://central.maven.org/maven2/log4j/log4j/1.2.17/log4j-1.2.17.jar

1b. Copy this file to your project’s lib directory
1c. Right-click your Eclipse project and select Properties

1d. Select Build Path > Libraries > Add JARS…

1e. Select the log4j-1.2.17.jar file from the lib directory

2. Add log4j.properties to your “src” directory

2a. Copy the text from below

# Root logger option
log4j.rootLogger=DEBUG, stdout
 
# Redirect log messages to console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
 
log4j.logger.org.hibernate=TRACE

2b. Save this file as "log4j.properties" in your “src” directory

Note: This file has an important setting:

log4j.logger.org.hibernate=TRACE 

This allows you see a low-level trace of Hibernate and this allows you see the real SQL parameter values.

Now run your application. You will see a lot of low-level TRACE logs in the Eclipse Console window.

Right-click in the Eclipse Console window and select Find/Replace…

Search for: binding parameter

or search for: extracted value

Updating Objects:
-----------------
Hibernate-Tutorial-6

When we update object using setter it will be in memory until we commit the transaction.

Retrive object and update object property by using setter:
session = factory.getCurrentSession();

			session.beginTransaction();

			Student student = session.get(Student.class, 1);
			System.out.println(student);

			System.out.println("Updating Student setting firstName = Ravi using setter");
			student.setFirstName("Ravi");

			session.getTransaction().commit();

Retrive object and update object using hql:
session = factory.getCurrentSession();

			session.beginTransaction();

			Student student = session.get(Student.class, 1);
			System.out.println(student);

			System.out.println("Updating Student setting firstName = Shalu using HQL");
			session.createQuery("update Student set firstName='Shalu' where id=1").executeUpdate();

			session.getTransaction().commit();

Delete Object:
--------------
Hibernate-Tutorial-7

System.out.println("********************************************************");
		try {

			session = factory.getCurrentSession();

			session.beginTransaction();

			List<Student> students = session.createQuery("from Student").getResultList();
			System.out.println("Delete student from table where firstName = 'Rahul' ");

			for (Student student : students) {
				if (student.getFirstName().equals("Rahul")) {
					session.delete(student);
				}
			}

			session.getTransaction().commit();

			System.out.println(students);

		} catch (Exception e) {
			factory.close();
		}

		System.out.println("********************************************************");
		try {

			session = factory.getCurrentSession();

			session.beginTransaction();

			System.out.println("Delete student from table where firstName = 'Deepak' ");
			session.createQuery("delete from Student where firstName='Deepak'").executeUpdate();

			session.getTransaction().commit();

		} catch (Exception e) {
			factory.close();
		}

One To One Mapping:
-------------------
Cascade : same operation will be done.
Like we have instructor and instructor_detail table.
if we save instructor then instructor_detail will also be save
if we delete instructor then instructor_detail will also be deleted

1. New/Transient -> save/persist -> Persistent/Managed ->rollback/new -> NewTransient

2. Persistent/Managed -> refresh (sync from db not what is in memory)

3. Persistent/Managed -> commit/rollback/close -> Detached -> merge -> Persistent/Managed

4. Persistent/Managed -> delete/remove -> Removed -> persist/rollback -> Removed

5. Removed -> commit -> New/Transient

6. Removed -> rollback -> Detached.

Cascade : If we have Instructor and Instructor Detail and we save Instructor than Instructor Detail will also be saved.

Same as Cascade Delete

Cascade Types:
Persist, Remove, Refresh, Detach, Merge, All(have all these)

One To One Mapping:
-------------------
Hibernate-Tutorial-9

Schema is collection of tables view ....
DROP SCHEMA IF EXISTS `hb-01-one-to-one-uni`;

CREATE SCHEMA `hb-01-one-to-one-uni`;

use `hb-01-one-to-one-uni`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `instructor_detail`;

CREATE TABLE `instructor_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `youtube_channel` varchar(128) DEFAULT NULL,
  `hobby` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `instructor`;

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `instructor_detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`instructor_detail_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`instructor_detail_id`) REFERENCES `instructor_detail` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

Test Connection with new schema:
package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;


public class TestJdbc {
	
	public static void main(String[] args) {
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/hb-01-one-to-one-uni?useSSL=false";
		String user = "hbstudent";
		String password = "hbstudent";
		
		
		try {
			
			System.out.println("Connecting to Database");
			
			Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
			
			System.out.println("Connection Successful " + connection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

One to One Add:

Instructor Detail Class
package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "youtube_channel")
	private String youtubeChannel;

	@Column(name = "hobby")
	private String hobby;

	public InstructorDetail() {
		// TODO Auto-generated constructor stub
	}

	public InstructorDetail(String youtubeChannel, String hobby) {
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeChannel() {
		return youtubeChannel;
	}

	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
	}

}

Instructor Class
package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "instructor")
public class Instructor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="instructor_detail_id")
	private InstructorDetail instructorDetail;

	public Instructor() {
		// TODO Auto-generated constructor stub
	}

	public Instructor(String firstName, String lastName, String email, InstructorDetail instructorDetail) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.instructorDetail = instructorDetail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public InstructorDetail getInstructorDetail() {
		return instructorDetail;
	}

	public void setInstructorDetail(InstructorDetail instructorDetail) {
		this.instructorDetail = instructorDetail;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", instructorDetail=" + instructorDetail + "]";
	}

}

Add one to one
Hibernate-Tutorial-10
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

Delete One To One:
package com.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.model.Instructor;
import com.model.InstructorDetail;

public class OneToOneDelete {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration()
												.configure("hibernate.cfg.xml")
												.addAnnotatedClass(InstructorDetail.class)
												.addAnnotatedClass(Instructor.class)
												.buildSessionFactory();
		
		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();
			
			Instructor instructor = session.get(Instructor.class,1);
			
			System.out.println("Instructor Found: " + instructor );
			
			if(instructor != null) {
				
				System.out.println("About to delete instructor");
				
				session.delete(instructor);
			}
			

			session.getTransaction().commit();
			
			System.out.println("One To One Mapping Delete Done");

		} catch (Exception e) {
			factory.close();
		}

	}

}

Till now we were seeing Unidirectional Mapping:
Instructor unidirectional mapping to Instructor Detail.
We can look instructor detail through instructor only.

Now we look Bidirectional mapping:
Hibernate-Tutorial-11

Insrtuctor bidirectional to instructor detail.
We can look instructor through instructor detail or instructor detail through instructor.

Instructor Detail Class:
package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "youtube_channel")
	private String youtubeChannel;

	@Column(name = "hobby")
	private String hobby;

	@OneToOne(mappedBy = "instructorDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Instructor instructor;

	public InstructorDetail() {
		// TODO Auto-generated constructor stub
	}

	public InstructorDetail(String youtubeChannel, String hobby) {
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
	}

	public InstructorDetail(String youtubeChannel, String hobby, Instructor instructor) {
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
		this.instructor = instructor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeChannel() {
		return youtubeChannel;
	}

	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	@Override
	public String toString() {
		return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
	}

}

Main :
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

			// remove bidirectional link
			instructorDetail.getInstructor().setInstructorDetail(null);
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

One To Many:
------------
Hibernate-Tutorial-12

Eager and Lazy Load:
--------------------
Hibernate-Tutorial-13

Default fetch type:
One To One : Eager
One To Many : Lazy
Many To One : Eager
Many To Many : Lazy

Instructor Class:
@OneToOne(fetch=FetchType.EAGER ,cascade = CascadeType.ALL)
	@JoinColumn(name = "instructor_detail_id")
	private InstructorDetail instructorDetail;

Main Class:
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

	}

}

Lazy Demo:
----------
Hibernate-Tutorial-14

If we close session and try to load lazy data we will get lazy data loading exception.

Solution:
1. Load the lazy data while session is open this data will be saved in memory and we can use it later.

2. Write Hql query to fetch data and again we will going to have all data in memory.

One To Many:
------------
A single Course can have Many Reviews.
If Course is delete than there is no menaing of Reviews.

So we are going to use 2 classes.
1. Course
2. Review

Course ----has many -----review
























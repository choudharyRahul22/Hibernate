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


















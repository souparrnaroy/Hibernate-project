package com.code.demo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.code.entity.Category;
import com.code.entity.Order;
import com.code.entity.OrderDetails;
import com.code.entity.Product;
import com.code.entity.Role;
import com.code.entity.User;

public class App {
    public static void main(String[] args) {
        // Create the configuration and add annotated classes
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetails.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(User.class);

        // Build the SessionFactory
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Open a session
        Session session = sessionFactory.openSession();

        // Start a transaction
        Transaction transaction = session.beginTransaction();

        try {
            // Example: Add test data to the database
            addTestData(session);

            // Commit the transaction
            transaction.commit();
            System.out.println("Test data inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of errors
            }
        } finally {
            session.close(); // Close session
            sessionFactory.close(); // Close sessionFactory
        }
    }

    // Method to add test data
    private static void addTestData(Session session) {
        // Insert Category
        Category category1 = new Category();
        category1.setName("Electronics");
        category1.setDescription("Electronic gadgets and devices");
        session.save(category1);

        Category category2 = new Category();
        category2.setName("Fashion");
        category2.setDescription("Clothing and accessories");
        session.save(category2);

        // Insert Products
        Product product1 = new Product();
        product1.setName("Smartphone");
        product1.setPrice(BigDecimal.valueOf(499.99));
        product1.setStockQuantity(50);
        product1.setCategory(category1);
        session.save(product1);

        Product product2 = new Product();
        product2.setName("Laptop");
        product2.setPrice(BigDecimal.valueOf(899.99));
        product2.setStockQuantity(30);
        product2.setCategory(category1);
        session.save(product2);

        Product product3 = new Product();
        product3.setName("T-shirt");
        product3.setPrice(BigDecimal.valueOf(19.99));
        product3.setStockQuantity(100);
        product3.setCategory(category2);
        session.save(product3);

        // Insert User
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("hashed_password1");
        user1.setEmail("john.doe@example.com");
        user1.setRole(Role.CUSTOMER);
        session.save(user1);

        User user2 = new User();
        user2.setUsername("admin_user");
        user2.setPassword("hashed_admin");
        user2.setEmail("admin@example.com");
        user2.setRole(Role.ADMIN);
        session.save(user2);

        // Insert Orders
        Order order1 = new Order();
        order1.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order1.setTotalAmount(BigDecimal.valueOf(499.99));
        order1.setUser(user1);
        session.save(order1);

        // Insert OrderDetails
        OrderDetails orderDetails1 = new OrderDetails();
        orderDetails1.setQuantity(1);
        orderDetails1.setUnitPrice(BigDecimal.valueOf(499.99));
        orderDetails1.setOrder(order1);
        orderDetails1.setProduct(product1);
        session.save(orderDetails1);
    }
}

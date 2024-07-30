package ps.springfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ps.springfinalproject.domain.*;
import ps.springfinalproject.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class SpringFinalProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringFinalProjectApplication.class, args);
//        RoleService roleService = context.getBean(RoleService.class);
//        UserService userService = context.getBean(UserService.class);
//        CategoryService categoryService = context.getBean(CategoryService.class);
//        ProductService productService = context.getBean(ProductService.class);
//        StockService stockService = context.getBean(StockService.class);
//        OrderService orderService = context.getBean(OrderService.class);
//        OrderDetailsService orderDetailsService = context.getBean(OrderDetailsService.class);

//        System.out.println("Roles:");
//        roleService.printAll();
//
//        System.out.println("Users:");
//        userService.printAll();
//
//        System.out.println("Categories");
//        categoryService.printAll();
//
//        System.out.println("Products:");
//        productService.printAll();
//
//        System.out.println("Stock:");
//        stockService.printAll();
//
//        System.out.println("Orders:");
//        orderService.printAll();
//
//        System.out.println("OrderDetails:");
//        orderDetailsService.printAll();
//
//
//        // creating entities:
//
////        Role testRole = new Role("testCustomer3"); // создаём роль
////        testRole = roleService.create(testRole); // получаем её из персиста
////        Role roleFromPersist = roleService.findById(testRole.getId()).get(); // ну тут явно получаем, бялть.
//
//
//        User testUser = new User("Antony the Test Customer", "tc3@mail.com", "test", "2011-12-31", roleService.findById(2).get());
//        testUser = userService.create(testUser); // всегда нужно получать из персиста
//
//        System.out.println("testUser = " + testUser);
//
//        System.out.println("userService.findById(testUser.getId()) = " + userService.findById(testUser.getId()));
//
////
////        Category testCategory = new Category("TestCategory2");
////        testCategory = categoryService.create(testCategory);
////        Product testProduct1 = new Product("TestProduct3", testCategory, "imagePath", "details");
////        Product testProduct2 = new Product("TestProduct4", testCategory, "imagePath", "details");
////        testProduct1 = productService.create(testProduct1);
////        testProduct2 = productService.create(testProduct2);
////
////        Order testOrderWithoutItems = new Order(testUser, 0); // new order
////        testOrderWithoutItems = orderService.create(testOrderWithoutItems); // order from persist
////
////        OrderDetails testOrderDetails1 = new OrderDetails(testOrderWithoutItems.getId(), testProduct1, 199, 29999.99);
////        OrderDetails testOrderDetails2 = new OrderDetails(testOrderWithoutItems.getId(), testProduct2, 188, 17987.99);
////        testOrderDetails1 = orderDetailsService.create(testOrderDetails1);
////        testOrderDetails2 = orderDetailsService.create(testOrderDetails2);
////
////        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();
////        orderDetailsArrayList.add(testOrderDetails1);
////        orderDetailsArrayList.add(testOrderDetails2);
////        double sum = testOrderDetails1.getPrice() + testOrderDetails2.getPrice();
////
////
////        Order testOrderWithItems = new Order(testOrderWithoutItems.getId(), testUser, orderDetailsArrayList, sum);
////        Order testOrderFromPersist = orderService.update(testOrderWithItems);
//
//
//        System.out.println("Roles:");
//        roleService.printAll();
//
//        System.out.println("Users:");
//        userService.printAll();
//
//        System.out.println("Categories");
//        categoryService.printAll();
//
//        System.out.println("Products:");
//        productService.printAll();
//
//        System.out.println("Stock:");
//        stockService.printAll();
//
//        System.out.println("Orders:");
//        orderService.printAll();
//
//        System.out.println("OrderDetails:");
//        orderDetailsService.printAll();

    }

}

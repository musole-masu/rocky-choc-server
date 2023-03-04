package tests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class loginTest {

    @Test
    public void login() throws IOException {

        Properties prop = new Properties();

        prop.load(loginTest.class.getClassLoader().getResourceAsStream("MyProject.properties"));

        String userName = prop.getProperty("uName");
        String password = prop.getProperty("Pwd");

        System.out.println("UserName ====> " + userName);
        System.out.println("Password ====> " + password);
    }
}

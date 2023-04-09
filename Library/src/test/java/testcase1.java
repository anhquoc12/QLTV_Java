
import Services.SachServices;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
public class testcase1 {

    private static int conn = 10;
    private static SachServices s;

    @BeforeClass
    public static void ConnectDB()
    {
        System.out.println("Start");
    }
    
    
//    
    @Test
    public void DisconnectDB1() {
        System.out.println(conn);

    }
    
    @AfterClass
    public static void DisconnectDB() {
        System.out.println("End");

    }
}

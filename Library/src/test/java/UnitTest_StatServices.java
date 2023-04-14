
import Services.StatServices;
import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import pojo.StatBook;
import pojo.StatDocGia;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dell
 */
public class UnitTest_StatServices {
    public static Connection conn;
    public static StatServices service;
    
    @BeforeAll
    public static void ConnectDB()
    {
        try {
            conn = JdbcUtils.getConn();
            service = new StatServices();
            System.out.println("Connect Database Success");
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_StatServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void CloseDB()
    {
        try {
            conn.close();
            System.out.print("CLose Database Success");
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_StatServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void TestStatReader()
    {
        int quarter = 2, year = 2022;
        try {
            List<StatDocGia> readers = service.StatByReader(quarter, year);
            Assertions.assertTrue(!readers.isEmpty());
            for (StatDocGia r : readers)
                Assertions.assertTrue(r.getSoLanMuon() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_StatServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void TestStatBook()
    {
        int quarter = 2, year = 2022;
        try {
            List<StatBook> books = service.StatByBook(quarter, year);
            Assertions.assertTrue(!books.isEmpty());
            for (StatBook b : books)
                Assertions.assertTrue(b.getSoLuongMuon() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_StatServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


import Services.DocGiaServices;
import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Assert;
import pojo.DocGia;
import Utils.PrimaryKey;
import java.time.Month;
import pojo.DocGia.Gender;
import pojo.DocGia.Object;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dell
 */
public class UnitTest_DocGiaServices {

    private static Connection conn;
    private static DocGiaServices service;

    @BeforeClass
    public static void ConnectDB() {
        try {
            conn = JdbcUtils.getConn();
            service = new DocGiaServices();
            System.out.println("Connect Database Success");
        } catch (SQLException ex) {
            System.out.println("Connect Database Failed");
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void CloseDB() {
        try {
            conn.close();
            System.out.print("CLose Database Success");
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SearchByName() {
        String kw = "Michael";
        try {
            List<DocGia> readers = service.GetDocGiaByName(kw);
            Assert.assertTrue(!readers.isEmpty());
            for (DocGia d : readers) {
                Assert.assertTrue(d.getTenDocGia().contains(kw));
            }
            readers = service.listDocGiaByName(kw);
            Assert.assertTrue(!readers.isEmpty());
            for (DocGia d : readers) {
                Assert.assertTrue(d.getTenDocGia().contains(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_DocGiaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SearchByID() {
        String kw = "DG";
        try {
            List<DocGia> readers = service.listDocGiaByID(kw);
            Assert.assertTrue(!readers.isEmpty());
            for (DocGia d : readers) {
                Assert.assertTrue(d.getMaDocGia().contains(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_DocGiaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_AddReader() {

        try {
            String id = new PrimaryKey().ID_4("DG", service.LastKey_Reader());
            String name = "Test case AddReader 1";
            Gender gender = Gender.NAM;
            LocalDate ngaysinh = LocalDate.of(2002, Month.MARCH, 16);
            int day = ngaysinh.getDayOfMonth();
            int month = ngaysinh.getMonthValue();
            int year = ngaysinh.getYear() - 1990;
            Date birthday = new Date(year, month, day);
            Object object = Object.STUDENT;
            String bophan = "KHOA CÔNG NGHỆ THÔNG TIN";
            LocalDate ngayLapthe = LocalDate.now();
            day = ngayLapthe.getDayOfMonth();
            month = ngayLapthe.getMonthValue();
            year = ngayLapthe.getYear() - 1990;
            Date createdday = new Date(year, month, day);
            String email = "test@gmail.com";
            String address = "test address";
            String phone = "0123456789";

            DocGia r = new DocGia(id, name, gender, birthday, object,
                    createdday, phone, address, bophan, email);
            Assert.assertTrue(service.AddReader(r));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_DocGiaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void TestCase_EditReader() {

        try {
            String id = "DG0102";
            String name = "Test case AddReader 1 Edited";
            Gender gender = Gender.NU;
            LocalDate ngaysinh = LocalDate.of(2002, Month.MARCH, 16);
            int day = ngaysinh.getDayOfMonth();
            int month = ngaysinh.getMonthValue();
            int year = ngaysinh.getYear() - 1990;
            Date birthday = new Date(year, month, day);
            Object object = Object.STUDENT;
            String bophan = "KHOA CÔNG NGHỆ THÔNG TIN";
            LocalDate ngayLapthe = LocalDate.now();
            day = ngayLapthe.getDayOfMonth();
            month = ngayLapthe.getMonthValue();
            year = ngayLapthe.getYear() - 1990;
            Date createdday = new Date(year, month, day);
            String email = "test@gmail.com";
            String address = "test address";
            String phone = "0123456789";

            DocGia r = new DocGia(id, name, gender, birthday, object,
                    createdday, phone, address, bophan, email);
            Assert.assertTrue(service.EditReader(r));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_DocGiaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void TestCase_DeleteReader() {

        try {
            String id = "DG0102";
            Assert.assertTrue(service.DeleteReader(id));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_DocGiaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Services.SachServices;
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
import pojo.Sach;
import pojo.Sach.StateOfBook;
import Utils.PrimaryKey;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author dell
 */
public class UnitTest_SachServices {

    private static Connection conn;
    private static SachServices service;

    @BeforeAll
    public static void ConnectDB() {
        try {
            conn = JdbcUtils.getConn();
            service = new SachServices();
            System.out.println("Connect Database Success");
        } catch (SQLException ex) {
            System.out.println("Connect Database Failed");
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void CloseDB() {
        try {
            conn.close();
            System.out.print("CLose Database Success");
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SeachByName() {
        String kw = "Harry";
        try {
            List<Sach> books = service.GetBookByName(kw);
            Assertions.assertTrue(!books.isEmpty());
            for (Sach s : books) {
                Assertions.assertTrue(s.getTenSach().contains(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SeachByTacGia() {
        String kw = "Ánh";
        try {
            List<Sach> books = service.GetBookByTacGia(kw);
            Assertions.assertTrue(!books.isEmpty());
            for (Sach s : books) {
                Assertions.assertTrue(s.getTacGia().contains(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SeachByDanhMuc() {
        String kw = "Văn học nước ngoài";
        try {
            List<Sach> books = service.GetBookByDanhMuc(kw);
            Assertions.assertTrue(!books.isEmpty());
            for (Sach s : books) {
                Assertions.assertTrue(s.getTheLoai().contains(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_SeachByNamXB() {
        String kw = "2000";
        try {
            List<Sach> books = service.GetBookByNamXB(kw);
            Assertions.assertTrue(!books.isEmpty());
            for (Sach s : books) {
                Assertions.assertTrue(s.getNamXB() == Integer.parseInt(kw));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_AddBook() {
        try {
            String id = new PrimaryKey().ID_4("SA", service.LastKey_Book());
            String name = "Test Case AddBook 2";
            String tacgia = "Nguyễn Anh Quốc";
            String theloai = "Unit test SachServices";
            int namxb = 2022;
            String noixb = "NXB Test";
            String vitri = "A3-1-1";
            Sach.StateOfBook trangThai = Sach.StateOfBook.KHA_DUNG;
            LocalDate n = LocalDate.now();
            int day = n.getDayOfMonth();
            int month = n.getMonthValue() - 1;
            int year = n.getYear() - 1900;
            Date ngayNhap = new Date(year, month, day);
            String mota = "Kiểm thử chức năng thêm 1 quyển sách";
            Sach s = new Sach(id, name, tacgia, theloai, namxb, noixb, ngayNhap, vitri, trangThai, mota);
            Assertions.assertTrue(service.AddBook(s));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Test
    public void TestCase_EditBook() {
        try {
            String id = "SA0049";
            String name = "Test Case AddBook 1";
            String tacgia = "Nguyễn Anh Quốc";
            String theloai = "Unit test SachServices";
            int namxb = 2022;
            String noixb = "NXB Test";
            String vitri = "A3-1-1";
            Sach.StateOfBook trangThai = Sach.StateOfBook.KHA_DUNG;
            LocalDate n = LocalDate.now();
            int day = n.getDayOfMonth();
            int month = n.getMonthValue() - 1;
            int year = n.getYear() - 1900;
            Date ngayNhap = new Date(year, month, day);
            String mota = "Kiểm thử chức năng thêm 1 quyển sách";
            Sach s = new Sach(id, name, tacgia, theloai, namxb, noixb, ngayNhap, vitri, trangThai, mota);
            Assertions.assertTrue(service.EditBook(s));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_DeleteBook() {
        try {
            String id = "SA0051";
            Assertions.assertTrue(service.DeleteBook(id));
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestCase_ListSachKhaDung() {
        List<Sach> books;
        try {
            books = service.listDachKhaDung();
            Assertions.assertTrue(!books.isEmpty());
            if (!books.isEmpty()) {
                StateOfBook state = StateOfBook.KHA_DUNG;
                for (Sach s : books) {
                    Assertions.assertEquals(s.getTrangThai(), state);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void TestCase_ListSachKhaDungByName() {
        String kw = "Harry";
        try {
            List<Sach> books = service.listSachKhaDungByName(kw);
            Assertions.assertTrue(!books.isEmpty());
            if (!books.isEmpty()) {
                StateOfBook state = StateOfBook.KHA_DUNG;
                for (Sach s : books) {
                    Assertions.assertEquals(s.getTrangThai(), state);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void TestCase_ListSachKhaDungByID() {
        String id = "SA0003";
        try {
            List<Sach> books = service.listSachKhaDungByID(id);
            Assertions.assertTrue(!books.isEmpty());
            if (!books.isEmpty()) {
                StateOfBook state = StateOfBook.KHA_DUNG;
                for (Sach s : books) {
                    Assertions.assertEquals(s.getTrangThai(), state);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void TestCase_ListSachKhaDungByTacGia() {
        String kw = "Paulo";
        try {
            List<Sach> books = service.listSachKhaDungByTG(kw);
            Assertions.assertTrue(!books.isEmpty());
            if (!books.isEmpty()) {
                StateOfBook state = StateOfBook.KHA_DUNG;
                for (Sach s : books) {
                    Assertions.assertEquals(s.getTrangThai(), state);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UnitTest_SachServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

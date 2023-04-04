/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.DocGia;

/**
 *
 * @author dell
 */
public class DocGiaServices {

    public List<DocGia> DocGiaList() throws SQLException {
        List<DocGia> readers = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM DocGia");

            while (rs.next()) {
                String id = rs.getString("maDocGia");
                String name = rs.getString("tenDocGia");
                DocGia.Gender gioiTinh = DocGia.Gender.valueOf(rs.getString("gioiTinh"));
                DocGia.Object doituong = DocGia.Object.valueOf(rs.getString("doiTuong"));
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayLapthe = rs.getDate("ngayLapThe");
                String sdt = rs.getString("SoDT");
                String DiaChi = rs.getString("diaChi");
                String boPhan = rs.getString("boPhan");
                String email = rs.getString("email");
                DocGia r = new DocGia(id, name, gioiTinh, ngaySinh, doituong, ngayLapthe, sdt, DiaChi, boPhan, email);
                readers.add(r);

            }
        }
        return readers;
    }

    public List<DocGia> GetDocGiaByName(String keywords) throws SQLException {
        List<DocGia> readers = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM DocGia";
            if (keywords != null && !keywords.isEmpty()) {
                sql += " WHERE tenDocGia like concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (keywords != null && !keywords.isEmpty()) {
                stm.setString(1, keywords);
            }
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String id = rs.getString("maDocGia");
                String name = rs.getString("tenDocGia");
                DocGia.Gender gioiTinh = DocGia.Gender.valueOf(rs.getString("gioiTinh"));
                DocGia.Object doituong = DocGia.Object.valueOf(rs.getString("doiTuong"));
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayLapthe = rs.getDate("ngayLapThe");
                String sdt = rs.getString("SoDT");
                String DiaChi = rs.getString("diaChi");
                String boPhan = rs.getString("boPhan");
                String email = rs.getString("email");
                DocGia r = new DocGia(id, name, gioiTinh, ngaySinh, doituong, ngayLapthe, sdt, DiaChi, boPhan, email);
                readers.add(r);

            }
        }
        return readers;
    }

    public boolean AddReader(DocGia s) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {

            String sql = "INSERT INTO DocGia(maDocGia, tenDocGia, gioiTinh, ngaySinh, doiTuong, ngayLapThe, soDT, diaChi, boPhan, email)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, s.getMaDocGia());
            stm.setString(2, s.getTenDocGia());
            stm.setString(3, s.getGioiTinh().name());
            stm.setDate(4, s.getNgaySinh());
            stm.setString(5, s.getDoiTuong().name());
            stm.setDate(6, s.getNgayLapThe());
            stm.setString(7, s.getSoDT());
            stm.setString(8, s.getDiaChi());
            stm.setString(9, s.getBoPhan());
            stm.setString(10, s.getEmail());
            int r = stm.executeUpdate();
            return r > 0;
        }
    }
//

    public String LastKey_Reader() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT maDocGia FROM qltv_db.DocGia ORDER BY maDocGia DESC LIMIT 1;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return rs.getString("maDocGia");
            } else {
                return null;
            }
        }
    }

    public DocGia FirstReader() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {

            String sql = "SELECT * FROM DocGia LIMIT 1;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                String id = rs.getString("maDocGia");
                String name = rs.getString("tenDocGia");
                DocGia.Gender gioiTinh = DocGia.Gender.valueOf(rs.getString("gioiTinh"));
                DocGia.Object doituong = DocGia.Object.valueOf(rs.getString("doiTuong"));
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayLapthe = rs.getDate("ngayLapThe");
                String sdt = rs.getString("SoDT");
                String DiaChi = rs.getString("diaChi");
                String boPhan = rs.getString("boPhan");
                String email = rs.getString("email");
                return new DocGia(id, name, gioiTinh, ngaySinh, doituong, ngayLapthe, sdt, DiaChi, boPhan, email);
            } else {
                return null;
            }
        }

    }

    public List<DocGia> listDocGiaByName(String kw) throws SQLException {
        List<DocGia> dgs = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM docgia";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE tenDocGia like concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("maDocGia");
                String name = rs.getString("tenDocGia");
                DocGia.Gender gioiTinh = DocGia.Gender.valueOf(rs.getString("gioiTinh"));
                DocGia.Object doituong = DocGia.Object.valueOf(rs.getString("doiTuong"));
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayLapthe = rs.getDate("ngayLapThe");
                String sdt = rs.getString("SoDT");
                String DiaChi = rs.getString("diaChi");
                String boPhan = rs.getString("boPhan");
                String email = rs.getString("email");
                DocGia dg = new DocGia(id, name, gioiTinh, ngaySinh, doituong, ngayLapthe, sdt, DiaChi, boPhan, email);
                dgs.add(dg);
            }
        }

        return dgs;
    }

    public List<DocGia> listDocGiaByID(String kw) throws SQLException {
        List<DocGia> dgs = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM docgia";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE maDocGia like concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("maDocGia");
                String name = rs.getString("tenDocGia");
                DocGia.Gender gioiTinh = DocGia.Gender.valueOf(rs.getString("gioiTinh"));
                DocGia.Object doituong = DocGia.Object.valueOf(rs.getString("doiTuong"));
                Date ngaySinh = rs.getDate("ngaySinh");
                Date ngayLapthe = rs.getDate("ngayLapThe");
                String sdt = rs.getString("SoDT");
                String DiaChi = rs.getString("diaChi");
                String boPhan = rs.getString("boPhan");
                String email = rs.getString("email");
                DocGia dg = new DocGia(id, name, gioiTinh, ngaySinh, doituong, ngayLapthe, sdt, DiaChi, boPhan, email);
                dgs.add(dg);
            }
        }

        return dgs;
    }
//
//    public boolean DeleteBook(String id) throws SQLException {
//        try (Connection conn = JdbcUtils.getConn()) {
//            String sql = "DELETE FROM DocGia WHERE maDocGia = ?";
//            PreparedStatement stm = conn.prepareStatement(sql);
//            stm.setString(1, id);
//            int r = stm.executeUpdate();
//            return r > 0;
//        }
//    }
//
//    public Boolean EditBook(DocGia s) throws SQLException {
//        try (Connection conn = JdbcUtils.getConn()) {
//
//            String sql = "UPDATE DocGia SET ";
//            sql += "tenDocGia = ?, ";
//            sql += "tacGia = ?, ";
//            sql += "theLoai = ?, ";
//            sql += "namXB = ?, ";
//            sql += "noiXb = ?, ";
//            sql += "ngayNhap = ?, ";
//            sql += "viTri = ?, ";
//            sql += "motaDocGia = ? ";
//            sql += "WHERE maDocGia = ?";
//            PreparedStatement stm = conn.prepareStatement(sql);
//            stm.setString(1, s.getTenDocGia());
//            stm.setString(2, s.getTacGia());
//            stm.setString(3, s.getTheLoai());
//            stm.setInt(4, s.getNamXB());
//            stm.setString(5, s.getNoiXB());
//            stm.setDate(6, s.getNgayNhap());
//            stm.setString(7, s.getViTri());
//            stm.setString(8, s.getMotaDocGia());
//            stm.setString(9, s.getMaDocGia());
//            int r = stm.executeUpdate();
//            return r > 0;
//        }
//    }
}

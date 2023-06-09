/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.StatBook;
import pojo.StatDocGia;

/**
 *
 * @author dell
 */
public class StatServices {

    public List<StatBook> StatByBook(int quarter, int year) throws SQLException {
        List<StatBook> stats = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT s.maSach, s.tenSach, COUNT(ctpm.maSach) AS soLuongMuon \n"
                    + "FROM Sach s\n"
                    + "INNER JOIN ChiTietPhieuMuon ctpm ON s.maSach = ctpm.maSach\n"
                    + "INNER JOIN PhieuMuon pm ON ctpm.maPhieuMuon = pm.maPhieuMuon AND pm.trangThai IN ('CHUA_TRA', 'DA_TRA')\n"
                    + "WHERE QUARTER(pm.ngayMuon) = ? AND YEAR(pm.ngayMuon) = ? \n"
                    + "GROUP BY s.maSach\n"
                    + "ORDER BY soLuongMuon ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, quarter);
            stm.setInt(2, year);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                StatBook s = new StatBook(rs.getString("maSach"),
                        rs.getString("tenSach"),
                        rs.getInt("soLuongMuon"));
                stats.add(s);
            }
        }
        return stats;
    }

    public List<StatDocGia> StatByReader(int quarter, int year) throws SQLException {
        List<StatDocGia> stats = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT d.maDocGia, d.tenDocGia, COUNT(pm.maDocGia) AS soLanMuon\n"
                    + "FROM docgia d\n"
                    + "INNER JOIN PhieuMuon pm ON d.maDocGia = pm.maDocGia AND pm.trangThai IN ('CHUA_TRA', 'DA_TRA')\n"
                    + "WHERE QUARTER(pm.ngayMuon) = ? AND YEAR(pm.ngayMuon) = ?\n"
                    + "GROUP BY d.maDocGia";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, quarter);
            stm.setInt(2, year);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                StatDocGia s = new StatDocGia(rs.getString("maDocGia"),
                        rs.getString("tenDocGia"),
                        rs.getInt("soLanMuon"));
                stats.add(s);
            }
            return stats;
        }
    }
}

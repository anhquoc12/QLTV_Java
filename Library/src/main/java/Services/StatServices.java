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
import pojo.Stat;

/**
 *
 * @author dell
 */
public class StatServices {

    public List<Stat> StatList(int quy, int year) throws SQLException {
        List<Stat> stats = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT s.maSach, s.tenSach, s.theLoai, COUNT(ctpm.maSach) AS soLuongMuon \n"
                    + "FROM Sach s\n"
                    + "INNER JOIN ChiTietPhieuMuon ctpm ON s.maSach = ctpm.maSach\n"
                    + "INNER JOIN PhieuMuon pm ON ctpm.maPhieuMuon = pm.maPhieuMuon AND pm.trangThai IN ('CHUA_TRA', 'DA_TRA')\n"
                    + "WHERE QUARTER(pm.ngayMuon) = ? AND YEAR(pm.ngayMuon) = ? \n"
                    + "GROUP BY s.maSach\n"
                    + "ORDER BY soLuongMuon ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, quy);
            stm.setInt(2, year);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Stat s = new Stat(rs.getString("maSach"), 
                        rs.getString("tenSach"), 
                        rs.getInt("soLuongMuon"));
                stats.add(s);
            }
        }
        return stats;
    }

    public static void main(String[] args) throws SQLException {
        System.out.print(new StatServices().StatList(2, 1933));
    }
}

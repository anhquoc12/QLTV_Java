/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Utils.PrimaryKey;
import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class ChiTietPhieuMuonServices {
    public String LastKey_CTPM() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT maCTPM FROM qltv_db.ChiTietPhieuMuon ORDER BY maCTPM DESC LIMIT 1;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return rs.getString("maCTPM");
            } else {
                return null;
            }
        }
    }
    public static void main(String[] args) throws SQLException {
        System.out.println(new PrimaryKey().ID_8("CTPM", new ChiTietPhieuMuonServices().LastKey_CTPM()));
    }
}

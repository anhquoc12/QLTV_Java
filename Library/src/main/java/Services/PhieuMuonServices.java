/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Utils.PrimaryKey;
import conf.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javafx.scene.input.KeyCode;
import pojo.ChiTietPhieuMuon;
import pojo.PhieuMuon;

/**
 *
 * @author Admin
 */
public class PhieuMuonServices {
//    public boolean checkDocGiaByID(String id) throws SQLException{
//        List<PhieuMuon> pms = new ArrayList<>();
//        try(Connection conn = JdbcUtils.getConn()){
//            String sql = "select * from phieumuon";
//            if(id != null && !id.isEmpty())
//                sql += "where trangThai = 'CHUA_TRA' and maDocGia like concat('%', ?, '%')";
//            PreparedStatement stm = conn.prepareCall(sql);
//            if(id != null && !id.isEmpty())
//                stm.setString(1, id);
//            ResultSet rs = stm.executeQuery();
//            while(rs.next()){
//                String maPhieuMuon = rs.getString("maPhieuMuon");
//                String maDocGia = rs.getString("maDocGia");
//                Date ngayMuon = rs.getDate("ngayMuon");
//                PhieuMuon.StateOfPM trangThai = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
//                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, trangThai);
//                pms.add(pm);
//            }
//            if(pms.size() >= 1)
//                return false;
//        }
//        return true;
//    }
    public void luuPhieuMuon(PhieuMuon pm, List<ChiTietPhieuMuon> ctpms) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            String sql = "insert into phieumuon(maPhieuMuon, ngayMuon, trangThai, maDocGia) values(?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            stm.setDate(2, (Date) pm.getNgayMuon());
            stm.setString(3, pm.getTrangThai().name());
            stm.setString(4, pm.getMaDocGia());
            stm.executeUpdate();
            for(ChiTietPhieuMuon ctpm : ctpms){
                sql = "insert into chitietphieumuon(maCTPM, maPhieuMuon, maSach) values(?, ?, ?)";
                PreparedStatement stm1 = conn.prepareCall(sql);
                stm1.setString(1, ctpm.getMaCTPM());
                stm1.setString(2, ctpm.getMaPhieuMuon());
                stm1.setString(3, ctpm.getMaSach());
                stm1.executeUpdate();
            }
            conn.commit();
        }
    }
    
    public String LastKey_PM() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT maPhieuMuon FROM qltv_db.PhieuMuon ORDER BY maPhieuMuon DESC LIMIT 1;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return rs.getString("maPhieuMuon");
            } else {
                return null;
            }
        }
    }
    
    public static void main(String[] args) throws SQLException {
        System.out.println(new PrimaryKey().ID_8("PM", new PhieuMuonServices().LastKey_PM()));
    }
}

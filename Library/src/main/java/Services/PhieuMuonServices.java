/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Utils.PrimaryKey;
import conf.JdbcUtils;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javafx.scene.input.KeyCode;
import pojo.ChiTietPhieuMuon;
import pojo.DocGia;
import pojo.PhieuMuon;
import pojo.Sach;

/**
 *
 * @author Admin
 */
public class PhieuMuonServices {

    public List getPhieuMuonByIDDocGia(String kw) throws SQLException {
        List<PhieuMuon> pms = new ArrayList<>();

        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE maDocGia like concat('%', ?, '%') and trangThai = 'CHUA_TRA'";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String maPhieuMuon = rs.getString("maPhieuMuon");
                PhieuMuon.StateOfPM state = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
                Date ngayMuon = rs.getDate("ngayMuon");
                String maDocGia = rs.getString("maDocGia");
                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, state);
                pms.add(pm);
            }
        }
        return pms;
    }

    public void luuPhieuMuon(PhieuMuon pm, List<ChiTietPhieuMuon> ctpms) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "insert into phieumuon(maPhieuMuon, ngayMuon, trangThai, maDocGia) values(?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            stm.setDate(2, (Date) pm.getNgayMuon());
            stm.setString(3, pm.getTrangThai().name());
            stm.setString(4, pm.getMaDocGia());
            stm.executeUpdate();
            for (ChiTietPhieuMuon ctpm : ctpms) {
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
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT maPhieuMuon FROM qltv_db.PhieuMuon ORDER BY maPhieuMuon DESC LIMIT 1;";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {
                return rs.getString("maPhieuMuon");
            } else {
                return null;
            }
        }
    }

    public List getPhieuDatByIDDocGia(String kw) throws SQLException {
        List<PhieuMuon> pms = new ArrayList<>();

        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE maDocGia like concat('%', ?, '%') and trangThai = 'DANG_DAT'";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String maPhieuMuon = rs.getString("maPhieuMuon");
                PhieuMuon.StateOfPM state = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
                Date ngayMuon = rs.getDate("ngayMuon");
                String maDocGia = rs.getString("maDocGia");
                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, state);
                pms.add(pm);
            }
        }
        return pms;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(new PrimaryKey().ID_8("PM", new PhieuMuonServices().LastKey_PM()));
    }

    public void chuyenTrangThaiSach(Sach s) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "update sach set trangThai = 'DANG_DUOC_MUON' where maSach like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, s.getMaSach());
            stm.executeUpdate();
        }
    }

    public void chuyenTrangThaiSachDat(Sach s) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "update sach set trangThai = 'CO_NGUOI_DAT' where maSach like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, s.getMaSach());
            stm.executeUpdate();
        }
    }

    public List<PhieuMuon> getListPhieuByID(String kw) throws SQLException {
        List<PhieuMuon> pms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon";
            sql += " WHERE maPhieuMuon like concat('%', ?, '%') and trangThai ='CHUA_TRA' or maPhieuMuon like concat('%', ?, '%') and trangThai ='DANG_DAT'";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, kw);
            stm.setString(2, kw);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String maPhieuMuon = rs.getString("maPhieuMuon");
                PhieuMuon.StateOfPM trangThai = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
                Date ngayMuon = rs.getDate("ngayMuon");
                String maDocGia = rs.getString("maDocGia");
                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, trangThai);
                pms.add(pm);
            }
        }
        return pms;
    }

    public List<PhieuMuon> getListPhieuByIDDG(String kw) throws SQLException {
        List<PhieuMuon> pms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM phieumuon";
//            if (kw != null && !kw.isEmpty()) {
            sql += " WHERE maDocGia like concat('%', ?, '%')and trangThai ='CHUA_TRA' or maDocGia like concat('%', ?, '%') and trangThai ='DANG_DAT'";
//            }

            PreparedStatement stm = conn.prepareCall(sql);
//            if (kw != null && !kw.isEmpty()) {
            stm.setString(1, kw);
            stm.setString(2, kw);
//            }
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String maPhieuMuon = rs.getString("maPhieuMuon");
                PhieuMuon.StateOfPM trangThai = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
                Date ngayMuon = rs.getDate("ngayMuon");
                String maDocGia = rs.getString("maDocGia");
                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, trangThai);
                pms.add(pm);
            }
        }
        return pms;
    }

    public List<PhieuMuon> loadListPhieuMuon() throws SQLException {
        List<PhieuMuon> pms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM phieumuon where trangThai = 'CHUA_TRA' or trangThai = 'DANG_DAT'");

            while (rs.next()) {
                String maPhieuMuon = rs.getString("maPhieuMuon");
                PhieuMuon.StateOfPM trangThai = PhieuMuon.StateOfPM.valueOf(rs.getString("trangThai"));
                Date ngayMuon = rs.getDate("ngayMuon");
                String maDocGia = rs.getString("maDocGia");
                PhieuMuon pm = new PhieuMuon(maPhieuMuon, maDocGia, ngayMuon, trangThai);
                pms.add(pm);
            }
        }
        return pms;
    }

    public List<ChiTietPhieuMuon> listCTPM(String kw) throws SQLException {
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from chitietphieumuon";
            if (kw != null && !kw.isEmpty()) {
                sql += " WHERE maPhieuMuon like concat('%', ?, '%')";
            }

            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs.getString("maCTPM"), "maPhieuMuon", "maSach");
                ctpms.add(ctpm);
            }
        }
        return ctpms;
    }

    public void setTrangThaiPM(PhieuMuon pm) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "update phieumuon set trangThai = 'DA_TRA' where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            stm.executeUpdate();
        }
    }

    public void chuyenTrangThaiSachTrongCTPM(PhieuMuon pm) throws SQLException {
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from chitietphieumuon where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs.getString("maCTPM"),
                        rs.getString("maPhieuMuon"), rs.getString("maSach"));
                ctpms.add(ctpm);
            }
            List<String> listMaSach = new ArrayList<>();
            if (ctpms.size() >= 1) {
                for (ChiTietPhieuMuon ctpm : ctpms) {
                    listMaSach.add(ctpm.getMaSach());
                }
            }
            if (listMaSach.size() >= 1) {
                for (String maSach : listMaSach) {
                    String sql2 = "update sach set trangThai = 'KHA_DUNG' where maSach like concat('%', ?, '%')";
                    PreparedStatement stm2 = conn.prepareCall(sql2);
                    stm2.setString(1, maSach);
                    stm2.executeUpdate();
                }
            }
        }
    }

    public void setTrangThaiPD(PhieuMuon pm) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "update phieumuon set trangThai = 'CHUA_TRA' where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            stm.executeUpdate();
        }
    }

    public void chuyenTrangThaiSachTrongCTPD(PhieuMuon pm) throws SQLException {
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from chitietphieumuon where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs.getString("maCTPM"),
                        rs.getString("maPhieuMuon"), rs.getString("maSach"));
                ctpms.add(ctpm);
            }
            List<String> listMaSach = new ArrayList<>();
            if (ctpms.size() >= 1) {
                for (ChiTietPhieuMuon ctpm : ctpms) {
                    listMaSach.add(ctpm.getMaSach());
                }
            }
            if (listMaSach.size() >= 1) {
                for (String maSach : listMaSach) {
                    String sql2 = "update sach set trangThai = 'DANG_DUOC_MUON' where maSach like concat('%', ?, '%')";
                    PreparedStatement stm2 = conn.prepareCall(sql2);
                    stm2.setString(1, maSach);
                    stm2.executeUpdate();
                }
            }
        }
    }

    public void deleteCTPD(PhieuMuon pm) throws SQLException {
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql1 = "select * from chitietphieumuon where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm1 = conn.prepareCall(sql1);
            stm1.setString(1, pm.getMaPhieuMuon());
            ResultSet rs1 = stm1.executeQuery();
            while (rs1.next()) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs1.getString("maCTPM"),
                        rs1.getString("maPhieuMuon"), rs1.getString("maSach"));
                ctpms.add(ctpm);
            }
            for (ChiTietPhieuMuon ctpm : ctpms) {
                String sql2 = "delete from chitietphieumuon where maCTPM like concat('%', ?, '%')";
                PreparedStatement stm2 = conn.prepareCall(sql2);
                stm2.setString(1, ctpm.getMaCTPM());
                stm2.executeUpdate();
            }
        }
    }

    public void deletePhieu(PhieuMuon pm) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            String sql = "delete from phieumuon where maPhieuMuon like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, pm.getMaPhieuMuon());
            stm.executeUpdate();
        }
    }
}

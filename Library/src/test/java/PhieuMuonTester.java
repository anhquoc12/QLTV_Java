
import Services.PhieuMuonServices;
import Services.SachServices;
import conf.JdbcUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import pojo.ChiTietPhieuMuon;
import pojo.PhieuMuon;
import pojo.Sach;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class PhieuMuonTester {

    private static Connection conn;

    private static PhieuMuonServices sv;
    private static Date ngayMuon;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        conn = JdbcUtils.getConn();
        sv = new PhieuMuonServices();
        LocalDate date = LocalDate.now();
        int d, m, y;
        d = date.getDayOfMonth();
        m = date.getMonthValue() - 1;
        y = date.getYear() - 1900;
        ngayMuon = new Date(y, m, d);
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void getPhieuMuonByIDDG() {
        List<PhieuMuon> pms = new ArrayList<>();
        List<PhieuMuon> pms2 = new ArrayList<>();
        List<PhieuMuon> pms3 = new ArrayList<>();
        try {
            pms = sv.getPhieuMuonByIDDocGia("DG0002");
            Assertions.assertTrue(!pms.isEmpty());
            pms2 = sv.getPhieuMuonByIDDocGia("DG0026");
            Assertions.assertFalse(!pms2.isEmpty());
            pms3 = sv.getPhieuMuonByIDDocGia("DG0001");
            Assertions.assertFalse(!pms3.isEmpty());
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void getPhieuDatByIDDG() {
        List<PhieuMuon> pms = new ArrayList<>();
        List<PhieuMuon> pms2 = new ArrayList<>();
        List<PhieuMuon> pms3 = new ArrayList<>();
        try {
            pms = sv.getPhieuDatByIDDocGia("DG0026");
            Assertions.assertTrue(!pms.isEmpty());
            pms2 = sv.getPhieuDatByIDDocGia("DG0046");
            Assertions.assertFalse(!pms2.isEmpty());
            pms3 = sv.getPhieuDatByIDDocGia("DG0024");
            Assertions.assertFalse(!pms3.isEmpty());

        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void luuPhieuMuon(){
        PhieuMuon pm = new PhieuMuon("PM00000033", "DG0006", ngayMuon, PhieuMuon.StateOfPM.DANG_DAT);
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        ctpms.add(new ChiTietPhieuMuon(pm.getMaPhieuMuon(), "SA0001"));
        try {
            sv.luuPhieuMuon(pm, ctpms);
            PreparedStatement stm = conn.prepareCall("select * from phieumuon where maPhieuMuon like concat('%', ?, '%')");
            stm.setString(1, pm.getMaPhieuMuon());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(rs.getString("maPhieuMuon"), pm.getMaPhieuMuon());
            Assertions.assertEquals(rs.getString("maDocGia"), pm.getMaDocGia());
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void chuyenTrangThaiSachMuon(){
        SachServices sachSV = new SachServices();
        List<Sach> sachs = new ArrayList<>();
        try {
            sachs = sachSV.listSachKhaDungByID("SA0001");
            Sach s = sachs.get(0);
        sv.chuyenTrangThaiSach(s);
        PreparedStatement stm = conn.prepareCall("select * from sach where maSach like concat('%', ?, '%')");
        stm.setString(1, s.getMaSach());
        ResultSet rs = stm.executeQuery();
        Assertions.assertEquals(rs.getString("trangThai"), s.getTrangThai().name());
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void chuyenTrangThaiSachDat(){
        SachServices sachSV = new SachServices();
        List<Sach> sachs = new ArrayList<>();
        try {
            sachs = sachSV.listSachKhaDungByID("SA0004");
            Sach s = sachs.get(0);
        sv.chuyenTrangThaiSachDat(s);
        PreparedStatement stm = conn.prepareCall("select * from sach where maSach like concat('%', ?, '%')");
        stm.setString(1, s.getMaSach());
        ResultSet rs = stm.executeQuery();
        Assertions.assertEquals(rs.getString("trangThai"), s.getTrangThai().name());
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void chuyenTrangThaiPM() {
        List<PhieuMuon> pms = new ArrayList<>();
        SachServices sachSV = new SachServices();
        List<Sach> sachs = new ArrayList<>();
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try {
            pms = sv.getPhieuMuonByIDDocGia("DG0025");
            PhieuMuon pm = pms.get(0);
            sv.setTrangThaiPM(pm);
            sv.chuyenTrangThaiSachTrongCTPM(pm);
            sv.chuyenTrangThaiSachTrongCTPM(pm);
            PreparedStatement stm1 = conn.prepareCall("select * from phieumuon where maPhieuMuon like concat('%', ?, '%') and trangthai = 'CHUA_TRA'");
            stm1.setString(1, pm.getMaPhieuMuon());
            ResultSet rs1 = stm1.executeQuery();
            Assertions.assertEquals(rs1.getString("trangThai"), pm.getTrangThai().name());
            PreparedStatement stm2 = conn.prepareCall("select * from chitietphieumuon where maPhieuMuon like concat('%', ?, '%')");
            stm2.setString(1, pm.getMaPhieuMuon());
            ResultSet rs2 = stm2.executeQuery();
            while(rs2.next()){
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs2.getString("maCTPM"), 
                        rs2.getString("maPhieuMuon"), rs2.getString("maSach"));
                ctpms.add(ctpm);
            }
            for(ChiTietPhieuMuon ctpm : ctpms){
                PreparedStatement stm3 = conn.prepareCall("select * from sach where maSach like concat('%', ?, '%')");
                stm3.setString(1, ctpm.getMaSach());
                ResultSet rs3 = stm3.executeQuery();
                Assertions.assertEquals("KHA_DUNG", rs3.getString("trangThai"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void chuyenTrangThaiPD(){
        List<PhieuMuon> pms = new ArrayList<>();
        SachServices sachSV = new SachServices();
        List<Sach> sachs = new ArrayList<>();
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try {
            pms = sv.getListPhieuByID("PM00000037");
            PhieuMuon pm = pms.get(0);
            sv.setTrangThaiPD(pm);
            sv.chuyenTrangThaiSachTrongCTPD(pm);
            PreparedStatement stm1 = conn.prepareCall("select * from phieumuon where maPhieuMuon like concat('%', ?, '%') and trangthai = 'DANG_DAT'");
            stm1.setString(1, pm.getMaPhieuMuon());
            ResultSet rs1 = stm1.executeQuery();
            Assertions.assertEquals(rs1.getString("trangThai"), pm.getTrangThai().name());
            PreparedStatement stm2 = conn.prepareCall("select * from chitietphieumuon where maPhieuMuon like concat('%', ?, '%')");
            stm2.setString(1, pm.getMaPhieuMuon());
            ResultSet rs2 = stm2.executeQuery();
            while(rs2.next()){
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(rs2.getString("maCTPM"), 
                        rs2.getString("maPhieuMuon"), rs2.getString("maSach"));
                ctpms.add(ctpm);
            }
            for(ChiTietPhieuMuon ctpm : ctpms){
                PreparedStatement stm3 = conn.prepareCall("select * from sach where maSach like concat('%', ?, '%')");
                stm3.setString(1, ctpm.getMaSach());
                ResultSet rs3 = stm3.executeQuery();
                Assertions.assertEquals("DANG_DUOC_MUON", rs3.getString("trangThai"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void deletePhieuDat(){
        int actual = 0;
        List<PhieuMuon> pms = new ArrayList<>();
        List<PhieuMuon> pms2 = new ArrayList<>();
        List<ChiTietPhieuMuon> ctpms = new ArrayList<>();
        try {
            pms = sv.getListPhieuByID("PM00000035");
            PhieuMuon pm = pms.get(0);
            sv.deleteCTPD(pm);
            sv.deletePhieu(pm);
            pms2 = sv.getListPhieuByID("PM00000035");
            ctpms = sv.listCTPM(pm.getMaPhieuMuon());
            if(pms2.isEmpty() && ctpms.isEmpty())
                actual = 1;
            Assertions.assertEquals(1, actual);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuMuonTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

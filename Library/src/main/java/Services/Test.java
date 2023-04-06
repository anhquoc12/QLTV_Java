/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.DocGia;
import pojo.PhieuMuon;

/**
 *
 * @author Admin
 */
public class Test {
    public static void main(String[] args) throws SQLException {
        PhieuMuonServices pmsv = new PhieuMuonServices();
        List<PhieuMuon> pms = new ArrayList<>();
        pms = pmsv.getPhieuDatByIDDocGia("DG0001");
        if(pms.size() >= 1)
            System.out.println("Ko dc muon");
        else
            System.out.println("Dc muon");
        
    }
//    PhieuMuon pm = tbPhieuMuon.getSelectionModel().getSelectedItem();
//        PhieuMuonServices pmsv = new PhieuMuonServices();
//        pmsv.setTrangThaiPM(pm);
//        pmsv.chuyenTrangThaiSachTrongCTPM(pm);
}




//    List<PhieuMuon> pms = new ArrayList<>();
//        PhieuMuonServices s = new PhieuMuonServices();
//        pms = s.getPhieuMuonByIDDocGia(lblMaDocGia.getText());
//        if (lblMaDocGia.getText().equals("") || tbSachMuon.getItems().size() < 1 || pms == null) {
    
    
    
//        LocalDate date = LocalDate.now();
//        int d, m, y;
//        d = date.getDayOfMonth();
//        m = date.getMonthValue() - 1;
//        y = date.getYear() - 1990;
//        Date ngayMuon = new Date(y, m, d);
//        General gn = new General();
//        PhieuMuonServices sv = new PhieuMuonServices();
//        List<Sach> listSachs = new ArrayList<>();
//        for (Sach s : tbSachMuon.getItems()) {
//            listSachs.add(s);
//        }
//        String id = new PrimaryKey().ID_8("PM", new PhieuMuonServices().LastKey_PM());
//        PhieuMuon pm = new PhieuMuon(id, lblMaDocGia.getText(), ngayMuon, PhieuMuon.StateOfPM.DANG_DAT);
//        List<ChiTietPhieuMuon> listCTPMs = new ArrayList<>();
//        for (int i = 0; i < listSachs.size(); i++) {
//            ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(pm.getMaPhieuMuon(), listSachs.get(i).getMaSach());
//            listCTPMs.add(ctpm);
//        }
//        try {
//            sv.luuPhieuMuon(pm, listCTPMs);
//            gn.MessageBox("Lưu phiếu mượn Thành Công!!!", "Successfull", Alert.AlertType.INFORMATION).showAndWait();
//        } catch (SQLException ex) {
//            gn.MessageBox("Lưu phiếu mượn Thất Bại!!!", ex.getMessage(), Alert.AlertType.INFORMATION).showAndWait();
//        }
//    }

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Services.DocGiaServices;
import Services.PhieuMuonServices;
import Utils.General;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojo.ChiTietPhieuMuon;
import pojo.DocGia;
import pojo.PhieuMuon;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class TraSachController implements Initializable {

    @FXML
    private TableView<PhieuMuon> tbPhieuMuon;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private ComboBox<String> cbPhieuMuon;
    @FXML
    private Label lblMaDocGia;
    @FXML
    private Label lblTenDocGia;
    @FXML
    private Label lblNgaySinh;
    @FXML
    private Label lblDoiTuong;
    @FXML
    private Label lblMaPhieuMuon;
    @FXML
    private Label lblNgayMuon;
    @FXML
    private Label lblSoLuong;
    @FXML
    private Label lblTrangThai;
    @FXML
    private Label lblContentM;
    @FXML
    private Label lblContenP;
    @FXML
    private Label lblSoNgayTre;
    @FXML
    private Label lblTienPhat;
    String[] itemsOfPM = {"Mã Phiếu", "Mã Độc Giả"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPhieuMuon.getItems().addAll(itemsOfPM);
        cbPhieuMuon.setValue("Mã Phiếu");
        loadTablePhieuMuon();
        try {
            loadPhieuMuon();
        } catch (SQLException ex) {
            Logger.getLogger(TraSachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtTimKiem.textProperty().addListener((evt) -> {
            try {
                this.fillterDataTablePM(tbPhieuMuon, this.txtTimKiem.getText(), cbPhieuMuon.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(TraSachController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadTablePhieuMuon() {
        TableColumn colMaPhieu = new TableColumn("Mã Phiếu");
        colMaPhieu.setCellValueFactory(new PropertyValueFactory("maPhieuMuon"));
        colMaPhieu.setPrefWidth(120);
        TableColumn colMaDocGia = new TableColumn("Mã Độc Giả");
        colMaDocGia.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        colMaDocGia.setPrefWidth(120);
        TableColumn colngayLapPhieu = new TableColumn("Ngày Mượn");
        colngayLapPhieu.setCellValueFactory(new PropertyValueFactory("ngayMuon"));
        colngayLapPhieu.setPrefWidth(120);
        TableColumn colTrangThai = new TableColumn("Trạng Thái");
        colTrangThai.setCellValueFactory(new PropertyValueFactory("trangThai"));
        colTrangThai.setPrefWidth(120);
        this.tbPhieuMuon.getColumns().addAll(colMaPhieu, colMaDocGia, colngayLapPhieu, colTrangThai);
    }

    private void loadPhieuMuon() throws SQLException {
        PhieuMuonServices sv = new PhieuMuonServices();
        List<PhieuMuon> pms = sv.loadListPhieuMuon();
        this.tbPhieuMuon.getItems().clear();
        this.tbPhieuMuon.setItems(FXCollections.observableArrayList(pms));
    }

    private void fillterDataTablePM(TableView tableView, String kw, String type) throws SQLException {
        PhieuMuonServices sv = new PhieuMuonServices();
        if (type.equals(itemsOfPM[0])) {
            tableView.setItems(FXCollections.observableArrayList(sv.getListPhieuByID(kw)));
        } else {
            tableView.setItems(FXCollections.observableArrayList(sv.getListPhieuByIDDG(kw)));
        }
    }

    public void rowClick(MouseEvent event) throws SQLException {
        General gn = new General();
        TableView<PhieuMuon> table = (TableView<PhieuMuon>) event.getSource();
        PhieuMuon pm = table.getSelectionModel().getSelectedItem();
        DocGiaServices sv = new DocGiaServices();
        PhieuMuonServices svpm = new PhieuMuonServices();
        int soNgayMuon = gn.CheckTime(pm.getNgayMuon());
        if (pm != null) {
            String trangThai = pm.getTrangThai().name();
            lblMaPhieuMuon.setText(pm.getMaPhieuMuon());
            lblMaDocGia.setText(pm.getMaDocGia());
            lblNgayMuon.setText(pm.getNgayMuon().toString());
            if (trangThai.equals("CHUA_TRA")) {
                lblTrangThai.setText("Chưa Trả Sách");
                if (soNgayMuon > 30) {
                    int soNgayTre = soNgayMuon - 30;
                    double tienPhat = soNgayTre * 5000;
                    lblContentM.setText("Số ngày trễ:");
                    lblSoNgayTre.setText(String.valueOf(soNgayTre) + " " + "ngày");
                    lblContenP.setText("Tiền phạt:");
                    lblTienPhat.setText(String.valueOf(tienPhat) + "VND");
                } else {
                    lblContentM.setText("");
                    lblContenP.setText("");
                    lblSoNgayTre.setText("");
                    lblTienPhat.setText("");
                }
            } //            else if(trangThai.equals("DA_TRA")){
            //                lblTrangThai.setText("Đã trả sách");
            //            }
            else {
                lblTrangThai.setText("Đang Đặt");
                if (soNgayMuon >= 2) {
                    lblContentM.setText("Số ngày trễ:");
                    lblSoNgayTre.setText(String.valueOf(soNgayMuon));
                    lblContenP.setText("");
                    lblTienPhat.setText("");
                } else {
                    lblContentM.setText("");
                    lblSoNgayTre.setText("");
                    lblContenP.setText("");
                    lblTienPhat.setText("");
                }
            }
            DocGia dg = sv.listDocGiaByID(lblMaDocGia.getText()).get(0);
            lblTenDocGia.setText(dg.getTenDocGia());
            lblNgaySinh.setText(dg.getNgaySinh().toString());
            lblDoiTuong.setText(dg.getDoiTuong().name());
            int count = svpm.listCTPM(lblMaPhieuMuon.getText()).size();
            lblSoLuong.setText(String.valueOf(count));
        }
    }

    public void traSachHandler(ActionEvent event) throws SQLException {
        General gn = new General();
        PhieuMuon pm = tbPhieuMuon.getSelectionModel().getSelectedItem();
        if (lblMaPhieuMuon.getText().strip().equals("")) {
            gn.MessageBox("Warrning", "Hãy chọn phiếu mượn cần trả!!!", Alert.AlertType.ERROR).showAndWait();
        } else if (pm.getTrangThai().name().equals("DANG_DAT")) {
            gn.MessageBox("Không Hợp Lệ", "Không thể trả phiếu đặt sách!!!", Alert.AlertType.ERROR).showAndWait();
        } //        else if(pm.getTrangThai().name().equals("DA_TRA")){
        //            int r = tbPhieuMuon.getSelectionModel().getSelectedIndex();
        //            if (r >= 0) {
        //                tbPhieuMuon.getSelectionModel().clearSelection();
        //            }
        //            gn.MessageBox("Không Hợp Lệ", "Không thể trả phiếu này!!!", Alert.AlertType.ERROR).showAndWait();
        //            clearInfo();
        //        }
        else {
            PhieuMuonServices pmsv = new PhieuMuonServices();
            pmsv.setTrangThaiPM(pm);
            pmsv.chuyenTrangThaiSachTrongCTPM(pm);
            int r = tbPhieuMuon.getSelectionModel().getSelectedIndex();
            if (r >= 0) {
                tbPhieuMuon.getItems().remove(r);
                tbPhieuMuon.getSelectionModel().clearSelection();
            }
            gn.MessageBox("Thành Công", "Trả phiếu mượn thành công!!!", Alert.AlertType.INFORMATION).showAndWait();
            clearInfo();
        }
    }

    private void clearInfo() {
        lblMaDocGia.setText("");
        lblTenDocGia.setText("");
        lblDoiTuong.setText("");
        lblNgaySinh.setText("");
        lblMaPhieuMuon.setText("");
        lblTrangThai.setText("");
        lblSoLuong.setText("");
        lblNgayMuon.setText("");
    }

    public void xacNhanDatHandler(ActionEvent event) throws SQLException {
        PhieuMuon pm = tbPhieuMuon.getSelectionModel().getSelectedItem();
        General gn = new General();
        int soNgayTre = gn.CheckTime(pm.getNgayMuon());
        if (lblMaPhieuMuon.getText().strip().equals("")) {
            gn.MessageBox("Warrning", "Hãy chọn phiếu mượn cần trả!!!", Alert.AlertType.ERROR).showAndWait();
        } else if (pm.getTrangThai().name().equals("CHUA_TRA")) {
            gn.MessageBox("Không Hợp Lệ", "Không thể xác nhận phiếu Mượn sách!!!", Alert.AlertType.ERROR).showAndWait();
        } //        else if(pm.getTrangThai().name().equals("DA_TRA")){
        //            int r = tbPhieuMuon.getSelectionModel().getSelectedIndex();
        //            if (r >= 0) {
        //                tbPhieuMuon.getSelectionModel().clearSelection();
        //            }
        //            gn.MessageBox("Không Hợp Lệ", "Không thể trả phiếu này!!!", Alert.AlertType.ERROR).showAndWait();
        //            clearInfo();
        //        }
        else if (soNgayTre >= 2) {
            gn.MessageBox("Không thể xác nhận phiếu đặt", "Phiếu đặt không khả dụng do trễ 2 ngày!!!", Alert.AlertType.ERROR).showAndWait();
        } else {
            PhieuMuonServices pmsv = new PhieuMuonServices();
            pmsv.setTrangThaiPD(pm);
            pmsv.chuyenTrangThaiSachTrongCTPD(pm);
            int r = tbPhieuMuon.getSelectionModel().getSelectedIndex();
            if (r >= 0) {
                tbPhieuMuon.getItems().remove(r);
                tbPhieuMuon.getSelectionModel().clearSelection();
            }
            gn.MessageBox("Thành Công", "Xác nhận đặt sách thành công", Alert.AlertType.INFORMATION).showAndWait();
            clearInfo();
            loadPhieuMuon();
        }
    }

    public void huyDatHandler(ActionEvent event) throws SQLException {
        PhieuMuon pm = tbPhieuMuon.getSelectionModel().getSelectedItem();
        General gn = new General();
        PhieuMuonServices pmsv = new PhieuMuonServices();
        if (lblMaPhieuMuon.getText().equals("")) {
            gn.MessageBox("Chú Ý", "Hãy chọn phiếu đặt cần Hủy!!!", Alert.AlertType.ERROR).showAndWait();
        }
        else if (pm.getTrangThai().name().equals("CHUA_TRA")) {
            gn.MessageBox("Không Hợp Lệ", "Không thể Hủy phiếu Mượn sách!!!", Alert.AlertType.ERROR).showAndWait();
        }
        else if(gn.CheckTime(pm.getNgayMuon()) < 2){
            gn.MessageBox("Error", "Không thể hủy phiếu đặt do chưa hết hạn đặt!!!", Alert.AlertType.ERROR).showAndWait();
        }
        else{
            pmsv.chuyenTrangThaiSachTrongCTPM(pm);
            pmsv.deleteCTPD(pm);
            pmsv.deletePhieu(pm);
            int r = tbPhieuMuon.getSelectionModel().getSelectedIndex();
            if (r >= 0) {
                tbPhieuMuon.getItems().remove(r);
                tbPhieuMuon.getSelectionModel().clearSelection();
            }
            gn.MessageBox("Thành Công", "Hủy phiếu đặt đặt sách thành công", Alert.AlertType.INFORMATION).showAndWait();
            clearInfo();
        }
                
    }
    public void exitHandler(ActionEvent event){
        Platform.exit();
    }
}

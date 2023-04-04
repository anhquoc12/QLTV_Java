/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Services.DocGiaServices;
import Services.PhieuMuonServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML private TableView<PhieuMuon> tbPhieuMuon;
    @FXML private TextField txtTimKiem;
    @FXML private ComboBox<String> cbPhieuMuon;
    @FXML private Label lblMaDocGia;
    @FXML private Label lblTenDocGia;
    @FXML private Label lblNgaySinh;
    @FXML private Label lblDoiTuong;
    @FXML private Label lblMaPhieuMuon;
    @FXML private Label lblNgayMuon;
    @FXML private Label lblSoLuong;
    @FXML private Label lblTrangThai;
    String [] itemsOfPM = {"Phiếu Mượn", "Phiếu Đặt"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPhieuMuon.getItems().addAll(itemsOfPM);
        cbPhieuMuon.setValue("Phiếu Mượn");
        loadTablePhieuMuon();
        try {
            loadPhieuMuon(null);
        } catch (SQLException ex) {
            Logger.getLogger(TraSachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtTimKiem.textProperty().addListener((evt) ->{
            try {
                this.fillterDataTablePM(tbPhieuMuon, this.txtTimKiem.getText(), cbPhieuMuon.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(TraSachController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    private void loadTablePhieuMuon(){
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
    private void loadPhieuMuon(String kw) throws SQLException{
        PhieuMuonServices sv = new PhieuMuonServices();
        List<PhieuMuon> pms = sv.listPhieuMuon();
        this.tbPhieuMuon.getItems().clear();
        this.tbPhieuMuon.setItems(FXCollections.observableArrayList(pms));
    }
    private void fillterDataTablePM(TableView tableView, String kw, String type) throws SQLException{
        PhieuMuonServices sv = new PhieuMuonServices();
        if(type.equals(itemsOfPM[0])){
            tableView.setItems(FXCollections.observableArrayList(sv.listPhieuMuon(kw)));
        }
        else{
            tableView.setItems(FXCollections.observableArrayList(sv.listPhieuDat(kw)));
        }
    }
    public void rowClick(MouseEvent event) throws SQLException{
        TableView<PhieuMuon> table = (TableView<PhieuMuon>) event.getSource();
        PhieuMuon pm = table.getSelectionModel().getSelectedItem();
        DocGiaServices sv = new DocGiaServices();
        PhieuMuonServices svpm = new PhieuMuonServices();
        if(pm != null){
            String trangThai = pm.getTrangThai().name();
            lblMaPhieuMuon.setText(pm.getMaPhieuMuon());
            lblMaDocGia.setText(pm.getMaDocGia());
            lblNgayMuon.setText(pm.getNgayMuon().toString());
            if(trangThai.equals("CHUA_TRA"))
                lblTrangThai.setText("Chưa Trả Sách");
            else
                lblTrangThai.setText("Đang Đặt");
            DocGia dg = sv.listDocGiaByID(lblMaDocGia.getText()).get(0);
            lblTenDocGia.setText(dg.getTenDocGia());
            lblNgaySinh.setText(dg.getNgaySinh().toString());
            lblDoiTuong.setText(dg.getDoiTuong().name());
            int count = svpm.listCTPM(lblMaPhieuMuon.getText()).size();
            lblSoLuong.setText(String.valueOf(count));
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Services.ChiTietPhieuMuonServices;
import Services.DocGiaServices;
import Services.PhieuMuonServices;
import Services.SachServices;
import Utils.General;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import pojo.Sach;
import Utils.PrimaryKey;
import javafx.application.Platform;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MuonSachController implements Initializable {

    @FXML
    private Label lblTenDocGia;
    @FXML
    private Label lblMaDocGia;
    @FXML
    private Label lblDoiTuong;
    @FXML
    private Label lblSoDienThoai;
    @FXML
    private TableView<DocGia> tbDocGia;
    @FXML
    private TableView<Sach> tbSach;
    @FXML
    private TableView<Sach> tbSachMuon;
    @FXML
    private TextField txtKeyWord;
    @FXML
    private TextField txtTimKiemSach;
    @FXML
    private ComboBox<String> cbTimKiemDG;
    @FXML
    private ComboBox<String> cbTimKiemSach;
    String[] itemsOfSach = {"Tên Sách", "Mã Sách", "Tên Tác Giả"};
    String[] itemsOfDocGia = {"Tên Độc Giả", "Mã Độc Giả"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTimKiemDG.getItems().addAll(itemsOfDocGia);
        cbTimKiemDG.setValue("Tên Độc Giả");
        cbTimKiemSach.getItems().addAll(itemsOfSach);
        cbTimKiemSach.setValue("Tên Sách");
        loadTableDocGia();
        LoadTableSach();
        LoadTableMuon();
        try {
            LoadDataViewSach(tbSach);
        } catch (SQLException ex) {
            Logger.getLogger(MuonSachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            loadDocGia(null);
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(MuonSachController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtKeyWord.textProperty().addListener((evt) -> {
            try {
                this.FillterDataTableDG(tbDocGia, this.txtKeyWord.getText(), cbTimKiemDG.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(MuonSachController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.txtTimKiemSach.textProperty().addListener(o -> {
            try {
                FillterDataTableSach(tbSach, this.txtTimKiemSach.getText(), cbTimKiemSach.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        ObservableList timKiemDG = FXCollections
//        this.cbTimKiemDG.setItems(ol);
    }

    private void loadTableDocGia() {
        TableColumn colMaDocGia = new TableColumn("Mã Độc Giả");
        colMaDocGia.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        colMaDocGia.setPrefWidth(100);
        TableColumn colTenDocGia = new TableColumn("Tên Độc Giả");
        colTenDocGia.setCellValueFactory(new PropertyValueFactory("tenDocGia"));
        colTenDocGia.setPrefWidth(250);
        TableColumn colDoiTuong = new TableColumn("Đối tượng");
        colDoiTuong.setCellValueFactory(new PropertyValueFactory("doiTuong"));
        colDoiTuong.setPrefWidth(100);
        TableColumn colSoDienThoai = new TableColumn("Số điện thoại");
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory("soDT"));
        colSoDienThoai.setPrefWidth(120);
        this.tbDocGia.getColumns().addAll(colMaDocGia, colTenDocGia, colDoiTuong, colSoDienThoai);
    }

    private void loadDocGia(String kw) throws SQLException {
        DocGiaServices s = new DocGiaServices();
        List<DocGia> dgs = s.listDocGiaByName(kw);
        this.tbDocGia.getItems().clear();
        this.tbDocGia.setItems(FXCollections.observableList(dgs));
    }

    private void FillterDataTableDG(TableView table, String keywords, String type) throws SQLException {
        DocGiaServices s = new DocGiaServices();
        if (type.equals(itemsOfDocGia[0])) {
            table.setItems(FXCollections.observableArrayList(s.listDocGiaByName(keywords)));
        } else if (type.equals(itemsOfDocGia[1])) {
            table.setItems(FXCollections.observableArrayList(s.listDocGiaByID(keywords)));
        }
    }

    @FXML
    public void RowCLickTBGocGia(MouseEvent event) {

        TableView<DocGia> table = (TableView<DocGia>) event.getSource();
        DocGia dg = table.getSelectionModel().getSelectedItem();
//        
        if (dg != null) {
            lblTenDocGia.setText(dg.getTenDocGia());
            lblMaDocGia.setText(dg.getMaDocGia());
            lblDoiTuong.setText(dg.getDoiTuong().toString());
            lblSoDienThoai.setText(dg.getSoDT());
        }
    }

    private void LoadTableSach() {
        TableColumn colID = new TableColumn("Mã Sách");
        TableColumn colName = new TableColumn("Tên Sách");
        TableColumn colAuthor = new TableColumn("Tác giả");
        TableColumn colType = new TableColumn("Thể Loại");
        TableColumn colPosition = new TableColumn("Vi Trí Sách");
        TableColumn colStateofBook = new TableColumn("Trạng Thái");

        colID.setCellValueFactory(new PropertyValueFactory("maSach"));
        colName.setCellValueFactory(new PropertyValueFactory("tenSach"));
        colAuthor.setCellValueFactory(new PropertyValueFactory("tacGia"));
        colType.setCellValueFactory(new PropertyValueFactory("theLoai"));

        colPosition.setCellValueFactory(new PropertyValueFactory("viTri"));
        colStateofBook.setCellValueFactory(new PropertyValueFactory("trangThai"));

        this.tbSach.getColumns().addAll(colID, colName, colAuthor, colType,
                colPosition, colStateofBook);

    }

    private void LoadDataViewSach(TableView table) throws SQLException {
        table.setItems(FXCollections.observableArrayList(new SachServices().listDachKhaDung()));
    }

    private void FillterDataTableSach(TableView table, String keywords, String type) throws SQLException {
        SachServices s = new SachServices();
        if (type.equals(itemsOfSach[0])) {
            table.setItems(FXCollections.observableArrayList(s.listSachKhaDungByName(keywords)));
        } else if (type.equals(itemsOfSach[1])) {
            table.setItems(FXCollections.observableArrayList(s.listSachKhaDungByID(keywords)));
        } else {
            table.setItems(FXCollections.observableArrayList(s.listSachKhaDungByTG(keywords)));
        }
    }

    public void themHandler(ActionEvent event) {
        int r = tbSach.getSelectionModel().getSelectedIndex();
        if (r >= 0) {
            Sach s = tbSach.getSelectionModel().getSelectedItem();
            tbSachMuon.getItems().add(s);
            tbSach.getItems().remove(r);
            tbSach.getSelectionModel().clearSelection();
        }
    }

    public void xoaHandler(ActionEvent event) {
        int r = tbSachMuon.getSelectionModel().getSelectedIndex();
        if (r >= 0) {
            Sach s = tbSachMuon.getSelectionModel().getSelectedItem();
            tbSach.getItems().add(s);
            tbSachMuon.getItems().remove(r);
            tbSachMuon.getSelectionModel().clearSelection();
        }
    }

    private void LoadTableMuon() {
        TableColumn colID = new TableColumn("Mã Sách");
        TableColumn colName = new TableColumn("Tên Sách");
        TableColumn colAuthor = new TableColumn("Tác giả");
        TableColumn colType = new TableColumn("Thể Loại");

        colID.setCellValueFactory(new PropertyValueFactory("maSach"));
        colName.setCellValueFactory(new PropertyValueFactory("tenSach"));
        colAuthor.setCellValueFactory(new PropertyValueFactory("tacGia"));
        colType.setCellValueFactory(new PropertyValueFactory("theLoai"));

        this.tbSachMuon.getColumns().addAll(colID, colName, colAuthor, colType);
    }

    public void lapPhieuHandler(ActionEvent event) throws SQLException {
        List<PhieuMuon> phieumuons = new ArrayList<>();
        List<PhieuMuon> phieudats = new ArrayList<>();

        General messageBox = new General();

        PhieuMuonServices servicepm = new PhieuMuonServices();
        phieumuons = servicepm.getPhieuMuonByIDDocGia(lblMaDocGia.getText().strip());
        phieudats = servicepm.getPhieuDatByIDDocGia(lblMaDocGia.getText().strip());
        if (lblMaDocGia.getText().equals("") || tbSachMuon.getItems().size() < 1 || phieumuons.size() >= 1 || phieudats.size() >= 1 || tbSachMuon.getItems().size() > 5) {
            messageBox.MessageBox("WARNING", "Phiếu mượn không hợp lệ", Alert.AlertType.ERROR).showAndWait();
        } else {
            LocalDate date = LocalDate.now();
            int d, m, y;
            d = date.getDayOfMonth();
            m = date.getMonthValue() - 1;
            y = date.getYear() - 1900;
            Date ngayMuon = new Date(y, m, d);
            General gn = new General();
            PhieuMuonServices sv = new PhieuMuonServices();
            List<Sach> listSachs = new ArrayList<>();
            for (Sach s : tbSachMuon.getItems()) {
                listSachs.add(s);
            }
            String id = new PrimaryKey().ID_8("PM", new PhieuMuonServices().LastKey_PM());
            PhieuMuon pm = new PhieuMuon(id, lblMaDocGia.getText(), ngayMuon, PhieuMuon.StateOfPM.CHUA_TRA);
            List<ChiTietPhieuMuon> listCTPMs = new ArrayList<>();
            for (int i = 0; i < listSachs.size(); i++) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(pm.getMaPhieuMuon(), listSachs.get(i).getMaSach());
                listCTPMs.add(ctpm);
            }
            try {
                sv.luuPhieuMuon(pm, listCTPMs);
                for (Sach s : listSachs) {
                    sv.chuyenTrangThaiSach(s);
                }
                gn.MessageBox("THÔNG BÁO", "Lập phiếu mượn thành công!!!", Alert.AlertType.INFORMATION).showAndWait();
                tbSachMuon.getItems().clear();
            } catch (SQLException ex) {
                gn.MessageBox("ERROR", ex.getMessage(), Alert.AlertType.INFORMATION).showAndWait();
            }
        }
    }

    public void lapPhieuDatHandler(ActionEvent event) throws SQLException {
        List<PhieuMuon> phieumuons = new ArrayList<>();
        List<PhieuMuon> phieudats = new ArrayList<>();

        General messageBox = new General();

        PhieuMuonServices servicepm = new PhieuMuonServices();
        phieumuons = servicepm.getPhieuMuonByIDDocGia(lblMaDocGia.getText().strip());
        phieudats = servicepm.getPhieuDatByIDDocGia(lblMaDocGia.getText().strip());
        if (lblMaDocGia.getText().equals("") || tbSachMuon.getItems().size() < 1 || phieumuons.size() >= 1 || phieudats.size() >= 1 || tbSachMuon.getItems().size() > 5) {
            messageBox.MessageBox("WARNING", "Phiếu đặt không hợp lệ", Alert.AlertType.ERROR).showAndWait();
        } else {
            LocalDate date = LocalDate.now();
            int d, m, y;
            d = date.getDayOfMonth();
            m = date.getMonthValue() - 1;
            y = date.getYear() - 1900;
            Date ngayMuon = new Date(y, m, d);
            General gn = new General();
            PhieuMuonServices sv = new PhieuMuonServices();
            List<Sach> listSachs = new ArrayList<>();
            for (Sach s : tbSachMuon.getItems()) {
                listSachs.add(s);
            }
            String id = new PrimaryKey().ID_8("PM", new PhieuMuonServices().LastKey_PM());
            PhieuMuon pm = new PhieuMuon(id, lblMaDocGia.getText(), ngayMuon, PhieuMuon.StateOfPM.DANG_DAT);
            List<ChiTietPhieuMuon> listCTPMs = new ArrayList<>();
            for (int i = 0; i < listSachs.size(); i++) {
                ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon(pm.getMaPhieuMuon(), listSachs.get(i).getMaSach());
                listCTPMs.add(ctpm);
            }
            try {
                sv.luuPhieuMuon(pm, listCTPMs);
                for (Sach s : listSachs) {
                    sv.chuyenTrangThaiSachDat(s);
                }
                gn.MessageBox("THÔNG BÁO", "Lập phiếu đặt thành công!!!", Alert.AlertType.INFORMATION).showAndWait();
                tbSachMuon.getItems().clear();
            } catch (SQLException ex) {
                gn.MessageBox("ERROR", ex.getMessage(), Alert.AlertType.INFORMATION).showAndWait();
            }
        }
    }

    public void exitHandler(ActionEvent evevnt) {
        Platform.exit();
    }

//    public static void main(String[] args) {
//        LocalDate d = LocalDate.now();
//        int day, m, y;
//        day = d.getDayOfMonth();
//        m = d.getMonthValue() - 1;
//        y = d.getYear() - 1900;
//        Date ngayMuon = new Date(y, m, day);
//        System.out.println(ngayMuon);
//    }
}

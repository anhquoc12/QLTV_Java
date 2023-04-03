/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Sach;
import Services.SachServices;
import Utils.General;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Utils.PrimaryKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableRow;
import pojo.Sach.StateOfBook;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class BookController implements Initializable {

    String items[] = {"Tên Sách", "Tên Tác Giả", "Năm Xuất Bản", "Danh Mục"};
    @FXML
    private TableView<Sach> tbBook;
    @FXML
    private ToggleButton tgAdd;
    @FXML
    private ToggleButton tgEdit;
    @FXML
    private ToggleButton tgDelete;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtTacGia;
    @FXML
    private TextField txtTheLoai;
    @FXML
    private TextField txtNamXB;
    @FXML
    private TextField txtNoiXB;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtPosition;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> comSearch;
    @FXML
    private DatePicker datenhap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SachServices first = new SachServices();
        try {
            tbBook.getSelectionModel().select(0);
            Sach s = new SachServices().FirstBook();
            txtID.setText(s.getMaSach());
            txtName.setText(s.getTenSach());
            txtTacGia.setText(s.getTacGia());
            txtTheLoai.setText(s.getTheLoai());
            txtNamXB.setText(String.valueOf(s.getNamXB()).toString());
            txtDescription.setText(s.getMotaSach());
            txtPosition.setText(s.getViTri());
            txtNoiXB.setText(s.getNoiXB());
            datenhap.setValue(new General().ConvertDateToLocalDate(s.getNgayNhap()));

        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        comSearch.getItems().addAll(items);
        comSearch.setValue("Tên Sách");
        ToggleGroup groupBook = new ToggleGroup();
        tgAdd.setToggleGroup(groupBook);
        tgDelete.setToggleGroup(groupBook);
        tgEdit.setToggleGroup(groupBook);
        // TODO
        LoadTableView();
        try {
            LoadDataView(this.tbBook);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(o -> {
            try {
                FillterDataView(tbBook, this.txtSearch.getText(), comSearch.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    public void RowCLick(MouseEvent event) {

        TableView<Sach> table = (TableView<Sach>) event.getSource();
        Sach s = table.getSelectionModel().getSelectedItem();       
        if (s != null) {
            txtID.setText(s.getMaSach());
            txtName.setText(s.getTenSach());
            txtTacGia.setText(s.getTacGia());
            txtTheLoai.setText(s.getTheLoai());
            txtNamXB.setText(String.valueOf(s.getNamXB()));
            txtDescription.setText(s.getMotaSach());
            txtPosition.setText(s.getViTri());
            txtNoiXB.setText(s.getNoiXB());
            datenhap.setValue(new General().ConvertDateToLocalDate(s.getNgayNhap()));
        }
    }

    @FXML
    private void AddClick(MouseEvent event) throws SQLException {
        PrimaryKey key = new PrimaryKey();
        txtID.setText(key.ID_4("SA", new SachServices().LastKey_Book()));
        datenhap.setValue(LocalDate.now());
    }

    @FXML
    private void SaveClick(ActionEvent event) throws SQLException {
        SachServices service = new SachServices();
        if (tgAdd.isSelected()) {
            AddBook();
        } else if (tgDelete.isSelected()) {
            DeleteBook();
        } else if (tgEdit.isSelected()) {
            EditBook();
        } else {
            new General().MessageBox("Thông Báo", "Bạn chưa chọn bất kỳ hành động nào", AlertType.WARNING).showAndWait();
        }
        LoadDataView(tbBook);
    }

    private void LoadTableView() {
        TableColumn colID = new TableColumn("Mã Sách");
        TableColumn colName = new TableColumn("Tên Sách");
        TableColumn colAuthor = new TableColumn("Tác giả");
        TableColumn colType = new TableColumn("Thể Loại");
        TableColumn colYear = new TableColumn(" Năm Xuất Bản");
        TableColumn colPlace = new TableColumn("Nơi Xuất Bản");
        TableColumn colDate = new TableColumn("Ngày Nhập");
        TableColumn colPosition = new TableColumn("Vi Trí Sách");
        TableColumn colStateofBook = new TableColumn("Trạng Thái");
        TableColumn colDescription = new TableColumn("Mô Tả Sách");

        // Đổ dữ liệu
        colID.setCellValueFactory(new PropertyValueFactory("maSach"));
        colName.setCellValueFactory(new PropertyValueFactory("tenSach"));
        colAuthor.setCellValueFactory(new PropertyValueFactory("tacGia"));
        colType.setCellValueFactory(new PropertyValueFactory("theLoai"));
        colYear.setCellValueFactory(new PropertyValueFactory("namXB"));
        colPlace.setCellValueFactory(new PropertyValueFactory("noiXB"));
        colDate.setCellValueFactory(new PropertyValueFactory("ngayNhap"));
        colPosition.setCellValueFactory(new PropertyValueFactory("viTri"));
        colStateofBook.setCellValueFactory(new PropertyValueFactory("trangThai"));
        colDescription.setCellValueFactory(new PropertyValueFactory("motaSach"));

        this.tbBook.getColumns().addAll(colID, colName, colAuthor, colType, colYear,
                colPlace, colDate, colPosition, colStateofBook, colDescription);

    }

    private void LoadDataView(TableView table) throws SQLException {
        table.setItems(FXCollections.observableArrayList(new SachServices().SachList()));
    }

    private void FillterDataView(TableView table, String keywords, String type) throws SQLException {
        SachServices s = new SachServices();
        if (type == items[0]) {
            table.setItems(FXCollections.observableArrayList(s.GetBookByName(keywords)));
        } else if (type == items[1]) {
            table.setItems(FXCollections.observableArrayList(s.GetBookByTacGia(keywords)));
        } else if (type == items[2]) {
            table.setItems(FXCollections.observableArrayList(s.GetBookByNamXB(keywords)));
        } else {
            table.setItems(FXCollections.observableArrayList(s.GetBookByDanhMuc(keywords)));
        }
    }

    private void AddBook() throws SQLException {
        String id = txtID.getText();
        String name = txtName.getText();
        String tacgia = txtTacGia.getText();
        String theloai = txtTheLoai.getText();
        int namxb = Integer.parseInt(txtNamXB.getText());
        String noixb = txtNoiXB.getText();
        String vitri = txtPosition.getText();
        StateOfBook trangThai = Sach.StateOfBook.KHA_DUNG;
        int day = datenhap.getValue().getDayOfMonth();
        int month = datenhap.getValue().getMonthValue() - 1;
        int year = datenhap.getValue().getYear() - 1990;
        Date ngayNhap = new Date(year, month, day);
        String mota = txtDescription.getText();
        Sach s = new Sach(id, name, tacgia, theloai, namxb, noixb, ngayNhap, vitri, trangThai, mota);

        if ( new SachServices().AddBook(s)) {
            new General().MessageBox("Thông Báo", "Thêm Thành Công", AlertType.INFORMATION).showAndWait();
        } else {
            new General().MessageBox("Thông Báo", "Thêm Thất Bại", AlertType.ERROR).showAndWait();
        }
    }
    
    private void DeleteBook() throws SQLException
    {
        if (new SachServices().DeleteBook(txtID.getText())) {
                new General().MessageBox("Thông Báo", "Xóa Thành Công", AlertType.INFORMATION).showAndWait();
            } else {
                new General().MessageBox("Thông Báo", "Xóa Thất Bại", AlertType.ERROR).showAndWait();
            }
    }
    
    private void EditBook() throws SQLException
    {
        String id = txtID.getText();
            String name = txtName.getText();
            String tacgia = txtTacGia.getText();
            String theloai = txtTheLoai.getText();
            int namxb = Integer.parseInt(txtNamXB.getText());
            String noixb = txtNoiXB.getText();
            String vitri = txtPosition.getText();
            StateOfBook trangThai = Sach.StateOfBook.KHA_DUNG;
            int day = datenhap.getValue().getDayOfMonth();
            int month = datenhap.getValue().getMonthValue() - 1;
            int year = datenhap.getValue().getYear() - 1990;
            Date ngayNhap = new Date(year, month, day);
            String mota = txtDescription.getText();
            Sach s = new Sach(id, name, tacgia, theloai, namxb, noixb, ngayNhap, vitri, trangThai, mota);
            if (new Services.SachServices().EditBook(s)) {
                new General().MessageBox("Thông Báo", "Sửa Thành Công", AlertType.INFORMATION).showAndWait();
            } else {
                new General().MessageBox("Thông Báo", "Sửa Thất Bại", AlertType.ERROR).showAndWait();
            }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Utils.General;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.DocGia;
import Services.DocGiaServices;
import Utils.PrimaryKey;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import pojo.DocGia.Gender;
import pojo.DocGia.Object;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ReaderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> comGender;
    @FXML
    private DatePicker datengaySinh;
    @FXML
    private DatePicker dateNgayLapThe;
    @FXML
    private ComboBox<String> comObject;
    @FXML
    private TextField txtBoPhan;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtPhone;
    @FXML
    private ToggleButton tgAdd;
    @FXML
    private ToggleButton tgEdit;
    @FXML
    private ToggleButton tgDelete;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView tbReader;
    @FXML
    private TextField txtHanThe;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Set các Toggle Button
        ToggleGroup tgReader = new ToggleGroup();
        tgAdd.setToggleGroup(tgReader);
        tgEdit.setToggleGroup(tgReader);
        tgDelete.setToggleGroup(tgReader);
        // Set combobox
        comGender.getItems().addAll("NAM", "NU");
        comObject.getItems().addAll("STUDENT", "TEACHER", "EMPLOYEE");

        // Set tableview
        LoadTableView();
        try {
            LoadDataView(this.tbReader);
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.txtSearch.textProperty().addListener(o -> {
            try {
                tbReader.setItems(FXCollections.observableArrayList(new DocGiaServices().listDocGiaByName(txtSearch.getText())));
            } catch (SQLException ex) {
                Logger.getLogger(ReaderController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        DocGiaServices ds = new DocGiaServices();
        try {
            tbReader.getSelectionModel().select(0);
            DocGia r = ds.FirstReader();
            txtID.setText(r.getMaDocGia());
            txtName.setText(r.getTenDocGia());
            comGender.setValue(r.getGioiTinh().toString());
            datengaySinh.setValue(new General().ConvertDateToLocalDate(r.getNgaySinh()));
            comObject.setValue(r.getDoiTuong().toString());
            txtBoPhan.setText(r.getBoPhan());
            txtEmail.setText(r.getEmail());
            txtPhone.setText(r.getSoDT());
            txtAddress.setText(r.getDiaChi());
            dateNgayLapThe.setValue(new General().ConvertDateToLocalDate(r.getNgayLapThe()));
            LocalDate expires = dateNgayLapThe.getValue().plusYears(4);
            txtHanThe.setText(dateNgayLapThe.getValue() + " -> " + expires);

        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LoadTableView() {
        TableColumn colID = new TableColumn("Mã Độc Giả");
        TableColumn colName = new TableColumn("Tên Độc Giả");
        TableColumn colGender = new TableColumn("Giới Tính");
        TableColumn colBirthday = new TableColumn("Ngày Sinh");
        TableColumn colObject = new TableColumn("Đối Tượng");
        TableColumn colCreateDay = new TableColumn("Ngày Lập Thẻ");
        TableColumn colPhone = new TableColumn("Số Điện Thoại");
        TableColumn colAddress = new TableColumn("Địa Chỉ");
        TableColumn colEmail = new TableColumn("Email");

        // Đổ dữ liệu
        colID.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        colName.setCellValueFactory(new PropertyValueFactory("tenDocGia"));
        colGender.setCellValueFactory(new PropertyValueFactory("gioiTinh"));
        colBirthday.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        colObject.setCellValueFactory(new PropertyValueFactory("doiTuong"));
        colCreateDay.setCellValueFactory(new PropertyValueFactory("ngayLapThe"));
        colPhone.setCellValueFactory(new PropertyValueFactory("soDT"));
        colAddress.setCellValueFactory(new PropertyValueFactory("diaChi"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        this.tbReader.getColumns().addAll(colID, colName, colGender,
                colBirthday, colObject, colCreateDay, colPhone, colAddress,
                colEmail);

    }

    private void LoadDataView(TableView table) throws SQLException {
        table.setItems(FXCollections.observableArrayList(new DocGiaServices().DocGiaList()));
    }

    @FXML
    public void RowCLick(MouseEvent event) {

        TableView<DocGia> table = (TableView<DocGia>) event.getSource();
        DocGia r = table.getSelectionModel().getSelectedItem();
        if (r != null) {
            txtID.setText(r.getMaDocGia());
            txtName.setText(r.getTenDocGia());
            comGender.setValue(r.getGioiTinh().toString());
            datengaySinh.setValue(new General().ConvertDateToLocalDate(r.getNgaySinh()));
            comObject.setValue(r.getDoiTuong().toString());
            txtBoPhan.setText(r.getBoPhan());
            txtEmail.setText(r.getEmail());
            txtPhone.setText(r.getSoDT());
            txtAddress.setText(r.getDiaChi());
            dateNgayLapThe.setValue(new General().ConvertDateToLocalDate(r.getNgayLapThe()));
            LocalDate expires = dateNgayLapThe.getValue().plusYears(4);
            txtHanThe.setText(dateNgayLapThe.getValue() + " -> " + expires);

        }
    }

    @FXML
    public void AddCLick(ActionEvent event) throws SQLException {
        txtHanThe.setText("");
        PrimaryKey key = new PrimaryKey();
        txtID.setText(key.ID_4("DG", new DocGiaServices().LastKey_Reader()));
        dateNgayLapThe.setValue(LocalDate.now());
        
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtBoPhan.setText("");
        txtEmail.setText("");
        txtHanThe.setText("");
        txtPhone.setText("");
        txtSearch.setText("");
        
    }

    public void AddReader() throws SQLException {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty()
                || txtBoPhan.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtAddress.getText().isEmpty() || txtPhone.getText().isEmpty()
                || !checkEmail(txtEmail.getText()) || !checkSDT(txtPhone.getText())) {
            new General().MessageBox("Thông Báo", "Vui lòng nhập đủ thông tin", AlertType.ERROR).showAndWait();
            return;
        }

        String id = txtID.getText();
        String name = txtName.getText();
        String gender = comGender.getValue();
        LocalDate ngaysinh = datengaySinh.getValue();
        int day = ngaysinh.getDayOfMonth();
        int month = ngaysinh.getMonthValue() - 1;
        int year = ngaysinh.getYear() - 1900;
        Date birthday = new Date(year, month, day);
        String object = comObject.getValue();
        String bophan = txtBoPhan.getText();
        LocalDate ngayLapthe = LocalDate.now();
        day = ngayLapthe.getDayOfMonth();
        month = ngayLapthe.getMonthValue() - 1;
        year = ngayLapthe.getYear() - 1900;
        Date createdday = new Date(year, month, day);
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();

        DocGia r = new DocGia(id, name, DocGia.Gender.valueOf(gender), birthday, DocGia.Object.valueOf(object),
                createdday, phone, address, bophan, email);

        if (new DocGiaServices().AddReader(r)) {
            new General().MessageBox("Thông Báo", "Thêm Thành Công", Alert.AlertType.INFORMATION).showAndWait();
        } else {
            new General().MessageBox("Thông Báo", "Thêm Thất Bại", Alert.AlertType.ERROR).showAndWait();
        }
    }

    private void EditReader() throws SQLException {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty()
                || txtBoPhan.getText().isEmpty() || txtEmail.getText().isEmpty()
                || txtAddress.getText().isEmpty() || txtPhone.getText().isEmpty()
                || !checkEmail(txtEmail.getText()) || !checkSDT(txtPhone.getText())) {
            new General().MessageBox("Thông Báo", "Vui lòng nhập đủ thông tin", AlertType.ERROR).showAndWait();
            return;
        }

        String id = txtID.getText();
        String name = txtName.getText();
        String gender = comGender.getValue();
        LocalDate ngaysinh = datengaySinh.getValue();
        int day = ngaysinh.getDayOfMonth();
        int month = ngaysinh.getMonthValue() - 1;
        int year = ngaysinh.getYear() - 1900;
        Date birthday = new Date(year, month, day);
        String object = comObject.getValue();
        String bophan = txtBoPhan.getText();
        LocalDate ngayLapthe = LocalDate.now();
        day = ngayLapthe.getDayOfMonth();
        month = ngayLapthe.getMonthValue() - 1;
        year = ngayLapthe.getYear() - 1900;
        Date createdday = new Date(year, month, day);
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();

        DocGia r = new DocGia(id, name, DocGia.Gender.valueOf(gender), birthday,
                DocGia.Object.valueOf(object),
                createdday, phone, address, bophan, email);

        if (new DocGiaServices().EditReader(r)) {
            new General().MessageBox("Thông Báo", "Sửa Thành Công", Alert.AlertType.INFORMATION).showAndWait();
        } else {
            new General().MessageBox("Thông Báo", "Sửa Thất Bại", Alert.AlertType.ERROR).showAndWait();
        }
    }

    private void DeleteReader() throws SQLException {
        String id = txtID.getText();
        if (new DocGiaServices().DeleteReader(id)) {
            new General().MessageBox("Thông Báo", "Xoá Thành Công", Alert.AlertType.INFORMATION).showAndWait();
        } else {
            new General().MessageBox("Thông Báo", "Xoá Thất Bại", Alert.AlertType.ERROR).showAndWait();
        }
    }

    @FXML
    public void SaveClick(ActionEvent event) throws SQLException {
        if (tgAdd.isSelected()) {
            AddReader();
        } else if (tgEdit.isSelected()) {
            EditReader();
        } else if (tgDelete.isSelected()) {
            DeleteReader();
        } else {
            new General().MessageBox("Thông Báo", "Bạn chưa chọn bất kỳ hành động nào", AlertType.WARNING).showAndWait();
        }
        LoadDataView(tbReader);
    }

    public boolean checkEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean checkSDT(String phone) {
        int sdt = 0;
        try {
            sdt = Integer.parseInt(phone);
            return true;
        } catch (Exception ex) {
            return false;
        }
    
    } 
}

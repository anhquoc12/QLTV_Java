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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;

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
    private ComboBox<DocGia.Gender> comGender;
    @FXML
    private DatePicker datengaySinh;
    @FXML
    private DatePicker dateNgayLapThe;
    @FXML
    private ComboBox<Object> comObject;
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
    private Button btnSave;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Set các Toggle Button
        ToggleGroup tgReader = new ToggleGroup();
        tgAdd.setToggleGroup(tgReader);
        tgEdit.setToggleGroup(tgReader);
        tgDelete.setToggleGroup(tgReader);
        // Set combobox
        for (DocGia.Gender value : DocGia.Gender.values()) {
            comGender.getItems().add(value);
        }
        for (DocGia.Object value : DocGia.Object.values()) {
            comObject.getItems().add(value);
        }

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
            comGender.setValue(r.getGioiTinh());
            datengaySinh.setValue(new General().ConvertDateToLocalDate(r.getNgaySinh()));
            comObject.setValue(r.getDoiTuong());
            txtBoPhan.setText(r.getBoPhan());
            txtEmail.setText(r.getEmail());
            txtPhone.setText(r.getSoDT());
            txtAddress.setText(r.getDiaChi());
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
            comGender.setValue(r.getGioiTinh());
            datengaySinh.setValue(new General().ConvertDateToLocalDate(r.getNgaySinh()));
            comObject.setValue(r.getDoiTuong());
            txtBoPhan.setText(r.getBoPhan());
            txtEmail.setText(r.getEmail());
            txtPhone.setText(r.getSoDT());
            txtAddress.setText(r.getDiaChi());
        }
    }

    @FXML
    public void AddCLick(ActionEvent event) throws SQLException {
        PrimaryKey key = new PrimaryKey();
        txtID.setText(key.ID_4("DG", new DocGiaServices().LastKey_Reader()));
        dateNgayLapThe.setValue(LocalDate.now());
    }
    
    @FXML
    public void SaveClick(ActionEvent event) throws SQLException
    {
        if (tgAdd.isSelected())
        {
            new General().MessageBox("Thông Báo", "Thêm Thành Công", Alert.AlertType.INFORMATION).showAndWait();
            String id = txtID.getText();
            String name = txtName.getText();
            String gender = comGender.getValue().toString();
            LocalDate ngaysinh = datengaySinh.getValue();
            int day = ngaysinh.getDayOfMonth();
            int month = ngaysinh.getMonthValue();
            int year = ngaysinh.getYear() - 1990;
            Date birthday = new Date(year, month, day);
            String object = comObject.getValue().toString();
            String bophan = txtBoPhan.getText();
            LocalDate ngayLapthe = dateNgayLapThe.getValue();
            day = ngayLapthe.getDayOfMonth();
            month = ngayLapthe.getMonthValue();
            year = ngayLapthe.getYear() - 1990;
            Date createdday = new Date(year, month, day);
            String email = txtEmail.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            
            DocGia r = new DocGia(id, name, DocGia.Gender.valueOf(gender), birthday, DocGia.Object.valueOf(object),
            createdday, phone, address, bophan, email);
            
            if (new DocGiaServices().AddReader(r))
            {
                new General().MessageBox("Thông Báo", "Thêm Thành Công", Alert.AlertType.INFORMATION).showAndWait();
            }
            else
            {
                new General().MessageBox("Thông Báo", "Thêm Thất Bại", Alert.AlertType.ERROR).showAndWait();
            }
        }
    }
    
    
}

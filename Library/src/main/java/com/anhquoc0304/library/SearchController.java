/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Services.SachServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class SearchController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML RadioButton rdSach;
    @FXML RadioButton rdTG;
    @FXML RadioButton rdNam;
    @FXML RadioButton rdDanhMuc;
    @FXML TextField txtSearch;
    @FXML TableView tbBook;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTableView();
        try {
            LoadDataView(tbBook);
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}

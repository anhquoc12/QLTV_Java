/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.anhquoc0304.library;

import Services.StatServices;
import Utils.General;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojo.StatBook;
import pojo.StatDocGia;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class StatController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker dateStat;
    @FXML
    private TableView tbStat_book;
    @FXML
    private TableView tbStat_reader;
    @FXML
    private PieChart chart_Book;
    @FXML
    private PieChart chart_Reader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTableView_StatByBook();
        LoadTableView_StatByReader();

    }

    @FXML
    private void StatClick(ActionEvent event) throws SQLException {
        if (dateStat.getValue() == null)
        {
            new General().MessageBox("Thông Báo", "Bạn chưa chọn thời gian", Alert.AlertType.WARNING).showAndWait();
        }
        int quarter = new General().Quarter(dateStat);
        int year = dateStat.getValue().getYear();
        try {
            LoadDataView_Book(quarter, year);
            LoadDataView_Reader(quarter, year);
        } catch (SQLException ex) {
            Logger.getLogger(StatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PieChart(quarter, year);
    }

    private void LoadTableView_StatByBook() {
        TableColumn colID = new TableColumn("Mã Sách");
        colID.setPrefWidth(100);
        TableColumn colName = new TableColumn("Tên Sách");
        colName.setPrefWidth(200);
        TableColumn colCount = new TableColumn("Số Lượng Mượn");
        colCount.setPrefWidth(100);

        // Đổ dữ liệu
        colID.setCellValueFactory(new PropertyValueFactory("maSach"));
        colName.setCellValueFactory(new PropertyValueFactory("tenSach"));
        colCount.setCellValueFactory(new PropertyValueFactory("soLuongMuon"));

        this.tbStat_book.getColumns().addAll(colID, colName, colCount);

    }

    private void LoadTableView_StatByReader() {
        TableColumn colID = new TableColumn("Mã Độc Giả");
        colID.setPrefWidth(100);
        TableColumn colName = new TableColumn("Tên Độc Giả");
        colName.setPrefWidth(200);
        TableColumn colCount_Rent = new TableColumn("Số Lần Mượn");
        colCount_Rent.setPrefWidth(100);

        // Đổ dữ liệu
        colID.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        colName.setCellValueFactory(new PropertyValueFactory("tenDocGia"));
        colCount_Rent.setCellValueFactory(new PropertyValueFactory("soLanMuon"));

        this.tbStat_reader.getColumns().addAll(colID, colName, colCount_Rent);
    }

    private void LoadDataView_Book(int quarter, int year) throws SQLException {
        tbStat_book.setItems(FXCollections.observableArrayList(new StatServices().StatByBook(quarter, year)));
    }
    
    private void LoadDataView_Reader(int quarter, int year) throws SQLException {
        tbStat_reader.setItems(FXCollections.observableArrayList(new StatServices().StatByReader(quarter, year)));
    }


    private void PieChart(int quarter, int year) throws SQLException {
        // Chart của sách
        chart_Book.getData().clear();
        List<StatBook> listbook = new StatServices().StatByBook(quarter, year);
        chart_Book.setTitle(String.format("Biểu đồ thống kê số lượng sách đã mượn trong quý %d của năm %d", quarter, year));
        chart_Book.setLabelLineLength(25);
        chart_Book.setLegendSide(Side.BOTTOM);
        for (StatBook l : listbook) {
            PieChart.Data p = new PieChart.Data(l.getTenSach(), l.getSoLuongMuon());
            chart_Book.getData().add(p);
        }
        chart_Reader.getData().clear();
        // Chart của độc giả
        List<StatDocGia> listreader = new StatServices().StatByReader(quarter, year);
        chart_Reader.setTitle(String.format("Biểu đồ thống kê độc giả có số lần đã mượn sách nhiều nhất trong quý %d của năm %d", quarter, year));
        chart_Reader.setLabelLineLength(25);
        chart_Reader.setLegendSide(Side.BOTTOM);
        for (StatDocGia l : listreader) {
            PieChart.Data p = new PieChart.Data(l.getTenDocGia(), l.getSoLanMuon());
            chart_Reader.getData().add(p);
        }
    }
}

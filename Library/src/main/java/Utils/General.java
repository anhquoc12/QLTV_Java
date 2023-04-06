/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

/**
 *
 * @author dell
 */
public class General {

    public Date ConvertLocalDateToDate(LocalDate localDate) {
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue() - 1;
        int year = localDate.getYear() - 1900;
        return new Date(year, month, day);
    }

    public LocalDate ConvertDateToLocalDate(Date date) {
        // Tạo đối tượng DateTimeFormatter với định dạng ngày tháng mong muốn
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

// Chuyển đổi chuỗi sang đối tượng LocalDate
        return LocalDate.parse(date.toString(), formatter);
    }

    public Alert MessageBox(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }
    public int Quarter(DatePicker dp) {
        LocalDate selectedDate = dp.getValue();
        int selectedMonth = selectedDate.getMonthValue();
        return (selectedMonth - 1) / 3 + 1;

    }

    public int CheckTime(Date d) {
        Date n = new General().ConvertLocalDateToDate(LocalDate.now());
        int seconds = (int) (n.getTime() / 1000 - d.getTime() / 1000);
        return seconds / (24 * 60 * 60);
    }
}

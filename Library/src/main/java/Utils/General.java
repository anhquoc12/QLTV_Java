/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Services.SachServices;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

/**
 *
 * @author dell
 */
public class General {

    public Date ConvertLocalDateToDate(LocalDate localDate) {

// Tạo một đối tượng ZoneId với múi giờ tương ứng
        ZoneId zoneId = ZoneId.systemDefault();

// Chuyển đổi LocalDate thành LocalDateTime
        LocalDateTime localDateTime = localDate.atStartOfDay();

// Chuyển đổi LocalDateTime sang ZonedDateTime với ZoneId tương ứng
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

// Chuyển đổi ZonedDateTime sang Instant
        Instant instant = zonedDateTime.toInstant();

// Chuyển đổi Instant sang java.util.Date
        return Date.from(instant);

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

    public Date ConvertFromDatePicker(DatePicker dp) {
        LocalDate localDate = dp.getValue();

// Chuyển đổi LocalDate thành Date
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

// Định dạng lại đối tượng Date thành chuỗi dạng yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);

        return date;

    }

    public static void main(String[] args) throws SQLException {
    }
}

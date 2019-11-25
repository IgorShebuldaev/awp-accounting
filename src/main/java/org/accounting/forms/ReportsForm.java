package org.accounting.forms;

import org.accounting.database.models.Delivery;
import org.accounting.libs.ReportBuilder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportsForm extends BaseController implements Initializable {
    @FXML private DatePicker dpFrom;
    @FXML private DatePicker dpTo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dpFrom.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        dpTo.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public void postInitializable() {
        stage.setTitle("Deliveries report");
    }

    @FXML
    private void handleBtnShow() {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        WebView webView = new WebView();

        webView.getEngine().loadContent(getReport());

        root.getChildren().add(webView);

        stage.setTitle("Report");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }

    private String getReport() {
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportBuilder.TableNode tableNode = new ReportBuilder.TableNode(new String[]{"Delivery date", "Supplier", "Product", "Price", "Worker"});
        tableNode.setTitle("Deliveries");

        String from = dpFrom.getValue().toString();
        String to = dpTo.getValue().toString();

        ArrayList<Delivery> results = new Delivery().getRecordsByDate(from, to);

        for (Delivery delivery : results) {
            tableNode.appendRow(new Object[]{
                    delivery.getDeliveryDate().toString(),
                    delivery.getSupplier().getName(),
                    delivery.getProduct(),
                    delivery.getPrice(),
                    delivery.getWorker().getFullName()
            });
        }

        ReportBuilder.TableNode innerTableNode = new ReportBuilder.TableNode(new String[]{"Price", "Worker"});
        innerTableNode.appendRow(new Object[]{"value1", "value2"});
        innerTableNode.setTitle("Inner table");

        tableNode.appendRow(new Object[]{innerTableNode});

        reportBuilder.addTableNode(tableNode);

        return reportBuilder.buildReport(ReportBuilder.html);
    }
}

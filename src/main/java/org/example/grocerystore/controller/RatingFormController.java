// RatingFormController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.IsoFields;
import java.util.List;

@Component
public class RatingFormController {

    @FXML private ComboBox<String> cbPeriodType;
    @FXML private TextField tfPeriodValue;
    @FXML private TableView<RatingItem> tvRating;
    @FXML private TableColumn<RatingItem,String> tcProduct, tcTotalSold;

    private final FormService formService;
    private final PurchaseService purchaseService;

    public RatingFormController(FormService fs, PurchaseService ps) {
        this.formService = fs;
        this.purchaseService = ps;
    }

    @FXML
    public void initialize() {
        cbPeriodType.setItems(FXCollections.observableArrayList("Все время","Год","Месяц","Неделя"));
    }

    @FXML private void handleCalculateRating() {
        LocalDateTime start=null,end=null;
        String t = cbPeriodType.getValue();
        String v = tfPeriodValue.getText().trim();
        try {
            if ("Год".equals(t)) {
                int y = Integer.parseInt(v);
                start=LocalDateTime.of(y,1,1,0,0);
                end=LocalDateTime.of(y,12,31,23,59,59);
            } else if ("Месяц".equals(t)) {
                int m = Integer.parseInt(v), y=LocalDate.now().getYear();
                YearMonth ym=YearMonth.of(y,m);
                start=ym.atDay(1).atStartOfDay();
                end=ym.atEndOfMonth().atTime(23,59,59);
            } else if ("Неделя".equals(t)) {
                int w = Integer.parseInt(v);
                LocalDate weekStart = LocalDate.now()
                        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, w)
                        .with(DayOfWeek.MONDAY);
                start=weekStart.atStartOfDay();
                end=weekStart.plusDays(7).atStartOfDay().minusNanos(1);
            }
        } catch (Exception ex) {
            showError("Нету"); return;
        }
        List<Object[]> list = (start!=null? purchaseService.getTopProductBetween(start,end)
                : purchaseService.getTopProductAllTime());
        ObservableList<RatingItem> data = FXCollections.observableArrayList();
        for (var r: list) {
            String name = ((org.example.grocerystore.model.entity.Product)r[0]).getName();
            int sold = ((Number)r[1]).intValue();
            data.add(new RatingItem(name,sold));
        }
        tvRating.setItems(data);
    }

    @FXML private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showError(String m) {
        new Alert(Alert.AlertType.ERROR, m).showAndWait();
    }

    public static class RatingItem {
        private final javafx.beans.property.SimpleStringProperty product;
        private final javafx.beans.property.SimpleStringProperty totalSold;
        public RatingItem(String p,int s) {
            this.product=new javafx.beans.property.SimpleStringProperty(p);
            this.totalSold=new javafx.beans.property.SimpleStringProperty(String.valueOf(s));
        }
        public javafx.beans.property.StringProperty productProperty(){return product;}
        public javafx.beans.property.StringProperty totalSoldProperty(){return totalSold;}
    }
}

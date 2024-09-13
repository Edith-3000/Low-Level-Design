module org.example.onlineshopping {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.onlineshopping to javafx.fxml;
    exports org.example.onlineshopping;
}
module FXModule {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    opens com.dawist_o.CodeAnalyzer.mainScreen;
    exports com.dawist_o.CodeAnalyzer.mainScreen;
}

module FXModule {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;
    requires transitive org.mapstruct.processor;

    exports com.dawist_o.CodeAnalyzer.mainScreen;
    opens com.dawist_o.CodeAnalyzer.mainScreen;
}

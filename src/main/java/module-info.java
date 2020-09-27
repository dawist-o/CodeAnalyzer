module FXModule {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;

    //TODO lombok(смотри HalsteadMetrics) установи плагин если не будет видеть и включи в Settings->Compiler->Annotation Processor та мгалочка
    requires static lombok;
    requires transitive org.mapstruct.processor;  //без него lombok выпендривается
    requires kotlin.stdlib;

    exports com.dawist_o.CodeAnalyzer.mainScreen;
    opens com.dawist_o.CodeAnalyzer.mainScreen;
    exports com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;
    opens com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;
}

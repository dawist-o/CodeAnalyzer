module FXModule {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;

    //TODO lombok(смотри HalsteadMertics) установи плагин если не будет видеть и включи в Settings->Compiler->Annotation Processor та мгалочка
    requires static lombok;
    requires transitive org.mapstruct.processor;  //без него lombok выпендривается

    exports com.dawist_o.CodeAnalyzer.mainScreen;
    opens com.dawist_o.CodeAnalyzer.mainScreen;
}

module FXModule {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;

    //TODO lombok(смотри HalsteadMetrics) установи плагин если не будет видеть и включи в Settings->Compiler->Annotation Processor та мгалочка
    requires static lombok;
    requires org.mapstruct.processor;  //без него lombok выпендривается
    requires kotlin.stdlib;

    exports com.dawist_o.CodeAnalyzer.FileParser;
    opens com.dawist_o.CodeAnalyzer.FileParser;

    exports com.dawist_o.CodeAnalyzer.StagesController;
    opens com.dawist_o.CodeAnalyzer.StagesController;

    exports com.dawist_o.CodeAnalyzer;
    opens com.dawist_o.CodeAnalyzer;

    exports com.dawist_o.CodeAnalyzer.MainScreen.controller;
    opens com.dawist_o.CodeAnalyzer.MainScreen.controller;

    exports com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;
    opens com.dawist_o.CodeAnalyzer.HalsteadMetrics.controller;

    exports com.dawist_o.CodeAnalyzer.JilbMetrics.controller;
    opens com.dawist_o.CodeAnalyzer.JilbMetrics.controller;

    exports com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.controller;
    opens com.dawist_o.CodeAnalyzer.SpenAndChepinsMetrics.controller;
}

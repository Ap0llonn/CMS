module com.cms {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.base;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens com.cms to javafx.fxml;
    opens com.cms.controller to javafx.fxml;

    exports com.cms;
    exports com.cms.controller;
}
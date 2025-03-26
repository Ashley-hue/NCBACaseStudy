module org.example.ncbacasestudy {
    requires javafx.controls;
    requires javafx.fxml;

    // JSON Processing
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    requires org.slf4j.simple;
    requires org.apache.httpcomponents.client5.httpclient5.fluent;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.client5.httpclient5;

    opens org.example.ncbacasestudy to javafx.fxml, com.fasterxml.jackson.databind;
    exports org.example.ncbacasestudy;
}
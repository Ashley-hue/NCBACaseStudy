module org.example.ncbacasestudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.client5.httpclient5.fluent;


    opens org.example.ncbacasestudy to javafx.fxml;
    exports org.example.ncbacasestudy;
}
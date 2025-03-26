module org.example.ncbacasestudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.example.ncbacasestudy to javafx.fxml;
    exports org.example.ncbacasestudy;
}
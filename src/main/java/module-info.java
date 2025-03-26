module org.example.ncbacasestudy {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ncbacasestudy to javafx.fxml;
    exports org.example.ncbacasestudy;
}
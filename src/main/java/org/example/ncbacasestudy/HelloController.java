package org.example.ncbacasestudy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.fluent.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

public class
HelloController {
    @FXML
    private ListView<String> productListView;
    @FXML
    private TextField searchField;

    private final ObservableList<String> productTitles = FXCollections.observableArrayList();
    private List<Product> products;

    private void initialize() {
        fetchProducts();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProducts(newValue));
    }

    private void fetchProducts() {
        try {
            String response = Request.get("https://dummyjson.com/products").execute().returnContent().asString();
            ObjectMapper objectMapper = new ObjectMapper();
            ProductResponse productResponse = objectMapper.readValue(response, ProductResponse.class);

            this.products = productResponse.getProducts();
            productTitles.setAll(products.stream().map(Product::getTitle).collect(Collectors.toList()));
            productListView.setItems(productTitles);
        }
        catch(Exception e) {
            System.out.println("Error fetching ptoducts: " + e.getMessage());
        }
    }

    private void filterProducts(String searchTerm) {
        List<String> filteredList = products.stream()
                .filter(product -> product.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .map(Product::getTitle)
                .collect(Collectors.toList());

                productTitles.setAll(filteredList);
    }
}
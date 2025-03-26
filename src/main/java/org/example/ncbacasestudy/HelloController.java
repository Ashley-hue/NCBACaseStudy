package org.example.ncbacasestudy;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


public class
HelloController {
    @FXML
    private ListView<String> productListView;
    @FXML
    private TextField searchField;

    private final ObservableList<String> productTitles = FXCollections.observableArrayList();
    private List<Product> products;

    @FXML
    private void initialize() {
        new Thread(this::fetchProducts).start();
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                        Platform.runLater(() -> filterProducts(newValue))
        );
    }

    private void fetchProducts() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ClassicHttpRequest request = new HttpGet("https://dummyjson.com/products");

            String response = httpClient.execute(request, httpResponse ->
                    EntityUtils.toString(httpResponse.getEntity()));
            System.out.println(response);
            ObjectMapper objectMapper = new ObjectMapper();
            ProductResponse productResponse = objectMapper.readValue(response, ProductResponse.class);

            Platform.runLater(() -> {
                this.products = productResponse.getProducts();
                productTitles.setAll(products.stream()
                        .map(Product::getTitle)
                        .collect(Collectors.toList()));
                productListView.setItems(productTitles);
            });
        }
        catch(Exception e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Network Error");
                alert.setHeaderText("Could not fetch products");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            });
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }

    private void filterProducts(String searchTerm) {
        if (products == null) return;

        List<String> filteredList = products.stream()
                .map(Product::getTitle)
                .filter(title -> title.toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());

                productTitles.setAll(filteredList);
    }
}
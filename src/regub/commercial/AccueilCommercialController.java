/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regub.commercial;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import regub.AbstractController;
import regub.Auth;
import regub.Main;
import regub.util.UserBarController;

/**
 *
 * @author Mesmerus
 */
public class AccueilCommercialController extends AbstractController {
    
    private final ObservableList<Client> clientData = FXCollections.observableArrayList();
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> societe;
    @FXML
    private TableColumn<Client, String> rue;
    
    private ResultSet rsClient;//Récupère la liste des clients dans la base de donées
    
    @FXML
    private UserBarController usermenuController;

    @FXML
    private void AjouterContrat(ActionEvent event) throws IOException {
        getApp().gotoPage("commercial/Contrat");
    }
    
    @FXML
    private void getClientDB() throws IOException {
        
        System.out.println(Auth.getUserInfo().toString());

        try (Connection cn = Auth.getConnection();
                Statement st = cn.createStatement()) {
            String sql = "SELECT * FROM Client";

            rsClient=st.executeQuery(sql);
            while(rsClient.next()){
                clientData.add(new Client(rsClient.getString("societe"), rsClient.getString("telephone"),rsClient.getString("email"),
                rsClient.getString("addr_ligne1"),rsClient.getString("ville"),rsClient.getString("code_postal")));
               
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Client> getClientData()  {
        
        try {
            this.getClientDB();
        } catch (IOException ex) {
             ex.printStackTrace();
        }
        
            clientData.add(new Client("4","4","4","4","4","4")) ;
        return clientData;
    }

    @FXML
    private void AjouterClient(ActionEvent event) throws IOException {
        getApp().gotoPage("commercial/Client");
    }
    @Override
    public void setApp(Main main) {
        super.setApp(main);
        usermenuController.setApp(main);
        
      clientTable.setItems(getClientData());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
        
        
        
        societe.setCellValueFactory(cellData -> cellData.getValue().societeProperty());
        rue.setCellValueFactory(cellData -> cellData.getValue().rueProperty());
    }

}
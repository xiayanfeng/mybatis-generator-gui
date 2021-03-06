package com.zzg.mybatis.generator.controller;

import com.zzg.mybatis.generator.model.DatabaseConfig;
import com.zzg.mybatis.generator.util.DbUtil;
import com.zzg.mybatis.generator.util.XMLConfigHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;

public class NewConnectionController extends BaseFXController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
//    @FXML
//    private CheckBox savePwdCheckBox;
    @FXML
    private ChoiceBox<String> encodingChoice;
    @FXML
    private ChoiceBox<String> dbTypeChoice;
    private MainUIController mainUIController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void saveConnection() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String encoding = encodingChoice.getValue();
        String dbType = dbTypeChoice.getValue();

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setHost(host);
        dbConfig.setPort(port);
        dbConfig.setDbType(dbType);
        dbConfig.setUsername(userName);
        dbConfig.setPassword(password);
//        if (savePwdCheckBox.isSelected()) {
//        }
        dbConfig.setEncoding(encoding);
        try {
            XMLConfigHelper.saveDatabaseConfig(name, dbConfig);
            getDialogStage().close();
            mainUIController.loadLeftDBTree();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO show error
        }
    }

    @FXML
    void testConnection() {
        String name = nameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String encoding = encodingChoice.getValue();
        String dbType = dbTypeChoice.getValue();
        DatabaseConfig config = new DatabaseConfig();
        config.setDbType(dbType);
        config.setHost(host);
        config.setPort(port);
        config.setUsername(userName);
        config.setPassword(password);
        config.setEncoding(encoding);
        String url = DbUtil.getConnectionUrlWithoutSchema(config);
        System.out.println(url);
        try {
            DbUtil.getConnection(config);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Connection success");
            alert.show();
        } catch (Exception e) {
            //e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Connection failed");
            alert.show();
        }

    }

    @FXML
    void cancel() {
        getDialogStage().close();
    }
    
    void setMainUIController(MainUIController controller) {
        this.mainUIController = controller;
    }

}

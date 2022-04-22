package com.example.fileencryptiontool;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class EncryptionApplicationController {
    private Stage stage;
    private Scene scene;

    private File pathOfFileToEncrypt;
    private File pathToSaveTo;


    public void initialise(Stage stage, Scene scene){
        this.stage = stage;
        this.scene = scene;

    }

    @FXML
    private Label fileToEncryptPathText;

    @FXML
    private Button encryptFileButton = new Button();


    @FXML
    protected void onOpenFileButtonCLick(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to encrypt.");
        pathOfFileToEncrypt = fileChooser.showOpenDialog(stage);

        fileToEncryptPathText.setTextFill(Color.web("#3d426b"));
        fileToEncryptPathText.setText(String.valueOf(pathOfFileToEncrypt));
        System.out.println(pathOfFileToEncrypt);

        //on success. not just right after.
        encryptFileButton.setText("Encrypt File");
        encryptFileButton.setDisable(false);

        setPasswordText.setVisible(true);
        confirmPasswordText.setVisible(true);
        setPasswordTextField.setVisible(true);
        confirmPasswordTextField.setVisible(true);
    }
    @FXML
    private Label setPasswordText;
    @FXML
    private Label confirmPasswordText;
    @FXML
    private TextField setPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    protected void opEncryptButtonClick(){

        //Check password match.
        if(setPasswordTextField.getText().equals(confirmPasswordTextField.getText())){
            //then passwords match.
            if(setPasswordTextField.getText().length() < 10){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Password Too short");
                alert.setContentText("Password must be 10 character or greater.");
                alert.showAndWait();
            }else{
                //ENCRYPT FILE WITH PASSWORD
                encryptFile(setPasswordTextField.getText());
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Passwords Do Not Match");
            alert.setContentText("Password do not match.");
            alert.showAndWait();
        }


    }

    private void encryptFile(String password){
        try {
            fileToEncryptPathText.setText("ENCRYPTING ...");
            InputStream in = new FileInputStream(pathOfFileToEncrypt);

            Menu file = new Menu("File");
            MenuItem item = new MenuItem("Save");
            file.getItems().addAll(item);
            //Creating a File chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
            //Adding action on the menu item
            pathToSaveTo = fileChooser.showSaveDialog(stage);

            OutputStream out = new FileOutputStream(pathToSaveTo);


            AESFileEncryption encryption = new AESFileEncryption();
            encryption.encrypt(256, password.toCharArray(), in, out);
            fileToEncryptPathText.setText("Done!");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (AESFileEncryption.InvalidKeyLengthException e) {
            e.printStackTrace();
        } catch (AESFileEncryption.StrongEncryptionNotAvailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
package com.friendbook;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Controller {
    @FXML
    public ListView<Friend> lstFriend = new ListView<>();
    public Label name;
    public Label phoneNum;
    public Label address;
    public Label birthday;
    public Button btnAddFriend;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtPhoneNumber;
    public TextField txtAddress;
    public TextField txtBirthday;
    public TextField txtGroup;
    public Label lblGroupList;
    public TextField txtGroupLoad;
    public Button btnLoad;
    public Label lblMessage;
    private ArrayList<TextField> info = new ArrayList<>(); //ArrayList for friend info
    public Button btnDeleteFriend;
    private ArrayList<String> badChars = new ArrayList<>();


    @FXML


    //Requires: mouse to click a friend in list
    //Modifies: this
    //Effects: shows fields of friend, enables delete button
    public void displayFriend(MouseEvent mouseEvent) {
        Friend temp;
        temp = lstFriend.getSelectionModel().getSelectedItem();
        if(temp != null){
            name.setText(temp.getFirstName()+" "+temp.getLastName());
            phoneNum.setText(temp.getPhoneNumber());
            address.setText(temp.getAddress());
            birthday.setText(temp.getBirthday());
            btnDeleteFriend.setDisable(false);
        }


    }


    //Requires:
    //Modifies: this
    //Effects: adds all of the friend info into the ArrayList, disables delete and add buttons and friend list and load button, defines unusable characters
    public void initialize(){
        btnAddFriend.setDisable(true);
        btnDeleteFriend.setDisable(true);
        lstFriend.setDisable(true);
        btnLoad.setDisable(true);

        Collections.addAll(info, txtFirstName, txtLastName, txtPhoneNumber, txtAddress, txtBirthday, txtGroup);
        Collections.addAll(badChars,  "#","%","&","{","}","\\","*","?","/","$","!", "'","\"",":","@","\t");
    }

    //Requires: delete button to be clicked
    //Modifies: this
    //Effects: removes friend from list and text file, resets display, disables list if no friends
    public void deleteFriend(ActionEvent actionEvent) throws IOException {

        Friend toRemove;
        toRemove = lstFriend.getSelectionModel().getSelectedItem();
        String filename = lblGroupList.getText()+".txt";

        ArrayList<Friend> friendList;
        friendList = new ArrayList<>(lstFriend.getItems());

        friendList.remove(toRemove);
        lstFriend.getItems().clear();

        Friend.rewriteToFile(filename, friendList);

        for (Friend f : friendList){
            lstFriend.getItems().add(f);
        }

        enableList();

        name.setText("");
        phoneNum.setText("");
        address.setText("");
        birthday.setText("");

        btnDeleteFriend.setDisable(true);
        if(lstFriend.getItems().isEmpty()){
            lstFriend.setDisable(true);
        }

    }

    //Requires: add button to be pressed, group name to not include bad characters
    //Modifies: this
    //Effects: adds friend to list and text file, disables add button, clears TextFields, displays list
    public void addFriend(ActionEvent actionEvent) throws IOException {
        boolean containsWrong = false;
        for(String s : badChars){
            if (txtGroup.getText().contains(s)){
                containsWrong = true;
            }
        }

        if(!containsWrong){
            Friend temp = new Friend(txtFirstName.getText(), txtLastName.getText(), txtBirthday.getText(), txtPhoneNumber.getText(), txtAddress.getText());
            temp.writeToFile(txtGroup.getText()+".txt");



            //loading group that friend is added in
            loadGroupGeneral(txtGroup.getText()+".txt");

            //disable add button and clear
            btnAddFriend.setDisable(true);
            for(TextField information : info){
                information.clear();
            }

        } else{
            lblMessage.setText("Please name group without using: \r"+"#,%,&,{,},\\,*,?,/,$,!, ',\",:,@,\t");
        }




    }

    //Requires:
    //Modifies: this
    //Effects: enables list if it is not empty
    private void enableList(){
        if(!lstFriend.getItems().isEmpty()){
            lstFriend.setDisable(false);
        }
    }

    //Requires: any key to be typed in any TextField except for txtGroupLoad
    //Modifies: this
    //Effects: enables add button if all TextFields have text, otherwise disables
    public void keyType(KeyEvent keyEvent) {
        boolean filled = true;
        for(TextField information : info){
            if (information.getText().isEmpty()){
                filled = false;

            }
        }
        //if filled, enable, else disable
        btnAddFriend.setDisable(!filled);


    }

    //Requires: mouse to be clicked while in contact with textfields or for mouse to leave textfield (same textfields as in keyType)
    //Modifies: this
    //Effects: enables add button if all TextFields have text, otherwise disables
    public void rightClick(MouseEvent mouseEvent) {
        boolean filled = true;
        for(TextField information : info){
            if (information.getText().isEmpty()){
                filled = false;

            }
        }
        //if filled, enable, else disable
        btnAddFriend.setDisable(!filled);
    }

    //Requires: btnLoad to be pressed
    //Modifies: this
    //Effects: Loads group using loadGroupGeneral with filename specified in txtGroupLoad, clears txtGroupLoad and disables load button
    public void loadGroup(ActionEvent actionEvent) throws IOException {

        loadGroupGeneral(txtGroupLoad.getText()+".txt");
        btnLoad.setDisable(true);
        txtGroupLoad.clear();

    }

    //Requires: filename to exist as name of txt file
    //Modifies: this
    //Effects: displays Friends from specified text file on friend list, sets lblGroupList to the name of the group
    private void loadGroupGeneral(String filename) throws IOException{


        File file = new File(filename);
        System.out.println(filename);
        if (file.exists() && !file.isDirectory()){
            lstFriend.getItems().clear();
            ArrayList<Friend> friendList;
            friendList = Friend.sort(filename);

            for (Friend f : friendList){
                lstFriend.getItems().add(f);
            }
            lblGroupList.setText(filename.substring(0,filename.length()-4));


            enableList();
            lblMessage.setText("");
        } else{
            lblMessage.setText("No group found with that name");
        }


    }



    //Requires: any key to be typed in txtGroupLoad
    //Modifies: this
    //Effects: enables load button if TextField has text, otherwise disables
    public void keyTypeLoad(KeyEvent keyEvent) {
        boolean filled = true;

        if (txtGroupLoad.getText().isEmpty()){
            filled = false;

        }

        //if filled, enable, else disable
        btnLoad.setDisable(!filled);
    }

    //Requires: mouse to be clicked while in contact with txtGroupLoad or for mouse to leave it
    //Modifies: this
    //Effects: enables load button if TextField has text, otherwise disables
    public void loadClick(MouseEvent mouseEvent) {
        boolean filled = true;

        if (txtGroupLoad.getText().isEmpty()){
                filled = false;

        }

        //if filled, enable, else disable
        btnLoad.setDisable(!filled);
    }
}
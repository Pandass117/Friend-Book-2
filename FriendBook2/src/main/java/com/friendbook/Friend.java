package com.friendbook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Friend {
    private String firstName;
    private String lastName;
    private String birthday;
    private String phoneNumber;
    private String address;

    public Friend(String firstName, String lastName, String birthday, String phoneNumber, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;



    }

    //for the ListView
    public String toString(){
        return firstName+" "+lastName;
    }


    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    //Requires: filename of file
    //Modifies: this, file specified by filename
    //Effects: writes fields of own fields to the file
    public void writeToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(firstName+",\r");
        bw.write(lastName+",\r");
        bw.write(birthday+",\r");
        bw.write(phoneNumber+",\r");
        bw.write(address+"\r");
        bw.write(";\r");

        bw.close();

    }

    //Requires: filename of file, ArrayList of Friends
    //Modifies: this, file specified by filename
    //Effects: rewrites fields of friends in ArrayList to the file
    public static void rewriteToFile(String filename, ArrayList<Friend> list) throws IOException {
        FileWriter fw = new FileWriter(filename, false);
        BufferedWriter bw = new BufferedWriter(fw);


        System.out.println("List to rewrite: "+list);
        for (Friend f : list) {

            bw.write(f.firstName+",\r");
            bw.write(f.lastName+",\r");
            bw.write(f.birthday+",\r");
            bw.write(f.phoneNumber+",\r");
            bw.write(f.address+"\r");
            bw.write(";\r");
        }

        bw.close();
    }

    //Requires: friend
    //Modifies: this
    //Effects: returns true if full names of friends are the same, otherwise returns false
    public boolean compareFriends(Friend f){

        if (this.firstName.equals(f.firstName) && this.lastName.equals(f.lastName)){
            return true;
        } else return false;
    }

    //Requires: filename of file
    //Modifies: this, file specified by filename
    //Effects: removes duplicate friends in the file, returns ArrayList of friends without duplicates
    public static ArrayList<Friend> sort(String filename) throws IOException{

        ArrayList<Friend> all = CreateList.create(filename);
        ArrayList<Friend> used = new ArrayList<>();
        used.add(new Friend("", "", "", "", ""));
        ArrayList<Friend> toRemove = new ArrayList<>();

        boolean same = false;

        for (Friend f : all){
            for (Friend ff : used){
                if (f.compareFriends(ff)){
                    same = true;
                }


            }
            System.out.println(same);
            if (same){
                toRemove.add(f);
            } else{
                used.add(f);
            }
            same = false;



        }
        for (Friend f : toRemove){
            all.remove(f);
        }
        rewriteToFile(filename, all);
        return all;
    }
}

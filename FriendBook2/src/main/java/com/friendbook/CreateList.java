package com.friendbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CreateList {
    private String firstName;
    private String lastName;
    private String birthday;
    private String phoneNumber;
    private String address;

    public CreateList() {
    }

    //Requires: filename of file
    //Modifies: this
    //Effects: returns ArrayList of Friends from the file
    public static ArrayList<Friend> create(String filename) throws IOException {
        ArrayList<Friend> friends = new ArrayList();
        String friend = "";
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null) {
            if (!line.equals(";")) {
                friend = friend + line;
            } else {
                parseFriend(friends, friend);
                friend = "";
            }
        }

        return friends;
    }

    //Requires: ArrayList of Friends, String of friend with fields separated by ,
    //Modifies: this
    //Effects: parses the Friend and adds to ArrayList of friends
    private static void parseFriend(ArrayList<Friend> friends, String sFriend) {
        ArrayList<Integer> indexes = new ArrayList();
        ArrayList<String> info = new ArrayList();
        Collections.addAll(info, new String[]{"", "", "", "", ""});
        indexes.add(-1);

        String s;
        for(int i = 0; i < sFriend.length(); ++i) {
            s = sFriend.substring(i, i + 1);
            if (s.equals(",")) {
                indexes.add(i);
            }
        }

        indexes.add(sFriend.length());



        for (String information: info){
            int index = info.indexOf(information);
            info.set(index, sFriend.substring(indexes.get(index) + 1,indexes.get(index + 1)));
        }

        Friend fFriend = new Friend(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4));
        friends.add(fFriend);
    }
}
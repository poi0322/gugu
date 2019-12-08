package com.example.gugu.DataClass;

public class User {
        private String userAddress;
        private String userBirth;
        private String userName;
        private String userNick;
        private String userPhone;
        private String userSex;
        public User(){}
        public User( String address,String birth ,String name, String nick, String phone){
            this.userAddress= address;
            this.userBirth= birth;
            this.userName= name;
            this.userNick= nick;
            this.userPhone= phone;
        }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}

package com.booklink.ui.panel.menu;

class UserLoginForm {
    private String id;
    private String pw;
    private String name;


    public UserLoginForm(String id, String pw, String name) {
        setId(id);
        setPw(pw);
        setName(name);
    }
    public UserLoginForm(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof UserLoginForm)) {
            return false;
        }
        UserLoginForm temp = (UserLoginForm)o;

        return id.equals(temp.getId());
    }

    @Override
    public String toString() {
        String info = "Id: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "Name: " + name + "\n";
        return info;
    }
}
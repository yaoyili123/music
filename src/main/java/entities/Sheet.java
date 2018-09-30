package entities;

import java.io.Serializable;

public class Sheet implements Serializable, DataBean{

    private int sheetid;
    private String username;
    private String nickname;
    private String name;
    private String detail;
    private String type;
    private String image;
    private int click;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBeanType() {
        return "sheet";
    }

    public int getId() {
        return sheetid;
    }

    public int getSheetid() {
        return sheetid;
    }

    public void setSheetid(int sheetid) {
        this.sheetid = sheetid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}

package com.kosmo.a30xmljsonparser02;
//영화배우 정보를 저장하기 위한 VO객체
public class ActorVO {

    private String name;
    private String age;
    private String hobbys;
    private String login;
    //이미지를 저장하는 것은 스트링 타입이지만
    //리소스는 실질적으로 정수형으로 작동한다.
    private int profileImg;


    public ActorVO(){}

    public ActorVO(String name, String age, String hobbys, String login, int profileImg) {
        this.name = name;
        this.age = age;
        this.hobbys = hobbys;
        this.login = login;
        this.profileImg = profileImg;
    }


    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getHobbys() {
        return hobbys;
    }

    public String getLogin() {
        return login;
    }

    public int getProfileImg() {
        return profileImg;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHobbys(String hobbys) {
        this.hobbys = hobbys;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setProfileImg(int profileImg) {
        this.profileImg = profileImg;
    }
}

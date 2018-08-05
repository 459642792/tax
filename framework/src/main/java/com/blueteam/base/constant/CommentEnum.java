package com.blueteam.base.constant;

public enum CommentEnum {

    ONE(1, "非常差"), TWO(2, "较差"), THREE(3, "一般"), FOUR(4, "较好"), FIVE(5, "非常好");

    private Integer score;
    private String desc;

    CommentEnum(Integer score, String desc) {
        this.score = score;
        this.desc = desc;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDesc(Integer id){
        for (CommentEnum e:CommentEnum.values()){
            if (e.score==id){
                return e.getDesc();
            }
        }
        return "";
    }
}

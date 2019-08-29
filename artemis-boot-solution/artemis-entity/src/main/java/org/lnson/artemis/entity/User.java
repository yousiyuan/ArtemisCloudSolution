package org.lnson.artemis.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 表名默认使用类名,驼峰转下划线(只对大写字母进行处理),如 UserInfo 默认对应的表名为 user_info 。
 * 表名可以使用 @Table(name = "tableName") 进行指定,对不符合第一条默认规则的可以通过这种方式指定表名.
 */
@Table(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 注解 @Id 作为主键的字段,可以有多个 @Id 注解的字段作为联合主键
     * 如果是MySQL的自增字段，加上 @GeneratedValue(generator = "JDBC") 即可
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer userId;

    /**
     * 字段默认和 @Column 一样,都会作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式.
     * 可以使用 @Column(name = "fieldName") 指定不符合第3条规则的字段名
     */
    @Column(name = "USER_CODE")
    private String userCode;

    private String userName;

    private String password;

    private Integer gender;

    private String address;

    private Integer age;

    private Date birthday;

    private Integer status;

    private Date createDate;

    private Date updateDate;


    /**
     * 额外的字段（不属于数据库表），必须加上 @Transient 注解；使用 @Transient 注解可以忽略字段,添加该注解的字段不会作为表字段使用.
     */
    @Transient
    private String fatherName;
    /**
     * 额外的字段（不属于数据库表），必须加上 @Transient 注解；使用 @Transient 注解可以忽略字段,添加该注解的字段不会作为表字段使用.
     */
    @Transient
    private String matherName;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMatherName() {
        return matherName;
    }

    public void setMatherName(String matherName) {
        this.matherName = matherName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("userCode='" + userCode + "'")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("gender=" + gender)
                .add("address='" + address + "'")
                .add("age=" + age)
                .add("birthday=" + birthday)
                .add("status=" + status)
                .add("createDate=" + createDate)
                .add("updateDate=" + updateDate)
                .add("fatherName='" + fatherName + "'")
                .add("matherName='" + matherName + "'")
                .toString();
    }
}
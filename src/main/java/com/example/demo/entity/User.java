package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2022-02-12
 */
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String address;

    private Date createTime;

    private String avatarUrl;

    
    public Integer getId() {
        return id;
    }

      public void setId(Integer id) {
          this.id = id;
      }
    
    public String getUsername() {
        return username;
    }

      public void setUsername(String username) {
          this.username = username;
      }
    
    public String getPassword() {
        return password;
    }

      public void setPassword(String password) {
          this.password = password;
      }
    
    public String getNickname() {
        return nickname;
    }

      public void setNickname(String nickname) {
          this.nickname = nickname;
      }
    
    public String getEmail() {
        return email;
    }

      public void setEmail(String email) {
          this.email = email;
      }
    
    public String getAddress() {
        return address;
    }

      public void setAddress(String address) {
          this.address = address;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public void setCreateTime(Date createTime) {
          this.createTime = createTime;
      }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }

      public void setAvatarUrl(String avatarUrl) {
          this.avatarUrl = avatarUrl;
      }

    @Override
    public String toString() {
        return "User{" +
              "id=" + id +
                  ", username=" + username +
                  ", password=" + password +
                  ", nickname=" + nickname +
                  ", email=" + email +
                  ", address=" + address +
                  ", createTime=" + createTime +
                  ", avatarUrl=" + avatarUrl +
              "}";
    }
}

package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String backgroundColor;
    private String backgroundColorLeft;
    private String backgroundColorRight;
    private String textColor;
    @JsonProperty("isActive")
    private boolean isActive;

    public Theme() {}

    public Theme(String name, String backgroundColor, String textColor, boolean isActive) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public String getBackgroundColorLeft() { return backgroundColorLeft; }
    public void setBackgroundColorLeft(String backgroundColorLeft) { this.backgroundColorLeft = backgroundColorLeft; }
    public String getBackgroundColorRight() { return backgroundColorRight; }
    public void setBackgroundColorRight(String backgroundColorRight) { this.backgroundColorRight = backgroundColorRight; }
    public String getTextColor() { return textColor; }
    public void setTextColor(String textColor) { this.textColor = textColor; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
}

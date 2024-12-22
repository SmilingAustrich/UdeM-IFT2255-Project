package org.udem.ift2255.dto;

public class UserDTO {
    public String userType; // "resident" or "intervenant"
    public String firstName; // Changed from fullName
    public String lastName; // Added to split the name
    public String email;
    public String password;
    public String phone; // Added for residents
    public String address; // For residents
    public int age; // For residents
    public String type; // For intervenants
    public String cityId; // For intervenants
}

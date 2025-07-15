package org.example.springshop.model.dto.responsemodel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springshop.model.FavoriteItem;
import org.example.springshop.model.User;
import org.example.springshop.model.UserRole;
import java.util.List;
@Getter
@NoArgsConstructor
public class UserResponseModel {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("nationalCode")
    private String nationalCode;
    @JsonProperty("profilePicture")
    private String profilePicture;
    @JsonProperty("user_role")
    private UserRole userRole;
//    @JsonProperty("country")
//    private String country;
//    @JsonProperty("city")
//    private String city;
//    @JsonProperty("number")
//    private Long number;
//    @JsonProperty("postNumber")
//    private Long postNumber;
    @JsonProperty("favorite")
    private List<FavoriteItem> favorite;

    @Builder
    public UserResponseModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.nationalCode = user.getNationalCode();
        this.profilePicture = user.getProfilePicture();
        this.userRole = user.getRole();
//        this.country = user.getAddress().getCountry();
//        this.city = user.getAddress().getCity();
//        this.number = user.getAddress().getNumber();
//        this.postNumber = user.getAddress().getNumber();
        this.favorite = user.getFavoriteItem();
    }
}

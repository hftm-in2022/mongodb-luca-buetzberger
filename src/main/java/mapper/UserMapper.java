package mapper;
import entity.User;
import entity.UserDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId() != null ? user.getId().toString() : null);
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        return user;
    }
}
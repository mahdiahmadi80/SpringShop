import org.example.springshop.model.Address;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.responsemodel.AddressResponseModel;
import org.example.springshop.repository.AddressRepository;
import org.example.springshop.repository.UserRepository;
import org.example.springshop.service.AddressService;
import org.example.springshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AddressService addressService;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListAddress() {
//        User user = User.userBuilder()
//                .id(1L)               //برای اجرا باید all arg constractor az class User bardashte shavad
//                .name("Mahdi")
//                .lastName("Ahmadi")
//                .email("mahdi@example.com")
//                .build();
        Address address = new Address(1L, "Iran", "Tehran", 11L, 11L, user);
        Address address1 = new Address(2L, "Iran", "Tabriz", 22L, 22L, user);
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address, address1));

        List<AddressResponseModel> result = addressService.listAddress();
        assertEquals(2, result.size());
        assertEquals("Tehran", result.get(0).getCity());
        assertEquals("Tabriz", result.get(1).getCity());
    }


}

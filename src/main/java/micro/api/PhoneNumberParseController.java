package micro.api;

import micro.api.exceptions.CountryNotFound;
import micro.services.CountryCodeDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberParseController {


    @Autowired
    private CountryCodeDetectionService countryCodeDetectionService;

    @PostMapping("/phone")
    public ResponseEntity<String> submitPhoneNumber(@RequestBody String number) throws CountryNotFound {
        String countryName = countryCodeDetectionService.detectCountry(number);
        return ResponseEntity.ok(countryName);
    }

}


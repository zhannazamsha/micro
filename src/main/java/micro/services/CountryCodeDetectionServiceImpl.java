package micro.services;

import org.springframework.stereotype.Service;

@Service
public class CountryCodeDetectionServiceImpl implements CountryCodeDetectionService {
    @Override
    public String detectCountry(String number) {
        return "Latvia" + CountryCodesHTMLParsingServiceImpl.COUNTRY_CODE_MAP.get("Latvia");
    }
}

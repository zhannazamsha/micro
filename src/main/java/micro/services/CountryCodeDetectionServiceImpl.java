package micro.services;

import micro.api.exceptions.CountryNotFound;
import micro.api.exceptions.WrongPhoneNumberFormat;
import micro.domain.CountryCode;
import micro.helpers.CodeFormattingHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryCodeDetectionServiceImpl implements CountryCodeDetectionService {
    @Override
    public String detectCountry(String number) {
        final String formattedNumber = CodeFormattingHelper.removeSpaces(number);
        validateNumberFormat(formattedNumber);
        int[] extractFirstTwoDigits = CodeFormattingHelper.extractFirstTwoNumbersFromCode(formattedNumber);
        List<CountryCode> matchedCountries = CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[extractFirstTwoDigits[0]][extractFirstTwoDigits[1]];
        //in case code have one number
        matchedCountries.addAll(CountryCodesHTMLParsingServiceImpl.CODE_MATRIX[extractFirstTwoDigits[0]][0]);
        for (CountryCode item : matchedCountries) {
            if (formattedNumber.startsWith(item.getCode())) {
                return item.getCountryName();
            }
        }
        throw new CountryNotFound("Country not found");
    }

    private void validateNumberFormat(String number) {
        if (!number.substring(0, 1).equals("+")) {
            throw new WrongPhoneNumberFormat("Phone number should start with +");
        }
        if (number.length() < 5) {
            throw new WrongPhoneNumberFormat("Phone number isa too small");
        }
        if (!number.replace("+", "").matches("[0-9]+")) {
            throw new WrongPhoneNumberFormat("Phone number isa too small");
        }
    }
}

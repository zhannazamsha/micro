package micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryCode implements Serializable {

    private String code;
    private String countryName;


    public static Comparator codeLengthSort = new Comparator<CountryCode>() {
        @Override
        public int compare(CountryCode cc1, CountryCode cc2) {
            if (cc1.getCode().length() == cc2.getCode().length()) {
                return 0;
            }
            return cc1.getCode().length() > cc2.getCode().length() ? -1 : 1;
        }
    };

}

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
    private String shortName;


    public static Comparator codeLengthSort = new Comparator<CountryCode>() {
        @Override
        public int compare(CountryCode m1, CountryCode m2) {
            if (m1.getCode().length() == m2.getCode().length()) {
                return 0;
            }
            return m1.getCode().length() > m2.getCode().length() ? -1 : 1;
        }
    };

}

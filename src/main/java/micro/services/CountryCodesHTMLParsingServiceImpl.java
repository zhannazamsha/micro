package micro.services;

import micro.domain.CountryCode;
import micro.helpers.CodeFormattingHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class CountryCodesHTMLParsingServiceImpl implements CountryCodesHTMLParsingService {

    public static final Map<String, String> COUNTRY_CODE_MAP = new HashMap<>();
    public static final String COUNTRY_CODES_LINK = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";
    public static final String COUNTRY_CODES_HTML_TABLE_HEADER = "Alphabetical_listing_by_country_or_region";
    public static final String TO_REMOVE = "\\[notes \\d\\]";
    public static final List<CountryCode>[][] CODE_MATRIX = new List[10][10];


    public void collectCountryCodes() throws IOException {
        Document doc = Jsoup.connect(COUNTRY_CODES_LINK).get();
        parseHTML(doc);
        createMatrix();
    }

    public void createMatrix() {
        COUNTRY_CODE_MAP.entrySet().stream().forEach(item -> {
            //TODO refactoring is required

            String extractFirstTwoDigits = CodeFormattingHelper
                    .extractFirstTwoNumbersFromCode(CodeFormattingHelper.removeSpaces(item.getValue()));
            int first = Integer.parseInt(String.valueOf(extractFirstTwoDigits.charAt(0)));
            int second = 0;
            if (extractFirstTwoDigits.length() > 1) {
                second = Integer.parseInt(String.valueOf(extractFirstTwoDigits.charAt(1)));
            }
            if (CODE_MATRIX[first][second] == null) {
                CODE_MATRIX[first][second] = new ArrayList<CountryCode>();
            }
            if (item.getValue().contains(",")) {
                for (String code : item.getValue().split(",")) {
                    CODE_MATRIX[first][second].add(new CountryCode().builder().countryName(item.getKey())
                            .code(CodeFormattingHelper.removeSpaces(code)).build());
                }
            } else {
                CODE_MATRIX[first][second].add(new CountryCode().builder().countryName(item.getKey())
                        .code(CodeFormattingHelper.removeSpaces(item.getValue())).build());
            }

            CODE_MATRIX[first][second].sort(CountryCode.codeLengthSort);
        });
    }

    public void parseHTML(Document doc) {
        Element header = doc.getElementById(COUNTRY_CODES_HTML_TABLE_HEADER);
        Element table = header.parent().nextElementSibling();
        Elements rows = table.select("tr");

        IntStream.range(1, rows.size()).forEach(i -> {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            Optional<String> optionalCode = Optional.ofNullable(cols.get(1).text());
            String code = optionalCode.orElse("").replaceAll(TO_REMOVE, "");
            System.out.println(cols.get(0).text() + " " + code);
            COUNTRY_CODE_MAP.put(cols.get(0).text(), code);
        });
    }
}


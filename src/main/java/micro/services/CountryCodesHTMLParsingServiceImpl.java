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
    public static final String TO_REMOVE = "\\[notes \\d\\]";
    public static final List<CountryCode>[][] CODE_MATRIX = new List[10][10];

    public void collectCountryCodes(String url, String countryCodesStartText) throws IOException {
        Document doc = Jsoup.connect(url).get();
        collectCountryCodes(doc, countryCodesStartText);
    }

    public void collectCountryCodes(Document document, String countryCodesStartText) {
        parseHTML(document, countryCodesStartText);
        createMatrix();
    }

    private void createMatrix() {
        fillWithEmptyLists();
        COUNTRY_CODE_MAP.entrySet().stream().forEach(item -> {
            String codeToAdd = CodeFormattingHelper.removeSpaces(item.getValue());
            for (String code : codeToAdd.split(",")) {
                int[] firstTwoDigits = CodeFormattingHelper
                        .extractFirstTwoNumbersFromCode(code);
                CODE_MATRIX[firstTwoDigits[0]][firstTwoDigits[1]]
                        .add(new CountryCode().builder().countryName(item.getKey())
                                .code(code).build());
                }
        });
        sortMatrixItems();
    }

    private void parseHTML(Document doc, String countryCodesStartText) {
        Element header = doc.getElementById(countryCodesStartText);
        Element table = header.parent().nextElementSibling();
        Elements rows = table.select("tr");

        IntStream.range(1, rows.size()).forEach(i -> {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            Optional<String> optionalCode = Optional.ofNullable(cols.get(1).text());
            String code = optionalCode.orElse("").replaceAll(TO_REMOVE, "");
            COUNTRY_CODE_MAP.put(cols.get(0).text(), code);
        });
    }

    private void fillWithEmptyLists() {
        IntStream.range(0, CODE_MATRIX.length).forEach(i -> {
            IntStream.range(0, CODE_MATRIX[i].length).forEach(j -> {
                CODE_MATRIX[i][j] = new ArrayList<>();
            });
        });
    }

    private void sortMatrixItems() {
        IntStream.range(0, CODE_MATRIX.length).forEach(i -> {
            IntStream.range(0, CODE_MATRIX[i].length).forEach(j -> {
                CODE_MATRIX[i][j].sort(CountryCode.codeLengthSort);
            });
        });
    }
}


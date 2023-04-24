package com.tarasiuk.soundify.helper;

import com.tarasiuk.soundify.exception.InvalidRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class ControllerHelper {

    private static final String RANGE_HEADER_REGEXP = "^\\d+-\\d+$";
    private static final int CSV_IDS_MAX_LENGTH = 199;
    private static final String CSV_IDS_PARAM_REGEXP = "^(?:\\d+,){0," + CSV_IDS_MAX_LENGTH + "}\\d+$";
    private static final String CSV_DELIMITER = ",";
    private static final String RANGE_DELIMITER = "-";

    private ControllerHelper() {
    }

    public static int[] validateAndParseRangeHeader(String rangeHeader) {
        if (Objects.isNull(rangeHeader) || !rangeHeader.matches(RANGE_HEADER_REGEXP)) {
            throw new InvalidRequestException("Range header is not valid.");
        }

        String[] rangeParts = rangeHeader.split(RANGE_DELIMITER);
        return new int[]{parseInt(rangeParts[0]), parseInt(rangeParts[1])};
    }

    public static List<Integer> validateAndParseIdsRequestParam(String ids) {
        if (!ids.matches(CSV_IDS_PARAM_REGEXP)) {
            throw new InvalidRequestException("Invalid request param 'ids'");
        }

        return Arrays.stream(ids.split(CSV_DELIMITER))
                .map(Integer::valueOf)
                .toList();
    }

    public static byte[] getByteRange(byte[] bytes, int arg1, int arg2) {
        if (arg1 < 0 || arg2 > bytes.length) {
            throw new InvalidRequestException("Invalid range parameters.");
        }
        return Arrays.copyOfRange(bytes, arg1, arg2);
    }
}

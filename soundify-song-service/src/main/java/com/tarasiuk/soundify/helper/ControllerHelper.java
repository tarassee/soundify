package com.tarasiuk.soundify.helper;

import com.tarasiuk.soundify.exception.InvalidRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerHelper {

    private static final int CSV_IDS_MAX_LENGTH = 199;
    private static final String CSV_IDS_PARAM_REGEXP = "^(?:\\d+,){0," + CSV_IDS_MAX_LENGTH + "}\\d+$";
    private static final String CSV_DELIMITER = ",";
    public static List<Integer> validateAndParseIdsRequestParam(String ids) {
        if (!ids.matches(CSV_IDS_PARAM_REGEXP)) {
            throw new InvalidRequestException("Invalid request param 'ids'");
        }

        return Arrays.stream(ids.split(CSV_DELIMITER))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}

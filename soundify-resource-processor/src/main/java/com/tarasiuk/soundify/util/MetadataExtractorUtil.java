package com.tarasiuk.soundify.util;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MetadataExtractorUtil {

    private MetadataExtractorUtil() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataExtractorUtil.class);
    private static final BodyContentHandler HANDLER = new BodyContentHandler();
    private static final Mp3Parser PARSER = new Mp3Parser();

    public static Metadata extractMetadata(byte[] audio) {
        Metadata metadata = new Metadata();
        try (InputStream stream = new ByteArrayInputStream(audio)) {
            PARSER.parse(stream, HANDLER, metadata);
        } catch (IOException | SAXException | TikaException e) {
            LOGGER.error("Failed to extract metadata from audio! Cause: {}", e.getCause().getCause().toString());
        }
        return metadata;
    }

}

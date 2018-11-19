package com.thread.excutor;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author v_pwlu 2018/11/19
 */
@Slf4j
public class MyEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        log.info("[resolveEntity] publicId = {}, systemId = {}", publicId, systemId );
        return null;
    }
}

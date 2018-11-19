package com.thread.excutor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author v_pwlu 2018/11/19
 */
@Slf4j
public class XmlMain {

    public static void main(String[] args) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // 是否支持验证, 默认为 false, 如果启用了验证, 需要设置 org.xml.sax.ErrorHandler
        // 否则会出现如下警告, 已启用验证, 但未设置 org.xml.sax.ErrorHandler, 并使用默认的 ErrorHandler
        // documentBuilderFactory.setValidating(false);

        // 是否支持 XML 命名空间, 如果使用的是 XSD 验证模式, 一般需要设置为 true, 默认为 false
        // documentBuilderFactory.setNamespaceAware(false);

        // 确定是否要忽略文件中的注释, 默认为 false
        // documentBuilderFactory.setIgnoringComments(true);

        // 确定是否要忽略元素内容中的空白, 默认为 false
        // documentBuilderFactory.setExpandEntityReferences(true);

        try {
            List<User> userList = new ArrayList<>();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new ClassPathResource("beans/Test.xml").getInputStream());
            NodeList nodeList = document.getElementsByTagName("User");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node userNode = nodeList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    User user = User.builder().myAttribute(userElement.getAttribute("myAttribute"))
                            .name(userElement.getElementsByTagName("name").item(0).getTextContent())
                            .age(userElement.getElementsByTagName("age").item(0).getTextContent())
                            .sex(userElement.getElementsByTagName("sex").item(0).getTextContent())
                            .build();
                    userList.add(user);
                }
            }
            userList.forEach(user -> log.info("[] user = {}", JSONObject.toJSONString(user)));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

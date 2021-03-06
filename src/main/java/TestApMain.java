import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TestApMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestApMain.class);
    private static String CHARSET_NAME = "utf8";


    public static void main(String[] args) {

        String elementId = "make-everything-ok-button";

//        String originalPass = "./samples/sample-0-origin.html";
        String originalPass = args[0];
        String diffPath = args[1];
//        String diffPath =  "./samples/sample-4-the-mash.html";//args[1]
//        String diffPath =  "./samples/sample-1-evil-gemini.html";//args[1]
//        String diffPath =  "./samples/sample-2-container-and-clone.html";//args[1]
//        String diffPath =  "./samples/sample-3-the-escape.html";//args[1]

        Optional<Element> buttonOpt = findElementById(new File(originalPass), elementId);

        Optional<String> stringifiedAttributesOpt = buttonOpt.map(button ->
                button.attributes().asList().stream()
                        .map(attr -> attr.getKey() + " = " + attr.getValue())
                        .collect(Collectors.joining(", "))
        );

        stringifiedAttributesOpt.ifPresent(attrs -> LOGGER.info("Target element attrs: [{}]", attrs));
        AttrsBean originalBean = getOriginalAttrBean(buttonOpt);
        findElementByAttrs(new File(diffPath), originalBean);

    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    private static AttrsBean getOriginalAttrBean(Optional<Element>  buttonOpt){
    AttrsBean attrsBean = new AttrsBean();
        attrsBean.setId(buttonOpt.get().attributes().get("id"));
        attrsBean.setCssClass(buttonOpt.get().attributes().get("class"));
        attrsBean.setTitle(buttonOpt.get().attributes().get("title"));
        attrsBean.setHref(buttonOpt.get().attributes().get("href"));
        attrsBean.setRel(buttonOpt.get().attributes().get("rel"));
        attrsBean.setOnclick(buttonOpt.get().attributes().get("onclick"));

        return attrsBean;
    }

    private static void findElementByAttrs(File diffHtmlFile, AttrsBean attrBean) {

        try {
            Document doc = Jsoup.parse(
                    diffHtmlFile,
                    CHARSET_NAME,
                    diffHtmlFile.getAbsolutePath());


            Elements elemId = doc.getElementsByAttributeValue("id", attrBean.getId());
            Elements elemCss = doc.getElementsByAttributeValue("css", attrBean.getCssClass());
            Elements elemHref = doc.getElementsByAttributeValue("href", attrBean.getHref());
            Elements elemTitle = doc.getElementsByAttributeValue("title", attrBean.getTitle());
            Elements elemRel = doc.getElementsByAttributeValue("rel", attrBean.getRel());
            Elements elementOnclick = doc.getElementsByAttributeValue("onclick", attrBean.getOnclick());

            HashSet<Element> els = new HashSet<>();
            els.addAll(elemId);
            els.addAll(elemCss);
            els.addAll(elemTitle);
            els.addAll(elemHref);
            els.addAll(elementOnclick);
            els.addAll(elemRel);

            elemId.clear();
            elemId.addAll(els);

            for (Iterator<Element> iter = elemId.listIterator();
                 iter.hasNext();){
                Element el = iter.next();
                if(el.attributes().get("style").equals("display:none")){
                    iter.remove();
                }
            }

            if(elemId.isEmpty()){
                LOGGER.info("No elements were found");
            } else {
                getPath(elemId);
            }
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", diffHtmlFile.getAbsolutePath(), e);
        }
    }

    private static void getPath(Elements elements) {
       for (int i = 0; i < elements.size(); i++) {
            String pathElem = elements.get(i).parents().stream().map(attr -> attr.tagName()).collect(Collectors.joining(" > "));
            LOGGER.info("Path for the element [{}]", pathElem);
        }
    }
}





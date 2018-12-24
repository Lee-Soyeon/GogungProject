package lecture.mobile.final_project.ma02_20151024;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class BuildingsXmlParser {

//    xml에서 읽어들일 태그를 구분한 enum  → 정수값 등으로 구분하지 않고 가독성 높은 방식을 사용
    private enum TagType { NONE, GUNG_NUMBER, CONTENTS_INDEX, CONTENTS, EXPLANATION, IMGURL };     // 해당없음, gungNumber, contentsIndex, contents, explanation, imgUrl
//    parsing 대상인 tag를 상수로 선언
    private final static String ITEM_TAG = "list";
    private final static String GUNG_NUMBER_TAG = "gung_number";
    private final static String CONTENTS_INDEX_TAG = "detail_code";
    private final static String CONTENTS_TAG = "contents_kor";
    private final static String EXPLANATION_TAG = "explanation_kor";
    private final static String IMGURL_TAG = "imgUrl";

    private XmlPullParser parser;

    public BuildingsXmlParser() {
//        xml 파서 관련 변수들은 필요에 따라 멤버변수로 선언 후 생성자에서 초기화
//        파서 준비
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Gogung> parse(String xml) {

        ArrayList<Gogung> resultList = new ArrayList();
        Gogung dbo = null;

//        태그를 구분하기 위한 enum 변수 초기화
        TagType tagType = TagType.NONE;

        try {
            parser.setInput(new StringReader(xml));

//            태그 유형 구분 변수 준비
            int eventType = parser.getEventType();

//            parsing 수행 - for 문 또는 while 문으로 구성
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals(ITEM_TAG)) {    // 새로운 항목을 표현하는 태그를 만났을 경우 dto 객체 생성
                            dbo = new Gogung();
                        } else if (parser.getName().equals(GUNG_NUMBER_TAG)) {
                            tagType = TagType.GUNG_NUMBER;
                        } else if (parser.getName().equals(CONTENTS_INDEX_TAG)) {
                            tagType = TagType.CONTENTS_INDEX;
                        } else if (parser.getName().equals(CONTENTS_TAG)) {
                            tagType = TagType.CONTENTS;
                        } else if (parser.getName().equals(EXPLANATION_TAG)) {
                            tagType = TagType.EXPLANATION;
                        } else if (parser.getName().equals(IMGURL_TAG)) {
                            tagType = TagType.IMGURL;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(ITEM_TAG)) {
                            resultList.add(dbo);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {       // 태그의 유형에 따라 dto 에 값 저장
                            case GUNG_NUMBER:
                                dbo.setGungNumber(parser.getText());
                                break;
                            case CONTENTS_INDEX:
                                dbo.setContentsIndex(parser.getText());
                                break;
                            case CONTENTS:
                                dbo.setContents(parser.getText());
                                break;
                            case EXPLANATION:
                                String explanation = parser.getText();
                                // 읽어온 내용 문단 정리
                                explanation = explanation.replace("\n", "\n\n ");
                                explanation = explanation.replace("<br /> ", "\n\n ");
                                explanation = explanation.replace("<br />", " ");
                                dbo.setExplanation(" " + explanation);
                                break;
                            case IMGURL:
                                dbo.setImgUrl(parser.getText());
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}

package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*TODO：JSON工具包
* */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();   //转换器

    //Map转json
    public String mapToJson(Map map)
            throws JsonProcessingException {
        String json = mapper.writeValueAsString(map);
        return json;
    }

    //对象集合转json
    /*java.util.Dato -> json:long
        List、Set均转成数组
    * */
    public String ObjectsToJson(List objs)
            throws JsonProcessingException {
        String json = (mapper.writeValueAsString(objs));
        return json;
    }

    //对象转json
    public String ObjectToJson(Object obj)
        throws JsonProcessingException{
        String json = mapper.writeValueAsString(obj);
        return json;
    }

    //json转MAP
    //（1） 日期--长整型  （2）map、子对象均转换成了LinkedHashMap （3）List、Set均转成ArrayList
    public Map JsonToMap(String json)
        throws IOException{
        Map map = mapper.readValue(json, Map.class);
        return map;
    }

    //json转obj List, json必须是数组
    public List JsonToObjects(String json) throws IOException {
        List list = mapper.readValue(json, List.class);
        return list;
    }

    public static void main(String[] args) {
        String json = "{\"username\":\"fuck\",\"nickname\":\"haha\",\"password\":\"123\"}";
        String json2 = "[{\"username\":\"fuck\",\"nickname\":\"haha\",\"password\":\"123\"}]";
        JsonUtil jsonUtil = new JsonUtil();
        try {
            List<Object> objs = new ArrayList<>();
            User user1 = new User(), user2 = new User();
            objs.add(user1);
            objs.add(user2);
            String jj = jsonUtil.ObjectsToJson(objs);
//            Map map = jsonUtil.JsonToMap(json);
//            List list = jsonUtil.JsonToObjects(json2);
//            System.out.println(map);
//            System.out.println(list);
            System.out.println(jj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package backend.Controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RestController
public class GeneralNotComplexController {

    private Connection con;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{type}/add", method = RequestMethod.POST)
    public HashMap<String, String> Add(@PathVariable String type, @RequestBody HashMap<String, String> addData) {

        HashMap<String, String> responseMap = new HashMap<String, String>();

        try {
            int i = 1;
            String connectionString = "jdbc:mysql://grimore-db.cebbknh24dty.us-west-2.rds.amazonaws.com/" + type;
            String objectList = "(";
            ArrayList<String> keyList = new ArrayList<String>();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(connectionString, "grimore", "grimorepos");
            Statement stmt = con.createStatement();
            String queryString = "insert into " + type;
            ArrayList<String> objectArray = new ArrayList<String>();

            addData.forEach((key, value) -> {
                String mapper = key + ", " + "values (";
                objectArray.add(mapper);
                keyList.add(value);
            });

            for (String key: keyList) {
                objectList += key;
                if(i != keyList.size())
                {
                    objectList += "', '";
                }
                else
                {
                    objectList +=  "')";
                }
                i++;
            }

            stmt.executeUpdate(queryString);
            responseMap.put("Success: ", "New Row Added!");
        }
        catch(Exception e)
        {
            responseMap.put("Error: ", e.toString());
        }

        return responseMap;
    }


}
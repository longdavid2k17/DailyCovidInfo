package com.danger.geolocalizer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Confirmed
{
    private DataRepo dataRepo;
    List<Point> confirmedList;
    List<Point> deathsList;

    public Confirmed(DataRepo dataRepo)
    {
        this.dataRepo = dataRepo;
        confirmedList = new ArrayList<>();
        deathsList = new ArrayList<>();
    }

    public String COVID_CONFIRMED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    public String COVID_DEATHS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    @EventListener(ApplicationReadyEvent.class)
    public void get() throws IOException, InterruptedException
    {
        RestTemplate confirmedRestTemplate = new RestTemplate();
        String confirmedObjects = confirmedRestTemplate.getForObject(COVID_CONFIRMED_URL,String.class);
        StringReader confirmedStringReader = new StringReader(confirmedObjects);

        CSVParser confirmedParse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(confirmedStringReader);

        RestTemplate restTemplate = new RestTemplate();
        String deathsObjects = restTemplate.getForObject(COVID_DEATHS_URL,String.class);
        StringReader deathsStringReader = new StringReader(deathsObjects);

        CSVParser deathsParse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(deathsStringReader);

        for(CSVRecord strings : confirmedParse)
        {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String confirmedText = strings.get("4/11/20");
            confirmedList.add(new Point(lat,lon,confirmedText));
        }

        for(CSVRecord strings : deathsParse)
        {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String deathsText = strings.get("4/11/20");
            deathsList.add(new Point(lat,lon,deathsText));
        }
        for(int i=0;i<confirmedList.size();i++)
        {
            dataRepo.addPoint(new Point(confirmedList.get(i).getX(),confirmedList.get(i).getY(),"Osób zarażonych: "+confirmedList.get(i).getText()+" \nPotwierdzonych zgonów: "+deathsList.get(i).getText()));
        }
    }
}

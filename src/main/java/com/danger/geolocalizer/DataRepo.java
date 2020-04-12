package com.danger.geolocalizer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepo
{

     private List<Point> pointsList;

    public DataRepo()
    {
        this.pointsList = new ArrayList<>();
    }

    public List<Point> getPointsList()
    {
        return pointsList;
    }

    public void addPoint(Point newPoint)
    {
        this.pointsList.add(newPoint);
    }
}

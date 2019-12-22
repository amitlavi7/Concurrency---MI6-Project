package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class Reports {
     private List<Report> reports;
     private int total;

     Reports(List<Report> reports, int total){
         this.reports = reports;
         this.total = total;
     }
}

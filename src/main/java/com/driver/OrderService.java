package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addorders(Order order){
        orderRepository.addorder(order);
    }

    public void adddelpartners(String partnerid){
        orderRepository.addpartner(partnerid);
    }

    public void addorderstopartner(String orderid,String partnerid){
        orderRepository.addorderpairpartner(orderid,partnerid);
    }
    public Order getorderbyid(String orderid){
        return orderRepository.getorderbyid(orderid);
    }
    public DeliveryPartner getpartnerbyid(String partnerid){
        return orderRepository.getpartnerbyid(partnerid);
    }
    public int getnooforder(String partnerid){
        return orderRepository.numberoforders(partnerid);
    }
    public List<String> getorderlist(String partnerid){
        return orderRepository.getorderbypartnerid(partnerid);
    }

    public List<String> getallorders(){
        return orderRepository.getallorders();
    }
    public int numberofunassignedorder(){
        return orderRepository.noofunassignedorders();
    }
    public int odersleftaftergiventime(String times,String partnerid){
        String time[] = times.split(":");
        int deliveryTime = Integer.valueOf(time[0])*60 +Integer.valueOf(time[1]);
        return orderRepository.orderleftaftergiventime(deliveryTime,partnerid);
    }

    public String getLastDelieveryTime(String partnerid){
        int time = orderRepository.getlastdelieverytime(partnerid);
        String timehh = String.valueOf(time/60);
        String timemm = String.valueOf(time%60);
        if (timehh.length() < 2){
            timehh = '0' + timehh;
        }
        if (timemm.length() < 2){
            timemm = '0' + timemm;
        }
        return timehh+':'+timemm;
    }

    public void deletepartnerbyid(String partnerid){
        orderRepository.deletepartner(partnerid);
    }
    public void deleteorder(String orderid){
        orderRepository.deleteorderbyid(orderid);
    }
}

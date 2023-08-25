package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addorders(Order order){
        orderRepository.addOrder(order);
    }

    public void adddelpartners(String partnerid){
        orderRepository.addPartner(partnerid);
    }

    public void addorderstopartner(String orderid,String partnerid){
        orderRepository.addOrderPartnerPair(orderid,partnerid);
    }
    public Order getorderbyid(String orderid){
        return orderRepository.getOrderById(orderid);
    }
    public DeliveryPartner getpartnerbyid(String partnerid){
        return orderRepository.getPartnerById(partnerid);
    }
    public int getnooforder(String partnerid){
        return orderRepository.getOrderCountByPartnerId(partnerid);
    }
    public List<String> getorderlist(String partnerid){
        return orderRepository.getOrdersByPartnerId(partnerid);
    }

    public List<String> getallorders(){
        return orderRepository.getAllOrders();
    }
    public int numberofunassignedorder(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public int odersleftaftergiventime(String times,String partnerid){
        String time[] = times.split(":");
        int deliveryTime = Integer.valueOf(time[0])*60 +Integer.valueOf(time[1]);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(deliveryTime,partnerid);
    }

    public String getLastDelieveryTime(String partnerid){
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerid);
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
        orderRepository.deletePartnerById(partnerid);
    }
    public void deleteorder(String orderid){
        orderRepository.deleteOrderById(orderid);
    }
}

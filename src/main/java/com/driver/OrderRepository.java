package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    //Creating the virtual database
    HashMap<String,Order> orderdb = new HashMap<>();
    HashMap<String,DeliveryPartner> delpartnerdb = new HashMap<>();

    //When it is finalised this particular order will be delivered by this partner
    HashMap<String,String> orderpartnerdb = new HashMap<>();
    //A partner can have multiple orders to be delievered
    //partnerid and then list of order id
    HashMap<String,List<String>> partnertoordersdb = new HashMap<String, List<String>>();

    public void addorder(Order order){
            orderdb.put(order.getId(), order);
    }

    public void addpartner(String partnerid){
        delpartnerdb.put(partnerid,new DeliveryPartner(partnerid));
    }

    public void addorderpairpartner(String orderid,String partnerid){
        if(orderdb.containsKey(orderid) && delpartnerdb.containsKey(partnerid)){
            orderpartnerdb.put(orderid,partnerid);

            //Now we need to update that partnertoorder db as well

            List<String> currorders = new ArrayList<>();
            if (partnertoordersdb.containsKey(partnerid)){
                currorders = partnertoordersdb.get(partnerid);
            }
            currorders.add(orderid);
            partnertoordersdb.put(partnerid,currorders);

            //Now also we needs to increase the numbers of orders
            DeliveryPartner deliveryPartner = delpartnerdb.get(partnerid);
            deliveryPartner.setNumberOfOrders(currorders.size());
        }
    }

    public Order getorderbyid(String orderid){
        return orderdb.get(orderid);
    }

    public DeliveryPartner getpartnerbyid(String partnerid){
        return delpartnerdb.get(partnerid);
    }

    public int numberoforders(String partnerid){
        return partnertoordersdb.get(partnerid).size();
    }

    public List<String> getorderbypartnerid(String partnerid){
        List<String> orderslist = partnertoordersdb.get(partnerid);
        return orderslist;
    }
    public List<String> getallorders(){
        List<String> allorders = new ArrayList<>();
        for (String order:orderdb.keySet()){
            allorders.add(order);
        }
        return allorders;
    }

    public int noofunassignedorders(){
        return orderdb.size() - orderpartnerdb.size();
    }
    public int orderleftaftergiventime(int time,String partnerid){
        int count = 0;
        List<String> orderlist = partnertoordersdb.get(partnerid);
        for (String orders:orderlist){
            int delieverytime = orderdb.get(orders).getDeliveryTime();
            if(delieverytime > time){
                count++;
            }
        }
        return count;
    }

    public int getlastdelieverytime(String partnerid){
        int max = 0;
        List<String> orders = partnertoordersdb.get(partnerid);
        for (String order:orders){
            int curtime = orderdb.get(order).getDeliveryTime();
            max = Math.max(max,curtime);
        }
        return max;
    }

    public void deletepartner(String partnerid){
        //If we are deleting partner
        //1. We needs to remove it from partnerdb
        //2. We needs to remove it from partner to order db
        //3. We needs to removes the orders listed for them

        delpartnerdb.remove(partnerid);
        List<String> orders = partnertoordersdb.get(partnerid);
        partnertoordersdb.remove(partnerid);

        for (String orderid:orders){
            orderpartnerdb.remove(orderid);
        }
    }

    public void deleteorderbyid(String orderid){
        orderdb.remove(orderid);

        String partnerId = orderpartnerdb.get(orderid);
        orderpartnerdb.remove(orderid);

        partnertoordersdb.get(partnerId).remove(orderid);

        delpartnerdb.get(partnerId).setNumberOfOrders(partnertoordersdb.get(partnerId).size());
    }
}
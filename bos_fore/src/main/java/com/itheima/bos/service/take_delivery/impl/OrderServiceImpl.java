
package com.itheima.bos.service.take_delivery.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OrderServiceImpl", targetNamespace = "http://impl.take_delivery.service.bos.itheima.com/")
public interface OrderServiceImpl {


    /**
     * 
     * @param order
     */
    @WebMethod
    @RequestWrapper(localName = "add", targetNamespace = "http://impl.take_delivery.service.bos.itheima.com/", className = "com.itheima.bos.service.take_delivery.impl.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://impl.take_delivery.service.bos.itheima.com/", className = "com.itheima.bos.service.take_delivery.impl.AddResponse")
    public void add(
        @WebParam(name = "order", targetNamespace = "")
        Order order);

}

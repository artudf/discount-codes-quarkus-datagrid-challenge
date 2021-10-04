package com.redhat.challenge.discount;

import com.redhat.challenge.discount.model.DiscountCode;
import com.redhat.challenge.discount.model.DiscountCodeType;
import com.redhat.challenge.discount.model.GlobalDiscountCountUse;
import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Path("/discounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscountCodesResource {
    private static final Logger LOGGER = LoggerFactory.getLogger("DiscountCodesResource");

    @Inject
    @Remote("discounts")
    RemoteCache<String, DiscountCode> discounts;

    @Inject
    @Remote("discountsUse")
    RemoteCache<String, GlobalDiscountCountUse> discountsUse;


    private void beginTransiction(RemoteCache cache) throws SystemException, NotSupportedException {
        if(cache.isTransactional())
            cache.getTransactionManager().begin();
    }

    private void commitTransiction(RemoteCache cache) throws SystemException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        if(cache.isTransactional())
            cache.getTransactionManager().commit();
    }

    private void rollbackTransiction(RemoteCache cache) throws SystemException {
        if(cache.isTransactional())
            cache.getTransactionManager().rollback();
    }

    //Map<String, DiscountCode> discounts = new HashMap<>();

    @POST
    public Response create(DiscountCode discountCode) {
        try {

            if (!discounts.containsKey(discountCode.getName())) {
                beginTransiction(discounts);
                discountCode.setUsed(0);
                if (discountCode.getExpire() == null) {
                    discountCode.setExpire(10);
                }
                discounts.put(discountCode.getName(), discountCode,
                        discountCode.getExpire(), TimeUnit.SECONDS);
                commitTransiction(discounts);
                return Response.created(URI.create(discountCode.getName())).build();
            }
        }catch(Exception e){
            LOGGER.warn("Something wrong today",e);
            try {
                rollbackTransiction(discounts);
            }catch (Exception e1){
                LOGGER.warn("Something wrong today",e1);
            }
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }

        return Response.ok(URI.create(discountCode.getName())).build();
    }

    @GET
    @Path("/consume/{name}")
    public Response consume(@PathParam("name") String name) {
        DiscountCode discountCode = discounts.get(name);

        if(discountCode == null) {
            return Response.noContent().build();
        }
        try{
            beginTransiction(discountsUse);
            GlobalDiscountCountUse global = discountsUse.get(discountCode.getType().name());
            if(global==null){
                global = new GlobalDiscountCountUse(discountCode.getType(),1L);
                discountsUse.put(discountCode.getType().name(),global);
            } else {
                global.setCount(global.getCount()+1);
                discountsUse.put(discountCode.getType().name(),global);
            }
            beginTransiction(discounts);
            discountCode.setUsed(discountCode.getUsed() + 1);
            discounts.put(name, discountCode,
                    discountCode.getExpire(), TimeUnit.SECONDS);
            commitTransiction(discountsUse);
            commitTransiction(discounts);
        }catch(Exception e){
            LOGGER.warn("Something wrong today",e);
            try {
                rollbackTransiction(discountsUse);
                rollbackTransiction(discounts);
            }catch (Exception e1){
                LOGGER.warn("Something wrong today",e1);
            }
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
        return Response.ok(discountCode ).build();
    }


    @GET
    @Path("/{type}")
    public DiscountCodes getByType(@PathParam("type") DiscountCodeType type) {
        List<DiscountCode> discountCodes = discounts.values().stream().filter((code) -> code.getType() == type)
              .collect(Collectors.toList());
        GlobalDiscountCountUse global = discountsUse.get(type.name());
        long count =0;
        if(global!=null) {
            count=global.getCount();
        }
        return new DiscountCodes(discountCodes, discountCodes.size(),count);
    }

}

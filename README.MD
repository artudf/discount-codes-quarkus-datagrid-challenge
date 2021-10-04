# DEVELOPER GAMES
# CORE DREAM TEAM
# DATAGRID QUARKUS (Challenge #06)
##
Project fork:  
https://github.com/artudf/discount-codes-quarkus-datagrid-challenge  
Openshift URL:  
http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts  
Infinispan Console:  (user01/User01)  
http://rhdg-external-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/console/

*Note*
Created script to remote call to OCP deploy of the application
**discountCreatorOCP.sh**

## Added lifesapn to Cache elements
New field at the object discountCode to indicate expire in seconds
*discounts.put(discountCode.getName(), discountCode,discountCode.getExpire(), TimeUnit.SECONDS);*

## Added new GlobalDiscountCount cache object to manage the count requested
"globalUSeCount" gives the value required
Example of response:
{"totalCount":2,**"globalUSeCount":6**,"discountCodesList":[{"name":"PROMO35","amount":10,"enterprise":"MERCADONO","type":"PERCENT","used":3,"expire":10},{"name":"PROMO36","amount":15,"enterprise":"ALBATROZ","type":"PERCENT","used":0,"expire":10}]}

## Support to race condition
Caches are now created as transactional.
We add to "create" and "consume" Endpoints an transactional behavior in case the transactions are supported.




#!/bin/sh
set -e

curl -H "Content-Type: application/json" http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts -d '{ "name": "PROMO12", "amount": 20, "enterprise": "ALBACETEBANK", "type": "VALUE","expire": 10}'
curl -H "Content-Type: application/json" http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts -d '{ "name": "PROMO35", "amount": 10, "enterprise": "MERCADONO", "type": "PERCENT" }'
curl -H "Content-Type: application/json" http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts -d '{ "name": "PROMO36", "amount": 15, "enterprise": "ALBATROZ", "type": "PERCENT" }'
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/consume/PROMO12
echo ""
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/consume/PROMO35
echo ""
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/consume/PROMO35
echo ""
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/consume/PROMO35
echo ""
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/VALUE
echo ""
curl http://datagridch6-user2-challenge6.apps.cluster-kc2df.kc2df.sandbox790.opentlc.com/discounts/PERCENT
echo ""

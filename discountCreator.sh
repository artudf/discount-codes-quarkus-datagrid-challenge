#!/bin/sh
set -e

curl -H "Content-Type: application/json" http://localhost:8081/discounts -d '{ "name": "PROMO12", "amount": 20, "enterprise": "ALBACETEBANK", "type": "VALUE","expire": 10}'
curl -H "Content-Type: application/json" http://localhost:8081/discounts -d '{ "name": "PROMO35", "amount": 10, "enterprise": "MERCADONO", "type": "PERCENT" }'
curl -H "Content-Type: application/json" http://localhost:8081/discounts -d '{ "name": "PROMO36", "amount": 15, "enterprise": "ALBATROZ", "type": "PERCENT" }'
curl http://localhost:8081/discounts/consume/PROMO12
echo ""
curl http://localhost:8081/discounts/consume/PROMO35
echo ""
curl http://localhost:8081/discounts/consume/PROMO35
echo ""
curl http://localhost:8081/discounts/consume/PROMO35
echo ""
curl http://localhost:8081/discounts/VALUE
echo ""
curl http://localhost:8081/discounts/PERCENT
echo ""
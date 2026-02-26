TOKEN=$1
ENDPOINT=$2
BASE_URL="http://localhost:8080"

RESPONSE=$(curl -s -X GET "$BASE_URL/$ENDPOINT" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json" | jq .)
echo "$RESPONSE"

USERNAME=$1 #EMAIL
DISPLAY_NAME=$2
PASSWORD=$3
URL="http://localhost:8080/sign-up"

if [ -z "$USERNAME" ] || [ -z "$DISPLAY_NAME" ] || [ -z "$PASSWORD" ]; then
  echo "Missing argument: $0 <username> <displayName> <password>"
  exit 1
fi
echo "=== SIGN UP ==="
echo "Creating user: $DISPLAY_NAME($USERNAME)"

SIGNUP_RESPONSE=$(jq -n \
  --arg dn "$DISPLAY_NAME" \
  --arg us "$USERNAME" \
  --arg ps "$PASSWORD" \
  '{displayName: $dn, username: $us, password: $ps}' |
  curl -s -X POST "$URL" \
    -H "Content-Type: application/json" \
    -d @-)
echo "$SIGNUP_RESPONSE"

echo ""

USERNAME=$1 #EMAIL
PASSWORD=$2
TOKEN_FILE=$3
URL="http://localhost:8080/login"

if [ -z "$USERNAME" ] || [ -z "$PASSWORD" ] || [ -z "$TOKEN_FILE" ]; then
  echo "MISSING ARGUMENT: $0 <email> <password> <token_file_name>"
  exit 1
fi

echo "=== LOGIN ==="
echo "Logging in... $USERNAME..."

LOGIN_RESPONSE=$(jq -n \
  --arg us "$USERNAME" \
  --arg ps "$PASSWORD" \
  '{username: $us, password: $ps}' |
  curl -s -X POST "$URL" \
    -H "Content-Type: application/json" \
    -d @-)

TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.token')

if [ -n "$TOKEN" ] && [ "$TOKEN" != "null" ]; then
  echo "User has been logged in"
  echo "$TOKEN" >".$TOKEN_FILE"
  echo "Token saved in $TOKEN_FILE"
else
  echo "error login in"
fi

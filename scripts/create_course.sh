TOKEN=$1
COURSE_CODE=$2
COURSE_NAME=$3
URL="http://localhost:8080/courses"

CREATION_RESPONSE=$(
  jq -n \
    --arg cc "$COURSE_CODE" \
    --arg cn "$COURSE_NAME" \
    '{courseCode: $cc, courseName: $cn}' |
    curl -s -v -X POST "$URL" \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer $TOKEN" \
      -d @-
)

echo "$CREATION_RESPONSE"

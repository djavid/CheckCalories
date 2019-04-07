#/bin/bash
GRADLE="./gradlew"
$GRADLE assembleDebug
DEBUG_FILE="${CIRCLE_ARTIFACTS}/checkit-debug-${CIRCLE_BUILD_NUM}-${CIRCLE_SHA1}.apk"
cp ./app/build/outputs/apk/app-release.apk ${DEBUG_FILE}
#/bin/bash
./gradlew assembleDebug
DEBUG_FILE="${CIRCLE_ARTIFACTS}/checkit-debug-${CIRCLE_BUILD_NUM}-${CIRCLE_SHA1}.apk"
cp ./app/build/outputs/apk/app-release.apk ${CIRCLE_ARTIFACTS}/checkit-debug-${CIRCLE_BUILD_NUM}-${CIRCLE_SHA1}.apk
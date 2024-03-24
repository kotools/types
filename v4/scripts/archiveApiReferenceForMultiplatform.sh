#!/bin/sh

# This script regenerates the API reference of an old version and archives it.
# It should be called from the root project!

PROJECT_VERSION=$1

BLUE='\033[0;36m'
GREEN='\033[0;32m'
NO_COLOR='\033[0m'

echo "${BLUE}> Get source code of v$PROJECT_VERSION.${NO_COLOR}"
rm -r src
git checkout "$PROJECT_VERSION" -- src

echo "${BLUE}> Generate API reference.${NO_COLOR}"
./gradlew -Pversion="$PROJECT_VERSION" -q :clean :dokkaHtml
git reset --hard -q

echo "${BLUE}> Archive API reference.${NO_COLOR}"
git checkout -q api-reference
cp -a "api/references/$PROJECT_VERSION" versions
git add "versions/$PROJECT_VERSION"
git diff-index --quiet HEAD || \
git commit -qS -m "📝 Archive API reference of v$PROJECT_VERSION"
git checkout -q -

echo "${BLUE}> Clean project.${NO_COLOR}"
rm -r "api/references/$PROJECT_VERSION"

echo "${GREEN}> SUCCESS${NO_COLOR}"

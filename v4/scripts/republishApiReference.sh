#!/bin/sh

# This script republishes the API reference of Kotools Types corresponding to
# the specified version.
# It should be called from the root project!

PROJECT_VERSION=$1

BLUE='\033[0;36m'
GREEN='\033[0;32m'
NO_COLOR='\033[0m'

echo "${BLUE}> Synchronize Gradle${NO_COLOR}"
./gradlew -q :unit

echo "${BLUE}> Get source code of version $PROJECT_VERSION${NO_COLOR}"
git checkout "$PROJECT_VERSION" -- src

echo "${BLUE}> Get archived versions of API reference${NO_COLOR}"
git checkout -q api-reference -- versions
mkdir api/references
git mv versions/* api/references
rm -rf versions

echo "${BLUE}> Generate API reference for v$PROJECT_VERSION${NO_COLOR}"
./gradlew -Pversion="$PROJECT_VERSION" -q :clean :dokkaHtml
git reset --hard -q

echo "${BLUE}> Republish API reference of v$PROJECT_VERSION${NO_COLOR}"
git checkout -q api-reference
mkdir docs-backup
mv docs/CNAME docs-backup
git rm -qr docs
mkdir docs
cp -a build/dokka/. docs
mv docs-backup/CNAME docs
rm -r docs-backup
git add docs/
git diff-index --quiet HEAD || \
git commit -qS -m "📝 Update API reference of v$PROJECT_VERSION"
git checkout -q -

echo "${GREEN}> SUCCESS${NO_COLOR}"

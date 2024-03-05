#!/bin/sh

# This script prepares the publication of the API reference of Kotools Types.
# It should be called from the root project!
# It accepts an argument for publishing the API reference on another branch than
# 'api-reference', which is the default branch used if no argument is specified.
# IMPORTANT: Make sure that the specified branch exists in the Git repository!

BLUE='\033[0;36m'
GREEN='\033[0;32m'
NO_COLOR='\033[0m'

BRANCH_TARGET=$([ -n "$1" ] && echo "$1" || echo "api-reference")
echo "$BLUE> Branch target:$NO_COLOR $BRANCH_TARGET"

echo "${BLUE}> Synchronize Gradle${NO_COLOR}"
./gradlew -q :unit

PROJECT_VERSION=$(./gradlew -q :version)
echo "${BLUE}> Project version:${NO_COLOR} $PROJECT_VERSION"

echo "${BLUE}> Get archived versions of API reference${NO_COLOR}"
SOURCE_FOLDER="versions"
git checkout -q api-reference -- $SOURCE_FOLDER

echo "${BLUE}> Prepare generation of latest API reference${NO_COLOR}"
OLD_VERSIONS_DIR="api/references"
mkdir $OLD_VERSIONS_DIR
git mv $SOURCE_FOLDER/* $OLD_VERSIONS_DIR
rm -rf $SOURCE_FOLDER

echo "${BLUE}> Generate latest API reference${NO_COLOR}"
./gradlew -q :assembleApiReferenceForWebsite

echo "${BLUE}> Archive latest API reference${NO_COLOR}"
git checkout -q "$BRANCH_TARGET"
git rm -qr $SOURCE_FOLDER
mkdir $SOURCE_FOLDER
mv $OLD_VERSIONS_DIR/* $SOURCE_FOLDER
git rm -qr $OLD_VERSIONS_DIR
git add $SOURCE_FOLDER
git diff-index --quiet HEAD || \
git commit -qS -m "📝 Archive API reference of v$PROJECT_VERSION"

echo "${BLUE}> Publish latest API reference${NO_COLOR}"
mkdir docs-backup
mv docs/CNAME docs-backup
git rm -qr docs
mkdir docs
cp -a build/dokka/. docs
mv docs-backup/CNAME docs
rm -r docs-backup
git add docs/
git diff-index --quiet HEAD || \
git commit -qS -m "🚀 Publish API reference of v$PROJECT_VERSION"
git checkout -q -

echo "${BLUE}> Clean temporary files${NO_COLOR}"
rm -r $OLD_VERSIONS_DIR

echo "${GREEN}> SUCCESS${NO_COLOR}"

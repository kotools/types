#!/bin/sh

# Copyright 2023 Loïc Lamarque.
# Use of this source code is governed by the MIT license.

# This script prepares the publication of the API reference of Kotools Types.
# It should be called from the root project!

BLUE='\033[0;36m'
GREEN='\033[0;32m'
NO_COLOR='\033[0m'

echo "${BLUE}> Synchronize Gradle${NO_COLOR}"
./gradlew -q :projects --dry-run

PROJECT_VERSION=$(./gradlew -q :version)
echo "${BLUE}> Project version:${NO_COLOR} $PROJECT_VERSION"

echo "${BLUE}> Get archived versions of API reference${NO_COLOR}"
BRANCH_TARGET="api-reference"
SOURCE_FOLDER="versions"
git checkout -q $BRANCH_TARGET -- $SOURCE_FOLDER

echo "${BLUE}> Prepare generation of latest API reference${NO_COLOR}"
OLD_VERSIONS_DIR="api/references"
mkdir $OLD_VERSIONS_DIR
git mv $SOURCE_FOLDER/* $OLD_VERSIONS_DIR
rm -rf $SOURCE_FOLDER

echo "${BLUE}> Generate latest API reference${NO_COLOR}"
./gradlew -q :dokkaHtml

echo "${BLUE}> Archive latest API reference${NO_COLOR}"
git checkout -q $BRANCH_TARGET
git rm -qr $SOURCE_FOLDER
mkdir $SOURCE_FOLDER
mv $OLD_VERSIONS_DIR/* $SOURCE_FOLDER
git rm -qr $OLD_VERSIONS_DIR
git add $SOURCE_FOLDER
git diff-index --quiet HEAD || \
git commit -qS -m "docs: archive API reference of v$PROJECT_VERSION"

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
git commit -qS -m "docs: publish API reference of v$PROJECT_VERSION"
git checkout -q -

echo "${GREEN}> SUCCESS${NO_COLOR}"

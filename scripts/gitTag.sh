#!/bin/sh

# This script creates a Git annotated tag using the project's version specified.

BLUE='\033[0;36m'
GREEN='\033[0;32m'
NO_COLOR='\033[0m'

echo "${BLUE}> Synchronize Gradle${NO_COLOR}"
./gradlew -q :unit

PROJECT_VERSION=$(./gradlew -q :version)
echo "${BLUE}> Project version:${NO_COLOR} $PROJECT_VERSION"

git tag "$PROJECT_VERSION" -s -m "🔖 Kotools Types $PROJECT_VERSION" \
  && echo "${GREEN}> Successfully created tag${NO_COLOR}"

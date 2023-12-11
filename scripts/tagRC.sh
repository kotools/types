#!/bin/sh

# Copyright 2023 Kotools S.A.S.U.
# Use of this source code is governed by the MIT license.

# This scripts tags a Release Candidate (RC) of Kotools Types.

TAG_NAME=$1
REGEX="^[0-9]\.[0-9]\.[0-9]-RC[0-9][0-9]*$"
[ -z "$(echo "$TAG_NAME" | sed "s/$REGEX//")" ] \
  || (echo "> Invalid input: $TAG_NAME" \
    && echo "> Tag name should match the following regular expression: $REGEX" \
    && exit 1)

RC_PREFIX="Release candidate"
RC_NUMBER=$(echo "$1" | sed "s/[0-9]\(\.[0-9]\)*-RC//")
RC_DESCRIPTION=$([ -z "$RC_NUMBER" ] \
  && echo "$RC_PREFIX" \
  || echo "$RC_PREFIX number $RC_NUMBER")

VERSION=$(echo "$1" | sed "s/-RC[0-9]*//")

git tag "$TAG_NAME" -s \
  -m "$RC_DESCRIPTION of Kotools Types $VERSION." && \
echo "> Successfully created tag $TAG_NAME." && \

# The following commands are useful for pushing the new tag and viewing tags in
# the browser.
# But the URL should be provided from the command 'git remote get-url origin'.

# git push --quiet origin "$TAG_NAME" && \
# open https://github.com/kotools/types/tags

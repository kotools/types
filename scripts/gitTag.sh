#!/bin/sh

# This scripts tags a stable release of Kotools Types.

TAG_NAME=$1
git tag "$TAG_NAME" -s -m "Stable release of Kotools Types $TAG_NAME." && \
git push origin "$TAG_NAME" -f
